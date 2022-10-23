package com.example.films;

import java.io.Serializable;

public class Film implements Serializable {
    private String id;
    private String title;
    private String  original_title;
    private String  original_title_romanised;
    private String  image;
    //private String  movie_banner;
    private String  description;
    private String  director;
    private String  producer;
    private String  release_date;
    private String  running_time;
    private String  rt_score;
    private String  url;

    public Film(String id, String title, String original_title, String original_title_romanised, String image, String description, String director, String producer, String release_date, String running_time, String rt_score, String url) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.original_title_romanised = original_title_romanised;
        this.image = image;
        this.description = description;
        this.director = director;
        this.producer = producer;
        this.release_date = release_date;
        this.running_time = running_time;
        this.rt_score = rt_score;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_title_romanised() {
        return original_title_romanised;
    }

    public void setOriginal_title_romanised(String original_title_romanised) {
        this.original_title_romanised = original_title_romanised;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRunning_time() {
        return running_time;
    }

    public void setRunning_time(String running_time) {
        this.running_time = running_time;
    }

    public String getRt_score() {
        return rt_score;
    }

    public void setRt_score(String rt_score) {
        this.rt_score = rt_score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
