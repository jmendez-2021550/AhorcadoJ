
package com.jeremymendez.Ahorcado.service;

import com.jeremymendez.Ahorcado.model.words;
import com.jeremymendez.Ahorcado.repository.wordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class wordsServiceImpl implements wordsService {

	private final wordsRepository wordsRepository;

	@Autowired
	public wordsServiceImpl(wordsRepository wordsRepository) {
		this.wordsRepository = wordsRepository;
	}

	@Override
	public List<words> getAllWords() {
		return wordsRepository.findAll();
	}

	@Override
	public words getWordById(Integer id) {
		Optional<words> word = wordsRepository.findById(id);
		return word.orElse(null);
	}

	@Override
	public words saveWord(words word) {
		return wordsRepository.save(word);
	}

	@Override
	public words updateWord(Integer id, words word) {
		if (wordsRepository.existsById(id)) {
			word.setId(id);
			return wordsRepository.save(word);
		}
		return null;
	}

	@Override
	public void deleteWord(Integer id) {
		wordsRepository.deleteById(id);
	}
}
