package facades;

import entities.Boat;
import entities.Owner;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class OwnerFacadeTest {

    private static EntityManagerFactory emf;
    private static OwnerFacade facade;
    Owner o1, o2;
    Boat b1, b2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = OwnerFacade.getOwnerFacade(emf);
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
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();

            o1 = new Owner("jayas", "utavej", "1223");
            o2 = new Owner("jürgen", "filevej", "123");
            b1 = new Boat("suzuki", "c492", "dea", "");
            b2 = new Boat("volvo", "334", "pingo","");
            o1.addBoat(b1);
            o2.addBoat(b2);

            em.persist(o1);
            em.persist(o2);
            em.persist(b1);
            em.persist(b2);

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
    void create() {
        Owner expected = new Owner("Jørgen", "bakkecvej", "23123");
        Owner actual = facade.create(expected);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void getById() throws NotFoundException {
        Owner expected = o1;
        Owner actual = facade.getById(o1.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void update() throws NotFoundException {
        o1.setName("Peter");
        Owner expected = o1;
        Owner actual = facade.update(o1);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void getAll() {
        int actual = facade.getAll().size();
        assertEquals(2, actual);
    }

    @Test
    void delete() throws NotFoundException {
        Owner user = facade.delete(o2.getId());
        int actual = facade.getAll().size();
        assertEquals(1, actual);
    }
}

