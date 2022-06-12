
//package security;
/*
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Role;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import facades.RoleFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("signup")
public class SignupEndpoint {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String jsonString) throws API_Exception, NotFoundException {
        String username;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }

        if (FACADE.usernameExists(username)) {
            JsonObject jo = new JsonObject();
            jo.addProperty("status", "ERROR");
            jo.addProperty("msg", "Username already exists");
            return Response
                    .ok(GSON.toJson(jo))
                    .build();
        }
        if (username.length() < 5) {
            JsonObject jo = new JsonObject();
            jo.addProperty("status", "ERROR");
            jo.addProperty("msg", "Username must be longer than 5 characters");
            return Response
                    .ok(GSON.toJson(jo))
                    .build();
        }
        if (password.length() < 5) {
            JsonObject jo = new JsonObject();
            jo.addProperty("status", "ERROR");
            jo.addProperty("msg", "Password must be longer than 5 characters");
            return Response
                    .ok(GSON.toJson(jo))
                    .build();
        }

        User user = new User(username, password);
        Role userRole = RoleFacade.getRoleFacade(EMF).getRoleByName("user");
        user.addRole(userRole);

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        JsonObject jo = new JsonObject();
        jo.addProperty("status", "SUCCESS");
        jo.addProperty("msg", "Signup successful");
        return Response
                .ok(GSON.toJson(jo))
                .build();
    }
}
*/