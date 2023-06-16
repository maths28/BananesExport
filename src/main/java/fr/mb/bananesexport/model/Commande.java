package fr.mb.bananesexport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Commande {

    @Id
    @GeneratedValue
    private int id;
    @JsonIgnore
    private int destinataireId;
    private String date;
    private int quantite;
    private double prix;
    @Transient
    @JsonIgnore
    private boolean alreadyExists;

    public Commande(int id, int destinataireId, String date, int quantite, double prix) {
        this.id = id;
        this.destinataireId = destinataireId;
        this.date = date;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Commande(String date, int quantite) {
        this.date = date;
        this.quantite = quantite;
    }

    public Commande(int destinataireId, String date, int quantite, double prix) {
        this.destinataireId = destinataireId;
        this.date = date;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(int destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(boolean alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public boolean anyNull(){
        if(this.destinataireId <= 0) return true;
        return (this.date == null || this.date.isEmpty());
    }
}
