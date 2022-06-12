/*package dtoFacades;

import dtos.OwnerDTO;
import entities.Owner;
import errorhandling.EntityNotFoundException;
import facades.IFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import java.util.List;

public class OwnerDTOFacade implements IFacade<OwnerDTO> {
    private static IFacade<OwnerDTO> instance;
    private static IFacade<Owner> ownerFacade;

    public OwnerDTOFacade() {
    }

    public static IFacade<OwnerDTO> getFacade() {
        if (instance == null) {
            ownerFacade = OwnerFacade.getFacade(EMF_Creator.createEntityManagerFactory());
            instance = new OwnerDTOFacade();
        }
        return instance;
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner p = ownerDTO.getEntity();
        p = ownerFacade.create(p);
        return new OwnerDTO(p);
    }

    @Override
    public OwnerDTO getById(int id) throws EntityNotFoundException {
        return new OwnerDTO(ownerFacade.getById(id));
    }

    @Override
    public List<OwnerDTO> getAll() {
        return OwnerDTO.toList(ownerFacade.getAll());
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) throws EntityNotFoundException {
        Owner owner = ownerDTO.getEntity();
        owner.setId(ownerDTO.getId());
        Owner p = ownerFacade.update(owner);
        return new OwnerDTO(p);
    }

    @Override
    public OwnerDTO delete(int id) throws EntityNotFoundException {
        return new OwnerDTO(ownerFacade.delete(id));
    }

    public OwnerDTO addRelation(int id1, int id2) throws EntityNotFoundException {
        return new OwnerDTO(ownerFacade.addRelation(id1, id2));
    }

    @Override
    public OwnerDTO removeRelation(int id1, int id2) throws EntityNotFoundException {
        return new OwnerDTO(ownerFacade.removeRelation(id1, id2));
    }
}*/
