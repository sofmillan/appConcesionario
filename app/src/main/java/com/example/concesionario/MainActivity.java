package com.example.concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Clientes(View view){
        Intent intClientes = new Intent(this,ClienteActivity.class);
        startActivity(intClientes);
    }

    public void Vehiculos(View view){
        Intent intVehiculos = new Intent(this,VehiculoActivity.class);
        startActivity(intVehiculos);
    }

    public void Ventas(View view){
        Intent intVentas = new Intent(this,VentaActivity.class);
        startActivity(intVentas);
    }
}