package facades;

import entities.Role;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RoleFacade {
    private static EntityManagerFactory emf;
    private static RoleFacade instance;

    private RoleFacade() {}

    public static RoleFacade getRoleFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RoleFacade();
        }
        return instance;
    }

    public Role getRoleByName(String rolename) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Role role;
        try {
            role = em.find(Role.class, rolename);
            if (role == null) {
                throw new NotFoundException("No role with this name exists");
            }
        } finally {
            em.close();
        }
        return role;
    }
}
