package rest;

import entities.Rental;
import entities.Role;
import entities.User;
import errorhandling.NotFoundException;
import facades.ResetDB;
import facades.RoleFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static User u1, u2;


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
    public void setUp() throws NotFoundException {
        ResetDB.truncate(emf);
        EntityManager em = emf.createEntityManager();
        u1 = new User("jayas", "utavej", "bager", "jayas123","test123");
        u2 = new User("laust", "filevej", "designer","last123","test123");
        Rental r1 = new Rental("01/01/2020", "01/01/2022", 200000, 30000,"Lone");

        r1.addUser(u1);
        Role userRole = new Role("user");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.persist(u1);
            em.persist(u2);
            em.persist(r1);
            em.persist(userRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/user").then().statusCode(200);
    }

    @Test
    public void testGetUsers() {
        given()
                .contentType("application/json")
                .when()
                .get("/user")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(Arrays.asList("laust","jayas")));
    }

    @Test
    public void getById() {
        given()
                .contentType("application/json")
                .when()
                .get("/user/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name",equalTo("laust"));
    }

}
