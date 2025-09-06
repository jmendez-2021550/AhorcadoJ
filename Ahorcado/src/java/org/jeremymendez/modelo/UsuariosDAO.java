package org.jeremymendez.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jeremymendez.config.Conexion;

/**
 * Clase de acceso a datos para la tabla Usuarios.
 * @author informatica
 */
public class UsuariosDAO {
        Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    Conexion cn = new Conexion();
    
    /**
     * Valida las credenciales de un usuario.
     * @param correo El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     * @return Un objeto Usuarios si las credenciales son correctas, de lo contrario, null.
     */
    public Usuarios validar(String correo, String contrasena) {
        Usuarios user = null;
        String sql = "SELECT * FROM Usuarios WHERE correo = ? AND contrasena = ?";
        
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new Usuarios();
                user.setCodigoUsuario(rs.getInt("codigoUsuario"));
                user.setNombreUsuario(rs.getString("nombreUsuario"));
                user.setCorreo(rs.getString("correo"));
                user.setContrasena(rs.getString("contrasena"));
                
                // Mapear el tipo de usuario del ENUM
                user.setTipoUsuario(Usuarios.TipoUsuarios.valueOf(rs.getString("tipoUsuario")));
            }
        } catch (SQLException e) {
            // Manejar errores de la base de datos
            System.out.println("Error al validar el usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return user;
    }
}
