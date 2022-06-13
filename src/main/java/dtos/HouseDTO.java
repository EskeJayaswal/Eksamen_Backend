package dtos;


import entities.House;
import entities.Rental;

import java.util.ArrayList;
import java.util.List;

public class HouseDTO {
    private long id;
    private String address;
    private String city;
    private String numberOfRooms;
    private List<Long> rentals = new ArrayList<>();

    public HouseDTO(House house) {
        if(house.getId() != 0) {
            this.id = house.getId();
        }

        this.address = house.getAddress();
        this.city = house.getCity();
        this.numberOfRooms = house.getNumberOfRooms();

        if(house.getRentalList() != null) {
            for (Rental rental : house.getRentalList()) {
                this.rentals.add(rental.getId());
            }
        }
    }
    public House getEntity() {
        House house = new House(this.address,this.city,this.numberOfRooms);
        return house;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Long> getRentals() {
        return rentals;
    }

    public void setRentals(List<Long> rentals) {
        this.rentals = rentals;
    }
}
