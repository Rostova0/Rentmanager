package com.epf.rentmanager.model;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;


    public Vehicle(){

    }
    public Vehicle (int id, String constructeur, String modele, int nb_places){
        this.id=id;
        this.constructeur=constructeur;
        this.modele=modele;
        this.nb_places=nb_places;
    }

    public Vehicle (String constructeur, String modele, int nb_places){
        this.constructeur=constructeur;
        this.modele=modele;
        this.nb_places=nb_places;
    }

    public int getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public static boolean isNbPlacesOK(Vehicle vehicle) {
        return vehicle.nb_places >= 2 && vehicle.nb_places <= 9;
    }

    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", manufacturer='" + constructeur + '\'' +
                ", model='" + modele + '\'' +
                ", numSeats=" + nb_places +
                '}';
    }
}
