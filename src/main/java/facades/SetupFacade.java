package facades;

import entities.Role;

import com.google.gson.JsonObject;
import errorhandling.NotFoundException;
import populators.HousePopulator;
import populators.RentalPopulator;
import populators.RolePopulator;
import utils.SetUpTestUsers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class SetupFacade {

    private static EntityManagerFactory emf;
    private static SetupFacade instance;

    private SetupFacade() {}

    public static SetupFacade getSetupFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SetupFacade();
        }
        return instance;
    }

    public JsonObject setupDatabase() throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Role> roleTQ = em.createQuery("SELECT r FROM Role r", Role.class);
        if (roleTQ.getResultList().size() != 0) {
            JsonObject jo = new JsonObject();
            jo.addProperty("status", "ERROR");
            jo.addProperty("msg", "Roles already exist");
            return jo;
        }

        RolePopulator.populateRoles(emf);
        SetUpTestUsers.userPopulator(emf);
        RentalPopulator.populateRentals(emf);
        HousePopulator.populateHouses(emf);
        JsonObject jo = new JsonObject();
        jo.addProperty("status", "SUCCESS");
        jo.addProperty("msg", "Database setup successfully");
        return jo;
    }
}
