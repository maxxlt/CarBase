package ru.maxlt.carbase;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.maxlt.carbase.data.User;
import ru.maxlt.carbase.ui.CarTypeAdapter;

public class CarTypeSelectionActivity extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    @BindView(R.id.car_type_recycler_view)
    RecyclerView mCarTypeRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_selection);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        CarTypeAdapter mCarTypeAdapter = new CarTypeAdapter(this);
        mCarTypeAdapter.setmIconList(getIconList());
        mCarTypeAdapter.setmNameList(getNameList());
        addUser(mCarTypeAdapter);
        mCarTypeAdapter.notifyDataSetChanged();
        mCarTypeRV.setAdapter(mCarTypeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void addUser(CarTypeAdapter mAdapter) {
        Intent mIntent = getIntent();
        mRef = FirebaseDatabase.getInstance().getReference("users");
        User user = new User(mIntent.getStringExtra("user_name"),mIntent.getStringExtra("user_id"));
        mRef.child(mIntent.getStringExtra("user_id")).setValue(user);
        mAdapter.setuID(user.getUser_id());
        Log.v("MyLog","user ID passed: " +user.getUser_id());
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
        mAuth.signOut();
        Intent mIntent = new Intent(this,MainActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_up);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Please use \"Log Out\" instead.",Toast.LENGTH_LONG).show();
    }
}
