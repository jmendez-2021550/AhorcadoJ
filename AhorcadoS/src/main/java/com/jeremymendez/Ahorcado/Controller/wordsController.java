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

        // Validación de longitud mínima de la palabra (por ejemplo, mínimo 3 letras)
        if (word.getWord().trim().length() < 3) {
            return ResponseEntity.badRequest().body("La palabra debe tener al menos 3 letras");
        }

        // Validación de que la palabra solo contenga letras (sin números ni símbolos)
        if (!word.getWord().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$")) {
            return ResponseEntity.badRequest().body("La palabra solo puede contener letras");
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
        // Validar que la palabra con ese id exista antes de actualizar
        words existingWord = wordsService.getWordById(id);
        if (existingWord == null) {
            return ResponseEntity.badRequest().body("No existe ninguna palabra con ese id");
        }

        // Validación de campos vacíos
        if (word.getWord() == null || word.getWord().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo word está vacío");
        }
        if (word.getHint1() == null || word.getHint1().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo hint1 está vacío");
        }

        // Validación de longitud mínima de la palabra (por ejemplo, mínimo 3 letras)
        if (word.getWord().trim().length() < 3) {
            return ResponseEntity.badRequest().body("La palabra debe tener al menos 3 letras");
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
        return ResponseEntity.ok(updatedWord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWord(@PathVariable Integer id) {
        words existingWord = wordsService.getWordById(id);
        if (existingWord == null) {
            return ResponseEntity.badRequest().body("No existe ninguna palabra con ese id");
        }
        wordsService.deleteWord(id);
        return ResponseEntity.ok("Palabra eliminada correctamente");
    }
}
