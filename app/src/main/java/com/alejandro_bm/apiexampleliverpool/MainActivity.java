package com.alejandro_bm.apiexampleliverpool;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alejandro_bm.apiexampleliverpool.ApiLiverpool.WsLiverpool;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WsLiverpool.OnRequestSuccessListener, View.OnClickListener{

    WsLiverpool wsLiverpool;
    TextInputLayout ti_main_producto;
    EditText et_main_producto;
    Button bt_main_search;
    RecyclerView rv_main_producto;

    private List<Producto> productos;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ti_main_producto = (TextInputLayout) findViewById(R.id.ti_main_producto);
        et_main_producto = (EditText) findViewById(R.id.et_main_producto);
        bt_main_search   = (Button) findViewById(R.id.but_main_search);
        rv_main_producto = (RecyclerView) findViewById(R.id.rv_main_producto);

        bt_main_search.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }

    @Override
    public void onSuccess(JsonObject response) {
        productos = new ArrayList<>();
        JsonObject contents = response.getAsJsonArray("contents").get(0).getAsJsonObject().getAsJsonArray("mainContent").get(3).getAsJsonObject().getAsJsonArray("contents").get(0).getAsJsonObject();
        for(int i = 0; i<contents.getAsJsonArray("records").size();i++){
            JsonObject records = contents.getAsJsonArray("records").get(i).getAsJsonObject();
            productos.add(new Producto(records.getAsJsonObject().getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("product.displayName").get(0).getAsString().toString(),
                                       records.getAsJsonObject().getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("sku.sale_Price").get(0).getAsString().toString(),
                                       "Polanco",
                                       records.getAsJsonObject().getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("sku.thumbnailImage").get(0).getAsString().toString()));
        }


        //Log.d(getClass().getName(), " :p " + response.getAsJsonObject("contents").getAsJsonArray("mainContent").get(0).getAsJsonObject());

        ItemAdapterProdcutos itemAdapterGoals = new ItemAdapterProdcutos(productos,this);
        rv_main_producto.setLayoutManager(linearLayoutManager);
        rv_main_producto.setAdapter(itemAdapterGoals);
        itemAdapterGoals.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {

        if(et_main_producto.getText().length() == 0 || et_main_producto.getText().toString().matches(" ")){
            ti_main_producto.setError("Introduzca el producto a buscar.");
        }else{
            ti_main_producto.setError(null);
            wsLiverpool = new WsLiverpool(this);
            wsLiverpool.setSuccessListener(this);
            wsLiverpool.sendRequest(et_main_producto.getText().toString(),"json");
        }


    }
}
