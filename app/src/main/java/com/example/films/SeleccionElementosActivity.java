package com.example.films;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SeleccionElementosActivity extends AppCompatActivity {

    Toolbar seleccion_toolbar;
    EditText etCantidad;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_elementos);

        seleccion_toolbar = findViewById(R.id.seleccion_toolbar);
        setSupportActionBar(seleccion_toolbar);
        getSupportActionBar().setTitle("Studio Ghibli");

        etCantidad = findViewById(R.id.etCantidad);
        //etCantidad.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "8")}); //para que podamos limitar la cantidad de registros
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SeleccionElementosActivity.this, MainActivity.class);
                intent.putExtra("cantidad", Integer.valueOf(etCantidad.getText().toString()));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_seleccion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){ //en el caso de que se quiera volver, elimina las preferenias
        if (item.getItemId() == R.id.item_volver){
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(SeleccionElementosActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}