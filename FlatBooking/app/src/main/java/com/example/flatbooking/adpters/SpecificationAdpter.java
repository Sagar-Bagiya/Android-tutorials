package com.example.flatbooking.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatbooking.R;
import com.example.flatbooking.data.ApiData;
import com.example.flatbooking.models.Item;
import com.example.flatbooking.models.Specification;

import java.util.List;

public class SpecificationAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Specification> myData;
    private Context context;

    private static final int VIEW_TYPE_ONE = 0;

    public SpecificationAdpter(Context context, List<Specification> myData) {
        this.myData = myData;
        this.context = context;
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
        return myData.size()+1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainHeading;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvMainHeading = itemView.findViewById(R.id.tvMainHeading);
        }

        public void bind() {
            Item data = ApiData.getJsonData(context);
            tvMainHeading.setText(data.getName().get(0));
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView cardHead, cardSubHead;
        public ItemViewHolder(View itemView) {
            super(itemView);
            cardHead = itemView.findViewById(R.id.tvCardHead);
            cardSubHead = itemView.findViewById(R.id.tvCardSubHead);

            // Initialize views for other items
        }

        public void bind(Specification specification) {
            cardHead.setText(specification.getName().get(0));
            cardSubHead.setText("chose up to " + specification.getMax_range());
        }
    }

}
