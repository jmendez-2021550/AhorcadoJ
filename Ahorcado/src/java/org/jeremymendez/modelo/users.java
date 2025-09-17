package org.jeremymendez.modelo;

/**
 * Clase modelo para la tabla 'users' de la base de datos AhorcadoDB.
 */
public class users {
    public enum user_type {
        Client,   
        Employee 
    }

    private int user_id;
    private String username;
    private String email;
    private String password;
    private user_type user_type;  

    // Constructores
    public users() {}

    public users(int user_id, String username, String email, String password, user_type user_type) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
    }

    // Getters y Setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public user_type getUser_type() {
        return user_type;
    }

    public void setUser_type(user_type user_type) {
        this.user_type = user_type;
    }
}
