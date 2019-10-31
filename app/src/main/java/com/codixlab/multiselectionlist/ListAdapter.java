package com.codixlab.multiselectionlist;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codixlab.multiselectionlist.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private Context mContext;
    private List<Inbox> list;
    private SparseBooleanArray selectedItems;
    private int selectedIndex = -1;
    private OnItemClick itemClick;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }


    public ListAdapter(Context mContext, List<Inbox> list) {
        this.mContext = mContext;
        this.list = list;
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListItemBinding bi = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.list_item, parent, false);

        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bi.from.setText(list.get(position).from);
        holder.bi.email.setText(list.get(position).email);
        holder.bi.imageLetter.setText(list.get(position).from.substring(0, 1));
        holder.bi.date.setText(list.get(position).date);
        holder.bi.lytParent.setActivated(selectedItems.get(position, false));


        holder.bi.lytParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick == null) return;
                itemClick.onItemClick(view, list.get(position), position);
            }
        });
        holder.bi.lytParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (itemClick == null) {
                    return false;
                } else {
                    itemClick.onLongPress(view, list.get(position), position);
                    return true;
                }
            }
        });
        toggleIcon(holder.bi, position);

    }

    private void toggleIcon(ListItemBinding bi, int position) {
        if (selectedItems.get(position, false)) {
            bi.lytImage.setVisibility(View.GONE);
            bi.lytChecked.setVisibility(View.VISIBLE);
            if (selectedIndex == position) selectedIndex = -1;
        } else {
            bi.lytImage.setVisibility(View.VISIBLE);
            bi.lytChecked.setVisibility(View.GONE);
            if (selectedIndex == position) selectedIndex = -1;
        }
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeItems(int position) {
        list.remove(position);
        selectedIndex = -1;


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


    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectedIndex = position;
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }


    public int selectedItemCount() {
        return selectedItems.size();
    }

    public interface OnItemClick {

        void onItemClick(View view, Inbox inbox, int position);

        void onLongPress(View view, Inbox inbox, int position);
    }
}
