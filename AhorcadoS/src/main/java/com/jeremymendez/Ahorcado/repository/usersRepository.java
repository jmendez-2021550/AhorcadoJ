package com.jeremymendez.Ahorcado.repository;

import com.jeremymendez.Ahorcado.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface usersRepository extends JpaRepository<users, Integer> {

}
