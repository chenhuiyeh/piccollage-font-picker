package com.piccollage.googlefontpicker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.piccollage.googlefontpicker.Constants.BASE_URL;

public class WebFontClient {

    private static Retrofit retrofit;
    
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = retrofitBuilder.build();
        }
        return retrofit;
    }
}
