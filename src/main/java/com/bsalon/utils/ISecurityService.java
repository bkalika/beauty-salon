package com.bsalon.utils;

/**
 * @author @bkalika
 */
public interface ISecurityService {
    String encryptPassword(String password);
    boolean checkPassword(String password, String hashed);
}
