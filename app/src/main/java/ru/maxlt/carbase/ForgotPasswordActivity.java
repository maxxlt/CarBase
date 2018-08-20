package ru.maxlt.carbase;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private FirebaseAuth mAuth;

    @BindView(R.id.email_textinputlayout_reset)
    TextInputLayout mResetInputLayout;
    @BindView(R.id.email_textedit_reset)
    TextInputEditText mResetTextEdit;
    @BindView(R.id.reset_btn)
    ImageButton mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    public void onResetButtonClicked(View view) {
        String email = mResetTextEdit.getText().toString().trim();
        hideKeyboard();
        if (validateEmail(email)){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                Toast.makeText(ForgotPasswordActivity.this,"Password reset sent.",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(ForgotPasswordActivity.this,"Account does not exist.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            mResetInputLayout.setError("Invalid email. Please type again.");
        }

    }

    private boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
