package com.alejandro_bm.apiexampleliverpool;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.alejandro_bm.apiexampleliverpool.ApiLiverpool.Format;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RaiserAlex on 29/03/17 in hour 15.
 */

public class ItemAdapterProdcutos extends RecyclerView.Adapter<ItemAdapterProdcutos.GoalViewHolder>{

    List<Producto> productos;
    Context context;
    private int lastPosition = -1;
    Format formatoPrecio;

    public ItemAdapterProdcutos(List<Producto> productos, Context context){
        this.productos = productos;
        this.context = context;
        formatoPrecio = new Format();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_productos_item_cardview, viewGroup, false);

        GoalViewHolder goalViewHolder = new GoalViewHolder(v);

        return goalViewHolder;
    }

    @Override
    public void onBindViewHolder(GoalViewHolder personViewHolder, final int position) {
        personViewHolder.tv_item_producto_title.setText(productos.get(position).titulo);
        personViewHolder.tv_item_producto_ubicacion.setText(productos.get(position).ubicacion);
        personViewHolder.tv_item_producto_precio.setText("$ " + formatoPrecio.formatNumberMoney(productos.get(position).precio) + " MXN");

        Picasso.with(context).load(productos.get(position).imagen).placeholder(R.drawable.progress_animation).into(personViewHolder.imv_item_producto_thumb);
        setAnimation(personViewHolder.container, position);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }




    public static class GoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView tv_item_producto_title,
                tv_item_producto_ubicacion,
                tv_item_producto_precio;

        ImageView imv_item_producto_thumb;
        //LinearLayout ll_goals_item_vest;

        FrameLayout container;

        GoalViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_prdoducto_item);

            tv_item_producto_title     = (TextView) itemView.findViewById(R.id.tv_item_producto_title);
            tv_item_producto_ubicacion = (TextView) itemView.findViewById(R.id.tv_item_producto_ubicacion);
            tv_item_producto_precio = (TextView) itemView.findViewById(R.id.tv_item_producto_precio);
            imv_item_producto_thumb = (ImageView) itemView.findViewById(R.id.imv_item_producto_thumb);

            container          = (FrameLayout) itemView.findViewById(R.id.frame_item_container);

        }

        @Override
        public void onClick(View v) {

        }
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(final View viewToAnimate, final int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
