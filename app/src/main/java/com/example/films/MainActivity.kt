package com.example.films

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var main_toolbar: Toolbar
    lateinit var rvFilms: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(main_toolbar)
        supportActionBar!!.title = "Studio Ghibli"

        setupAdapterServicioRest()
    }

    private fun setupAdapterServicioRest(){


        var respuesta : MutableList<Film> = mutableListOf()

        //val api = RetrofitClient.retrofit.create(MyAPI::class.java)
        //val callGetFilm = api.getFilms()


        val api = RetrofitClient.retrofit.create(MyAPI::class.java)
        lateinit var callGetFilm : Call<List<Film>>
        val hilo = Thread(kotlinx.coroutines.Runnable {
            Log.d("THREAD", Thread.currentThread().name)
            callGetFilm = api.getFilms()
            callGetFilm.enqueue(object : retrofit2.Callback<List<Film>> {
                override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>){
                    val films = response.body()
                    if (films != null) {
                        films?.forEach {
                            val film = Film(it.id, it.title, it.original_title, it.original_title_romanised, it.image, it.description, it.director, it.producer, it.release_date, it.running_time, it.rt_score, it.url)
                            respuesta.add(film)

                        }

                        rvFilms = findViewById(R.id.rvFilms)
                        var numero = getIntent().getIntExtra("cantidad", 0)

                        if (numero >= respuesta.size) {
                            FilmAdapter(
                                this@MainActivity,
                                respuesta,
                            ) {
                                    film -> mensajeCorto(film.title)}.let {
                                rvFilms.adapter = it
                            }
                        } else {
                            FilmAdapter(
                                this@MainActivity,
                                respuesta.subList(0, numero),
                            ) {
                                    film -> mensajeCorto(film.title)}.let {
                                rvFilms.adapter = it
                            }
                        }
                    }
                }

                override fun onFailure(call : Call<List<Film>>, t: Throwable) {
                    Log.e("REST", t.message ?: "")
                }
            })
        })
        hilo.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_volver) {
            val intent = Intent(this@MainActivity, SeleccionElementosActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val prefs =
                applicationContext.getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE)
            prefs.edit().clear().apply()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun callAPI() : Call<List<Film>> {
        val api = RetrofitClient.retrofit.create(MyAPI::class.java)
        lateinit var callGetPost : Call<List<Film>>
        var hilo = Thread(kotlinx.coroutines.Runnable {
            callGetPost = api.getFilms()
        })
        hilo.start()
        return callGetPost
    }
}