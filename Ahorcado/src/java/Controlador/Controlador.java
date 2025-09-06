package Controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import org.jeremymendez.modelo.Palabras;
import org.jeremymendez.modelo.PalabrasDAO;
import org.jeremymendez.modelo.Usuarios;
import org.jeremymendez.modelo.UsuariosDAO;

@WebServlet(name = "ControladorAhorcado", urlPatterns = {"/ControladorAhorcado"})
public class Controlador extends HttpServlet {

    PalabrasDAO palabrasDao = new PalabrasDAO();
    UsuariosDAO usuariosDao = new UsuariosDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        // --- Lógica de Autenticación ---
        if ("Login".equals(menu)) {
            if ("ValidarIngreso".equals(accion)) {
                String email = request.getParameter("txtCorreo");
                String pass = request.getParameter("txtPassword");
                
                Usuarios usuario = usuariosDao.validar(email, pass);

                if (usuario != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogueado", usuario);

                    if (usuario.getTipoUsuario() == Usuarios.TipoUsuarios.Cliente) {
                        request.getRequestDispatcher("JSP/PrincipalCliente.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "Bienvenido, " + usuario.getNombreUsuario());
                        request.getRequestDispatcher("JSP/PrincipalCliente.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorLogin", "Correo o contraseña incorrectos");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else if ("CerrarSesion".equals(accion)) {
                HttpSession session = request.getSession();
                session.invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        // --- Lógica del Juego Ahorcado ---
        else if ("Juego".equals(menu) && "obtenerPalabra".equals(accion)) {
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    Palabras palabra = palabrasDao.obtenerPalabraAleatoria();

    if (palabra != null) {
        // Formato: palabra|pista1|pista2|pista3
        out.print(palabra.getPalabra() + "|" +
                  (palabra.getPista1() != null ? palabra.getPista1() : "") + "|" +
                  (palabra.getPista2() != null ? palabra.getPista2() : "") + "|" +
                  (palabra.getPista3() != null ? palabra.getPista3() : ""));
    } else {
        out.print("ERROR|No se encontró ninguna palabra|||");
    }
    out.flush();
    return;
}else {
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
}
