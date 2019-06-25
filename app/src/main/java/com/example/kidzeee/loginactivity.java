package com.example.kidzeee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class loginactivity extends AppCompatActivity {
    private EditText Email, password;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String E, P;
                E = Email.getText().toString();
                P = password.getText().toString();
                if (TextUtils.isEmpty(E) && TextUtils.isEmpty(P)) {
                    showsnackbar("Email and Password missing");
                }else if (TextUtils.isEmpty(E))
                {
                    showsnackbar("Email missing");
                }
                else if (TextUtils.isEmpty(P)){
                    showsnackbar("Password Missing");
                }
                else{
                    NewUser(E,P);
                }
            }
        });


    }

    private void NewUser(final String e, final String p) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(loginactivity.this,MainActivity.class));finish();
                }
                else{

                    try{
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(String.valueOf(e),p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    startActivity(new Intent(loginactivity.this,MainActivity.class));finish();
                                }
                            }
                        });
                    }catch (Exception e1) {

                        showsnackbar(task.getException().getMessage());

                    }
                }
            }
        });
    }

    public void showsnackbar(String message) {
        Snackbar.make(findViewById(R.id.rparent), message, Snackbar.LENGTH_LONG).show();
    }
}
