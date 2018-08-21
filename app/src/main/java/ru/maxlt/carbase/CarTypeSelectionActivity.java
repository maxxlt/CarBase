package ru.maxlt.carbase;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeSelectionActivity extends AppCompatActivity {
    @BindView(R.id.car_type_recycler_view)
    RecyclerView mCarTypeRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_selection);
        ButterKnife.bind(this);
        CarTypeAdapter mCarTypeAdapter = new CarTypeAdapter();
        mCarTypeAdapter.setmIconList(getIconList());
        mCarTypeAdapter.setmNameList(getNameList());
        mCarTypeAdapter.notifyDataSetChanged();
        mCarTypeRV.setAdapter(mCarTypeAdapter);
    }

    private List<Integer> getIconList() {
        Resources res = getResources();
        List<Integer> mIconList = new ArrayList<>();
        mIconList.add(R.drawable.car_icon_0);
        mIconList.add(R.drawable.car_icon_1);
        mIconList.add(R.drawable.car_icon_2);
        mIconList.add(R.drawable.car_icon_3);
        mIconList.add(R.drawable.car_icon_4);
        mIconList.add(R.drawable.car_icon_5);
        mIconList.add(R.drawable.car_icon_6);
        mIconList.add(R.drawable.car_icon_7);
        mIconList.add(R.drawable.car_icon_8);
        mIconList.add(R.drawable.car_icon_9);
        return mIconList;
    }

    private List<String> getNameList() {
        Resources res = getResources();
        String[] mStringArr = res.getStringArray(R.array.carTypeList);
        List<String> mNameList = new ArrayList<>(Arrays.asList(mStringArr));
        return mNameList;
    }

    public void onLogOutCLicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent mIntent = new Intent(this,MainActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_up);
    }
}
