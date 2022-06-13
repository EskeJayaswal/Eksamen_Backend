package facades;

import entities.Rental;
import entities.Rental;
import entities.Role;
import entities.User;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    User u1, u2;
    Rental r1, r2;

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
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();

            //u1 = new User("jayas", "utavej", "bager", "jayas123","test123");
            //u2 = new User("laust", "filevej", "designer","last123","test123");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

           // r1 = new Rental("01/01/2020", "01/01/2022", 200000, 30000,"Lone");
           // r2 = new Rental("06/06/2020", "06/06/2022", 20000, 5000,"Jakob");
            //u1.addRental(r1);
           // u2.addRental(r2);

            em.persist(userRole);
            em.persist(adminRole);

          //  em.persist(r1);
            //em.persist(r2);

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
        User expected = u1;
        User actual = facade.getById(u1.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void update() throws NotFoundException {
        u1.setName("Peter");
        User expected = u1;
        User actual = facade.update(u1);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void getAll() {
        int actual = facade.getAll().size();
        assertEquals(2, actual);
    }

    @Test
    void delete() throws NotFoundException {
        User user = facade.delete(u2.getId());
        int actual = facade.getAll().size();
        assertEquals(1, actual);
    }
}

