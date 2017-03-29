package com.alejandro_bm.apiexampleliverpool.ApiLiverpool;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RaiserAlex on 29/03/17 in hour 13.
 */

public interface LiverpoolAPI {

    @GET("/tienda?")
    Call<JsonElement> requestTienda(@Query("s") String id, @Query("d3106047a194921c01969dfdec083925") String format);

}
