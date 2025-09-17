package org.jeremymendez.modelo;

/**
 * Clase modelo para la tabla 'words' de la base de datos AhorcadoDB.
 * Representa una palabra del juego con sus pistas asociadas.
 */
public class words {
    private int id;
    private String word;
    private String hint1;
    private String hint2;
    private String hint3;

    // Constructores
    public words() {}

    public words(int id, String word, String hint1, String hint2, String hint3) {
        this.id = id;
        this.word = word;
        this.hint1 = hint1;
        this.hint2 = hint2;
        this.hint3 = hint3;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public void setHint3(String hint3) {
        this.hint3 = hint3;
    }
}
