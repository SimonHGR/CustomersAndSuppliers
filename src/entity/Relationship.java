package entity;

import java.util.Objects;
import java.util.UUID;

public class Relationship {

    private UUID customer;
    private UUID supplier;

    public UUID getCustomer() {
        return customer;
    }

    public UUID getSupplier() {
        return supplier;
    }

    public Relationship(UUID customer, UUID supplier) {
        this.customer = customer;
        this.supplier = supplier;
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
        final Relationship other = (Relationship) obj;
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.supplier, other.supplier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Relationship{" + "customer=" + customer + ", supplier=" + supplier + '}';
    }
}
