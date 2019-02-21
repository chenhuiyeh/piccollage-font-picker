package com.piccollage.googlefontpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.piccollage.googlefontpicker.model.Item;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {

    private static final String TAG = "FontAdapter";
    Context context;
    FontAdapterClickListener listner;
    List<Item> mGoogleWebFonts;
    List<String> fontList;
    List<String> typefaceUrl;
    private Handler mHandler = null;
    List<Typeface> typefaceDownloaded;

    int rowSelected = -1;

    public FontAdapter(Context context, FontAdapterClickListener listner, List<Item> googleWebFontList) {
        this.context = context;
        this.listner = listner;
        mGoogleWebFonts = googleWebFontList;
        loadAllFontsFromUrl();
    }

    private void loadAllFontsFromUrl() {
        Log.d(TAG, "loadAllFontsFromUrl: ");
        for (int i = 0; i < mGoogleWebFonts.size(); i++) {
            String family =  mGoogleWebFonts.get(i).getFamily();
            download(mGoogleWebFonts.get(i).getFiles().getRegular());
//            File file = new File("/mnt/sdcard/" + family +".ttf");
//            if (file.exists()) {
//                Typeface typeface = Typeface.createFromFile(
//                        new File(Environment.getExternalStorageDirectory(), "/" + family +".ttf"));
//                typefaceDownloaded.add(typeface);
//            } else {
//                download();
//            }
        }
    }

    private void download(String url) {
        Log.d(TAG, "download: ");
        new DownloadFileFromURL().execute(url);
    }

    // File download process from URL
    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... f_url) {
            Log.d(TAG, "doInBackground: ");
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(f_url[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // download the file
                input = connection.getInputStream();
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File (sdCard.getAbsolutePath() + "/fonts");
                dir.mkdirs();
                File file = new File(dir, f_url[0]);
                try {
                    OutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while((len=input.read(buf))>0){
                        out.write(buf,0,len);
                    }
                    out.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File sdcard = Environment.getExternalStorageDirectory();
                File dirs = new File(sdcard.getAbsolutePath()+"/fonts");

                if(dirs.exists()) {
                    File[] files = dirs.listFiles();
                    Log.d("s","files");
                }
                final Typeface typeface = Typeface.createFromFile(
                        new File(Environment.getExternalStorageDirectory()+"/fonts", f_url[0]));
                typefaceDownloaded.add(typeface);
                Log.d("a","created");

            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }



    @NonNull
    @Override
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.font_item, parent, false);

        return new FontViewHolder(itemView);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull FontViewHolder holder, int position) {
        if (rowSelected == position)
            holder.img_check.setVisibility(View.VISIBLE);
        else
            holder.img_check.setVisibility(View.INVISIBLE);

        String url = mGoogleWebFonts.get(position).getFiles().getRegular();

//        holder.txt_font_demo.setTypeface(Typeface.createFromFile(url));
        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... f_url) {
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(f_url[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();
                    }

                    // download the file
                    input = connection.getInputStream();
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File (sdCard.getAbsolutePath() + "/fonts");
                    dir.mkdirs();
                    File file = new File(dir, mGoogleWebFonts.get(position).getFamily() +".ttf");
                    try {
                        OutputStream out = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int len;
                        while((len=input.read(buf))>0){
                            out.write(buf,0,len);
                        }
                        out.close();
                        input.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    File sdcard = Environment.getExternalStorageDirectory();
                    File dirs = new File(sdcard.getAbsolutePath()+"/fonts");

                    if(dirs.exists()) {
                        File[] files = dirs.listFiles();
                        Log.d("s","files");
                    }
                    final Typeface typeface = Typeface.createFromFile(
                            new File(Environment.getExternalStorageDirectory()+"/fonts", mGoogleWebFonts.get(position).getFamily() + ".ttf"));
                    Log.d("a","created " + typeface.getStyle());
                    holder.txt_font_demo.setTypeface(typeface);

                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute(url);


        holder.txt_font_name.setText(mGoogleWebFonts.get(position).getFamily());
        Log.d(TAG, "onBindViewHolder: " + mGoogleWebFonts.get(position).getFamily());
    }

    @Override
    public int getItemCount() {
        return mGoogleWebFonts == null ? 0:mGoogleWebFonts.size();
    }

    public class FontViewHolder extends RecyclerView.ViewHolder {
        TextView txt_font_name, txt_font_demo;
        ImageView img_check;

        public FontViewHolder(View itemView) {
            super(itemView);
            txt_font_demo = itemView.findViewById(R.id.txt_font_demo);
            txt_font_name = itemView.findViewById(R.id.txt_font_name);
            img_check = itemView.findViewById(R.id.img_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // load the ttf file from font item
                    if (getAdapterPosition() != -1) {
                        listner.onFontSelected(
                                mGoogleWebFonts.get(getAdapterPosition()));
                        rowSelected = getAdapterPosition();
                        Log.d(TAG, "onClick: item clicked");
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public interface FontAdapterClickListener {
        void onFontSelected(Item fontName);
    }
}
