package com.example.myride.adpters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myride.R;
import com.example.myride.models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;

    private static Context context;

    private static final String TAG = "CarAdapter";

    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public CarAdapter(Context context, List<Car> carList) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        Log.d(TAG, "onBindViewHolder: " + car.toString());

        holder.carImageView.setImageResource(car.getCarImage());
        holder.carNameTextView.setText(car.getCarName());
        holder.onItemSelect();

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView carImageView;

        LinearLayout myCarContainer;
        TextView carNameTextView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImageView = itemView.findViewById(R.id.carImageView);
            carNameTextView = itemView.findViewById(R.id.carNameTextView);
            myCarContainer = itemView.findViewById(R.id.myCarContainer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Drawable drawable =  context.getDrawable(R.drawable.frame_drawable);
//                    Drawable cardDrawable = itemView.findViewById(R.id.myCarContainer).getBackground();
//                    if (cardDrawable == null){
//                        itemView.findViewById(R.id.myCarContainer).setBackground(drawable);
//                    }
//                    else
//                        itemView.findViewById(R.id.myCarContainer).setBackground(null);

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedItemPosition = position;
                        notifyDataSetChanged();
                    }
                }

            });
        }

        void onItemSelect() {
            if (getAdapterPosition() == selectedItemPosition) {
                myCarContainer.setBackground(context.getDrawable(R.drawable.frame_drawable));
                carNameTextView.setTypeface(null, Typeface.BOLD);
            } else {
                myCarContainer.setBackground(null);
                carNameTextView.setTypeface(null, Typeface.NORMAL);
            }
        }

    }
}