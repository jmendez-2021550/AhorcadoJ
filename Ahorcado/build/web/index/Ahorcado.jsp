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
    <title>Juego del Ahorcado - Salva al Estudiante</title>
    <link rel="stylesheet" href="../css/estilo.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
</head>
<body>
    <!-- Header del juego -->
    <header class="game-header">
        <div class="header-container">
            <div class="game-logo">
                <i class="fas fa-dice game-icon"></i>
                <h1 class="game-title">Ahorc<span>ado</span></h1>
                <p class="game-subtitle">Salva al Estudiante de Kinal</p>
            </div>
            
            <!-- Panel de información del jugador -->
            <div class="player-info">
                <div class="player-welcome">
                    <i class="fas fa-user-circle player-icon"></i>
                    <div class="player-details">
                        <span class="welcome-text">Bienvenido</span>
                        <span class="player-name"><%= nombreUsuario %></span>
                    </div>
                </div>
                
                <!-- Botón para regresar al login -->
                <button class="back-to-login-btn" onclick="window.location.href='../index.jsp'" title="Regresar al Login">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Salir</span>
                </button>
            </div>
        </div>
    </header>

    <!-- Panel principal del juego -->
    <main class="game-main">
        <div class="game-wrapper">
            
            <!-- Panel de estadísticas y timer -->
            <aside class="game-stats-panel">
                <div class="stats-header">
                    <i class="fas fa-clock stats-icon"></i>
                    <h3>Estadisticas del Juego</h3>
                </div>
                
                <!-- Contador de tiempo -->
                <div class="timer-section">
                    <div class="timer-display">
                        <div class="timer-circle">
                            <div class="timer-progress"></div>
                            <div class="timer-content">
                                <span id="timer-minutes">03</span>
                                <span class="timer-separator">:</span>
                                <span id="timer-seconds">00</span>
                            </div>
                        </div>
                    </div>
                    <p class="timer-label">Tiempo Restante</p>
                    <div class="timer-status" id="timer-status">
                        <i class="fas fa-play-circle"></i>
                        <span>Listo para comenzar</span>
                    </div>
                </div>
                
                <!-- Información del juego -->
                <div class="game-info">
                    <div class="info-item">
                        <i class="fas fa-heart life-icon"></i>
                        <div class="info-content">
                            <span class="info-label">Intentos</span>
                            <span class="info-value" id="attempts-display">7/7</span>
                        </div>
                    </div>
                    
                    <div class="info-item">
                        <i class="fas fa-lightbulb hint-icon"></i>
                        <div class="info-content">
                            <span class="info-label">Pistas</span>
                            <span class="info-value" id="hints-display">3</span>
                        </div>
                    </div>
                    
                    <div class="info-item">
                        <i class="fas fa-trophy score-icon"></i>
                        <div class="info-content">
                            <span class="info-label">Puntuación</span>
                            <span class="info-value" id="score-display">0</span>
                        </div>
                    </div>
                </div>
                
                <!-- Mascota motivacional -->
                <div class="mascot-motivation">
                    <div class="mascot-bubble">
                        <i class="fas fa-comment-dots"></i>
                        <span id="mascot-message">¡Sálvame! Adivina la palabra</span>
                    </div>
                    <div class="mascot-status" id="mascot-status">
                        <i class="fas fa-heartbeat"></i>
                        <span>En peligro</span>
                    </div>
                </div>
            </aside>

            <!-- Área de juego principal -->
            <section class="game-area">
                <div id="game-container">
                    
                    <!-- Título del juego -->
                    <div class="game-title-section">
                        <h2 class="main-game-title">Juego del Ahorcado</h2>
                        <div class="title-decoration">
                            <div class="deco-line"></div>
                            <i class="fas fa-skull-crossbones danger-icon"></i>
                            <div class="deco-line"></div>
                        </div>
                        <p class="game-description">Adivina la palabra antes de que se complete el ahorcado</p>
                    </div>
                    
                    <!-- Dibujo del ahorcado mejorado -->
                    <div id="hangman-drawing">
                        <div class="hangman-stage">
                            <img id="hangman-image" src="../image/Cuerdaa.png" alt="Estado del Ahorcado">
                            <div class="danger-indicator" id="danger-level">
                                <div class="danger-bar">
                                    <div class="danger-fill" id="danger-fill"></div>
                                </div>
                                <span class="danger-text">Nivel de Peligro</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Display de la palabra -->
                    <div class="word-section">
                        <div id="word-display"></div>
                        <div class="word-category" id="word-category">
                        </div>
                    </div>
                    
                    <!-- Teclado mejorado -->
                    <div class="keyboard-section">
                        <div class="keyboard-header">
                            <i class="fas fa-keyboard"></i>
                            <span>Selecciona una letra</span>
                        </div>
                        <div id="keyboard"></div>
                        <div class="keyboard-legend">
                            <div class="legend-item">
                                <div class="legend-color correct"></div>
                                <span>Correcta</span>
                            </div>
                            <div class="legend-item">
                                <div class="legend-color incorrect"></div>
                                <span>Incorrecta</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Mensajes del juego -->
                    <div class="message-section">
                        <div id="message"></div>
                        <div class="message-decoration">
                            <div class="pulse-dot"></div>
                            <div class="pulse-dot"></div>
                            <div class="pulse-dot"></div>
                        </div>
                    </div>
                    
                    <!-- Controles del juego -->
                    <div id="controls">
                        <div class="controls-group">
                            <button id="start-btn" class="control-btn start-btn hidden">
                                <i class="fas fa-play"></i>
                                <span>Comenzar Aventura</span>
                            </button>
                            <button id="restart-btn" class="control-btn restart-btn hidden">
                                <i class="fas fa-redo"></i>
                                <span>Nueva Partida</span>
                            </button>
                            <button id="clue-btn" class="control-btn clue-btn">
                                <i class="fas fa-lightbulb"></i>
                                <span>Usar Pista</span>
                            </button>
                        </div>
                    </div>
                    
                </div>
            </section>

        </div>
    </main>

    <!-- Footer informativo -->
    <footer class="game-footer">
        <div class="footer-content">
            <div class="footer-info">
                <i class="fas fa-info-circle"></i>
                <span>Tienes 3 minutos y 7 intentos para salvar al estudiante</span>
            </div>
            <div class="footer-copyright">
                <i class="fas fa-graduation-cap"></i>
                <span>Instituto Kinal - Juego Educativo</span>
            </div>
        </div>
    </footer>

    <script src="../js/script.js"></script>
</body>
</html>