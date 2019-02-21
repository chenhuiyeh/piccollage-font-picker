package com.piccollage.googlefontpicker;

import com.piccollage.googlefontpicker.model.GoogleWebFont;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebFontApi {
    @GET("?key=AIzaSyCcPir6N7VMkxy0Wkk2P8Dlg59ub4sY8qg")
    Call<GoogleWebFont>
        getWebFonts();
}
