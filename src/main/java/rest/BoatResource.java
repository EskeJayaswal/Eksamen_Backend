package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Boat;
import entities.Owner;
import errorhandling.NotFoundException;
import facades.BoatFacade;
import facades.BoatFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("boat")
public class BoatResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade FACADE =  BoatFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Boat> boatList = FACADE.getAll();
        List<BoatDTO> dto = new ArrayList<>();
        for (Boat boat : boatList) {
            dto.add(new BoatDTO(boat));
        }
        return Response.ok().entity(GSON.toJson(dto)).build();
    }

    // Get by id
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        BoatDTO boatDTO = new BoatDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(boatDTO)).build();
    }

    // Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        BoatDTO dto = GSON.fromJson(content, BoatDTO.class);
        Boat boat = FACADE.create(dto.getEntity());
        return Response.ok().entity(GSON.toJson(new BoatDTO(boat))).build();
    }

    // Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        BoatDTO dto = GSON.fromJson(jsonContext, BoatDTO.class);
        Boat boat = new Boat(dto.getBrand()
                ,dto.getMake()
                ,dto.getName()
                ,dto.getImage()
        );
        boat.setId(id);
        BoatDTO updated = new BoatDTO(FACADE.update(boat));
        return Response.ok("SUCCESS").entity(GSON.toJson(updated)).build();
    }

    @GET
    @Path("/owner/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOwnersByBoatId(@PathParam("id") Long id) throws NotFoundException {
        List<Owner> owners = FACADE.getOwnersByBoatId(id);
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner : owners) {
            ownerDTOS.add(new OwnerDTO(owner));

        }
        return Response.ok().entity(GSON.toJson(ownerDTOS)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    //@RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        Boat boat = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new BoatDTO(boat))).build();
    }

}
