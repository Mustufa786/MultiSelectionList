package com.codixlab.multiselectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codixlab.multiselectionlist.databinding.ListItemBinding;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    Context mContext;
    List<Inbox> list;

    public ListAdapter(Context mContext, List<Inbox> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListItemBinding bi = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.list_item, parent, false);

        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bi.from.setText(list.get(position).from);
        holder.bi.email.setText(list.get(position).email);
        holder.bi.imageLetter.setText(list.get(position).from.substring(0, 1));
        holder.bi.date.setText(list.get(position).date);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding bi;


        public ViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }
}
