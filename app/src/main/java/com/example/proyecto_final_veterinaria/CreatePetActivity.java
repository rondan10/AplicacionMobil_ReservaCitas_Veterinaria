package com.example.proyecto_final_veterinaria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatePetActivity extends AppCompatActivity {
  Button btn_add;
  EditText name,age,color;
  private FirebaseFirestore mfirestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);
    this.setTitle("Registrar mascota");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    String id = getIntent().getStringExtra("id_pet");
    mfirestore = FirebaseFirestore.getInstance();
    name=findViewById(R.id.nombre);
    age=findViewById(R.id.edad);
    color=findViewById(R.id.color);
    btn_add = findViewById(R.id.btn_add);

    if(id == null ||id ==""){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namepet =name.getText().toString().trim();
                String agepet =age.getText().toString().trim();
                String colorpet =color.getText().toString().trim();

                if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                }else{
                    postPet(namepet,agepet,colorpet);
                }
            }
        });
    }else{
        btn_add.setText("Update");
        getPet(id);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namepet =name.getText().toString().trim();
                String agepet =age.getText().toString().trim();
                String colorpet =color.getText().toString().trim();

                if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                }else{
                    updatePet(namepet,agepet,colorpet, id);
                }
            }
        });
    }

    }

    private void updatePet(String namepet, String agepet, String colorpet, String id) {
        Map<String,Object> map= new HashMap<>();
        map.put("name",namepet);
        map.put("age",agepet);
        map.put("color",colorpet);

        mfirestore.collection("pet").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado exitosamente ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al  actualizar ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postPet(String namepet, String agepet, String colorpet) {
        Map<String,Object> map= new HashMap<>();
        map.put("name",namepet);
        map.put("age",agepet);
        map.put("color",colorpet);
    mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Toast.makeText(getApplicationContext(), "Creado exitosamente ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), "Error al ingresar ", Toast.LENGTH_SHORT).show();
        }
    });
    }
    private  void getPet(String id){
        mfirestore.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namePet = documentSnapshot.getString("name");
                String agePet = documentSnapshot.getString("age");
                String colorPet = documentSnapshot.getString("color");

                name.setText(namePet);
                age.setText(agePet);
                color.setText(colorPet);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener datos ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
      onBackPressed();
      return false;
    }
}