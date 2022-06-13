package dtos;

import entities.Rental;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class RentalDTO {
    private long id;
    private String startDate;
    private String endDate;
    private int priceAnnual;
    private int deposit;
    private String contactPerson;
    private String house;
    private List<String> tenants = new ArrayList<>();

    public RentalDTO(Rental rental) {
        if(rental.getId() != 0) {
            this.id = rental.getId();
        }
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.priceAnnual = rental.getPriceAnnual();
        this.deposit = rental.getDeposit();
        this.contactPerson = rental.getContactPerson();
        if(rental.getHouse() != null) {
            this.house = rental.getHouse().getAddress();
        } else {
            this.house = "";
        }

        for (User user : rental.getUserList()) {
            this.tenants.add(user.getName());
        }
    }

    public Rental getEntity() {
        Rental rental = new Rental(this.startDate, this.endDate, this.priceAnnual, this.deposit,this.contactPerson);
        return rental;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(int priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<String> getTenants() {
        return tenants;
    }

    public void setTenants(List<String> tenants) {
        this.tenants = tenants;
    }
}
