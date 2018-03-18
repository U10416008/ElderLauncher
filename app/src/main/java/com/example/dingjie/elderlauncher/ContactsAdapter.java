package com.example.dingjie.elderlauncher;

/**
 * Created by dingjie on 2018/3/18.
 */

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class ContactsAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public static interface OnRecyclerViewListener {
        void onItemClick(View v ,int position);
        boolean onItemLongClick(int position);
    }
    private OnRecyclerViewListener onRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("TAG", "onCreateViewHolder, i: " + i);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts_list_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return new ContactsViewHolder(view);
    }
    private ArrayList<Contacts> list;
    public ContactsAdapter(ArrayList<Contacts> list) {
        this.list = list;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //put item from the date list
        Log.d("TAG", "onBindViewHolder, i: " + position + ", viewHolder: " + viewHolder);
        ContactsViewHolder holder = (ContactsViewHolder) viewHolder;
        holder.position = position;
        String name = list.get(position).getName();
        holder.name.setText(name);
        Drawable image   = list.get(position).getImage();
        if(image != null) {
            holder.image.setImageDrawable(image);
        }else{
            holder.image.setImageResource(R.drawable.contacts);

        }
        holder.image.getLayoutParams().height = MainActivity.getScreenHeight()/5;
        holder.image.getLayoutParams().width = MainActivity.getScreenHeight()/5;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onClick(View v) {
        if (onRecyclerViewListener != null) {
            onRecyclerViewListener.onItemClick(v,(int)v.getTag());

        }
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        public TextView name;
        public ImageView image;
        public int position;

        //item view
        public ContactsViewHolder(View itemView) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.contacts_name);
            image = (ImageView) itemView.findViewById(R.id.contacts_images);
            rootView = itemView.findViewById(R.id.contacts_info);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(v , position);
            }
        }
        @Override
        public boolean onLongClick(View v) {
            if(null != onRecyclerViewListener){
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }

    }


}

