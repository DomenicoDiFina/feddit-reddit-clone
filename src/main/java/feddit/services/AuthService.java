package feddit.services;

import feddit.HibernateUtils;
import feddit.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class AuthService {


    public boolean getUser(String username, String password) {
        var isValidUser = false;
        var sessionFactory = HibernateUtils.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u where u.usr=usr and u.password=psw", User.class);
            query.setParameter("usr", username);
            query.setParameter("psw", password);
            List<User> user = query.getResultList();
            if(user != null && user.size() > 0) {
                isValidUser = true;
            }
        }

        return isValidUser;
    }

}
