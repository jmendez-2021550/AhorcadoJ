package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jeremymendez.modelo.Usuarios;
import org.jeremymendez.modelo.UsuariosDAO;

@WebServlet(name = "ValidarLoginAhorcado", urlPatterns = {"/ValidarLoginAhorcado"})
public class Validar extends HttpServlet {

    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Usuarios usuario = new Usuarios();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Este método es para la respuesta HTML directa si es necesario,
            // pero usualmente se redirige o se usa response.sendRedirect.
            // Para el login, nos centraremos en la lógica de doPost.
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * Redirige a la página de inicio de sesión si se accede por GET.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si alguien intenta acceder directamente a ValidarLoginAhorcado via GET, lo mandamos al index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Procesa la solicitud de inicio de sesión.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String accion = request.getParameter("accion");
    if ("Ingresar".equalsIgnoreCase(accion)) {
        String email = request.getParameter("txtCorreo");
        String pass = request.getParameter("txtPassword");

        // Validar las credenciales del usuario
        usuario = usuariosDAO.validar(email, pass);

        if (usuario != null) {
            // Crear sesión y guardar atributos del usuario
            HttpSession session = request.getSession();
            session.setAttribute("codigoUsuario", usuario.getCodigoUsuario());
            session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
            session.setAttribute("tipoUsuario", usuario.getTipoUsuario().name());

            // Redirigir directamente a Ahorcado.jsp
            response.sendRedirect("index/Ahorcado.jsp");

        } else {
            // Si la validación falla, mostrar mensaje de error y redirigir al índice
            request.setAttribute("errorLogin", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    } else {
        // Si la acción no es "Ingresar", redirigir al índice
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para validar credenciales de usuario en el login";
    }
    // </editor-fold>
}