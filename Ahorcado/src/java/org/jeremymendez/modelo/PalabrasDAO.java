package org.jeremymendez.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jeremymendez.config.Conexion;

/**
 * Clase de acceso a datos para la tabla Palabras.
 * @author informatica
 */
public class PalabrasDAO {
    
    // Conexión a la base de datos
    Connection con;
    // Sentencia SQL precompilada
    PreparedStatement ps;
    // Resultado de la consulta SQL
    ResultSet rs;
    
    // Objeto para la conexión
    Conexion cn = new Conexion();
    
    /**
     * Obtiene una palabra aleatoria de la base de datos junto con sus pistas.
     * @return Un objeto Palabras con una palabra y sus pistas, de lo contrario, null.
     */
    public Palabras obtenerPalabraAleatoria() {
        Palabras palabra = null;
        String sql = "SELECT * FROM Palabras ORDER BY RAND() LIMIT 1";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabras();
                palabra.setId(rs.getInt("id"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista1(rs.getString("pista1"));
                palabra.setPista2(rs.getString("pista2"));
                palabra.setPista3(rs.getString("pista3"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palabra;
    }

    /**
     * Obtiene todas las palabras de la base de datos.
     * @return Una lista de objetos Palabras con todas las palabras y sus pistas.
     */
    public ArrayList<Palabras> obtenerTodas() {
        ArrayList<Palabras> lista = new ArrayList<>();
        String sql = "SELECT * FROM Palabras";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Palabras p = new Palabras();
                p.setId(rs.getInt("id"));
                p.setPalabra(rs.getString("palabra"));
                p.setPista1(rs.getString("pista1"));
                p.setPista2(rs.getString("pista2"));
                p.setPista3(rs.getString("pista3"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
