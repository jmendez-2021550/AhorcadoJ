let selectedWord = "";
let guessedLetters = [];
let attempts = 7;
const maxAttempts = 7;
let clueIndex = 0;
let currentClues = [];
let gameTimer = null;
let timeLeft = 180; // 3 minutos en segundos
let gameActive = false;
let score = 0;
let hintsUsed = 0;

// Referencias a elementos del DOM
const wordDisplay = document.getElementById("word-display");
const keyboard = document.getElementById("keyboard");
const message = document.getElementById("message");
const startBtn = document.getElementById("start-btn");
const restartBtn = document.getElementById("restart-btn");
const hangmanImage = document.getElementById("hangman-image");
const clueBtn = document.getElementById("clue-btn");

// Elementos del timer
const timerMinutes = document.getElementById("timer-minutes");
const timerSeconds = document.getElementById("timer-seconds");
const timerStatus = document.getElementById("timer-status");

// Elementos de estadísticas
const attemptsDisplay = document.getElementById("attempts-display");
const hintsDisplay = document.getElementById("hints-display");
const scoreDisplay = document.getElementById("score-display");
const dangerFill = document.getElementById("danger-fill");
const mascotMessage = document.getElementById("mascot-message");
const mascotStatus = document.getElementById("mascot-status");

// Mensajes motivacionales de la mascota
const mascotMessages = {
    start: "¡Salvame! Adivina la palabra",
    progress: "¡Vas bien! Sigue asi",
    danger: "¡Cuidado! Estoy en peligro",
    critical: "¡Ayudame! ¡Tiempo limitado!",
    saved: "¡Gracias por salvarme!",
    lost: "Oh no... me atraparon"
};

// Función para obtener nueva palabra del servidor
async function obtenerNuevaPalabra() {
    try {
        const response = await fetch("../ControladorAhorcado?menu=Juego&accion=obtenerPalabra");
        if (!response.ok) {
            throw new Error('Error al obtener la palabra del servidor: ' + response.statusText);
        }
        const text = await response.text();
        const partes = text.split("|");
        if (partes[0] === "ERROR") {
            message.textContent = partes[1];
            return null;
        }
        return {
            word: partes[0].toUpperCase(),
            clues: [partes[1], partes[2], partes[3]].filter(Boolean)
        };
    } catch (error) {
        console.error("Error en la solicitud:", error);
        message.textContent = "No se pudieron cargar las palabras. Inténtalo de nuevo.";
        return null;
    }
}

// Inicialización al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    initializeGame();
});

// Función de inicialización
function initializeGame() {
    message.textContent = "¡Bienvenido al juego del Ahorcado!";
    startBtn.classList.remove("hidden");
    restartBtn.classList.add("hidden");
    clueBtn.classList.add("hidden");
    wordDisplay.textContent = "Bienvenido a la Aventura";
    keyboard.innerHTML = "";
    hangmanImage.src = "../image/Cuerdaa.png";
    
    // Reset timer display
    resetTimer();
    updateMascotMessage('start');
    updateStats();
    updateDangerLevel(0);
}

// Event listeners
startBtn.addEventListener("click", startGame);
restartBtn.addEventListener("click", startGame);
clueBtn.addEventListener("click", useClue);

// Función principal para iniciar el juego
async function startGame() {
    const randomWordData = await obtenerNuevaPalabra();
    if (!randomWordData) {
        return;
    }

    // Reset game state
    selectedWord = randomWordData.word;
    guessedLetters = [];
    attempts = maxAttempts;
    clueIndex = 0;
    currentClues = randomWordData.clues;
    gameActive = true;
    timeLeft = 180; // Reset timer to 3 minutes
    hintsUsed = 0;

    // Update UI
    startBtn.classList.add("hidden");
    restartBtn.classList.remove("hidden");
    clueBtn.classList.remove("hidden");
    message.textContent = "¡Comienza a adivinar las letras!";
    hangmanImage.src = "../image/Cuerdaa.png";
    wordDisplay.textContent = "_ ".repeat(selectedWord.length);
    
    // Remove game state classes
    document.body.classList.remove('game-won', 'game-lost', 'time-warning');
    
    createKeyboard();
    startTimer();
    updateStats();
    updateDangerLevel(0);
    updateMascotMessage('start');
    updateMascotStatus('En peligro', 'fas fa-heartbeat', '#ef4444');
}

// Función del timer
function startTimer() {
    // Clear existing timer
    if (gameTimer) {
        clearInterval(gameTimer);
    }
    
    updateTimerDisplay();
    updateTimerStatus('Tiempo corriendo', 'fas fa-clock', '#3b82f6');
    
    gameTimer = setInterval(() => {
        timeLeft--;
        updateTimerDisplay();
        updateTimerProgress();
        
        // Warning when 30 seconds left
        if (timeLeft <= 30 && timeLeft > 0) {
            document.body.classList.add('time-warning');
            updateTimerStatus('¡Tiempo crítico!', 'fas fa-exclamation-triangle', '#ef4444');
            if (timeLeft <= 10) {
                updateMascotMessage('critical');
            }
        }
        
        // Time's up
        if (timeLeft <= 0) {
            timeUp();
        }
    }, 1000);
}

function updateTimerDisplay() {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    timerMinutes.textContent = minutes.toString().padStart(2, '0');
    timerSeconds.textContent = seconds.toString().padStart(2, '0');
}

function updateTimerProgress() {
    const progress = ((180 - timeLeft) / 180) * 360;
    const timerCircle = document.querySelector('.timer-circle');
    
    if (timeLeft <= 30) {
        timerCircle.style.background = `conic-gradient(#ef4444 ${progress}deg, rgba(239, 68, 68, 0.2) ${progress}deg)`;
    } else {
        timerCircle.style.background = `conic-gradient(#3b82f6 ${progress}deg, rgba(59, 130, 246, 0.2) ${progress}deg)`;
    }
}

function updateTimerStatus(text, icon, color) {
    const statusElement = document.getElementById('timer-status');
    statusElement.innerHTML = `
        <i class="${icon}" style="color: ${color}"></i>
        <span>${text}</span>
    `;
}

function resetTimer() {
    if (gameTimer) {
        clearInterval(gameTimer);
        gameTimer = null;
    }
    timeLeft = 180;
    updateTimerDisplay();
    updateTimerStatus('Listo para comenzar', 'fas fa-play-circle', '#3b82f6');
    
    const timerCircle = document.querySelector('.timer-circle');
    timerCircle.style.background = `conic-gradient(#3b82f6 0deg, rgba(59, 130, 246, 0.2) 0deg)`;
    document.body.classList.remove('time-warning');
}

// Función para cuando se acaba el tiempo
function timeUp() {
    gameActive = false;
    clearInterval(gameTimer);
    message.textContent = `¡Tiempo agotado! La palabra era: ${selectedWord}`;
    updateTimerStatus('Tiempo agotado', 'fas fa-hourglass-end', '#ef4444');
    updateMascotMessage('lost');
    updateMascotStatus('Perdido', 'fas fa-dizzy', '#ef4444');
    document.body.classList.add('game-lost');
    disableKeyboard();
}

// Crear teclado
function createKeyboard() {
    keyboard.innerHTML = "";
    for (let i = 65; i <= 90; i++) {
        const letter = String.fromCharCode(i);
        const button = document.createElement("button");
        button.textContent = letter;
        button.className = "keyboard-btn";
        button.addEventListener("click", () => guessLetter(button, letter));
        keyboard.appendChild(button);
    }
}

// Función para adivinar letra
function guessLetter(button, letter) {
    if (!gameActive || button.classList.contains("guessed")) {
        return;
    }

    button.classList.add("guessed");
    let correctGuess = false;

    if (selectedWord.includes(letter)) {
        correctGuess = true;
        button.classList.add("correct");
        guessedLetters.push(letter);
        message.textContent = "¡Letra correcta! ¡Bien hecho!";
        updateMascotMessage('progress');
        
        // Add points for correct guess
        score += 10;
        if (timeLeft > 120) score += 5; // Bonus for early correct guess
    } else {
        attempts--;
        button.classList.add("incorrect");
        const errores = maxAttempts - attempts;
        if (errores > 0) {
            hangmanImage.src = `../image/Fase${errores}.png`;
        }
        message.textContent = `¡Letra incorrecta! Te quedan ${attempts} intentos.`;
        
        if (attempts <= 2) {
            updateMascotMessage('danger');
        }
        
        // Update danger level
        updateDangerLevel(errores);
    }

    // Update word display
    let displayWord = "";
    for (const char of selectedWord) {
        displayWord += guessedLetters.includes(char) ? char + " " : "_ ";
    }
    wordDisplay.textContent = displayWord.trim();

    updateStats();
    checkGameStatus();
}

// Usar pista
function useClue() {
    if (!gameActive) return;
    
    if (currentClues.length > 0 && clueIndex < currentClues.length) {
        message.textContent = `💡 Pista: ${currentClues[clueIndex]}`;
        clueIndex++;
        hintsUsed++;
        
        // Penalize score for using hint
        score = Math.max(0, score - 5);
        
        updateStats();
        
        if (clueIndex >= currentClues.length) {
            clueBtn.disabled = true;
            clueBtn.innerHTML = `
                <i class="fas fa-times"></i>
                <span>Sin pistas</span>
            `;
        }
    } else {
        message.textContent = "No hay más pistas disponibles.";
    }
}

// Verificar estado del juego
function checkGameStatus() {
    if (!wordDisplay.textContent.includes("_")) {
        // Game won
        gameWon();
    } else if (attempts === 0) {
        // Game lost
        gameLost();
    }
}

function gameWon() {
    gameActive = false;
    clearInterval(gameTimer);
    
    // Calculate bonus points
    const timeBonus = Math.floor(timeLeft / 6); // 1 point per 6 seconds left
    const attemptBonus = attempts * 5; // 5 points per remaining attempt
    const hintPenalty = hintsUsed * 3; // 3 point penalty per hint used
    
    score += timeBonus + attemptBonus - hintPenalty;
    
    message.textContent = `¡Felicidades, ganaste! 🎉 Puntuación: ${score}`;
    updateTimerStatus('¡Completado!', 'fas fa-check-circle', '#22c55e');
    updateMascotMessage('saved');
    updateMascotStatus('¡Salvado!', 'fas fa-heart', '#22c55e');
    document.body.classList.add('game-won');
    disableKeyboard();
    updateStats();
}

function gameLost() {
    gameActive = false;
    clearInterval(gameTimer);
    message.textContent = `Perdiste. La palabra era: ${selectedWord}`;
    updateTimerStatus('Game Over', 'fas fa-times-circle', '#ef4444');
    updateMascotMessage('lost');
    updateMascotStatus('Perdido', 'fas fa-dizzy', '#ef4444');
    document.body.classList.add('game-lost');
    disableKeyboard();
}

// Deshabilitar teclado
function disableKeyboard() {
    const buttons = keyboard.getElementsByTagName('button');
    for (const button of buttons) {
        button.disabled = true;
    }
}

// Actualizar estadísticas
function updateStats() {
    attemptsDisplay.textContent = `${attempts}/${maxAttempts}`;
    hintsDisplay.textContent = `${Math.max(0, currentClues.length - hintsUsed)}`;
    scoreDisplay.textContent = score.toString();
}

// Actualizar nivel de peligro
function updateDangerLevel(errors) {
    const percentage = (errors / maxAttempts) * 100;
    dangerFill.style.width = `${percentage}%`;
    
    if (errors === 0) {
        dangerFill.style.background = 'linear-gradient(90deg, #10b981, #22c55e)';
    } else if (errors <= 2) {
        dangerFill.style.background = 'linear-gradient(90deg, #10b981, #f59e0b)';
    } else if (errors <= 4) {
        dangerFill.style.background = 'linear-gradient(90deg, #f59e0b, #ef4444)';
    } else {
        dangerFill.style.background = 'linear-gradient(90deg, #ef4444, #dc2626)';
    }
}

// Actualizar mensaje de la mascota
function updateMascotMessage(state) {
    if (mascotMessages[state]) {
        mascotMessage.textContent = mascotMessages[state];
    }
}

// Actualizar estado de la mascota
function updateMascotStatus(text, icon, color) {
    mascotStatus.innerHTML = `
        <i class="${icon}" style="color: ${color}"></i>
        <span>${text}</span>
    `;
}

// Efectos de sonido simulados con vibración (si está disponible)
function playFeedbackEffect(type) {
    if ('vibrate' in navigator) {
        switch(type) {
            case 'correct':
                navigator.vibrate([50, 30, 50]);
                break;
            case 'incorrect':
                navigator.vibrate([100, 50, 100, 50, 100]);
                break;
            case 'win':
                navigator.vibrate([200, 100, 200, 100, 200]);
                break;
            case 'lose':
                navigator.vibrate([500]);
                break;
        }
    }
}

// Mejorar la función guessLetter con efectos
function guessLetterWithEffects(button, letter) {
    if (!gameActive || button.classList.contains("guessed")) {
        return;
    }

    button.classList.add("guessed");
    let correctGuess = false;

    if (selectedWord.includes(letter)) {
        correctGuess = true;
        button.classList.add("correct");
        guessedLetters.push(letter);
        message.textContent = "¡Letra correcta! ¡Bien hecho!";
        updateMascotMessage('progress');
        playFeedbackEffect('correct');
        
        // Add points for correct guess
        score += 10;
        if (timeLeft > 120) score += 5; // Bonus for early correct guess
    } else {
        attempts--;
        button.classList.add("incorrect");
        const errores = maxAttempts - attempts;
        if (errores > 0) {
            hangmanImage.src = `../image/Fase${errores}.png`;
        }
        message.textContent = `¡Letra incorrecta! Te quedan ${attempts} intentos.`;
        playFeedbackEffect('incorrect');
        
        if (attempts <= 2) {
            updateMascotMessage('danger');
        }
        
        // Update danger level
        updateDangerLevel(errores);
    }

    // Update word display
    let displayWord = "";
    for (const char of selectedWord) {
        displayWord += guessedLetters.includes(char) ? char + " " : "_ ";
    }
    wordDisplay.textContent = displayWord.trim();

    updateStats();
    checkGameStatus();
}

// Reemplazar la función original con la mejorada
window.guessLetter = guessLetterWithEffects;

// Función para manejar teclas del teclado físico
document.addEventListener('keydown', (event) => {
    if (!gameActive) return;
    
    const key = event.key.toUpperCase();
    if (key >= 'A' && key <= 'Z') {
        const button = Array.from(keyboard.children).find(btn => 
            btn.textContent === key && !btn.classList.contains('guessed')
        );
        if (button) {
            guessLetter(button, key);
        }
    }
    
    // Teclas especiales
    if (event.key === 'Enter' && startBtn && !startBtn.classList.contains('hidden')) {
        startGame();
    }
    if (event.key === 'r' || event.key === 'R') {
        if (restartBtn && !restartBtn.classList.contains('hidden')) {
            startGame();
        }
    }
    if (event.key === 'h' || event.key === 'H') {
        if (clueBtn && !clueBtn.classList.contains('hidden') && !clueBtn.disabled) {
            useClue();
        }
    }
});

// Función para formatear tiempo restante en texto
function formatTimeLeft() {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    return `${minutes}:${seconds.toString().padStart(2, '0')}`;
}

// Función para guardar estadísticas del juego (simulada)
function saveGameStats(won) {
    const gameStats = {
        word: selectedWord,
        won: won,
        timeUsed: 180 - timeLeft,
        attemptsUsed: maxAttempts - attempts,
        hintsUsed: hintsUsed,
        score: score,
        timestamp: new Date().toISOString()
    };
    
    // En una implementación real, esto se enviaría al servidor
    console.log('Game Stats:', gameStats);
    
    // Guardar en localStorage para demo
    try {
        const allStats = JSON.parse(localStorage.getItem('ahorcadoStats') || '[]');
        allStats.push(gameStats);
        // Mantener solo las últimas 10 partidas
        if (allStats.length > 10) {
            allStats.splice(0, allStats.length - 10);
        }
        localStorage.setItem('ahorcadoStats', JSON.stringify(allStats));
    } catch (e) {
        // Ignore localStorage errors in restricted environments
        console.log('Could not save stats to localStorage');
    }
}

// Mejorar las funciones de victoria y derrota
function gameWonImproved() {
    gameActive = false;
    clearInterval(gameTimer);
    
    // Calculate bonus points
    const timeBonus = Math.floor(timeLeft / 6);
    const attemptBonus = attempts * 5;
    const hintPenalty = hintsUsed * 3;
    
    score += timeBonus + attemptBonus - hintPenalty;
    
    message.textContent = `¡Felicidades, ganaste! 🎉 Puntuación: ${score}`;
    updateTimerStatus('¡Completado!', 'fas fa-check-circle', '#22c55e');
    updateMascotMessage('saved');
    updateMascotStatus('¡Salvado!', 'fas fa-heart', '#22c55e');
    document.body.classList.add('game-won');
    disableKeyboard();
    updateStats();
    playFeedbackEffect('win');
    saveGameStats(true);
}

function gameLostImproved() {
    gameActive = false;
    clearInterval(gameTimer);
    message.textContent = `Perdiste. La palabra era: ${selectedWord}`;
    updateTimerStatus('Game Over', 'fas fa-times-circle', '#ef4444');
    updateMascotMessage('lost');
    updateMascotStatus('Perdido', 'fas fa-dizzy', '#ef4444');
    document.body.classList.add('game-lost');
    disableKeyboard();
    playFeedbackEffect('lose');
    saveGameStats(false);
}

// Reemplazar las funciones originales
window.gameWon = gameWonImproved;
window.gameLost = gameLostImproved;

// Función para mostrar estadísticas (opcional)
function showStats() {
    try {
        const stats = JSON.parse(localStorage.getItem('ahorcadoStats') || '[]');
        if (stats.length > 0) {
            const totalGames = stats.length;
            const wins = stats.filter(s => s.won).length;
            const avgScore = stats.reduce((sum, s) => sum + s.score, 0) / totalGames;
            const winRate = (wins / totalGames * 100).toFixed(1);
            
            console.log(`Estadísticas del Jugador:
- Partidas jugadas: ${totalGames}
- Victorias: ${wins}
- Tasa de victoria: ${winRate}%
- Puntuación promedio: ${avgScore.toFixed(0)}`);
        }
    } catch (e) {
        console.log('No se pudieron cargar las estadísticas');
    }
}

// Función de ayuda para mostrar controles
function showControls() {
    const controlsInfo = `
🎮 CONTROLES DEL JUEGO:
- Haz clic en las letras o usa el teclado
- ENTER: Comenzar juego
- R: Reiniciar juego  
- H: Usar pista
- ESC: Ver esta ayuda

⏰ TIEMPO: 3 minutos para completar
❤️ VIDAS: 7 intentos máximo
💡 PISTAS: Hasta 3 disponibles por palabra
🏆 PUNTUACIÓN: Bonificaciones por velocidad y intentos restantes
    `;
    
    console.log(controlsInfo);
}

// Manejar tecla ESC para mostrar ayuda
document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape') {
        showControls();
    }
});

// Función para prevenir pérdida de progreso al salir
window.addEventListener('beforeunload', (event) => {
    if (gameActive && timeLeft > 0) {
        event.preventDefault();
        event.returnValue = '¿Estás seguro de que quieres salir? Perderás tu progreso actual.';
        return event.returnValue;
    }
});

// Auto-focus en el juego para mejorar la experiencia con teclado
document.addEventListener('DOMContentLoaded', () => {
    // Focus en el contenedor del juego para capturar teclas
    const gameContainer = document.getElementById('game-container');
    if (gameContainer) {
        gameContainer.setAttribute('tabindex', '0');
        gameContainer.focus();
    }
});

// Mensaje de bienvenida con instrucciones
console.log(`
🎯 ¡Bienvenido al Juego del Ahorcado Mejorado!

Presiona ESC en cualquier momento para ver los controles completos.
¡Buena suerte salvando al estudiante de Kinal! 🎓
`);