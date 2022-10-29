package com.bsalon.constants;

/**
 * This class represents all SQL queries to the database. This class can be used throughout all
 * DAO layer.
 *
 * @author bkalika
 */
public class SQLConstants {

    public static final String SQL_SELECT_USERS = "SELECT * FROM usr WHERE role_id = ?;";
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM usr WHERE id = ?;";
    public static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM usr WHERE email = ?;";
    public static final String SQL_SELECT_CLIENTS = "SELECT * FROM usr WHERE role_id = 2";
    public static final String SQL_SELECT_HAIRDRESSERS = "SELECT * FROM usr WHERE role_id = 3";
    public static final String SQL_INSERT_USER = "INSERT INTO usr (email, password, name, role_id) VALUES (?, ?, ?, ?);";
    public static final String SQL_UPDATE_USER = "UPDATE usr SET name = ?, rating = ? WHERE id = ?;";
    public static final String SQL_DELETE_USER = "DELETE FROM usr WHERE id = ?;";

    public static final String SQL_SELECT_REQUESTS = "SELECT r.id, r.servicehairdresser_id, r.client_id, r.status, r.date, r.paid r_paid, sh.id, sh.service_id, sh.hairdresser_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM request r\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "ORDER BY status, date DESC;";
    public static final String SQL_SELECT_REQUEST_BY_ID = "SELECT r.id, r.servicehairdresser_id, r.client_id, r.status, r.date, r.paid r_paid, sh.id, sh.service_id, sh.hairdresser_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM request r\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "WHERE r.id = ?;";
    public static final String SQL_SELECT_REQUEST_BY_HAIRDRESSER = "SELECT r.id, r.servicehairdresser_id, r.client_id, r.status, r.date, r.paid r_paid, sh.id, sh.service_id, sh.hairdresser_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM request r\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "WHERE sh.hairdresser_id = ?\n" +
            "ORDER BY status, date DESC;";
    public static final String SQL_SELECT_REQUEST_BY_CLIENT = "SELECT r.id, r.servicehairdresser_id, r.client_id, r.status, r.date, r.paid r_paid, sh.id, sh.service_id, sh.hairdresser_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM request r\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "WHERE r.client_id = ?\n" +
            "ORDER BY status, date DESC;";
    public static final String SQL_INSERT_REQUEST = "INSERT INTO request(servicehairdresser_id, client_id, date) VALUES (?, ?, ?);";
    public static final String SQL_UPDATE_REQUEST = "UPDATE request SET servicehairdresser_id = ?, client_id = ?, status = ?, date = ?, paid = ? WHERE id = ?;";
    public static final String SQL_DELETE_REQUEST = "DELETE FROM request WHERE id = ?;";

    public static final String SQL_SELECT_SERVICEHAIRDRESSER = "SELECT sh.id sh_id, s.id s_id, h.id h_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating\n" +
            "FROM service_hairdresser sh\n" +
            "JOIN service s on sh.service_id = s.id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id;";
    public static final String SQL_SELECT_SERVICEHAIRDRESSER_BY_ID = "SELECT sh.id sh_id, s.id s_id, h.id h_id, s.name s_name, s.price s_price, h.name h_name, h.email h_email, h.rating h_rating\n" +
            "FROM service_hairdresser sh\n" +
            "JOIN service s on sh.service_id = s.id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "WHERE sh.id = ?;";
    public static final String SQL_INSERT_SERVICEHAIRDRESSER = "INSERT INTO service_hairdresser (service_id, hairdresser_id) VALUES (?, ?);";
    public static final String SQL_UPDATE_SERVICEHAIRDRESSER = "UPDATE service_hairdresser SET service_id = ?, hairdresser_id = ? WHERE id = ?;";
    public static final String SQL_DELETE_SERVICE_HAIRDRESSER = "DELETE FROM service_hairdresser WHERE id = ?;";

    public static final String SQL_SELECT_SERVICE = "SELECT * FROM service;";
    public static final String SQL_SELECT_SERVICE_BY_ID = "SELECT * FROM service WHERE id = ?;";
    public static final String SQL_INSERT_SERVICE = "INSERT INTO service(name, price) VALUES (?, ?);";
    public static final String SQL_UPDATE_SERVICE = "UPDATE service SET name = ?, price = ? WHERE id = ?;";
    public static final String SQL_DELETE_SERVICE = "DELETE FROM service WHERE id = ?;";

    public static final String SQL_SELECT_FEEDBACK = "SELECT f.id f_id, f.name f_name, f.rate f_rate, f.created f_created, r.id r_id,\n" +
            "       r.servicehairdresser_id r_servicehairdresser_id, r.client_id r_client_id,\n" +
            "       r.status r_status, r.date r_date, r.paid r_paid, sh.id sh_id, sh.service_id sh_service_id,\n" +
            "       sh.hairdresser_id sh_hairdresser_id, s.name s_name, s.price s_price,\n" +
            "       h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM feedback f\n" +
            "JOIN request r on f.request_id = r.id\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "ORDER BY created DESC;";
    public static final String SQL_SELECT_FEEDBACK_BY_ID = "SELECT f.id f_id, f.name f_name, f.rate f_rate, f.created f_created, r.id r_id,\n" +
            "       r.servicehairdresser_id r_servicehairdresser_id, r.client_id r_client_id,\n" +
            "       r.status r_status, r.date r_date, r.paid r_paid, sh.id sh_id, sh.service_id sh_service_id,\n" +
            "       sh.hairdresser_id sh_hairdresser_id, s.name s_name, s.price s_price,\n" +
            "       h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM feedback f\n" +
            "JOIN request r on f.request_id = r.id\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id" +
            "WHERE f.id = ?;";
    public static final String SQL_SELECT_FEEDBACKS_BY_HAIRDRESSER = "SELECT f.id f_id, f.name f_name, f.rate f_rate, f.created f_created, r.id r_id,\n" +
            "       r.servicehairdresser_id r_servicehairdresser_id, r.client_id r_client_id,\n" +
            "       r.status r_status, r.date r_date, r.paid r_paid, sh.id sh_id, sh.service_id sh_service_id,\n" +
            "       sh.hairdresser_id sh_hairdresser_id, s.name s_name, s.price s_price,\n" +
            "       h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM feedback f\n" +
            "JOIN request r on f.request_id = r.id\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "WHERE sh.hairdresser_id = ?\n" +
            "ORDER BY created DESC;";
    public static final String SQL_SELECT_FEEDBACK_BY_REQUEST = "SELECT f.id f_id, f.name f_name, f.rate f_rate, f.created f_created, r.id r_id,\n" +
            "       r.servicehairdresser_id r_servicehairdresser_id, r.client_id r_client_id,\n" +
            "       r.status r_status, r.date r_date, r.paid r_paid, sh.id sh_id, sh.service_id sh_service_id,\n" +
            "       sh.hairdresser_id sh_hairdresser_id, s.name s_name, s.price s_price,\n" +
            "       h.name h_name, h.email h_email, h.rating h_rating, c.name c_name, c.email c_email\n" +
            "FROM feedback f\n" +
            "JOIN request r on f.request_id = r.id\n" +
            "JOIN service_hairdresser sh on r.servicehairdresser_id = sh.id\n" +
            "JOIN service s on s.id = sh.service_id\n" +
            "JOIN usr h on h.id = sh.hairdresser_id\n" +
            "JOIN usr c on c.id = client_id\n" +
            "WHERE r.id = ?;";
    public static final String SQL_INSERT_FEEDBACK = "INSERT INTO feedback(request_id, name, rate) VALUES (?, ?, ?);";
}
