package ru.maxlt.carbase;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.remember_me_checkbox)
    CheckBox mRememberMeCheckbox;
    @BindView(R.id.logo_iv)
    ImageView mImageView;
    @BindView(R.id.activity_main)
    ViewGroup mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_from_left));
    }

    public void signInButtonClicked(View view) {
        Toast.makeText(getBaseContext(), "Sign in btn_sign_in_background clicked", Toast.LENGTH_LONG).show();
    }

    public void onPasswordForgotten(View view) {
        Toast.makeText(getBaseContext(), "Forgot password btn_sign_in_background clicked", Toast.LENGTH_LONG).show();
    }

    public void onRememberMeChecked(View view) {
        if (mRememberMeCheckbox.isChecked())
            Toast.makeText(getBaseContext(), "Checked", Toast.LENGTH_SHORT).show();
    }

    public void onSignUpTextClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_up);
    }
}
