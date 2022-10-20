package com.bsalon.daos;

import com.bsalon.daos.jdbc.*;
import com.bsalon.exceptions.NoDaoEntityException;

/**
 * @author @bkalika
 */
public class DaoFactory {
    public static IDao create(Entity entity) {
        if (entity == Entity.USER) {
            return new UserDAOJDBC();
        } else if(entity == Entity.SERVICE) {
            return new ServiceDAOJDBC();
        } else if(entity == Entity.REQUEST) {
            return new RequestDAOJDBC();
        } else if(entity == Entity.SERVICEHAIRDRESSER) {
            return new ServiceHairdresserDAOJDBC();
        } else if(entity == Entity.FEEDBACK) {
            return new FeedbackDAOJDBC();
        }
        throw new NoDaoEntityException();
    }

    public enum Entity {
        USER,
        REQUEST,
        SERVICE,
        SERVICEHAIRDRESSER,
        FEEDBACK
    }
}
