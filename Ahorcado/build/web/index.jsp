<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ahorcado - Login</title>
    <link rel="icon" type="image/x-icon" href="Images/ahorcado-icon.png">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/material-icons@1.13.12/iconfont/material-icons.min.css">
</head>
<body>
    <!-- Header con navegación mejorada -->
    <header class="main-header">
        <nav class="navbar">
            <div class="container">
                <div class="nav-content">
                    <div class="logo-section">
                        <i class="fas fa-dice game-icon"></i>
                        <div class="logo">Ahorc<span>ado</span></div>
                        <div class="logo-subtitle">Juego de Palabras</div>
                    </div>
                    <div class="nav-decorations">
                        <div class="floating-letters">
                            <span class="letter">A</span>
                            <span class="letter">B</span>
                            <span class="letter">C</span>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </header>

    <!-- Contenido principal -->
    <main class="main-content">
        <div class="background-pattern"></div>
        <div class="container">
            <div class="game-layout">
                
                <!-- Panel de bienvenida mejorado -->
                <section class="welcome-panel">
                    <div class="panel-header">
                        <i class="fas fa-gamepad welcome-icon"></i>
                        <div class="decorative-line"></div>
                    </div>
                    <div class="welcome-content">
                        <h2 class="welcome-title">
                            <span class="title-main">Bienvenido al</span>
                            <span class="title-game">Juego del Ahorcado</span>
                        </h2>
                        <div class="game-description">
                            <p class="description-text">
                                <i class="fas fa-brain brain-icon"></i>
                                Demuestra tu habilidad adivinando palabras antes de que se complete el ahorcado.
                            </p>
                            <p class="challenge-text">
                                <i class="fas fa-trophy trophy-icon"></i>
                                ¿Seras capaz de salvar al alumno de <strong>Kinal</strong>?
                            </p>
                        </div>
                        <div class="game-stats">
                            <div class="stat-item">
                                <i class="fas fa-clock"></i>
                                <span>Tiempo ilimitado</span>
                            </div>
                            <div class="stat-item">
                                <i class="fas fa-heart"></i>
                                <span>6 intentos</span>
                            </div>
                            <div class="stat-item">
                                <i class="fas fa-star"></i>
                                <span>Multiples niveles</span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Mascota del juego (Kinalero) -->
                <aside class="mascot-section">
                    <div class="mascot-container">
                        <div class="mascot-frame">
                            <a href="image/kinalero.png" target="_blank" title="Ver imagen del alumno Kinal">
                                <img src="image/kinalero.png" alt="Alumno Kinal - Mascota del juego" class="kinalero-img">
                            </a>
                            <div class="mascot-speech">
                                <i class="fas fa-comment-dots"></i>
                                <span>¡Ayudame!</span>
                            </div>
                        </div>
                        <div class="mascot-info">
                            <h3 class="mascot-name">Estudiante Kinal</h3>
                            <p class="mascot-description">Tu mision es salvarlo</p>
                        </div>
                    </div>
                    <div class="gallows-preview">
                        <i class="fas fa-tools"></i>
                        <span>Horca preparada</span>
                    </div>
                </aside>

                <!-- Panel de login mejorado -->
                <section class="login-panel">
                    <div class="login-container">
                        <div class="login-header">
                            <div class="login-icon-bg">
                                <i class="fas fa-user-circle login-main-icon"></i>
                            </div>
                            <h3 class="login-title">Inicio de Sesion</h3>
                            <p class="login-subtitle">Accede para comenzar tu aventura</p>
                        </div>
                        
                        <form action="ValidarLoginAhorcado" method="Post" class="login-form">
                            <div class="form-section">
                                <div class="input-group">
                                    <div class="input-wrapper">
                                        <i class="input-icon material-icons">person</i>
                                        <input type="text" name="txtCorreo" id="txtUsuario" class="form-control" placeholder="Ingresa tu usuario" autocomplete="off" required>
                                        <div class="input-underline"></div>
                                    </div>
                                </div>
                                
                                <div class="input-group">
                                    <div class="input-wrapper">
                                        <i class="input-icon material-icons">lock</i>
                                        <input type="password" name="txtPassword" id="txtPassword" class="form-control" placeholder="Ingresa tu contraseña" autocomplete="off" required>
                                        <div class="input-underline"></div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-actions">
                                <button type="submit" class="btn-login" name="accion" value="Ingresar">
                                    <i class="fas fa-sign-in-alt"></i>
                                    <span>Comenzar Juego</span>
                                    <div class="btn-shine"></div>
                                </button>
                            </div>
                            
                            <div class="login-footer">
                                <div class="security-badge">
                                    <i class="fas fa-shield-alt"></i>
                                    <span>Conexión segura</span>
                                </div>
                            </div>
                        </form>
                    </div>
                </section>

            </div>
        </div>
    </main>

    <!-- Footer decorativo -->
    <footer class="game-footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-game-info">
                    <i class="fas fa-dice-d20"></i>
                    <span>Juego Educativo - Instituto Kinal</span>
                </div>
                <div class="footer-social">
                    <a href="#" class="social-link" title="Facebook">
                        <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" class="social-link" title="Twitter">
                        <i class="fab fa-twitter"></i>
                    </a>
                    <a href="#" class="social-link" title="Instagram">
                        <i class="fab fa-instagram"></i>
                    </a>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>