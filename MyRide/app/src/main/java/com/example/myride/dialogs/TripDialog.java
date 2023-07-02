package com.example.myride.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myride.R;

public class TripDialog extends Dialog {

    private static Context context;
    private View tripView;

    public TripDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        this.tripView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_arrival_dialog,null,false);
        this.setContentView(tripView);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void setHeadingText(String dialogHeadingText){
        TextView txtDialogHeading =  tripView.findViewById(R.id.txtDialogHeading);
        txtDialogHeading.setText(dialogHeadingText);
    }

    public void setButtonText(String dialogButtonText){
        AppCompatButton btnDialogAction =  tripView.findViewById(R.id.btnDialogAction);
        btnDialogAction.setText(dialogButtonText);
    }

    public void setButtonTextWithStyle(String dialogButtonText){
        AppCompatButton btnDialogAction =  tripView.findViewById(R.id.btnDialogAction);
        btnDialogAction.setText(dialogButtonText);

        ColorStateList colorStateList = ColorStateList.valueOf(context.getColor(R.color.btnColor));
        btnDialogAction.setBackgroundTintList(colorStateList);
    }

}
