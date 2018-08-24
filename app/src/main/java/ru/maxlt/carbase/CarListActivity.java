package ru.maxlt.carbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CarListActivity extends AppCompatActivity {
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        getCarPosition();
        CarTypeTabsFragment mCarTypeTabsFragment = new CarTypeTabsFragment();
        mCarTypeTabsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.car_type_tabs,mCarTypeTabsFragment)
                .commit();
        CarListFragment mCarListFragment = new CarListFragment();
        mCarListFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.car_list_fragment,mCarListFragment)
                .commit();
    }

    void getCarPosition(){
        Intent mIntent = getIntent();
        int car_type_position = mIntent.getIntExtra("car_type_position",0);
        bundle.putInt("car_type_position",car_type_position);
    }
}
