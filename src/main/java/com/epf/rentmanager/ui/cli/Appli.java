package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.except.DaoException;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.epf.rentmanager.utils.IOUtils.*;

public class Appli {

    private static ClientService clientService;
    private  static VehicleService vehicleService;
    private static  ReservationService reservationService;

    public static void main (String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        clientService = context.getBean(ClientService.class);
        vehicleService = context.getBean(VehicleService.class);
        reservationService = context.getBean(ReservationService.class);

        Scanner scan=new Scanner(System.in);
        while(true){
            print("1. Créer un client");
            print("2. Lister tous les clients");
            print("3. Créer un véhicule");
            print("4. Lister tous les véhicules");
            print("5. Créer une réservation");
            print("6. Lister toutes les réservations");
            print("7. Lister les réservations associées à un client");
            print("8. Lister les réservations associées à un véhicule");
            print("9. Supprimer un client");
            print("10. Supprimer un véhicule");
            print("11. Supprimer une réservation");
            print("Choisissez une option : ");
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice){
                case 1:
                    String nom=readString("Nom du client :",true);
                    String prenom=readString("Prenom du client :",true);
                    String email=readString("Email du client :",true);
                    LocalDate naissance=readDate("Date de naissance :",true);
                    try {
                        clientService.create(new Client(nom, prenom, email, naissance));
                        print("Client créé avec succés !");
                    }catch (ServiceException e){
                        print(e.getMessage());
                    }
                    break;

                case 2:

                    try {
                        print("Liste des clients :");
                       List<Client> c=clientService.findAll();
                        System.out.println(c);
                    }catch (ServiceException e){
                        print("Un probléme a été rencontrer, veuillez réessayer");
                    }
                    break;

                case 3:
                    String constructeur=readString("Constructeur vehicule : ",true);
                    String modele=readString("Modele du vehicule",true);
                    int nbplaces=readInt("Nombre de place");

                    try {
                        vehicleService.create(new Vehicle(constructeur, modele, nbplaces));
                        print("Vehicule créé avec succès !");
                    }catch (ServiceException e){
                        print("Un probléme a été rencontrer, veuillez réessayer");
                    }
                    break;

                case 4:
                    try {
                        print("Liste des vehicles :");
                        List<Vehicle> v=vehicleService.findAll();
                        System.out.println(v);

                    }catch (ServiceException e){
                        print(e.getMessage());
                       print("Un probléme a été rencontrer, veuillez réessayer");

                    }
                    break;
                case 5:
                    int clientId = readInt("ID du client :");
                    int vehicleId = readInt("ID du véhicule :");
                    LocalDate debut = readDate("Date de début (yyyy-mm-dd) :", true);
                    LocalDate fin = readDate("Date de fin (yyyy-mm-dd) :", true);
                    try {
                        reservationService.create(new Reservation(clientId, vehicleId, debut, fin));
                        print("Réservation créée avec succès !");
                    } catch (ServiceException e) {
                        print("Un problème a été rencontré, veuillez réessayer");
                    }

                    break;

                case 6:
                    try {
                        print("Liste des réservations :");
                        List<Reservation> reservations = reservationService.findAll();
                        System.out.println(reservations);
                    } catch (ServiceException e) {
                        print("Un problème a été rencontré, veuillez réessayer");
                    }
                    break;

                case 7:
                    int cId = readInt("ID du client :");
                    try {
                        print("Liste des réservations pour le client " + cId + ":");
                        List<Reservation> reservations = reservationService.findResaByClientID(cId);
                        System.out.println(reservations);
                    } catch (ServiceException e) {
                        print("Un problème a été rencontré, veuillez réessayer");
                    }
                    break;
                case 8:
                    int vId = readInt("ID du véhicule :");
                    try {
                        print("Liste des réservations pour le véhicule " + vId + ":");
                        List<Reservation> reservations = reservationService.findResaByVehicleId((vId));
                        System.out.println(reservations);
                    } catch (ServiceException e) {
                        print("Un problème a été rencontré, veuillez réessayer");
                    }
                    break;
                case 9:
                    int idCsup=readInt("ID du client à supprimer ");
                    try {
                        Client c= new Client();
                        c.setId(idCsup);
                        clientService.delete(c.getId());
                        print("Client supprimer");

                    }catch (ServiceException e){
                        print("Vous tentez peut-etre de supprimer un client inexistant");
                    }
                    break;
                case 10:
                    int idVsup=readInt("ID du vehicule à supprimer ");
                    try {
                        Vehicle v= new Vehicle();
                        v.setId(idVsup);
                        vehicleService.delete(v.getId());
                        print("Vehicule supprimer");
                    }catch (ServiceException e){
                        print("Vous tentez peut-etre de supprimer un vehicule inexistant");
                    }
                    break;

                case 11:
                    int resaId = readInt("ID de la réservation à supprimer :");
                    try {
                        Reservation r=new Reservation();
                        r.setId(resaId);
                        reservationService.delete(r.getId());
                        print("Réservation supprimée avec succès !");
                    } catch (ServiceException e) {
                        print("Un problème a été rencontré, veuillez réessayer");
                    }
                    break;
            }
        }
    }
}
