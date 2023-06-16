package fr.mb.bananesexport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.stream.Stream;

@Entity
public class Destinataire {

    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String pays;

    @Transient
    @JsonIgnore
    private boolean alreadyExists;

    public Destinataire(int id, String nom, String adresse, String codePostal, String ville, String pays) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public Destinataire(String nom, String adresse, String codePostal, String ville, String pays) {
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public Destinataire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public boolean isAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(boolean alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public boolean anyNull(){
        Stream<String> stream = Stream.of(this.nom, this.adresse, this.codePostal, this.ville, this.pays);
        return stream.anyMatch((val)->val==null || val.isEmpty());
    }

    public void cleanSpaces(){
        this.nom = this.nom.replaceAll("\\s+", " ").trim();
        this.adresse = this.adresse.replaceAll("\\s+", " ").trim();
        this.codePostal = this.codePostal.replaceAll("\\s+", " ").trim();
        this.ville = this.ville.replaceAll("\\s+", " ").trim();
        this.pays = this.pays.replaceAll("\\s+", " ").trim();
    }
}
