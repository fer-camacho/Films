package com.example.films;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class FilmActivity extends AppCompatActivity {
    Toolbar studio_toolbar;
    ImageView imgItem;
    TextView tvTitle, tvOriginal_title, tvOriginal_title_romanised, tvDescription, tvDirector, tvProducer, tvRelease_date, tvRunning_time, tvUrl, tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Film film = (Film) getIntent().getSerializableExtra("film");
        tvId = findViewById(R.id.tvId);
        tvTitle = findViewById(R.id.tvTitle);
        tvOriginal_title = findViewById(R.id.tvOriginal_title);
        tvOriginal_title_romanised = findViewById(R.id.tvOriginal_title_romanised);
        imgItem = findViewById(R.id.imgItem);
        tvDescription = findViewById(R.id.tvDescription);
        tvDirector = findViewById(R.id.tvDirector);
        tvProducer = findViewById(R.id.tvProducer);
        tvRelease_date = findViewById(R.id.tvRelease_date);
        tvRunning_time = findViewById(R.id.tvRunning_time);
        tvUrl = findViewById(R.id.tvUrl);

        studio_toolbar = findViewById(R.id.studio_toolbar);
        setSupportActionBar(studio_toolbar);
        getSupportActionBar().setTitle(film.getTitle());

        tvId.setText(film.getId());
        tvTitle.setText("Title: " + film.getTitle());
        tvOriginal_title.setText("Original title: " + film.getOriginal_title());
        tvOriginal_title_romanised.setText("Original title romanised: " + film.getOriginal_title_romanised());
        Glide.with(FilmActivity.this).load(film.getImage()).into(imgItem);
        //imgItem.setImageResource(film.getImage());
        tvDescription.setText("Description: " + film.getDescription());
        tvDirector.setText("Director: " + film.getDirector());
        tvProducer.setText("Producer: " + film.getProducer());
        tvRelease_date.setText("Release date: " + film.getRelease_date());
        tvRunning_time.setText("Running time: " + film.getRunning_time());
        tvUrl.setText("Url: " + film.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_film, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_volver){
            Intent intent = new Intent(FilmActivity.this, SeleccionElementosActivity.class);
            //intent.putExtra("cantidad", getIntent().getIntExtra("cantidad", 0));
            startActivity(intent);
            finish();
        } else {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(FilmActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
