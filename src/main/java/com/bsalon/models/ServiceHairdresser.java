package com.bsalon.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the ServiceHairdresser model. This model class can be used throughout all
 * layers, the data layer, the controller layer and the view layer.
 * @author @bkalika
 */
public class ServiceHairdresser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Service service;
    private User hairdresser;

    public ServiceHairdresser() {
    }

    public ServiceHairdresser(Long id, Service service, User hairdresser) {
        this.id = id;
        this.service = service;
        this.hairdresser = hairdresser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(User hairdresser) {
        this.hairdresser = hairdresser;
    }

    @Override
    public String toString() {
        return "ServiceHairdresser{" +
                "id=" + id +
                ", service=" + service +
                ", hairdresser=" + hairdresser +
                '}';
    }
}
