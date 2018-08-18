package ru.maxlt.carbase;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private FirebaseAuth mAuth;
    private SharedPreferences mLoginPreferences;
    private SharedPreferences.Editor mLoginPreferencesEditor;
    private Boolean saveLogin;
    private String email, password;

    @BindView(R.id.remember_me_checkbox)
    CheckBox mRememberMeCheckbox;
    @BindView(R.id.logo_iv)
    ImageView mImageView;
    @BindView(R.id.activity_main)
    ViewGroup mViewGroup;
    @BindView(R.id.email_textedit_main)
    TextInputEditText mEmailSignIn;
    @BindView(R.id.password_textedit_main)
    TextInputEditText mPasswordSignIn;
    @BindView(R.id.email_textinputlayout_main)
    TextInputLayout mEmailInputLayout;
    @BindView(R.id.password_textinputlayout_main)
    TextInputLayout mPasswordInputLayout;
    @BindView(R.id.google_sign_in_btn)
    ImageButton mGoogleSignInButton;
    @BindView(R.id.fb_sign_in_btn)
    ImageButton mFbSignInButton;
    @BindView(R.id.sign_in_progress_bar)
    ProgressBar mSignInProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);

        //https://stackoverflow.com/questions/9370293/add-a-remember-me-checkbox
        mLoginPreferences = getSharedPreferences("mLoginPreferences", MODE_PRIVATE);
        mLoginPreferencesEditor = mLoginPreferences.edit();
        saveLogin = mLoginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            mEmailSignIn.setText(mLoginPreferences.getString("email", ""));
            mPasswordSignIn.setText(mLoginPreferences.getString("password", ""));
            mRememberMeCheckbox.setChecked(true);
        }

        Animation mLogoAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
        mLogoAnimation.setDuration(1000);
        mImageView.startAnimation(mLogoAnimation);
    }

    public void signInButtonClicked(View view) {
        hideKeyboard();
        mFbSignInButton.setVisibility(View.GONE);
        mGoogleSignInButton.setVisibility(View.GONE);
        mSignInProgressBar.setVisibility(View.VISIBLE);
        email = mEmailSignIn.getText().toString().trim();
        password = mPasswordSignIn.getText().toString().trim();


        if (!validateEmail(email)) {
            mEmailInputLayout.setErrorEnabled(true);
            mEmailInputLayout.setError("Invalid email. Please type again.");
            mSignInProgressBar.setVisibility(View.GONE);
        } else if (!validatePassword(password)) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError("Invalid password. Password should be more than 5 characters.");
            mSignInProgressBar.setVisibility(View.GONE);
        } else {
            mEmailInputLayout.setErrorEnabled(false);
            mPasswordInputLayout.setErrorEnabled(false);
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent mIntent = new Intent(MainActivity.this,
                            CarTypeSelectionActivity.class);
                    //https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(MainActivity.this,
                                    (View) mImageView,
                                    getResources().getString(R.string.logo));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(mIntent, options.toBundle());
                    } else
                        startActivity(mIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mGoogleSignInButton.setVisibility(View.VISIBLE);
        mFbSignInButton.setVisibility(View.VISIBLE);
        mSignInProgressBar.setVisibility(View.GONE);
    }

    public void onPasswordForgotten(View view) {
        Toast.makeText(getBaseContext(), "Forgot password btn_sign_in_background clicked", Toast.LENGTH_LONG).show();
    }

    public void onRememberMeChecked(View view) {
        email = mEmailSignIn.getText().toString().trim();
        password = mPasswordSignIn.getText().toString().trim();
        if (mRememberMeCheckbox.isChecked()) {
            mLoginPreferencesEditor.putBoolean("saveLogin", true);
            mLoginPreferencesEditor.putString("email", email);
            mLoginPreferencesEditor.putString("password", password);
            mLoginPreferencesEditor.commit();
        } else {
            mLoginPreferencesEditor.clear();
            mLoginPreferencesEditor.commit();
        }
    }

    public void onSignUpTextClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_up);
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 5;
    }
}
