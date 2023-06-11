package com.example.jobportalemployee;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    EditText emailid,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailid=(EditText)findViewById(R.id.et_signin_id);
        pwd=(EditText)findViewById(R.id.et_signin_pwd);
        mAuth= FirebaseAuth.getInstance();
        findViewById(R.id.tv_signin).setOnClickListener(this);
        findViewById(R.id.btn_signin).setOnClickListener(this);
        findViewById(R.id.tv_forgot).setOnClickListener(this);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent i= new Intent(this, com.example.jobportalemployee.ProfileActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }



    }


    private void userLogin(){


        String email = emailid.getText().toString().trim();
        String password = pwd.getText().toString().trim();


        if (email.isEmpty()){
            emailid.setError("Email is required");
            emailid.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailid.setError("Please enter valid email");
            emailid.requestFocus();
            return;
        }

        if (password.isEmpty()){
            pwd.setError("Password is required");
            pwd.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(com.example.jobportalemployee.MainActivity.this, "Loggin In.........", Toast.LENGTH_SHORT).show();
                    emailid.setText("");
                    pwd.setText("");
                    Intent intent=new Intent(com.example.jobportalemployee.MainActivity.this, com.example.jobportalemployee.ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(com.example.jobportalemployee.MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signin:
                startActivity(new Intent(this, com.example.jobportalemployee.SignUpActivity.class));
                break;

            case R.id.btn_signin:
                userLogin();
                break;

            case R.id.tv_forgot:
            startActivity(new Intent(this, com.example.jobportalemployee.ForgetPasswordActivity.class));
                break;


        }

    }
}
