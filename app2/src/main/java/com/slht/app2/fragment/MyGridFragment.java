package com.slht.app2.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slht.app2.R;
import com.slht.app2.Utils.ACache;
import com.slht.app2.Utils.VibratorUtil;
import com.slht.app2.adapter.MyAdapter;
import com.slht.app2.bean.Item;
import com.slht.app2.help.MyItemTouchCallback;
import com.slht.app2.help.MyItemTouchCallback2;
import com.slht.app2.help.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LI on 2016/5/19.
 */
public class MyGridFragment extends Fragment implements MyItemTouchCallback2.onDragListener {

    private List<Item> results;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = new ArrayList<Item>();
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(getContext()).getAsObject("items");
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
        results.remove(results.size() - 1);
        results.add(new Item(results.size(), "更多", R.drawable.takeout_ic_more));

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
        adapter = new MyAdapter(getContext(), results, R.layout.item_grid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback2(adapter).setOnDragListener
                (this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != results.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(getActivity(),70);
                }
            }
        });
    }

    @Override
    public void onFinishing() {
        ACache.get(getContext()).put("items", (ArrayList<Item>) results);
    }
}
