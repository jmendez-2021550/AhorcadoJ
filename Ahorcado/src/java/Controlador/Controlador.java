package Controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@WebServlet(name = "ControladorAhorcado", urlPatterns = {"/ControladorAhorcado"})
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        if ("Juego".equals(menu) && "obtenerPalabra".equals(accion)) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            try {
                URL url = new URL("http://localhost:8081/api/words"); //
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
                String[] palabras = json.split("\\},\\{");
                if (palabras.length > 0) {
                    int idx = (int) (Math.random() * palabras.length);
                    String palabra = palabras[idx];

                    String word = extraerCampo(palabra, "\"word\":\"");
                    String hint1 = extraerCampo(palabra, "\"hint1\":\"");
                    String hint2 = extraerCampo(palabra, "\"hint2\":\"");
                    String hint3 = extraerCampo(palabra, "\"hint3\":\"");

                    out.print((word != null ? word : "") + "|" +
                              (hint1 != null ? hint1 : "") + "|" +
                              (hint2 != null ? hint2 : "") + "|" +
                              (hint3 != null ? hint3 : ""));
                } else {
                    out.print("ERROR|No se encontró ninguna palabra|||");
                }
            } catch (Exception e) {
                out.print("ERROR|No se pudo conectar con la API de palabras|||");
            }
            out.flush();
            return;
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
