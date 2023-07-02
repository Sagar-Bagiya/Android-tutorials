package com.example.myride;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myride.adpters.CarAdapter;
import com.example.myride.dialogs.TripDialog;
import com.example.myride.models.Car;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myride.databinding.ActivityMapsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Dialog bottomSheetDialog = null;
    boolean isOfferApply = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng india = new LatLng(22.3039, 70.8022);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions().position(india).title("Marker in Rajkot").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_point2)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india, 10f));
    }

    public void init() {

        binding.btnStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null) {
                    if (!bottomSheetDialog.isShowing())
                        bottomSheetDialog.show();
                }
            }


        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openBottomSheetPopup();
            }
        }, 3000);

    }

    @SuppressLint("MissingInflatedId")
    private void openBottomSheetPopup() {
        // Create an instance of BottomSheetDialog
        if (bottomSheetDialog == null)
            bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setCancelable(false);

        List<Car> carList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            carList.add(new Car(R.drawable.mimi_car_image, "mimi" + i));
        }
        CarAdapter carAdapter = new CarAdapter(this, carList);

        // Inflate the layout for the bottom sheet
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_layout, null);
        ((AppCompatButton) (bottomSheetView.findViewById(R.id.btnCreateTrip))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                startTrip();
            }
        });

        if (isOfferApply) {
            ((ImageView) (bottomSheetView.findViewById(R.id.imgOffers))).setImageDrawable(this.getDrawable(R.drawable.offer_selected_icon));
            ((AppCompatButton) (bottomSheetView.findViewById(R.id.btnCreateTrip))).setText("Create Trip 12 Jan, 15:56 PM");


        } else {
            ((ImageView) (bottomSheetView.findViewById(R.id.imgOffers))).setImageDrawable(this.getDrawable(R.drawable.discount_icon));

        }


        bottomSheetView.findViewById(R.id.imgOffers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
                openDialogForOffers();
            }
        });

        RecyclerView myRecyclerView = bottomSheetView.findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        myRecyclerView.setAdapter(carAdapter);

        // Set the view for the bottom sheet dialog
        bottomSheetDialog.setContentView(bottomSheetView);

        if (!bottomSheetDialog.isShowing())
            bottomSheetDialog.show();

        Window window = bottomSheetDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void startTrip() {

        TripDialog dialog1 = new TripDialog(this);

        TripDialog dialog4 = new TripDialog(this);
        dialog4.setContentView(R.layout.bottom_sheet_invoice_dialog);
        dialog4.findViewById(R.id.btnDialogCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.cancel();
            }
        });

        dialog1.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.setHeadingText("Provider is at your location");
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.setHeadingText("Your Trip has started");
                dialog1.setButtonTextWithStyle("SoS");
            }
        }, 10000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.cancel();
                dialog4.show();
            }
        }, 15000);


//        TripDialog dialog2 = new TripDialog(this);
//        dialog2.setHeadingText("Provider is at your location");
//
//        TripDialog dialog3 = new TripDialog(this);
//        dialog3.setHeadingText("Your Trip has started");
//        dialog3.setButtonTextWithStyle("SoS");
//
//        TripDialog dialog4 = new TripDialog(this);
//        dialog4.setContentView(R.layout.bottom_sheet_invoice_dialog);
//        dialog4.findViewById(R.id.btnDialogCancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog4.cancel();
//            }
//        });
//
//        dialog1.show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog1.setHeadingText("Provider is at your location");
//            }
//        }, 5000);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog1.setHeadingText("Your Trip has started");
//                dialog1.setButtonTextWithStyle("SoS");
//            }
//        }, 10000);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog1.cancel();
//                dialog4.show();
//            }
//        }, 15000 );
    }


    private void openTripDialogs(String DialogHeadingText, String btnActionText) {

        Dialog bottomSheetOfferDialog = new BottomSheetDialog(this);
        bottomSheetOfferDialog.setCancelable(false);

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_arrival_dialog, null);

        AppCompatButton btnDialogAction = bottomSheetView.findViewById(R.id.btnDialogAction);
        btnDialogAction.setText(btnActionText);

        TextView txtDialogHeading = bottomSheetView.findViewById(R.id.txtDialogHeading);
        txtDialogHeading.setText(DialogHeadingText);

        // Set the view for the bottom sheet dialog
        bottomSheetOfferDialog.setContentView(bottomSheetView);

        bottomSheetOfferDialog.show();

        Window window = bottomSheetOfferDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void openDialogForOffers() {
        Dialog bottomSheetOfferDialog = new BottomSheetDialog(this);
        bottomSheetOfferDialog.setCancelable(false);

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_offer_dialog, null);
        bottomSheetView.findViewById(R.id.btnDialogCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetOfferDialog.cancel();
                bottomSheetDialog.show();
            }
        });
        bottomSheetView.findViewById(R.id.btnApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetOfferDialog.cancel();
                isOfferApply = true;
                openBottomSheetPopup();
            }
        });

        // Set the view for the bottom sheet dialog
        bottomSheetOfferDialog.setContentView(bottomSheetView);

        bottomSheetOfferDialog.show();

        Window window = bottomSheetOfferDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}