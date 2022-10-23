package com.example.films;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {
    private Context context;
    private List<Film> films;
    private OnItemClickListener onItemClickListener;

    public FilmAdapter(Context context, List<Film> films) {
        this.context = context;
        this.films = films;
    }

    public FilmAdapter(List<Film> films, OnItemClickListener onItemClickListener) {
        this.films = films;
        this.onItemClickListener = onItemClickListener;
    }

    public FilmAdapter(Context context, List<Film> films, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.films = films;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //retornar una instancia de FilmViewHolder
        View itemFilm = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(itemFilm);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //darle funcionalidad a partir de la posicion, trae info del item
        //holder.imgItem.setImageResource(personajes.get(position).getImgResource());
        holder.tvId.setText(films.get(position).getId());
        holder.tvTitle.setText(films.get(position).getTitle());
        holder.tvOriginal_title.setText(films.get(position).getOriginal_title());
        holder.tvOriginal_title_romanised.setText(films.get(position).getOriginal_title_romanised());
        Glide.with(context).load(films.get(position).getImage()).into(holder.imgItem);
        //holder.imgMovie_banner.setImageResource(films.get(position).getMovie_banner());
        holder.tvDescription.setText(films.get(position).getDescription());
        holder.tvDirector.setText(films.get(position).getDirector());
        holder.tvProducer.setText(films.get(position).getProducer());
        holder.tvRelease_date.setText(films.get(position).getRelease_date());
        holder.tvRunning_time.setText(films.get(position).getRunning_time());
        holder.tvRt_score.setText(films.get(position).getRt_score());
        holder.tvUrl.setText(films.get(position).getUrl());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onItemClickListener.onItemClick(films.get(holder.getAdapterPosition()));
                Intent intent = new Intent(context, FilmActivity.class);
                intent.putExtra("film", films.get(position));
                //intent.putExtra("cantidad", Intent.getIntent().getIntExtra("cantidad", 0));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { //cantidad de items que se trajeron
        return films.size();
    }


    public class FilmViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId;
        private TextView tvTitle;
        private TextView tvOriginal_title;
        private TextView tvOriginal_title_romanised;
        private ImageView imgItem;
        //private ImageView imgMovie_banner;
        private TextView tvDescription;
        private TextView tvDirector;
        private TextView tvProducer;
        private TextView tvRelease_date;
        private TextView tvRunning_time;
        private TextView tvRt_score;
        private TextView tvUrl;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvOriginal_title = itemView.findViewById(R.id.tvOriginal_title);
            this.tvOriginal_title_romanised = itemView.findViewById(R.id.tvOriginal_title_romanised);
            this.imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            //this.imgMovie_banner = itemView.findViewById(R.id.imgMovie_banner);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvDirector = itemView.findViewById(R.id.tvDirector);
            this.tvProducer = itemView.findViewById(R.id.tvProducer);
            this.tvRelease_date = itemView.findViewById(R.id.tvRelease_date);
            this.tvRunning_time = itemView.findViewById(R.id.tvRunning_time);
            this.tvRt_score = itemView.findViewById(R.id.tvRt_score);
            this.tvUrl = itemView.findViewById(R.id.tvUrl);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Film film);
    }
}
