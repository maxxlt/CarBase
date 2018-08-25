package ru.maxlt.carbase;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class CarListActivity extends AppCompatActivity implements CarTypeTabsFragment.AdapterTabClicked{
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        if (savedInstanceState == null){
            bundle = new Bundle();
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
        else {
            bundle = savedInstanceState.getBundle("car_list_bundle");
        }
    }

    void getCarPosition(){
        Intent mIntent = getIntent();
        int car_type_position = mIntent.getIntExtra("car_type_position",0);
        String uID = mIntent.getStringExtra("user_id");
        bundle.putInt("car_type_position",car_type_position);
        bundle.putString("user_id",uID);
    }

    @Override
    public void adapterTabClicked(int position) {
        bundle.remove("car_type_position");
        bundle.putInt("car_type_position",position);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle("car_list_bundle",bundle);
        super.onSaveInstanceState(outState);
    }
}
