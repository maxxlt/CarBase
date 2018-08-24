package ru.maxlt.carbase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    FirebaseAuth mAuth;

    @BindView(R.id.full_name_edittext)
    TextInputEditText mFullName;
    @BindView(R.id.email_textedit)
    TextInputEditText mEmail;
    @BindView(R.id.password_textedit)
    TextInputEditText mPassword;
    @BindView(R.id.confirm_password_textedit)
    TextInputEditText mConfirmedPassword;
    @BindView(R.id.sign_up_btn)
    ImageButton mSignUpButton;
    @BindView(R.id.progressBar_sign_up)
    ProgressBar mProgressBar;
    @BindView(R.id.email_textinputlayout)
    TextInputLayout mEmailTextLayout;
    @BindView(R.id.password_textinputlayout)
    TextInputLayout mPasswordTextLayout;
    @BindView(R.id.confirm_password_textinputlayout)
    TextInputLayout mConfirmedPasswordTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = mFullName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String confirmedPassword = mConfirmedPassword.getText().toString().trim();
                hideKeyboard();
                setmProgressBar(View.VISIBLE);
                if (!validateEmail(email)) {
                    mEmailTextLayout.setError("Invalid email. Please type again.");
                    setmProgressBar(View.GONE);
                } else if (!validatePassword(password)) {
                    mPasswordTextLayout.setError("Invalid password. Password should be more than ct5 characters.");
                    setmProgressBar(View.GONE);
                } else if (!validateConfirmedPassword(confirmedPassword, password)) {
                    mConfirmedPasswordTextLayout.setError("Invalid confirmed password. It must match your typed password.");
                    setmProgressBar(View.GONE);
                } else {
                    signUp(email, password, fullName);
                }
            }
        });
    }

    private void clearText() {
        mFullName.setText("");
        mEmail.setText("");
        mPassword.setText("");
        mConfirmedPassword.setText("");
    }

    private void setmProgressBar(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    //https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
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

    private boolean validateConfirmedPassword(String confirmedPassword, String password) {
        return confirmedPassword.equals(password);
    }

    private void signUp(String email, String password, final String fullName) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                clearText();
                setmProgressBar(View.GONE);


                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Failed to create an account", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest mRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build();
                    user.updateProfile(mRequest);
                    Intent mIntent = new Intent(SignUpActivity.this,
                            SignUpSuccessfulActivity.class);
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_left);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_right);
    }
}
