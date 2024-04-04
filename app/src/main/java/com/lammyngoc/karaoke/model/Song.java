package com.lammyngoc.karaoke.model;

public class Song {
    private String ma;
    private String tenbaihat;
    private String tacgia;
    private Integer favorite;

    public Song() {
    }

    public Song(String ma, String tenbaihat, String tacgia, Integer favorite) {
        this.ma = ma;
        this.tenbaihat = tenbaihat;
        this.tacgia = tacgia;
        this.favorite = favorite;
    }

    public Song(String ma, String tenbaihat, String tacgia, String favorite) {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }
}

