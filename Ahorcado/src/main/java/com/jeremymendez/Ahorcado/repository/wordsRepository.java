package com.jeremymendez.Ahorcado.repository;

import com.jeremymendez.Ahorcado.model.words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface wordsRepository extends JpaRepository<words, Integer> {
}
