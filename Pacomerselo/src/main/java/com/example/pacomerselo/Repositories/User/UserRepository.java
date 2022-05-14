package com.example.pacomerselo.Repositories.User;

import com.example.pacomerselo.Entities.User;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUsernameAndEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @Transactional
    int updateUser(String username, String name, String surname, String email);
}
