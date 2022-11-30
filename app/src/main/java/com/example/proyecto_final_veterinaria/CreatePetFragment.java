package com.example.proyecto_final_veterinaria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CreatePetFragment extends DialogFragment {
    Button btn_add;
    EditText name,age,color;
    private FirebaseFirestore mfirestore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_pet, container, false);
        mfirestore = FirebaseFirestore.getInstance();
        name=v.findViewById(R.id.nombre);
        age=v.findViewById(R.id.edad);
        color=v.findViewById(R.id.color);
        btn_add = v.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namepet =name.getText().toString().trim();
                String agepet =age.getText().toString().trim();
                String colorpet =color.getText().toString().trim();
                if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                    Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();

                }else{
                    postPet(namepet,agepet,colorpet);
                }
            }
        });


        return v;
    }
    private void postPet(String namepet, String agepet, String colorpet) {
        Map<String,Object> map= new HashMap<>();
        map.put("name",namepet);
        map.put("age",agepet);
        map.put("color",colorpet);
        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Creado exitosamente ", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}