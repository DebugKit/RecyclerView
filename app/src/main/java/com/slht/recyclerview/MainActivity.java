package com.slht.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyItemTouchCallback.OnDragListener {

    private RecyclerView mRecyclerView;
    private List<String> mData;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mData = new ArrayList<String>();
        ArrayList<String> items = (ArrayList<String>) ACache.get(this).getAsObject("items");
        if (items != null) {
            mData.addAll(items);
        } else {
            for (int i = 'A'; i < 'z'; i++) {
                mData.add("" + (char) i);
            }
        }
        adapter = new MyAdapter(this, mData);
        mRecyclerView.setAdapter(adapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter)
                .setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(MainActivity.this, "第" + vh.getAdapterPosition() + "行单击事件", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {

                Toast.makeText(MainActivity.this, "第" + vh.getAdapterPosition() + "行长按事件", Toast.LENGTH_SHORT).show();
                if (vh.getAdapterPosition() != mData.size() - 1) {
                    itemTouchHelper.startDrag(vh);//调用此方法来进行拖拽
                    VibratorUtil.Vibrate(MainActivity.this, 70);//震动70ms
                }
            }
        });


    }

    @Override
    public void onFinishDrag() {
        ACache.get(this).put("items", (ArrayList<String>) mData);
    }
}
