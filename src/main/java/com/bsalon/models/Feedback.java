package com.bsalon.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents the Feedback model. This model class can be used throughout all
 * layers, the data layer, the controller layer and the view layer.
 *
 * @author @bkalika
 */
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Request request;
    private String name;
    private int rate;
    private LocalDateTime created;

    public Feedback() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", request=" + request +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", created=" + created +
                '}';
    }
}
