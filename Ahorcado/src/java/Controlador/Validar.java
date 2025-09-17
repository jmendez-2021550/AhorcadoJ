package Controlador;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ValidarLoginAhorcado", urlPatterns = {"/ValidarLoginAhorcado"})
public class Validar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");

        // Llama a la API de Spring Boot para obtener todos los usuarios
        URL url = new URL("http://localhost:8081/api/users"); //
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // Procesa el JSON manualmente (sin org.json)
        String json = content.toString();
        boolean loginExitoso = false;
        String nombreUsuario = "";

        String[] usuarios = json.split("\\},\\{");
        for (String usuario : usuarios) {
            // Busca los campos email y password
            String email = extraerCampo(usuario, "\"email\":\"");
            String pass = extraerCampo(usuario, "\"password\":\"");
            String username = extraerCampo(usuario, "\"username\":\"");

            if (email != null && pass != null && email.equalsIgnoreCase(correo) && pass.equals(password)) {
                loginExitoso = true;
                nombreUsuario = username != null ? username : "";
                break;
            }
        }

        if (loginExitoso) {
            HttpSession session = request.getSession();
            session.setAttribute("nombreUsuario", nombreUsuario);
            response.sendRedirect("index/Ahorcado.jsp");
        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }

    // Método auxiliar para extraer campos de un JSON plano
    private String extraerCampo(String texto, String campo) {
        int idx = texto.indexOf(campo);
        if (idx == -1) return null;
        int start = idx + campo.length();
        int end = texto.indexOf("\"", start);
        if (end == -1) return null;
        return texto.substring(start, end);
    }
}