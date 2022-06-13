package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.UserDTO;
import entities.House;
import entities.Rental;
import entities.User;
import errorhandling.NotFoundException;
import facades.RentalFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("rental")
public class RentalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final RentalFacade FACADE =  RentalFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Rental> rentalList = FACADE.getAll();
        List<RentalDTO> dto = new ArrayList<>();
        for (Rental rental : rentalList) {
            dto.add(new RentalDTO(rental));
        }
        return Response.ok().entity(GSON.toJson(dto)).build();
    }

    // Get by id
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        RentalDTO rentalDTO = new RentalDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(rentalDTO)).build();
    }

    // Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        RentalDTO dto = GSON.fromJson(content, RentalDTO.class);
        Rental rental = FACADE.create(dto.getEntity());
        return Response.ok().entity(GSON.toJson(new RentalDTO(rental))).build();
    }

    // Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        RentalDTO dto = GSON.fromJson(jsonContext, RentalDTO.class);
        Rental rental = new Rental(dto.getStartDate()
                ,dto.getEndDate()
                ,dto.getPriceAnnual()
                ,dto.getDeposit()
                ,dto.getContactPerson()
        );
        rental.setId(id);
        RentalDTO updated = new RentalDTO(FACADE.update(rental));
        return Response.ok("SUCCESS").entity(GSON.toJson(updated)).build();
    }

    @GET
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsersByRentalId(@PathParam("id") Long id) throws NotFoundException {
        List<User> users = FACADE.getUsersByRentalId(id);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));

        }
        return Response.ok().entity(GSON.toJson(userDTOS)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    //@RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        Rental rental = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new RentalDTO(rental))).build();
    }

    @GET
    @Path("/house/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHouseByRentalId(@PathParam("id") Long id) throws NotFoundException {

        System.out.println("im here");
        House house = FACADE.getHouseByRentalId(id);
        return Response.ok().entity(GSON.toJson(new HouseDTO(house))).build();
    }
}
