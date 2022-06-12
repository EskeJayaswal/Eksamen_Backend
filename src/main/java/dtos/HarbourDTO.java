package dtos;

import entities.Boat;
import entities.Harbour;

import java.util.ArrayList;
import java.util.List;

public class HarbourDTO {
    private long id;
    private String name;
    private String address;
    private int capacity;
    private List<String> boats = new ArrayList<>();

    public HarbourDTO(Harbour harbour) {
        if (harbour.getId() != 0) {
            this.id = harbour.getId();
        }
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
        for(Boat boat : harbour.getBoats()) {
            this.boats.add(boat.getName());
        }
    }

    public Harbour getEntity() {
        Harbour harbour = new Harbour(this.name,this.address,this.capacity);
        return harbour;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getBoats() {
        return boats;
    }

    public void setBoats(List<String> boats) {
        this.boats = boats;
    }
}
