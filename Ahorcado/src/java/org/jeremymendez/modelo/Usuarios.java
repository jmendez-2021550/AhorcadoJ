package org.jeremymendez.modelo;

/**
 * Clase modelo para representar un usuario.
 * @author informatica
 */
public class Usuarios {

    // Enum para definir los tipos de usuario
    public enum TipoUsuarios {
        Cliente,
        Empleado
    }

    private int codigoUsuario;
    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private TipoUsuarios tipoUsuario;

    public Usuarios() {
    }

    public Usuarios(int codigoUsuario, String nombreUsuario, String correo, String contrasena, TipoUsuarios tipoUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoUsuarios getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarios tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
