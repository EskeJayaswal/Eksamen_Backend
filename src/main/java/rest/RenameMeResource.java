package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RenameMeDTO;
import entities.RenameMe;
import errorhandling.NotFoundException;
import facades.RenameMeFacade;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("renameme")
public class RenameMeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final RenameMeFacade FACADE =  RenameMeFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("ping")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String jsonContext) {
        RenameMeDTO dto = GSON.fromJson(jsonContext, RenameMeDTO.class);
        RenameMe renameMe = new RenameMe(
            dto.getDummyStr1(),
            dto.getDummyStr2()
        );
        RenameMeDTO created = new RenameMeDTO(FACADE.create(renameMe));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(created))
                .build();
    }

    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        RenameMeDTO dto = GSON.fromJson(jsonContext, RenameMeDTO.class);
        RenameMe renameMe = new RenameMe(
                dto.getDummyStr1(),
                dto.getDummyStr2()
        );
        renameMe.setId(id);
        RenameMeDTO updated = new RenameMeDTO(FACADE.update(renameMe));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(updated))
                .build();
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws NotFoundException {
        RenameMeDTO deleted = new RenameMeDTO(FACADE.delete(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        RenameMeDTO found = new RenameMeDTO(FACADE.getById(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(found))
                .build();
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<RenameMeDTO> dtoList = new ArrayList<>();
        for (RenameMe renameMe : FACADE.getAll()) {
            dtoList.add(new RenameMeDTO(renameMe));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }
}
