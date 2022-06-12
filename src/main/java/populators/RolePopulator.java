package populators;

import entities.Role;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RolePopulator {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populateRoles(emf);
    }

    public static void populateRoles(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        try {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
