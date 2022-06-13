package facades;

import entities.*;
import entities.Rental;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        ResetDB.truncate(emf);

        EntityManager em = emf.createEntityManager();

        User u1 = new User("jayas", "utavej", "bager", "jayas123","test123");
        User u2 = new User("laust", "filevej", "designer","last123","test123");

        Rental r1 = new Rental("01/01/2020", "01/01/2022", 200000, 30000,"Lone");
        Rental r2 = new Rental("06/06/2020", "06/06/2022", 20000, 5000,"Jakob");

        Role userRole = new Role("user");

        House h1 = new House("Birkevej", "kbh","4");
        House h2 = new House("Lodevej", "kbh","7");

        h1.addRental(r1);
        h2.addRental(r2);

        r1.addUser(u1);
        r2.addUser(u2);

        try {
            em.getTransaction().begin();
            //em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(r1);
            em.persist(r2);
            em.persist(h1);
            em.persist(h2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    void create() throws NotFoundException {
        User expected = new User("Jørgen","12334","Bager","jørgen123","test123");

        User actual = facade.create(expected);
        assertEquals(expected.getId(), actual.getId());
    }

   @Test
    void getById() throws NotFoundException {
       User actual = facade.getById(1L);
       assertEquals(1L,actual.getId());
    }


    @Test
    void getAll() {
        int actual = facade.getAll().size();
        assertEquals(2, actual);
    }

    @Test
    void delete() throws NotFoundException {
        User expected = new User("Jørgen","12334","Bager","jørgen123","test123");
        facade.create(expected);
        assertEquals(3, facade.getCount());
        facade.delete(1L);
        assertEquals(2, facade.getCount());
    }

}

