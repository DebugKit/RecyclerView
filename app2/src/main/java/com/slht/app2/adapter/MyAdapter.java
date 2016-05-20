package com.slht.app2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slht.app2.R;
import com.slht.app2.bean.Item;
import com.slht.app2.help.MyItemTouchCallback;
import com.slht.app2.help.MyItemTouchCallback2;

import java.util.Collections;
import java.util.List;

/**
 * Created by LI on 2016/5/18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements MyItemTouchCallback2
        .ItemTouchAdapter {


    private Context context;
    private List<Item> items;
    private LayoutInflater inflater;
    private int resId;

    public MyAdapter(Context context, List<Item> items, int resId) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
        this.resId = resId;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(resId, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(items.get(position).getImg());
        holder.textView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition > toPosition) {//往上拖拽
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        } else {//往下拖拽
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.height = width / 4;
            itemView.setLayoutParams(lp);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
