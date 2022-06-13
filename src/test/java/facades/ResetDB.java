package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ResetDB {

    public static void delete(EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE phone AUTO_INCREMENT = 1").executeUpdate();

            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE hobby AUTO_INCREMENT = 1").executeUpdate();

            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE person AUTO_INCREMENT = 1").executeUpdate();

        } finally {
            em.close();
        }
    }

    public static void truncate(EntityManagerFactory emf) {
//        System.out.println("--- TRUNCATING TEST DATABASE ---");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createNativeQuery("truncate table users").executeUpdate();
            em.createNativeQuery("truncate table rental").executeUpdate();
            em.createNativeQuery("truncate table roles").executeUpdate();
            em.createNativeQuery("truncate table house").executeUpdate();
            em.createNativeQuery("truncate table house").executeUpdate();
            em.createNativeQuery("truncate table user_rental").executeUpdate();
            em.createNativeQuery("truncate table user_roles").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } finally {
            em.close();
        }
    }
}
