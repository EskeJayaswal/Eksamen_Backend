package facades;

import entities.House;
import entities.Rental;
import entities.User;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RentalFacade implements IFacade<Rental> {

    private static EntityManagerFactory emf;
    private static RentalFacade instance;

    private RentalFacade() {}

    public static RentalFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    @Override
    public Rental create(Rental rental) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rental);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return rental;
    }

    @Override
    public Rental update(Rental rental) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Rental found = em.find(Rental.class, rental.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + rental.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Rental updated = em.merge(rental);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Rental delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Rental found = em.find(Rental.class, id);
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
        }
    }

    @Override
    public Rental getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Rental rental;
        try {
            rental = em.find(Rental.class, id);
            if (rental == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return rental;
    }

    @Override
    public List<Rental> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Rental> query = em.createQuery("SELECT z FROM Rental z", Rental.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Rental z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Rental addRelation(Long rentalId, Long userId) throws NotFoundException {
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

            rental.addUser(user);
            em.getTransaction().begin();
            Rental updated = em.merge(rental);
            em.getTransaction().commit();
            return updated;

        } finally {
            em.close();
        }
    }

    public List<User> getUsersByRentalId(Long id) throws NotFoundException {

        List<User> users = new ArrayList<>();
        Rental rental = getById(id);

        for (User user : rental.getUserList()) {
            users.add(user);
        }

        return users;
    }

    public House getHouseByRentalId(Long id) throws NotFoundException {

        Rental rental = getById(id);
        House house = rental.getHouse();

        return house;
    }

}
