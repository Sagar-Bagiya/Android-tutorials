package com.example.demomap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdpter extends ArrayAdapter<String> {
    private static final String TAG = "MyAdpter";
    ArrayList<String> myDataList = new ArrayList<>();
    Context context;
    public MyAdpter(@NonNull Context context, int resource, ArrayList<String> myDataList) {
        super(context, resource);
        this.context = context;
        this.myDataList = myDataList;
    }


    @Override
    public int getCount() {
        return  myDataList.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_list,parent,false);
        TextView textView = view.findViewById(R.id.text_view);
//        textView.setText(myDataList.get(position));
        return view;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return myFilter;
    }


    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> arrayList = new ArrayList<>();
//
//            if (constraint == null || constraint.length()==0)
//                    arrayList.addAll(myDataList);
            if (constraint.length()>0)
            {
                for(String data : myDataList){
                    if (data.contains(constraint.toString().toLowerCase()))
                        arrayList.add(data);
                }
            }
            else
                arrayList.add("nothing is found.....");
            filterResults.values = arrayList;
            filterResults.count = arrayList.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myDataList.clear();
            Log.d(TAG, "publishResults: "+results.count);
            if (results.count>0){
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return  (String) resultValue;
        }
    };
}
