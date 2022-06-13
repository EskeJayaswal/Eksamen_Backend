package populators;

import entities.House;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HousePopulator {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    }

    public static void populateHouses(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        House house1 = new House("Fuglevej 23", "KBH", "4");
        House house2 = new House("Klatregade", "Aarhus", "7");

        try {
            em.getTransaction().begin();
            em.persist(house1);
            em.persist(house2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
