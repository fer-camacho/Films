package com.example.films;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrearCuentaActivity extends AppCompatActivity {

    Toolbar crear_toolbar;
    EditText etUsuario, etPassword, etPassword2;
    CheckBox chAceptarTerminos;
    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        crear_toolbar = findViewById(R.id.crear_toolbar);
        setSupportActionBar(crear_toolbar);
        getSupportActionBar().setTitle("Studio Ghibli");

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        chAceptarTerminos = findViewById(R.id.chAceptarTerminos);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String usuario = etUsuario.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etPassword2.getText().toString();
                boolean terminos = chAceptarTerminos.isChecked();

                if (usuario.isEmpty()) {
                    Toast.makeText(CrearCuentaActivity.this, "Complete username", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(CrearCuentaActivity.this, "Complete password", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(password2)) {
                    Toast.makeText(CrearCuentaActivity.this, "Passwords must be the same", Toast.LENGTH_SHORT).show();
                }
                else if (!terminos){
                    Toast.makeText(CrearCuentaActivity.this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        if (UsuarioManager.getInstancia(CrearCuentaActivity.this).existeUsuario(usuario) > 0) {
                            etUsuario.setText("");
                            Toast.makeText(CrearCuentaActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        } else{
                            Usuario nuevoUsuario = new Usuario();
                            nuevoUsuario.setUsuario(usuario);
                            nuevoUsuario.setPassword(password);
                            UsuarioManager.getInstancia(CrearCuentaActivity.this).agregarUsuario(nuevoUsuario);
                            Toast.makeText(CrearCuentaActivity.this, "Signup completed succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CrearCuentaActivity.this, SeleccionElementosActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crear, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_volver){
            Intent intent = new Intent(CrearCuentaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}