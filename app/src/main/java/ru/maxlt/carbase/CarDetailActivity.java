package ru.maxlt.carbase;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDetailActivity extends AppCompatActivity {

    @BindView(R.id.engineering_mechanical_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout;
    @BindView(R.id.expand_collapse_btn_1)
    ToggleButton mExpandCollapseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mExpandCollapseBtn.setPressed(false);
    }

    public void onExpandCollapseButtonClicked(View view) {

        if (mExpandCollapseBtn.isChecked()){
            mHiddenConstraintLayout.setVisibility(View.VISIBLE);
        }
        else
            mHiddenConstraintLayout.setVisibility(View.GONE);
    }
}
