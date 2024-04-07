package com.epf.rentmanager.model;

import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.service.ClientService;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;


    public Client(){

    }

    public Client(int id,String nom, String prenom, String email, LocalDate naissance){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.naissance=naissance;
    }
    public Client(String nom, String prenom, String email, LocalDate naissance){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.naissance=naissance;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public static boolean isLegal(Client client) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(client.getNaissance(), currentDate);
        int age = period.getYears();
        System.out.println(age);
        return age >= 18;
    }

    public static boolean isNameOK(Client client) {
        return client.getNom().length() >= 3 && client.getPrenom().length() >= 3;
    }

    public static boolean isMailFree(Client client, ClientService clientService) throws DaoException {
        boolean mailIdentique = true;
        Client clientMail = clientService.findByEmail(client.getEmail());
        if (clientMail != null && Objects.equals(client.getEmail(), clientMail.getEmail())) {
            mailIdentique = false;
        }
        return mailIdentique;
    }
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
