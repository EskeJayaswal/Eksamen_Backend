package facades;

import entities.RenameMe;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RenameMeFacade implements IFacade<RenameMe> {

    private static EntityManagerFactory emf;
    private static RenameMeFacade instance;

    private RenameMeFacade() {}

    public static RenameMeFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RenameMeFacade();
        }
        return instance;
    }

    @Override
    public RenameMe create(RenameMe renameMe) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(renameMe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return renameMe;
    }

    @Override
    public RenameMe update(RenameMe renameMe) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe found = em.find(RenameMe.class, renameMe.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + renameMe.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            RenameMe updated = em.merge(renameMe);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public RenameMe delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe found = em.find(RenameMe.class, id);
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
    public RenameMe getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe renameMe;
        try {
            renameMe = em.find(RenameMe.class, id);
            if (renameMe == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return renameMe;
    }

    @Override
    public List<RenameMe> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RenameMe> query = em.createQuery("SELECT z FROM RenameMe z", RenameMe.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM RenameMe z").getSingleResult();
        } finally {
            em.close();
        }
    }
}
