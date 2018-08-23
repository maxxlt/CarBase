package ru.maxlt.carbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CarListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        CarTypeTabsFragment mCarTypeTabsFragment = new CarTypeTabsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.car_type_tabs,mCarTypeTabsFragment)
                .commit();
    }
}
