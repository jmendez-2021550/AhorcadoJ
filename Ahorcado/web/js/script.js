let selectedWord = "";
let guessedLetters = [];
let attempts = 7;
const maxAttempts = 7;
let clueIndex = 0;
let currentClues = [];

const wordDisplay = document.getElementById("word-display");
const keyboard = document.getElementById("keyboard");
const message = document.getElementById("message");
const startBtn = document.getElementById("start-btn");
const restartBtn = document.getElementById("restart-btn");
const hangmanImage = document.getElementById("hangman-image");
const clueBtn = document.getElementById("clue-btn");

// Cambia la ruta del fetch según la ubicación de tu JSP
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

document.addEventListener('DOMContentLoaded', () => {
    message.textContent = "¡Bienvenido al juego del Ahorcado!";
    startBtn.classList.remove("hidden");
    restartBtn.classList.add("hidden");
    clueBtn.classList.add("hidden");
    wordDisplay.textContent = "";
    keyboard.innerHTML = "";
    hangmanImage.src = "../image/Cuerdaa.png";
});

startBtn.addEventListener("click", startGame);
restartBtn.addEventListener("click", startGame);
clueBtn.addEventListener("click", () => {
    if (currentClues.length > 0 && clueIndex < currentClues.length) {
        message.textContent = "Pista: " + currentClues[clueIndex];
        clueIndex++;
    } else {
        message.textContent = "No hay más pistas disponibles.";
    }
});

async function startGame() {
    const randomWordData = await obtenerNuevaPalabra();
    if (!randomWordData) {
        return;
    }

    selectedWord = randomWordData.word;
    guessedLetters = [];
    attempts = maxAttempts;
    clueIndex = 0;
    currentClues = randomWordData.clues;

    startBtn.classList.add("hidden");
    restartBtn.classList.remove("hidden");
    clueBtn.classList.remove("hidden");
    message.textContent = "";
    hangmanImage.src = "../image/Cuerdaa.png";
    wordDisplay.textContent = "_ ".repeat(selectedWord.length);

    createKeyboard();
}

function createKeyboard() {
    keyboard.innerHTML = "";
    for (let i = 65; i <= 90; i++) {
        const letter = String.fromCharCode(i);
        const button = document.createElement("button");
        button.textContent = letter;
        button.addEventListener("click", () => guessLetter(button, letter));
        keyboard.appendChild(button);
    }
}

function guessLetter(button, letter) {
    if (button.classList.contains("guessed")) {
        return;
    }

    button.classList.add("guessed");
    let correctGuess = false;

    if (selectedWord.includes(letter)) {
        correctGuess = true;
        button.classList.add("correct");
        guessedLetters.push(letter);
        message.textContent = "¡Letra correcta!";
    } else {
        attempts--;
        button.classList.add("incorrect");
        const errores = maxAttempts - attempts;
        if (errores > 0) {
            hangmanImage.src = `../image/Fase${errores}.png`;
        }
        message.textContent = `¡Letra incorrecta! Te quedan ${attempts} intentos.`;
    }

    let displayWord = "";
    for (const char of selectedWord) {
        displayWord += guessedLetters.includes(char) ? char + " " : "_ ";
    }
    wordDisplay.textContent = displayWord.trim();

    checkGameStatus();
}

function checkGameStatus() {
    if (!wordDisplay.textContent.includes("_")) {
        message.textContent = "¡Felicidades, ganaste! 🎉";
        disableKeyboard();
    } else if (attempts === 0) {
        message.textContent = `Perdiste. La palabra era: ${selectedWord}`;
        disableKeyboard();
    }
}

function disableKeyboard() {
    const buttons = keyboard.getElementsByTagName('button');
    for (const button of buttons) {
        button.disabled = true;
    }
}
