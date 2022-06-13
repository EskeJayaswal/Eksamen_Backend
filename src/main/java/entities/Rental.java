package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental")
@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "startDate")
    private String startDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    private String endDate ;

    @Basic(optional = false)
    @NotNull
    @Column(name = "priceAnnual")
    private int priceAnnual;

    @Basic(optional = false)
    @NotNull
    @Column(name = "deposit")
    private int deposit;

    @Basic(optional = false)
    @NotNull
    @Column(name = "contactPerson")
    private String contactPerson;

    @ManyToMany(mappedBy = "rentalList")
    private List<User> userList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "house_id")
    private House house;

    public Rental() {
    }

    public Rental(String startDate, String endDate, int priceAnnual, int deposit, String contactPerson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addUser(User user) {
        this.userList.add(user);
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", priceAnnual=" + priceAnnual +
                ", deposit=" + deposit +
                ", contactPerson='" + contactPerson + '\'' +
                ", userList=" + userList +
                ", house=" + house +
                '}';
    }
}
