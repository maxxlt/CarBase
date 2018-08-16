package ru.maxlt.carbase;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void signInButtonClicked(View view) {
        buttonEffect(view);
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
    }

    //https://stackoverflow.com/questions/7175873/click-effect-on-button-in-android
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}
