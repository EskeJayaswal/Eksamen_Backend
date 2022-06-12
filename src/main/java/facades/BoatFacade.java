package facades;

import entities.Boat;
import entities.Owner;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class BoatFacade implements IFacade<Boat> {

    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    private BoatFacade() {}

    public static BoatFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    @Override
    public Boat create(Boat boat) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return boat;
    }

    @Override
    public Boat update(Boat boat) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Boat found = em.find(Boat.class, boat.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + boat.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Boat updated = em.merge(boat);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Boat delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Boat found = em.find(Boat.class, id);
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
    public Boat getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Boat boat;
        try {
            boat = em.find(Boat.class, id);
            if (boat == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return boat;
    }

    @Override
    public List<Boat> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT z FROM Boat z", Boat.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Boat z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Boat addRelation(Long boatId, Long ownerId) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            Owner owner = em.find(Owner.class, ownerId);
            Boat boat = em.find(Boat.class, boatId);

            if(owner == null) {
                throw new NotFoundException("Entity with id " + ownerId + " was not found");
            }

            if(boat == null) {
                throw new NotFoundException("Entity with id " + boatId + " was not found");
            }

            boat.addOwner(owner);
            em.getTransaction().begin();
            Boat updated = em.merge(boat);
            em.getTransaction().commit();
            return updated;

        } finally {
            em.close();
        }
    }

    public List<Owner> getOwnersByBoatId(Long id) throws NotFoundException {

        List<Owner> owners = new ArrayList<>();
        Boat boat = getById(id);

        for (Owner owner : boat.getOwnerList()) {
            owners.add(owner);
        }

        return owners;
    }
}
