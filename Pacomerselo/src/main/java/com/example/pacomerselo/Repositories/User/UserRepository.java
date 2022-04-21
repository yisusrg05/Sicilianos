package com.example.pacomerselo.Repositories.User;

import com.example.pacomerselo.Entities.User;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public List<User> findByUsernameAndEmail(String username, String email);

    public List<User> findByUsername(String username);

    @Transactional
    public int updateUser(long id,String name, String surname, String email);
}
