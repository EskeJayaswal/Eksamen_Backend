package facades;

import dtos.RentalDTO;
import entities.User;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import entities.Rental;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class RentalFacadeTest {

    private static EntityManagerFactory emf;
    private static RentalFacade facade;

    public RentalFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            User u1 = new User("jayas", "utavej", "bager", "jayas123","test123");
            User u2 = new User("laust", "filevej", "designer","last123","test123");
            Rental r1 = new Rental("01/01/2020", "01/01/2022", 200000, 30000,"Lone");
            Rental r2 = new Rental("06/06/2020", "06/06/2022", 20000, 5000,"Jakob");
            em.persist(r1);
            em.persist(r2);
            em.persist(u1);
            em.persist(u2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void create() throws Exception {
        Rental expected = new Rental("01/01/2021","01/01/2016",120000,10000,"Leif");
        Rental actual = facade.create(expected);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void delete() throws NotFoundException {
        Rental expected = new Rental("01/01/2021","01/01/2016",120000,10000,"Leif");
        Rental actual = facade.delete(expected.getId());
        assertEquals(expected.getId(),actual.getId());
    }



    @Test
    public void addRelation() {


    }



}
