package com.example.recyclerview;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,DividerItemDecoration.VERTICAL));//為RecyclerView每個item畫底線
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> arrayList = new ArrayList<>();//做陣列
        for (int i =0;i<3;i++){
            arrayList.add("A"+i);
            arrayList.add("B"+i);
            arrayList.add("C"+i);
            arrayList.add("D"+i);
        }

        MyAdapter myAdapter = new MyAdapter(arrayList);
        recyclerView.setAdapter(myAdapter);

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        private ArrayList<String> arrayList;

        public MyAdapter(ArrayList<String> arrayList){
            this.arrayList = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private View parent;
            private TextView tvValue;
            private Button btDelete,btGetInfo;
            private SwipeRevealLayout swipeRevealLayout;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvValue = itemView.findViewById(R.id.textView);
                parent = itemView;
                btDelete = itemView.findViewById(R.id.button_Delete);//刪除
                btGetInfo= itemView.findViewById(R.id.button_Show);
                swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            }
        }//ViewHolder
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            viewBinderHelper.setOpenOnlyOne(true);//設置swipe只能有一個item被拉出
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));//綁定Layout
            holder.tvValue.setText(arrayList.get(position));

            holder.btGetInfo.setOnClickListener((v -> {
                Toast.makeText(MainActivity.this,arrayList.get(position),Toast.LENGTH_SHORT).show();
                holder.swipeRevealLayout.close(true);
            }));//holder.btGetInfo

            holder.btDelete.setOnClickListener((v -> {
                holder.swipeRevealLayout.close(true);
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }));//holder.btDelete
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


    }//MyAdapter
}