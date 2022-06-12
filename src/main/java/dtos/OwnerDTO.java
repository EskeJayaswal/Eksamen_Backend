package dtos;

import entities.Boat;
import entities.Harbour;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDTO {
    private long id;
    private String name;
    private String address;
    private String phone;
    private List<String> boats = new ArrayList<>();

    public OwnerDTO(Owner owner) {
        if (owner.getId() != 0) {
            this.id = owner.getId();
        }
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
        for (Boat boat : owner.getBoatList()) {
            this.boats.add(boat.getName());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getBoats() {
        return boats;
    }

    public void setBoats(List<String> boats) {
        this.boats = boats;
    }

    public Owner getEntity() {
        Owner owner = new Owner(this.name,this.address,this.phone);
        return owner;
    }
}

 