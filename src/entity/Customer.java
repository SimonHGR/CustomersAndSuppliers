package entity;

import java.util.Objects;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String address;
    private int creditLimit;

    public Customer(UUID id, String name, String address, int creditLimit) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.creditLimit = creditLimit;
    }

    public Customer() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (this.creditLimit != other.creditLimit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name
                + ", address=" + address
                + ", creditLimit=" + creditLimit + "]";
    }
}