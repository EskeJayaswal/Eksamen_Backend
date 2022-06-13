package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import errorhandling.NotFoundException;
import facades.HouseFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("house")
public class HouseResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HouseFacade FACADE =  HouseFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<House> houseList = FACADE.getAll();
        List<HouseDTO> dto = new ArrayList<>();
        for (House house : houseList) {
            dto.add(new HouseDTO(house));
        }
        return Response.ok().entity(GSON.toJson(dto)).build();
    }

    // Get by id
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        HouseDTO houseDTO = new HouseDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(houseDTO)).build();
    }

    @GET
    @Path("/rentals/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalsByHouseId(@PathParam("id") Long id) throws NotFoundException {
        List<Rental> rentals = FACADE.getRentalsByHouseId(id);
        List<RentalDTO> rentalDTOS = new ArrayList<>();
        for (Rental rental : rentals) {
            rentalDTOS.add(new RentalDTO(rental));

        }
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }

    // Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        HouseDTO dto = GSON.fromJson(content, HouseDTO.class);
        House house = FACADE.create(dto.getEntity());
        return Response.ok().entity(GSON.toJson(new HouseDTO(house))).build();
    }

    // Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        HouseDTO dto = GSON.fromJson(jsonContext, HouseDTO.class);
        House house = new House(dto.getAddress()
                ,dto.getCity()
                ,dto.getNumberOfRooms()
        );
        house.setId(id);
        HouseDTO updated = new HouseDTO(FACADE.update(house));
        return Response.ok("SUCCESS").entity(GSON.toJson(updated)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        House house = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new HouseDTO(house))).build();
    }

    /*@Path("/remove/{id1}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeRelation(@PathParam("id1") Long id1) throws NotFoundException {
        House house = FACADE.removeRelation(id1);
        return Response.ok().entity(GSON.toJson(new HouseDTO(house))).build();
    }*/
}
