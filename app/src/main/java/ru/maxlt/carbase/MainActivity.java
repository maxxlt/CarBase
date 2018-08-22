package ru.maxlt.carbase;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private FirebaseAuth mAuth;
    private SharedPreferences mLoginPreferences;
    private SharedPreferences.Editor mLoginPreferencesEditor;
    private Boolean saveLogin;
    private Boolean fbLoginClicked = false;
    private String email, password;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;
    private CallbackManager mCallbackManager;

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
    LoginButton mFbSignInButton;
    @BindView(R.id.sign_in_progress_bar)
    ProgressBar mSignInProgressBar;
    @BindView(R.id.forgot_password_tv)
    TextView mForgotPasswordTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);

        //https://developers.google.com/identity/sign-in/android/sign-in
        //https://www.youtube.com/watch?v=-ywVw2O1pP8
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Connection Failed.", Toast.LENGTH_SHORT);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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

    @Override
    protected void onStart() {
        super.onStart();
        fbLoginClicked = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fbLoginClicked = false;
        LoginManager.getInstance().logOut();
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
            mGoogleSignInButton.setVisibility(View.VISIBLE);
            mFbSignInButton.setVisibility(View.VISIBLE);

        } else if (!validatePassword(password)) {
            mPasswordInputLayout.setErrorEnabled(true);
            mPasswordInputLayout.setError("Invalid password. Password should be more than ct5 characters.");
            mSignInProgressBar.setVisibility(View.GONE);
            mGoogleSignInButton.setVisibility(View.VISIBLE);
            mFbSignInButton.setVisibility(View.VISIBLE);
        } else {
            mEmailInputLayout.setErrorEnabled(false);
            mPasswordInputLayout.setErrorEnabled(false);
            signIn(email, password);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (fbLoginClicked) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else {
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    // ...
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Signed In " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            mGoogleApiClient.clearDefaultAccountAndReconnect();
                            signInAction();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }


    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signInAction();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mGoogleSignInButton.setVisibility(View.VISIBLE);
        mFbSignInButton.setVisibility(View.VISIBLE);
        mSignInProgressBar.setVisibility(View.GONE);
    }

    private void signInAction() {
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
    }

    public void onPasswordForgotten(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(MainActivity.this,
                        (View) mForgotPasswordTV,
                        getResources().getString(R.string.forgot_password_transition_name));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);

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

    public void onGoogleSignInClicked(View view) {
        signIn();
    }


    public void onFacebookSignInClicked(View view) {
        fbLoginClicked = true;
        mCallbackManager = CallbackManager.Factory.create();
        mFbSignInButton.setReadPermissions("email", "public_profile");
        mFbSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

    }

    //https://firebase.google.com/docs/auth/android/facebook-login
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Welcome, " + user.getDisplayName() + "!",
                                    Toast.LENGTH_SHORT).show();
                            signInAction();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
