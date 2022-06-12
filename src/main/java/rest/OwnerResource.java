package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import dtos.OwnerDTO;
import entities.Harbour;
import entities.Owner;
import errorhandling.NotFoundException;
import facades.OwnerFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("owner")
public class OwnerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade FACADE =  OwnerFacade.getOwnerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Owner> ownerList = FACADE.getAll();
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner : ownerList) {
            ownerDTOS.add(new OwnerDTO(owner));
        }
        return Response.ok().entity(GSON.toJson(ownerDTOS)).build();
    }

    // Get by id
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        OwnerDTO ownerDTO = new OwnerDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(ownerDTO)).build();
    }

    // Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        OwnerDTO dto = GSON.fromJson(content, OwnerDTO.class);
        Owner owner = FACADE.create(dto.getEntity());
        return Response.ok().entity(GSON.toJson(new OwnerDTO(owner))).build();
    }

    // Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        OwnerDTO dto = GSON.fromJson(jsonContext, OwnerDTO.class);
        Owner owner = new Owner(dto.getName()
                ,dto.getAddress()
                ,dto.getPhone()
        );
        owner.setId(id);
        OwnerDTO updated = new OwnerDTO(FACADE.update(owner));
        return Response.ok("SUCCESS").entity(GSON.toJson(updated)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        Owner owner = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new OwnerDTO(owner))).build();
    }

}
