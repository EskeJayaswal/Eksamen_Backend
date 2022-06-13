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

public class HouseFacade implements IFacade<House> {

    private static EntityManagerFactory emf;
    private static HouseFacade instance;

    private HouseFacade() {
    }

    public static HouseFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    @Override
    public House create(House house) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(house);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return house;
    }

    @Override
    public House update(House house) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        House found = em.find(House.class, house.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + house.getId() + " not found");
        }

        // TODO: update values here

        //found.setName(house.getName());
        //found.setAddress(house.getAddress());
        //found.setCapacity(house.getCapacity());

        try {
            em.getTransaction().begin();
            House updated = em.merge(house);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public House delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        House found = em.find(House.class, id);
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
    public House getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        House house;
        try {
            house = em.find(House.class, id);
            if (house == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return house;
    }

    @Override
    public List<House> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<House> query = em.createQuery("SELECT z FROM House z", House.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return (Long) em.createQuery("SELECT COUNT(z) FROM House z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public House addRelation(Long id1, Long id2) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            House house = em.find(House.class, id1);
            if (house == null) {
                throw new NotFoundException("Entity with id " + id1 + " was not found");
            }

            Rental rental = em.find(Rental.class, id2);
            if (rental == null) {
                throw new NotFoundException("Entity with id" + id2 + " was not found");
            }

            house.addRental(rental);
            em.getTransaction().begin();
            House updated = em.merge(house);
            em.getTransaction().commit();
            return updated;

        } finally {

            em.close();
        }
    }

    public List<Rental> getRentalsByHouseId(Long id) throws NotFoundException {


        List<Rental> rentals = new ArrayList<>();
        House house = getById(id);

        for (Rental rental : house.getRentalList()) {
            rentals.add(rental);
        }

        return rentals;
    }

    public List<User> getUsersByHouseId(Long id) throws NotFoundException {

        RentalFacade rentalFacade = RentalFacade.getFacade(emf);
        List<User> userList = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();
        House house = getById(id);

        for (Rental rental : house.getRentalList()) {
            rentals.add(rental);
        }

        for (Rental rental : rentals) {
            List<User> users = rentalFacade.getUsersByRentalId(rental.getId());
            for (User user : users) {
                userList.add(user);
            }
        }
     return userList;
    }
}
