package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harbours")
@NamedQuery(name = "Harbour.deleteAllRows", query = "DELETE from Harbour")
public class Harbour implements Serializable {

    private static final long serializedVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "address")
    private String address;

    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "harbour")
    private List<Boat> boats;

    public Harbour() {
    }

    public Harbour(String name, String address, int capacity) {
        this.boats = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Boat> getBoats() {
        return boats;
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
        boat.setHarbour(this);
    }

    public void removeBoat(Boat boat) {
        this.boats.remove(boat);
        boat.setHarbour(null);
    }

    @Override
    public String toString() {
        return "Harbour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", boats=" + boats +
                '}';
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }


}
