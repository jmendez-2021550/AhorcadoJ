package com.jeremymendez.Ahorcado.service;

import com.jeremymendez.Ahorcado.model.users;
import com.jeremymendez.Ahorcado.repository.usersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class usersServiceImpl implements usersService {

    private final usersRepository usersRepository;

    public usersServiceImpl(usersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public users getUserById(Integer user_id) {
        return usersRepository.findById(user_id).orElse(null);
    }

    @Override
    public users saveUser(users user) {
        return usersRepository.save(user);
    }

    @Override
    public users updateUser(Integer user_id, users user) {
        users existingUser = usersRepository.findById(user_id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setUser_type(user.getUser_type());
            return usersRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Integer user_id) {
        usersRepository.deleteById(user_id);
    }
}
