/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
/*
package populators;

import entities.Boat;

import javax.persistence.EntityManagerFactory;

import entities.Harbour;
import errorhandling.NotFoundException;
import facades.BoatFacade;
import facades.UserFacade;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
/*
public class Populator {
    public static void populate() throws NotFoundException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        UserFacade userFacade = UserFacade.getUserFacade(emf);
        BoatFacade boatFacade = BoatFacade.getFacade(emf);
        Boat boat = new Boat("suzuki", "c10", "Hanne", "");
        User aske = new User("aske123", "12345","Aske", "fuglevej","31934531");
        Harbour harbour = new Harbour("Nordhavn", "Nordhavn Vej", 500);
        userFacade.create(aske);
        System.out.println(userFacade.getCount());
        User user = userFacade.getById(1L);
        user.addBoat(boat);
        harbour.addBoat(boat);
        userFacade.update(user);
        /*EntityManager em = emf.createEntityManager();
        User.java martin = new User.java("Martin", "fuglevej","31934531");
        Boat boat = new Boat("suzuki", "c10", "Hanne", "");
        Harbour harbour = new Harbour("Nordhavn", "Nordhavn Vej", 500);
        harbour.addBoat(boat);
        martin.addBoat(boat);
        try {
            em.getTransaction().begin();
            em.persist(martin);
            em.persist(boat);
            em.persist(harbour);
            em.getTransaction().commit();
        } finally {
            em.close();
        }*/


        //FacadeExample fe = FacadeExample.getFacadeExample(emf);
        //fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        //fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        //fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
/*
    }

    public static void main(String[] args) throws NotFoundException {
        populate();
    }
}
*/