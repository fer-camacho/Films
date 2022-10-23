package com.example.films

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.graphics.LightingColorFilter
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Throws
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var login_toolbar: Toolbar;
    private lateinit var  etUsuario: EditText;
    private lateinit var  etPassword: EditText;
    private lateinit var  btnCrearUsuario: Button;
    private lateinit var  btnIniciarSesion: Button;
    private lateinit var  swRecordarUsuario: Switch;


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_toolbar = findViewById(R.id.login_toolbar)
        setSupportActionBar(login_toolbar)
        supportActionBar!!.title = "Studio Ghibli"

        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        swRecordarUsuario = findViewById(R.id.swRecordarUsuario)
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        val prefs =
            applicationContext.getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE)
        val usuarioGuardado = prefs.getString(Constantes.USUARIO, null)
        val passGuardada = prefs.getString(Constantes.PASSWORD, null)
        if (usuarioGuardado != null && passGuardada != null) {
            iniciarSeleccionElementosActivity(usuarioGuardado)
        }

        btnIniciarSesion.setOnClickListener(View.OnClickListener { //Log.i("TODO", "Se apreto el boton iniciar sesion.");
            val usuario = etUsuario.getText().toString()
            val password = etPassword.getText().toString()
            if (usuario.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Complete username", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Complete password", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    login(usuario, password)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        btnCrearUsuario.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, CrearCuentaActivity::class.java)
            startActivity(intent)
            finish()
        })

        swRecordarUsuario.setOnClickListener(View.OnClickListener {
            if (swRecordarUsuario!!.isChecked) {
                createNotifChannel()
                val notif = NotificationCompat.Builder(this,"SHARED PREFERENCES")
                    .setContentTitle("Studio Ghipli")
                    .setContentText("Se guardaron sus credenciales")
                    .setSmallIcon(R.drawable.alert)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()

                val notifManger = NotificationManagerCompat.from(this)
                notifManger.notify(1, notif)
            }
        })

    }

    @Throws(Exception::class)
    private fun login(usuario: String, password: String) {
        try {
            val user = UsuarioManager.getInstancia(this@LoginActivity)
                .traer(UsuarioManager.getInstancia(this@LoginActivity).usuarios, usuario)
            if (user == null) {
                etUsuario!!.setText("")
                Toast.makeText(this@LoginActivity, "Username does not exist", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (user.password == password) {
                    if (swRecordarUsuario!!.isChecked) {
                        val prefs = applicationContext.getSharedPreferences(
                            Constantes.SP_CREDENCIALES,
                            MODE_PRIVATE
                        )
                        val editor = prefs.edit()
                        editor.putString(Constantes.USUARIO, usuario)
                        editor.putString(Constantes.PASSWORD, password)
                        editor.apply()
                    }

                    apiYesNo(usuario)
                } else {
                    etPassword!!.setText("")
                    Toast.makeText(this@LoginActivity, "Password is incorrect", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun iniciarSeleccionElementosActivity(usuarioGuardado: String) {
        val intent = Intent(this@LoginActivity, SeleccionElementosActivity::class.java)
        intent.putExtra(Constantes.USUARIO, usuarioGuardado)
        startActivity(intent)
        finish()
    }

    private fun apiYesNo(usuario : String) {
        lateinit var ans: Answer
        val api = RetrofitClient.retrofitYesNo.create(MyAPI::class.java)
        val callGetAnswer = api.getAnswer()


        callGetAnswer.enqueue(object : retrofit2.Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                val answer = response.body()
                if (answer != null) {
                    ans = Answer(answer.answer)
                    if (ans.answer.equals("yes")) {
                        val intent = Intent(this@LoginActivity, SplashScreenActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        iniciarSeleccionElementosActivity(usuario)
                    }
                }
            }

            override fun onFailure(call : Call<Answer>, t: Throwable) {
                Log.e("REST", t.message ?: "")
            }
        })
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("SHARED PREFERENCES", "Recordar credenciales", NotificationManager.IMPORTANCE_DEFAULT).apply {
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}

