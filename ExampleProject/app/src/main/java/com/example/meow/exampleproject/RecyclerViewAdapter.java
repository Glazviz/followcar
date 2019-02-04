package com.example.meow.exampleproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context ctx;
    ArrayList<Entry> entries;
    ArrayList<String> uuids;

    public RecyclerViewAdapter(Context ctx, ArrayList<Entry> entries, ArrayList<String> uuids) {
        this.ctx = ctx;
        this.entries = entries;
        this.uuids = uuids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.from.setText(entries.get(position).getFromCity());
        holder.to.setText(entries.get(position).getToCity());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ItemActivity.class);
                i.putExtra("uid", uuids.get(position));
                i.putExtra("from", entries.get(position).getFromCity());
                i.putExtra("to", entries.get(position).getToCity());
                i.putExtra("info", entries.get(position).getAdditionalInfo());
                i.putExtra("phone", entries.get(position).getPhone());
                i.putExtra("cost", entries.get(position).getCost());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView from;
        TextView to;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
