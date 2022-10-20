package com.bsalon.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents the Request model. This model class can be used throughout all
 * layers, the data layer, the controller layer and the view layer.
 *
 * @author bkalika
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private ServiceHairdresser serviceHairdresser;
    private User client;
    private Status status;
    private LocalDateTime date;
    private boolean paid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceHairdresser getServiceHairdresser() {
        return serviceHairdresser;
    }

    public void setServiceHairdresser(ServiceHairdresser serviceHairdresser) {
        this.serviceHairdresser = serviceHairdresser;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
