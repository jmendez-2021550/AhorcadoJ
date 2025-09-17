package com.jeremymendez.Ahorcado.Controller;

import com.jeremymendez.Ahorcado.model.words;
import com.jeremymendez.Ahorcado.service.wordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class wordsController {

    private final wordsService wordsService;

    public wordsController(wordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping
    public List<words> getAllWords() {
        return wordsService.getAllWords();
    }

    @GetMapping("/{id}")
    public words getWordById(@PathVariable Integer id) {
        return wordsService.getWordById(id);
    }


    @PostMapping
    public ResponseEntity<?> createWord(@RequestBody words word) {
        // Validación de campos vacíos
        if (word.getWord() == null || word.getWord().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo word está vacío");
        }
        if (word.getHint1() == null || word.getHint1().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo hint1 está vacío");
        }

        // Validación de unicidad de la palabra y de los hints
        List<words> existingWords = wordsService.getAllWords();
        for (words w : existingWords) {
            if (w.getWord().equalsIgnoreCase(word.getWord())) {
                return ResponseEntity.badRequest().body("La palabra ya existe en la base de datos");
            }
            if (w.getHint1() != null && word.getHint1() != null && w.getHint1().equalsIgnoreCase(word.getHint1())) {
                return ResponseEntity.badRequest().body("El hint1 ya está en uso");
            }
        }

        // Guardar la palabra
        words savedWord = wordsService.saveWord(word);
        return ResponseEntity.ok(savedWord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWord(@PathVariable Integer id, @RequestBody words word) {
        // Validación de campos vacíos
        if (word.getWord() == null || word.getWord().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo word está vacío");
        }
        if (word.getHint1() == null || word.getHint1().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo hint1 está vacío");
        }

        // Validación de unicidad de la palabra y de los hints (no permitir actualizar a un valor ya existente en otra palabra)
        List<words> existingWords = wordsService.getAllWords();
        for (words w : existingWords) {
            if (!w.getId().equals(id)) {
                if (w.getWord().equalsIgnoreCase(word.getWord())) {
                    return ResponseEntity.badRequest().body("La palabra ya existe en la base de datos");
                }
                if (w.getHint1() != null && word.getHint1() != null && w.getHint1().equalsIgnoreCase(word.getHint1())) {
                    return ResponseEntity.badRequest().body("El hint1 ya está en uso");
                }
            }
        }

        words updatedWord = wordsService.updateWord(id, word);
        if (updatedWord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedWord);
    }

    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Integer id) {
        wordsService.deleteWord(id);
    }
}
