package com.example.proyecto_final_veterinaria;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReservaCitasActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_fecha, btn_hora, btn_save;
    EditText txt_fecha, txt_hora;
    private int dia, mes, ano, hora, minutos;
    private Spinner spinner;
    private TextView txt_respuesta;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_citas);
        mfirestore= FirebaseFirestore.getInstance();
        btn_save=(Button) findViewById(R.id.btn_save);
        btn_fecha = (Button) findViewById(R.id.btn_fecha);
        btn_hora = (Button) findViewById(R.id.btn_hora);
        txt_fecha = (EditText) findViewById(R.id.txt_fecha);
        txt_hora = (EditText) findViewById(R.id.txt_hora);
        btn_fecha.setOnClickListener(this);
        btn_hora.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        txt_respuesta = (TextView) findViewById(R.id.txt_respuesta);
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] respuestas = {"Cirugía Especializada", "Consulta Médica", "Dermatología Veterinaria",
                "Estética Canina", "Vacunas y Desparasitación", "Pension Animal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, respuestas);
        spinner.setAdapter(adapter);
    }

   public void mostrar(View view) {
       String seleccionado = spinner.getSelectedItem().toString();
       if (seleccionado.equals("Cirugía Especializada")) {
           txt_respuesta.setText("Contamos con quirófanos equipados con equipo tecnológico  ");
       } else if (seleccionado.equals("Consulta Médica")) {
           txt_respuesta.setText("Nuestra consulta veterinaria incluye un examen completo de tu mascota");
       } else if (seleccionado.equals("Dermatología Veterinaria")) {
           txt_respuesta.setText("Es la ciencia de la medicina veterinaria encargada del diagnóstico");
       } else if (seleccionado.equals("Estética Canina")) {
           txt_respuesta.setText("Es un producto diseñado para la belleza y el cuidado");

       } else if (seleccionado.equals("Vacunas y Desparasitación")) {
           txt_respuesta.setText("La medicina preventiva animal es una ciencia que tiene como objetivo prevenir enfermedades ");
       } else if (seleccionado.equals("Pension Animal")) {
           txt_respuesta.setText("En el mercado encontrarás hospedajes para perros,agencias que brindan tutores temporales y guardianes");
       }
   }
    @Override
    public void onClick(View v) {
        if(v==btn_save){
            postDate(txt_fecha.getText().toString(),txt_hora.getText().toString(), spinner.getSelectedItem().toString());
        }
        if (v == btn_fecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txt_fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dia, mes, ano);
            datePickerDialog.show();

        }

        if (v == btn_hora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                   txt_hora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
                }
            }
    private void postDate(String date, String hours, String services) {
        Map<String,Object> map= new HashMap<>();
        map.put("Fecha",date);
        map.put("Hora",hours);
        map.put("Servicio",services);
        mfirestore.collection("dates").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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


        }




