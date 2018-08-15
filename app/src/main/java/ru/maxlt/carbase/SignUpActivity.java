package ru.maxlt.carbase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {


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
                String password = mPassword.getText().toString().trim();
                String confirmedPassword = mConfirmedPassword.getText().toString().trim();

                setmProgressBar(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        clearText();
                        setmProgressBar(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Failed to create an account", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent mIntent = new Intent(this,)
                        }
                    }
                });

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
}
