package com.example.flatbooking.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatbooking.R;
import com.example.flatbooking.data.ApiData;
import com.example.flatbooking.models.Item;
import com.example.flatbooking.models.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnRadioButtonClickListener {

    private List<Specification> myData;
    private static Context context;
    private static final int VIEW_TYPE_ONE = 0;

    private Item apiData;


    public SpecificationAdpter(Context context, List<Specification> myData) {
        this.myData = myData;
        this.context = context;
        this.apiData = ApiData.getJsonData(context);
    }

    public SpecificationAdpter() {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_ONE;
        else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE)
            return (new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false)));
        else
            return (new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind();
        } else if (holder instanceof ItemViewHolder) {
            int adjustedPosition = position - 1;
            ((ItemViewHolder) holder).bind(myData.get(adjustedPosition));
        }
    }

    @Override
    public int getItemCount() {
        return myData.size() + 1;
    }


    //    ------------------------------------------------- call back ---------------------------------------------
    @Override
    public void onRadioButtonClicked(String id) {
        List<Specification> spList = apiData.getSpecifications();
        List<Specification> newList = new ArrayList<>();

        newList.add(myData.get(0));
        for (Specification sp : spList) {
            if (sp.getModifierId() != null) {
                if (sp.getModifierId().equals(id)) {
                    newList.add(sp);
                }
            }
        }
        replaceDataList(newList);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainHeading;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvMainHeading = itemView.findViewById(R.id.tvMainHeading);
        }

        public void bind() {
            tvMainHeading.setText(apiData.getName().get(0));
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView cardHead, cardSubHead;
        RecyclerView rvInner;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardHead = itemView.findViewById(R.id.tvCardHead);
            cardSubHead = itemView.findViewById(R.id.tvCardSubHead);
            rvInner = itemView.findViewById(R.id.rvInner);

            // Initialize views for other items
        }

        public void bind(Specification specification) {
            cardHead.setText(specification.getName().get(0));
            cardSubHead.setText("chose up to " + specification.getMax_range());

            InnerAdapter adapter = new InnerAdapter(specification.getList(), specification.getType());
            adapter.setOnRadioButtonClickListener(SpecificationAdpter.this);
            rvInner.setAdapter(adapter);
            rvInner.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        }

    }

    public void replaceDataList(List<Specification> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }

}
