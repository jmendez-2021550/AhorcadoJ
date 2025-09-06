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
    <nav class="navbar">
        <div class="container">
            <div class="logo">Ahorc<span>ado</span></div>
        </div>
    </nav>
    <section>
        <div class="container">
            <div class="segundo_contenedor">
                <!-- Columna de información -->
                <div class="informacion">
                    <span class="line"></span>
                    <h2>Bienvenido al <br><span>Juego del Ahorcado</span></h2>
                    <p>Demuestra tu habilidad adivinando palabras antes de que se complete el ahorcado.</p>
                    </div>
                </div>

                <!-- Columna de login/registro -->
                <div class="login">
                    <div class="form">
                        <div class="text-center">
                            <h6><span>Iniciar Sesión</span> <span>Registrarse</span></h6>
                            <input type="checkbox" class="checkbox" id="reg-log">
                            <label for="reg-log"></label>
                            <div class="card-3d-wrap">
                                <div class="card-3d-wrapper">
                                    <!-- FORMULARIO DE LOGIN -->
                                    <div class="card-front">
                                        <div class="center-wrap">
                                            <form action="ValidarLoginAhorcado" method="Post">
                                                <h4 class="heading">Inicio de Sesión</h4>
                                                <div class="form-group">
                                                    <input type="text" name="txtCorreo" id="txtUsuario" class="form-style" placeholder="Usuario" autocomplete="off" required>
                                                    <i class="input-icon material-icons">person</i>
                                                </div>
                                                <div class="form-group">
                                                    <input type="password" name="txtPassword" id="txtPassword" class="form-style" placeholder="Contraseña" autocomplete="off" required>
                                                    <i class="input-icon material-icons">lock</i>
                                                </div>
                                                <center>
                                                    <input type="submit" class="btnIniciar" name="accion" value="Ingresar">
                                                </center>
                                                <p class="text-center">
                                                    <a href="#" class="link">¿Olvidaste tu contraseña?</a>
                                                </p>
                                            </form>
                                        </div>
                                    </div>
                                    <!-- FORMULARIO DE REGISTRO -->
                                    <div class="card-back">
                                        <div class="center-wrap">
                                            <form action="Controlador?menu=Usuarios&accion=RegistroLogin" method="post">
                                                <h4 class="heading">Registrarse</h4>
                                                <div class="form-group">
                                                    <input type="text" name="txtUsuarioR" id="usuario_registro" class="form-style" placeholder="Usuario" autocomplete="off" required>
                                                    <i class="input-icon material-icons">person</i>
                                                </div>
                                                <div class="form-group">
                                                    <input type="password" name="txtPasswordR" id="password_registro" class="form-style" placeholder="Contraseña" autocomplete="off" required>
                                                    <i class="input-icon material-icons">lock</i>
                                                </div>
                                                <div class="form-group">
                                                    <input type="password" name="confirmar" id="confirmar_registro" class="form-style" placeholder="Confirmar contraseña" autocomplete="off" required>
                                                    <i class="input-icon material-icons">lock</i>
                                                </div>
                                                <center>
                                                    <button type="submit" class="btnRegistrar">Registrarme</button>
                                                </center>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- /login -->
            </div>
        </div>
    </section>
</body>
</html>
