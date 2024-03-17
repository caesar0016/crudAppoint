package com.example.crudappoint;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addData extends AppCompatActivity {

    private EditText edtxtServiceName, edTxtprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);

        Button btnAddData = findViewById(R.id.btnAddService);

        edtxtServiceName = findViewById(R.id.edtxtServiceName);
        edTxtprice = findViewById(R.id.edtxtPrice);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serviceName = edtxtServiceName.getText().toString();
                String servicePrice = edTxtprice.getText().toString();

                if(serviceName.isEmpty()){

                    edtxtServiceName.setError("Cannot be empty");

                }
                if(servicePrice.isEmpty()){

                    edTxtprice.setError("Cannot be empty for price");

                }
                    addInputToDB(serviceName, servicePrice);

            }

            private void addInputToDB(String serviceName, String servicePrice) {

                HashMap<String, Object> serviceHash = new HashMap<String, Object>();
                serviceHash.put("ServiceName", serviceName);
                serviceHash.put("ServicePrice", servicePrice);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference serviceRef = database.getReference("Service");

                String key = serviceRef.push().getKey();
                serviceRef.child(key).setValue(serviceHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(addData.this, "Successfully added", Toast.LENGTH_SHORT).show();
                        edtxtServiceName.getText().clear();
                        edTxtprice.getText().clear();
                    }
                });
            }
        });

    }
}