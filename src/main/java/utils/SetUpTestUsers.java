package utils;


import entities.Role;
import entities.User;
import errorhandling.NotFoundException;
import facades.RoleFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetUpTestUsers {

    public static void main(String[] args) throws NotFoundException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        //userPopulator(emf);
    }

    public static void userPopulator(EntityManagerFactory emf) throws NotFoundException {


        EntityManager em = emf.createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        // Also, either delete this file, when users are created or rename and add to .gitignore
        // Whatever you do DO NOT COMMIT and PUSH with the real passwords

        User user = new User("Rolf", "34921235", "Proffessor", "user", "test123");
        User admin = new User("Leif", "21350314", "Stenhugger", "admin", "test123");

        // OBS: Admin doesnt get a regular user owner


        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        // Role userRole = new Role("user");
        // Role adminRole = new Role("admin");

        Role userRole = RoleFacade.getRoleFacade(emf).getRoleByName("user");
        Role adminRole = RoleFacade.getRoleFacade(emf).getRoleByName("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);

        em.persist(user);
        em.persist(admin);

        em.getTransaction().commit();
        //System.out.println("PW: " + user.getUserPass());
        //System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        //System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        //System.out.println("Created TEST Users");

    }

}



