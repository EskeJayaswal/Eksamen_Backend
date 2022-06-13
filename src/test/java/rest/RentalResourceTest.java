package rest;

import entities.House;
import entities.Rental;
import entities.Role;
import entities.User;
import facades.RentalFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class RentalResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Rental r1, r2;


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        r1 = new Rental("01/01/2020", "01/01/2022", 200000, 30000,"Lone");
        r2 = new Rental("06/06/2020", "06/06/2022", 20000, 5000,"Jakob");
        User u1 = new User("Jørgen","12334","Bager","jørgen123","test123");
        House h1 = new House("Birkevej","KBH","12");
        r1.addUser(u1);
        r1.setHouse(h1);
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.persist(r1);
            em.persist(r2);
            em.persist(u1);
            em.persist(h1);
            em.persist(userRole);
            em.persist(adminRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/rental").then().statusCode(200);
    }

    //This test assumes the database contains two rows

    @Test
    public void getRentals() {
        given()
                .contentType("application/json")
                .when()
                .get("/rental")
                .then()
                .assertThat()
                .statusCode(200)
                .body("deposit", equalTo(Arrays.asList(30000,5000)));
    }

    @Test
    public void getById() {
        given()
                .contentType("application/json")
                .when()
                .get("/rental/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("startDate",equalTo("01/01/2020"));
    }

    @Test
    public void createRental() {
        given()
                .contentType("application/json")
                .body(new Rental("02/02/2002", "04/06/2030",200000,1000,"Leif"))
                .when()
                .post("/rental")
                .then()
                .body("startDate", is("02/02/2002"))
                .body("endDate", is("04/06/2030"))
                .body("priceAnnual",is(200000))
                .body("deposit",is(1000))
                .body("contactPerson",is("Leif"));
    }

   /* @Test
    public void testDelete() {
        Rental rental = new Rental("01/01/2021","01/01/2016",120000,10000,"Leif");
        RentalFacade facade = RentalFacade.getFacade(emf);
        facade.create(rental);

        given()
                .when()
                .delete("/guest/1");
        expect().statusCode(200)
                .given()
                .when()
                .get("/guest/count")
                .then()
                .assertThat()
                .body("count", equalTo(0));
    }*/

    @Test
    public void getUserByRentalId() {

        given()
                .contentType("application/json")
                .when()
                .get("/rental/user/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(Arrays.asList("Jørgen")));
    }


}
