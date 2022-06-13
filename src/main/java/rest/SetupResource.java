package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import errorhandling.NotFoundException;
import facades.SetupFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("setup")
public class SetupResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final SetupFacade FACADE = SetupFacade.getSetupFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response setupDatabase() throws NotFoundException {
        JsonObject object = FACADE.setupDatabase();
        return Response
                .ok(GSON.toJson(object))
                .build();
    }
}
