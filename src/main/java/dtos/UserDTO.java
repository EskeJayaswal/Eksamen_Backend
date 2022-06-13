package dtos;

import entities.Rental;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private long id;
    private String name;
    private String phone;
    private String job;
    private String userName;
    private String userPass;
    private List<Long> rentals = new ArrayList<>();

    public UserDTO(User user) {
        if(user.getId() != 0) {
            this.id = user.getId();
        }
        this.name = user.getName();
        this.phone = user.getPhone();
        this.job = user.getJob();
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        for (Rental rental : user.getRentalList()) {
            this.rentals.add(rental.getId());
        }
    }

    public User getEntity() {
        User user = new User(this.getName(),this.getPhone(),this.getJob());
        return user;
    }

    public User getEntityFull() {
        User user = new User(this.userName, this.getPhone(), this.getJob(), this.getUserName(), this.userPass);
        return user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Long> getRentals() {
        return rentals;
    }

    public void setRentals(List<Long> rentals) {
        this.rentals = rentals;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
