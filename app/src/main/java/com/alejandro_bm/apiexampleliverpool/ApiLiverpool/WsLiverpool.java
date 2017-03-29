package com.alejandro_bm.apiexampleliverpool.ApiLiverpool;

import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RaiserAlex on 29/03/17 in hour 13.
 */

public class WsLiverpool {

    private static final String TAG = WsLiverpool.class.getSimpleName();

    protected static String baseUrl = "http://liverpool.com.mx/";

    private Call<JsonElement> response;
    private Call<ResponseBody> responseRB;
    private ProgressDialog progressDialog;
    private Context context;
    private OnRequestSuccessListener successListener;

    Retrofit        retrofit;
    LiverpoolAPI    liverpoolAPI;
    JsonParser      parser;
    JsonObject      obj;

    public interface OnRequestSuccessListener {
        void onSuccess( JsonObject response);
    }

    public WsLiverpool(Context context) {
        this.context = context;

    }

    private void showProgressDialog(boolean show,String textLoad) {

        if (show) {

            progressDialog = new ProgressDialog(context);
            progressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage(textLoad);
            progressDialog.setCancelable(false);
            progressDialog.show();

        } else if (progressDialog != null) {

            progressDialog.dismiss();

        }
    }

    public void sendRequest(String productoRequest, String params) {
        showProgressDialog(true,"Loading...");
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        liverpoolAPI = retrofit.create(LiverpoolAPI.class);

        response = liverpoolAPI.requestTienda(productoRequest,params);

        response.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                try{
                    JsonElement jsonElement = response.body();

                    if(response.errorBody() != null) {

                        String error  = response.errorBody().string();
                        Log.d(TAG, "" + error);

                        parser = new JsonParser();
                        obj = parser.parse(error).getAsJsonObject();
                        Toast.makeText(context,obj.getAsJsonObject().get("errors").getAsJsonArray().get(0).getAsJsonObject().get("detail").getAsString().toString() ,Toast.LENGTH_SHORT).show();
                        showProgressDialog(false,"Loading...");

                    }else{
                        Log.d(String.valueOf(TAG), "RESPONSE: " + jsonElement.getAsJsonObject());

                        successListener.onSuccess(jsonElement.getAsJsonObject());
                        showProgressDialog(false,"Loading...");

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    showProgressDialog(false,"Loading...");

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
                showProgressDialog(false,"Loading...");

            }
        });

    }

    public void setSuccessListener(OnRequestSuccessListener listener){
        this.successListener = listener;
    }

}