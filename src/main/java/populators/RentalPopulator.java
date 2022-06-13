package populators;

import entities.Rental;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RentalPopulator {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    }

    public static void populateRentals(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Rental rental1 = new Rental("03/06/2022", "03/06/2032", 60000, 10000, "Bjarne");
        Rental rental2 = new Rental("01/01/2012", "01/01/2023", 80000, 20000, "Lauge");

        try {
            em.getTransaction().begin();
            em.persist(rental1);
            em.persist(rental2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
