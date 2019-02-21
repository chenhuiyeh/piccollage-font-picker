package com.piccollage.googlefontpicker;

import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.piccollage.googlefontpicker.model.Item;
import com.piccollage.googlefontpicker.model.GoogleWebFont;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FontAdapter.FontAdapterClickListener {

    private static final String TAG = "MainActivity";
    private Handler mHandler = null;
    private WebFontApi webFontApi;
    private GoogleWebFont googleWebFontResponse;
    List<Item> webFontList = new ArrayList<>();

    EditText edt_add_text;
    TextView txt_preview;


    RecyclerView recycler_font;
    Button btn_done;

    Typeface typefaceSelected = Typeface.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webFontApi = WebFontClient.getRetrofitInstance().create(WebFontApi.class);
        Call<GoogleWebFont> call = webFontApi.getWebFonts();
        call.enqueue(new Callback<GoogleWebFont>() {
            @Override
            public void onResponse(Call<GoogleWebFont> call, Response<GoogleWebFont> response) {
                googleWebFontResponse = response.body();
//                webFontList = googleWebFontResponse.getItems();
                Log.d(TAG, "RESPONSE_CODE " + String.valueOf(response.code()));
                loadFontData();
            }

            @Override
            public void onFailure(Call<GoogleWebFont> call, Throwable t) {
                Log.d(TAG, "onFailure: network error");
            }
        });

        txt_preview = findViewById(R.id.txt_preview);
        btn_done = findViewById(R.id.btn_done);
        edt_add_text = findViewById(R.id.edt_add_text);

        recycler_font = findViewById(R.id.recycler_font);
        recycler_font.setHasFixedSize(true);
        recycler_font.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        FontAdapter fontAdapter = new FontAdapter(this, this, webFontList);
        recycler_font.setAdapter(fontAdapter);

        setupRxListener();

    }

    private void setupRxListener() {
        RxTextView.textChanges(edt_add_text).subscribe(text->{
            txt_preview.setText(text);
        });
    }

    private void loadFontData() {
        if (googleWebFontResponse != null) {
            for (int i = 0; i < googleWebFontResponse.getItems().size(); ++i) {
                webFontList.add(googleWebFontResponse.getItems().get(i));
            }
        }
    }

    @Override
    public void onFontSelected(Item item) {
        typefaceSelected = Typeface.createFromFile(
                new File(Environment.getExternalStorageDirectory()+"/fonts", item.getFamily() + ".ttf"));
        txt_preview.setTypeface(typefaceSelected);
    }

}
