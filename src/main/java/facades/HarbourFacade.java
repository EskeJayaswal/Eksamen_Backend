package facades;

import dtos.BoatDTO;
import entities.Boat;
import entities.Harbour;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class HarbourFacade implements IFacade<Harbour> {

    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    private HarbourFacade() {}

    public static HarbourFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

    @Override
    public Harbour create(Harbour harbour) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(harbour);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return harbour;
    }

    @Override
    public Harbour update(Harbour harbour) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Harbour found = em.find(Harbour.class, harbour.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + harbour.getId() + " not found");
        }

        // TODO: update values here

        //found.setName(harbour.getName());
        //found.setAddress(harbour.getAddress());
        //found.setCapacity(harbour.getCapacity());

        try {
            em.getTransaction().begin();
            Harbour updated = em.merge(harbour);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Harbour delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Harbour found = em.find(Harbour.class, id);
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
    public Harbour getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Harbour harbour;
        try {
            harbour = em.find(Harbour.class, id);
            if (harbour == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return harbour;
    }

    @Override
    public List<Harbour> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Harbour> query = em.createQuery("SELECT z FROM Harbour z", Harbour.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Harbour z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Harbour addRelation(Long id1, Long id2) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            Harbour harbour = em.find(Harbour.class, id1);
            if(harbour == null) {
                throw new NotFoundException("Entity with id " + id1 + " was not found");
            }

            Boat boat = em.find(Boat.class, id2);
            if(boat == null) {
                throw new NotFoundException("Entity with id" + id2 + " was not found");
            }

            harbour.addBoat(boat);
            em.getTransaction().begin();
            Harbour updated = em.merge(harbour);
            em.getTransaction().commit();
            return updated;

        } finally {

            em.close();
        }
    }

    public Harbour removeRelation(Long id1) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            Boat boat = em.find(Boat.class, id1);
            if(boat == null) {
                throw new NotFoundException("Entity with id" + id1 + " was not found");
            }

            Harbour harbour = boat.getHarbour();
            harbour.removeBoat(boat);
            em.getTransaction().begin();
            Harbour updated = em.merge(harbour);
            em.getTransaction().commit();
            return updated;

        } finally {

            em.close();
        }
    }

    public List<Boat> getBoatsByHarbourId(Long id) throws NotFoundException {


        List<Boat> boats = new ArrayList<>();
        Harbour harbour = getById(id);

        for (Boat boat : harbour.getBoats()) {
            boats.add(boat);
        }

        return boats;
    }

}
