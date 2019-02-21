package com.piccollage.googlefontpicker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebFontClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.googleapis.com/webfonts/v1/webfonts/";
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
