 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nombreUsuario = (String) session.getAttribute("nombreUsuario");
    if (nombreUsuario == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Juego del Ahorcado</title>
    <link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
    <div id="game-container">
        <h1>Bienvenido al Ahorcado</h1>
        <div id="hangman-drawing">
            <img id="hangman-image" src="../image/Cuerdaa.png" alt="Ahorcado">
        </div>
        <div id="word-display"></div>
        <div id="keyboard"></div>
        <div id="message"></div>
        <div id="controls">
            <button id="start-btn" class="hidden">Iniciar</button>
            <button id="restart-btn" class="hidden">Reiniciar</button>
            <button id="clue-btn">Pista</button>
        </div>
    </div>
    <script src="../js/script.js"></script>
</body>
</html>