package entities;

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultElement;
import jdk.jfr.Name;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "house")
@NamedQuery(name = "House.deleteAllRows", query = "DELETE from House")
public class House implements Serializable {

    private static final long serializedVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "address")
    private String address;

    @Basic(optional = false)
    @NotNull
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @NotNull
    @Column(name = "numberOfRooms")
    private String numberOfRooms;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "house")
    private List<Rental> rentalList;

    public House() {
    }

    public House(String address, String city, String numberOfRooms) {
        this.address = address;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Rental> getRentalList() {
        return rentalList;
    }

    public void setRentalList(List<Rental> rentalList) {
        this.rentalList = rentalList;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", rentalList=" + rentalList +
                '}';
    }
}
