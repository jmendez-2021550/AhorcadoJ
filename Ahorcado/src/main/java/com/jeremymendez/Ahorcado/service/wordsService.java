package com.jeremymendez.Ahorcado.service;

import com.jeremymendez.Ahorcado.model.words;
import java.util.List;

public interface wordsService {
    List<words> getAllWords();
    words getWordById(Integer id);
    words saveWord(words word);
    words updateWord(Integer id, words word);
    void deleteWord(Integer id);
}
