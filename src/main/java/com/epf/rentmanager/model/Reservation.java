package com.epf.rentmanager.model;


import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Reservation {
    private int id;
    private int client_id;
    private int vehicle_id;
    private LocalDate debut;
    private LocalDate fin;


    public Reservation(){

    }
    public Reservation(int id, int client_id, int vehicle_id, LocalDate debut, LocalDate fin){
        this.id=id;
        this.client_id=client_id;
        this.vehicle_id=vehicle_id;
        this.debut=debut;
        this.fin=fin;
    }

    public Reservation(int client_id, int vehicle_id, LocalDate debut, LocalDate fin){
        this.client_id=client_id;
        this.vehicle_id=vehicle_id;
        this.debut=debut;
        this.fin=fin;
    }

    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public static boolean isCarNotRentUnder7days(Reservation rent) {
        Period period = Period.between(rent.getDebut(),rent.getFin());
        return period.getDays() < 7;
    }

    public static boolean isNotTheSameDay(Reservation rent, ReservationService rentService) throws ServiceException {
        List<Reservation> reservation;
        reservation = rentService.findResaByVehicleId(rent.vehicle_id);
        for (int i = 0; i < reservation.size(); i++) {
            if (rent.getDebut().isAfter(reservation.get(i).getDebut()) && rent.getDebut().isBefore(reservation.get(i).getFin())) {
                return false;
            }
            if (rent.getFin().isAfter(reservation.get(i).getDebut()) && rent.getFin().isBefore(reservation.get(i).getFin())) {
                return false;
            }
            if (reservation.get(i).getDebut().isAfter(rent.getDebut()) && reservation.get(i).getDebut().isBefore(rent.getFin())) {
                return false;
            }
            if (reservation.get(i).getDebut().isEqual(rent.getDebut()) && reservation.get(i).getFin().isEqual(rent.getFin())) {
                return false;
            }

        }
        return true;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
