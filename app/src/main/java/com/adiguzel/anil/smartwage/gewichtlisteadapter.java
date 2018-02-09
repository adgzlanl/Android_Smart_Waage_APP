package com.adiguzel.anil.smartwage;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 04.02.2018.
 */

public class gewichtlisteadapter extends RecyclerView.Adapter<gewichtlisteadapter.ViewHolder> {

    private List<liste> listItems;
    private Context context;

    public gewichtlisteadapter(List<liste> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.combination_card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        liste listItem=listItems.get(position);
        holder.textThema.setText(listItem.getThema());
        holder.textNachricht.setText(listItem.getNachricht());
        holder.textDatum.setText(listItem.getDatum());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textThema;
        public TextView textNachricht;
        public TextView textDatum;

        public ViewHolder(View itemView) {
            super(itemView);

            textThema=(TextView) itemView.findViewById(R.id.itemThema);
            textNachricht=(TextView) itemView.findViewById(R.id.itemNachricht);
            textDatum=(TextView) itemView.findViewById(R.id.itemDatum);;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }



}
