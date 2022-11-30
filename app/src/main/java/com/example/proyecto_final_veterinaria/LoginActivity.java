package com.example.proyecto_final_veterinaria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import io.grpc.android.BuildConfig;

public class LoginActivity extends AppCompatActivity {
   Button btn_login,btn_register;//btn_citas;
   EditText email, password;
FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.correo);
        password = findViewById(R.id.contrasena);
        btn_login = findViewById(R.id.btn_ingresar);
        btn_register = findViewById(R.id.btn_register);
        //btn_citas=findViewById(R.id.btn_citas);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();
                if (emailUser.isEmpty() && passUser.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Ingresar datos ", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailUser, passUser);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
              startActivity(i);

            }
        });
 /*btn_citas.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent i = new Intent(LoginActivity.this, ReservaCitasActivity.class);
         startActivity(i);
     }
 });*/
    }





    private void loginUser(String emailUser, String passUser) {
    mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
         if(task.isSuccessful()){
             finish();
             Log.d("Buildconfig",BuildConfig.TYPE);
             if(BuildConfig.TYPE.contains("admin")){
                 Toast.makeText(LoginActivity.this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
             }else{
                 Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(LoginActivity.this,MainActivity.class));
             }
         }else{
             Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
         }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
        }
    });
    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            finish();
            if(BuildConfig.TYPE.contains("admin")){
                startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
            }else{
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }
    }
}