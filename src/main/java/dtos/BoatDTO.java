package dtos;

import entities.Boat;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class BoatDTO {
    private Long id;
    private String brand;
    private String make;
    private String name;
    private String image;
    private String harbour;
    private List<String> owners = new ArrayList<>();

    public BoatDTO(Boat boat) {
        if (boat.getId() != 0) {
            this.id = boat.getId();
        }
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();

        if(boat.getHarbour() != null) {
            this.harbour = boat.getHarbour().getName();
        } else {
            this.harbour = "";
        }

        for (Owner owner : boat.getOwnerList()) {
            this.owners.add(owner.getName());
        }
    }

    public Boat getEntity() {
        Boat boat = new Boat(this.brand, this.make, this.name, this.image);
        return boat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public String getHarbour() {
        return harbour;
    }

    public void setHarbour(String harbour) {
        this.harbour = harbour;
    }
}
