package facades;

import entities.Rental;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade implements IFacade<User>{

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }


    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }



    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = '" + username + "'", User.class);
            user = query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");

            //user = em.find(User.class, username);
            //if (user == null || !user.verifyPassword(password)) {
              //  throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User create(User user) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Role userRole = RoleFacade.getRoleFacade(emf).getRoleByName("user");
        user.addRole(userRole);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User update(User user) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User found = em.find(User.class, user.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + user.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            User updated = em.merge(user);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public User delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User found = em.find(User.class, id);
        if (found == null) {
            throw new NotFoundException("Could not remove Entity with id: " + id);
        }

        try {
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
            return found;
        } finally {
            em.close();
        }    }

    @Override
    public User getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, id);
            if (user == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return user;    }

    @Override
    public List<User> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT z FROM User z", User.class);
        return query.getResultList();    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM User z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public User addRelation(Long userId, Long rentalId) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.find(User.class, userId);
            Rental rental = em.find(Rental.class, rentalId);

            if(user == null) {
                throw new NotFoundException("Entity with id " + userId + " was not found");
            }

            if(rental == null) {
                throw new NotFoundException("Entity with id " + rentalId + " was not found");
            }

            user.addRental(rental);
            em.getTransaction().begin();
            User updated = em.merge(user);
            em.getTransaction().commit();
            return updated;

        } finally {
            em.close();
        }
    }


    public List<Rental> getRentalByUserName(String username) {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = '" + username + "'", User.class);
            user = query.getSingleResult();
        } finally {
            em.close();
        }
        return user.getRentalList();
    }

}