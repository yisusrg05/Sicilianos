package com.example.pacomerselo.Repositories.User;

import com.example.pacomerselo.Entities.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findByUsernameAndEmail(String username, String email){
        TypedQuery<User> query= entityManager.createQuery
                ("SELECT u FROM User u WHERE u.username=:username AND u.email=:email",User.class);
        return query.setParameter("username",username).setParameter("email",email).getResultList();
    }

    @Transactional
    public int updateUser(String username,String name, String surname, String email){
        Query query = entityManager.createQuery
                ("UPDATE User u SET u.name=:name, u.surname=:surname,u.email=:email WHERE u.username=:username");
        return query.setParameter("username",username).setParameter("name",name).setParameter("surname",surname).setParameter("email",email).executeUpdate();
    }
}
