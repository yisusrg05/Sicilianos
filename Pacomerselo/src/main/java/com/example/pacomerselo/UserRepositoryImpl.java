package com.example.pacomerselo;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsernameAndEmail(String username, String email){
        TypedQuery<User> query= entityManager.createQuery
                ("SELECT u FROM User u WHERE u.username=:username AND u.email=:email",User.class);
        return query.setParameter("username",username).setParameter("email",email).getSingleResult();
    }
}
