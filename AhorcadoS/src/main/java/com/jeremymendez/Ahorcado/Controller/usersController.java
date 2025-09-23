package com.jeremymendez.Ahorcado.Controller;

import com.jeremymendez.Ahorcado.model.users;
import com.jeremymendez.Ahorcado.service.usersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class usersController {

    private final usersService usersService;

    public usersController(usersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    public users getUserById(@PathVariable Integer user_id) {
        return usersService.getUserById(user_id);
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody users user) {
        // Validación de campos vacíos
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo username está vacío");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo email está vacío");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo password está vacío");
        }
        if (user.getUser_type() == null || user.getUser_type().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo user_type está vacío");
        }

        // Validación de formato de correo
        String email = user.getEmail().toLowerCase();
        if (!(email.endsWith("@gmail.com") || email.endsWith("@kinal.edu.gt") || email.endsWith("@yahoo.com"))) {
            return ResponseEntity.badRequest().body("Correo inválido, los correos permitidos son gmail, kinal.edu.gt y yahoo.com");
        }

        // Validación de unicidad
        List<users> existingUsers = usersService.getAllUsers();
        for (users u : existingUsers) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return ResponseEntity.badRequest().body("Username: No está permitido utilizar un username ya existente");
            }
            if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                return ResponseEntity.badRequest().body("El email ya está en uso");
            }
        }

        // Guardar el usuario
        users savedUser = usersService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<?> updateUser(@PathVariable(required = false) Integer user_id, @RequestBody users user) {
        // Validación de id no proporcionado
        if (user_id == null) {
            return ResponseEntity.badRequest().body("No colocaste el id que quieres actualizar");
        }

        // Validar que el usuario con ese id exista antes de actualizar
        users existingUser = usersService.getUserById(user_id);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body("No existe ningún usuario con ese id");
        }

        // Validación de campos vacíos
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo username está vacío");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo email está vacío");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo password está vacío");
        }
        if (user.getUser_type() == null || user.getUser_type().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo user_type está vacío");
        }

        // Validación de formato de correo
        String email = user.getEmail().toLowerCase();
        if (!(email.endsWith("@gmail.com") || email.endsWith("@kinal.edu.gt") || email.endsWith("@yahoo.com"))) {
            return ResponseEntity.badRequest().body("Correo inválido, los correos permitidos son gmail, kinal.edu.gt y yahoo.com");
        }

        // Validación de unicidad (no permitir actualizar a un username/email/password ya existente en otro usuario)
        List<users> existingUsers = usersService.getAllUsers();
        for (users u : existingUsers) {
            if (!u.getUser_id().equals(user_id)) {
                if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                    return ResponseEntity.badRequest().body("Username: No está permitido utilizar un username ya existente");
                }
                if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                    return ResponseEntity.badRequest().body("El email ya está en uso");
                }
                if (u.getPassword().equals(user.getPassword())) {
                    return ResponseEntity.badRequest().body("La contraseña ya está en uso");
                }
            }
        }

        users updatedUser = usersService.updateUser(user_id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer user_id) {
        users user = usersService.getUserById(user_id);
        if (user == null) {
            return ResponseEntity.badRequest().body("No existe ningún usuario con ese id");
        }
        usersService.deleteUser(user_id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
