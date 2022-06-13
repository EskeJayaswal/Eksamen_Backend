package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RentalDTO;
import dtos.UserDTO;
import entities.Rental;
import entities.User;
import errorhandling.NotFoundException;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Get all
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<User> userList = FACADE.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userList) {
            userDTOS.add(new UserDTO(user));
        }
        return Response.ok().entity(GSON.toJson(userDTOS)).build();
    }

    //Get by id
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        UserDTO userDTO = new UserDTO(FACADE.getById(id));
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }

    //Create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) throws NotFoundException {
        UserDTO dto = GSON.fromJson(content, UserDTO.class);
        User user = FACADE.create(dto.getEntityFull());
        return Response.ok().entity(GSON.toJson(new UserDTO(user))).build();
    }

    //Update
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        UserDTO dto =GSON.fromJson(jsonContext, UserDTO.class);
        User user = new User(dto.getName(), dto.getPhone(), dto.getJob(),dto.getUserName(),dto.getUserPass());
        user.setId(id);
        UserDTO updated = new UserDTO(FACADE.update(user));
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @Path("/add/{id1}/{id2}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response addRelation(@PathParam("id1") Long id1, @PathParam("id2") Long id2) throws NotFoundException {
        User user = FACADE.addRelation(id1,id2);
        return Response.ok().entity(GSON.toJson(new UserDTO(user))).build();
    }


    @GET
    @Path("/rentals/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalsByUserId(@PathParam("username") String username) throws NotFoundException {
        List<Rental> rentals = FACADE.getRentalByUserName(username);
        List<RentalDTO> rentalDTOS = new ArrayList<>();
        for (Rental rental : rentals) {
            rentalDTOS.add(new RentalDTO(rental));

        }
        return Response.ok().entity(GSON.toJson(rentalDTOS)).build();
    }

    /*@GET
    @Path("/role/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserRole(@PathParam("username") String username) throws EntityNotFoundException {
        String role = FACADE.getById(id);
        return Response.ok().entity(GSON.toJson(pdto)).build();
    }*/




    /*

    //TODO: NOT DONE YET
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        ProfileDTO pdto = GSON.fromJson(content, ProfileDTO.class);
        ProfileDTO newPdto = profileDTOIFacade.create(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }*/


}
