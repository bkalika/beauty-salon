package com.bsalon.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class represents the Role ENUM. This class can be used throughout all
 * layers, the data layer, the controller layer and the view layer.
 *
 * @author bkalika
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public enum Role {
    ADMIN(1, "Admin"),
    CUSTOMER(2, "Customer"),
    HAIRDRESSER(3, "Hairdresser");

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Role getById(int id) {
        return Arrays.stream(Role.values()).filter(role -> role.id == id).findFirst().orElse(null);
    }

    public static Role getByName(String name) {
        return Arrays.stream(Role.values()).filter(role -> role.name.equals(name)).findFirst().orElse(null);
    }
}
