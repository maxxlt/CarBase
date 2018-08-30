package ru.maxlt.carbase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDetailActivity extends AppCompatActivity {

    CarDetail mCarDetail;
    CarOverview mCarOverview;
    Query mQueryOverview, mQueryDetail;
    @BindView(R.id.engineering_mechanical_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout;
    @BindView(R.id.expand_collapse_btn)
    ToggleButton mExpandCollapseBtn;
    @BindView(R.id.expand_collapse_btn_2)
    ToggleButton mExpandCollapseBtn2;
    @BindView(R.id.design_features_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout2;
    @BindView(R.id.power_tv)
    TextView mPowerTV;
    @BindView(R.id.drive_type_tv)
    TextView mDriveTypeTV;
    @BindView(R.id.mpg_tv)
    TextView mMpgTV;
    @BindView(R.id.capacity_tv)
    TextView mCapacityTV;
    @BindView(R.id.transmission_tv)
    TextView mTransmissionTV;
    @BindView(R.id.interior_color_tv)
    TextView mInteriorColorTV;
    @BindView(R.id.exterior_color_tv)
    TextView mExteriorColorTV;
    @BindView(R.id.features_tv)
    TextView mFeaturesTV;
    @BindView(R.id.msrp_tv)
    TextView mMsrpTV;
    @BindView(R.id.beaten_msrp_tv)
    TextView mBeatenMsrpTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        Intent mIntent = getIntent();
        String car_id = mIntent.getStringExtra("car_id");
        mQueryOverview = FirebaseDatabase.getInstance().getReference("car_overview").orderByChild("car_id").equalTo(car_id);
        mQueryDetail = FirebaseDatabase.getInstance().getReference("car_details").orderByChild("car_id").equalTo(car_id);
        mQueryOverview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mCarOverview = dataSnapshot.getValue(CarOverview.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("CarDetailActivity","failed to read " + databaseError.getCode());
            }
        });
        mQueryDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    mCarDetail = dataSnapshot.getValue(CarDetail.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("CarDetailActivity","failed to read " + databaseError.getCode());
            }
        });
        populateUI();
    }

    private void populateUI() {
        mPowerTV.setText(mCarDetail.getEngine());
        mDriveTypeTV.setText(mCarDetail.getDrive_type());
        mMpgTV.setText(mCarDetail.getMpg());
        mCapacityTV.setText(mCarDetail.getCapacity());
        mTransmissionTV.setText(mCarDetail.getTransmission());
        mInteriorColorTV.setText(mCarDetail.getInterior_color());
        mExteriorColorTV.setText(mCarDetail.getExterior_color());
        mFeaturesTV.setText(mCarDetail.getConvenience());
        mMsrpTV.setText(mCarDetail.getPrice_msrp());
        mBeatenMsrpTV.setText(mCarDetail.getBeaten_price_msrp());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mExpandCollapseBtn.setPressed(false);
    }

    public void onExpandCollapseButtonClicked(View view) {

        if (mExpandCollapseBtn.isChecked()) {
            mHiddenConstraintLayout.setVisibility(View.VISIBLE);
        } else
            mHiddenConstraintLayout.setVisibility(View.GONE);
    }

    public void onExpandCollapseButtonClicked2(View view) {
        if (mExpandCollapseBtn2.isChecked()) {
            mHiddenConstraintLayout2.setVisibility(View.VISIBLE);
        } else
            mHiddenConstraintLayout2.setVisibility(View.GONE);
    }

    public void onExpandCollapseButtonClicked3(View view) {
    }
}
