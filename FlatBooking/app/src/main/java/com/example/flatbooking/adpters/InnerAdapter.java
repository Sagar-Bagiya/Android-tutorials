package com.example.flatbooking.adpters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatbooking.R;
import com.example.flatbooking.data.ApiData;
import com.example.flatbooking.models.Item;
import com.example.flatbooking.models.Property;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.ViewHolder> {

    private static final String TAG = "InnerAdapter";

    private OnRadioButtonClickListener radioButtonClickListener;

    private List<Property> innerDataList;

    private int selectedPoints = 0;

    private int type;

    private Set<Integer> selectedPositions = new HashSet<>();

    private static final int MAX_SELECTION_LIMIT = 2;
    public InnerAdapter(List<Property> innerDataList, int type) {
        this.innerDataList = innerDataList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Property data = innerDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return innerDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvText,tvPrice;
        CheckBox cbInnerCard;
        RadioButton rbInnerCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvText = itemView.findViewById(R.id.tvText);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cbInnerCard = itemView.findViewById(R.id.cbInnerCard);
            rbInnerCard = itemView.findViewById(R.id.rbInnerCard);

            rbInnerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Property property = innerDataList.get(getAdapterPosition());

                    Log.d(TAG, "onClick: data is .... price"+property.getPrice()+ "\n"+".............."+property.get_id());
                    radioButtonClickListener.onRadioButtonClicked(property.get_id());

                    selectedPoints = getAdapterPosition();
                    notifyDataSetChanged();
                    rbInnerCard.setChecked(selectedPoints == getAdapterPosition());
                }
            });

        }

        public void bind(Property data){
//            tvText.setText(data.getName().get(0));
            tvPrice.setText(String.valueOf(data.getPrice()));

            if (type == 1) {
                cbInnerCard.setVisibility(View.GONE);
                rbInnerCard.setChecked(data.isIs_default_selected());
                rbInnerCard.setText(data.getName().get(0));
                rbInnerCard.setVisibility(View.VISIBLE);
                rbInnerCard.setChecked(data.isIs_default_selected());

            }
            else {
                rbInnerCard.setVisibility(View.GONE);
                cbInnerCard.setChecked(data.isIs_default_selected());
                cbInnerCard.setText(data.getName().get(0));
                cbInnerCard.setVisibility(View.VISIBLE);
                cbInnerCard.setChecked(selectedPositions.contains(getAdapterPosition()));
            }

        }

    }

    public void setOnRadioButtonClickListener(OnRadioButtonClickListener listener) {
        this.radioButtonClickListener = listener;
    }

}
