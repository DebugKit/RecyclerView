package com.slht.app2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.slht.app2.R;
import com.slht.app2.Utils.ACache;
import com.slht.app2.Utils.VibratorUtil;
import com.slht.app2.adapter.MyAdapter;
import com.slht.app2.bean.Item;
import com.slht.app2.help.MyItemTouchCallback;
import com.slht.app2.help.MyItemTouchCallback2;
import com.slht.app2.help.OnRecyclerItemClickListener;
import com.slht.app2.help.OnRecyclerViewItemClickListener2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LI on 2016/5/18.
 */
public class MyListFragment extends Fragment implements MyItemTouchCallback2.onDragListener {


    private List<Item> results;
    private RecyclerView recyclerView;
    private MyAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = new ArrayList<Item>();
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(getContext()).getAsObject("item");
        if (items != null) {
            results.addAll(items);
        } else {
            for (int i = 0; i < 3; i++) {
                results.add(new Item(i * 8 + 0, "收款", R.drawable.takeout_ic_category_brand));
                results.add(new Item(i * 8 + 1, "转账", R.drawable.takeout_ic_category_flower));
                results.add(new Item(i * 8 + 2, "余额宝", R.drawable.takeout_ic_category_fruit));
                results.add(new Item(i * 8 + 3, "手机充值", R.drawable.takeout_ic_category_medicine));
                results.add(new Item(i * 8 + 4, "医疗", R.drawable.takeout_ic_category_motorcycle));
                results.add(new Item(i * 8 + 5, "彩票", R.drawable.takeout_ic_category_public));
                results.add(new Item(i * 8 + 6, "电影", R.drawable.takeout_ic_category_store));
                results.add(new Item(i * 8 + 7, "游戏", R.drawable.takeout_ic_category_sweet));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view;
        adapter = new MyAdapter(getContext(), results, R.layout.item_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback2(adapter).setOnDragListener
                (this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerViewItemClickListener2(recyclerView) {
            @Override
            public void itemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(getContext(), "点击第" + vh.getAdapterPosition() + "行", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void itemLongClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(getContext(), "长按第" + vh.getAdapterPosition() + "行", Toast.LENGTH_SHORT).show();
                if (vh.getLayoutPosition() != results.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(getActivity(),70);//震动70ms
                }
            }
        });
    }

    @Override
    public void onFinishing() {
        ACache.get(getContext()).put("item", (ArrayList<Item>) results);
    }
}
