package com.jeremymendez.Ahorcado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "hint1", nullable = false)
    private String hint1;

    @Column(name = "hint2")
    private String hint2;

    @Column(name = "hint3")
    private String hint3;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
