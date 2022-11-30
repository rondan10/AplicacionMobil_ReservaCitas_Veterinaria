package com.example.proyecto_final_veterinaria.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_veterinaria.CreatePetActivity;
import com.example.proyecto_final_veterinaria.R;
import com.example.proyecto_final_veterinaria.model.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PetAdapter  extends FirestoreRecyclerAdapter<Pet, PetAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    public PetAdapter(@NonNull FirestoreRecyclerOptions<Pet> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Pet Pet) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id= documentSnapshot.getId();

   viewHolder.name.setText(Pet.getName());
   viewHolder.age.setText(Pet.getAge());
   viewHolder.color.setText(Pet.getColor());

   viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent i = new Intent(activity, CreatePetActivity.class);
           i.putExtra("id_pet",id);
           activity.startActivity(i);
       }
   });

   viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
            deletePet(id);
       }
   });
    }

    private void deletePet(String id) {
        mFirestore.collection("pet").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al Eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pet_single, parent, false);
       return new ViewHolder(v);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, color;
        ImageView btn_delete, btn_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.nombre);
            age =itemView.findViewById(R.id.edad);
            color =itemView.findViewById(R.id.color);
            btn_delete= itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);

        }
    }
}
