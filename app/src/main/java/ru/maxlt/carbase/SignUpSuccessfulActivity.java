package ru.maxlt.carbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpSuccessfulActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_successful);
    }

    public void onContinueButtonClicked(View view) {
        Intent mIntent = new Intent(this,MainActivity.class);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_up);
    }
}
