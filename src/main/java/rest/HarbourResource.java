package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.HarbourDTO;
import entities.Boat;
import entities.Harbour;
import entities.Harbour;
import errorhandling.NotFoundException;
import facades.HarbourFacade;
import facades.HarbourFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("harbour")
public class HarbourResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarbourFacade FACADE =  HarbourFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Harbour> harbourList = FACADE.getAll();
        List<HarbourDTO> dto = new ArrayList<>();
        for (Harbour harbour : harbourList) {
            dto.add(new HarbourDTO(harbour));
        }
        return Response.ok().entity(GSON.toJson(dto)).build();
    }

    // Get by id
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        HarbourDTO harbourDTO = new HarbourDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(harbourDTO)).build();
    }

    @GET
    @Path("/boats/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatsByHarbourId(@PathParam("id") Long id) throws NotFoundException {
        List<Boat> boats = FACADE.getBoatsByHarbourId(id);
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for (Boat boat : boats) {
            boatDTOS.add(new BoatDTO(boat));

        }
        return Response.ok().entity(GSON.toJson(boatDTOS)).build();
    }

    // Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        HarbourDTO dto = GSON.fromJson(content, HarbourDTO.class);
        Harbour harbour = FACADE.create(dto.getEntity());
        return Response.ok().entity(GSON.toJson(new HarbourDTO(harbour))).build();
    }

    // Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        HarbourDTO dto = GSON.fromJson(jsonContext, HarbourDTO.class);
        Harbour harbour = new Harbour(dto.getName()
                ,dto.getAddress()
                ,dto.getCapacity()
        );
        harbour.setId(id);
        HarbourDTO updated = new HarbourDTO(FACADE.update(harbour));
        return Response.ok("SUCCESS").entity(GSON.toJson(updated)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        Harbour harbour = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new HarbourDTO(harbour))).build();
    }

    @Path("/remove/{id1}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeRelation(@PathParam("id1") Long id1) throws NotFoundException {
        Harbour harbour = FACADE.removeRelation(id1);
        return Response.ok().entity(GSON.toJson(new HarbourDTO(harbour))).build();
    }
}
