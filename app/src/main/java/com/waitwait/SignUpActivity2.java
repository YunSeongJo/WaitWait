package com.waitwait;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity2 extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private EditText editTextName;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.emailWord);
        editTextPassword = findViewById(R.id.pwWord);
        editTextPassword2 = findViewById(R.id.pwWord2);
        editTextName = findViewById(R.id.nameWord);
        editTextPhone = findViewById(R.id.phoneWord);
    }


    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(SignUpActivity2.this, "성공", Toast.LENGTH_SHORT).show();
                        } else {
                            // 회원가입 실패
                            Toast.makeText(SignUpActivity2.this, "실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
