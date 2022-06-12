package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import entities.Boat;
import entities.Owner;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class OwnerFacade implements IFacade<Owner>{

    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    private OwnerFacade() {}

    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }


    @Override
    public Owner create(Owner owner) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return owner;
    }

    @Override
    public Owner update(Owner owner) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Owner found = em.find(Owner.class, owner.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + owner.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Owner updated = em.merge(owner);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Owner delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Owner found = em.find(Owner.class, id);
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
    public Owner getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Owner owner;
        try {
            owner = em.find(Owner.class, id);
            if (owner == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return owner;    }

    @Override
    public List<Owner> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Owner> query = em.createQuery("SELECT z FROM Owner z", Owner.class);
        return query.getResultList();    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Owner z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Owner addRelation(Long ownerId, Long boatId) throws NotFoundException {
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

            owner.addBoat(boat);
            em.getTransaction().begin();
            Owner updated = em.merge(owner);
            em.getTransaction().commit();
            return updated;

        } finally {
            em.close();
        }
    }

}
