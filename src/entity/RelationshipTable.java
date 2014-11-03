package entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class RelationshipTable {

    private static List<Relationship> relationships = new LinkedList<Relationship>();

    static {
        for (Relationship x : new Relationship[]{
            new Relationship(new UUID(0, 1), new UUID(0, 1)), // Fred - Floor
            new Relationship(new UUID(0, 1), new UUID(0, 3)), // Fred - Garden
            new Relationship(new UUID(0, 2), new UUID(0, 2)), // Jim - Bullseye
            new Relationship(new UUID(0, 3), new UUID(0, 3)), // Bill - Garden
            new Relationship(new UUID(0, 3), new UUID(0, 4)), // Bill - SCCS
            new Relationship(new UUID(0, 3), new UUID(0, 2)), // Bill - Bullseye
            new Relationship(new UUID(0, 3), new UUID(0, 1)), // Bill - Floor
            new Relationship(new UUID(0, 4), new UUID(0, 1)), // Sheila - Floor
            new Relationship(new UUID(0, 4), new UUID(0, 3)), // Sheila - Garden
            new Relationship(new UUID(0, 5), new UUID(0, 4)), // Ella - SCCS
            new Relationship(new UUID(0, 7), new UUID(0, 2)), // Christine - Bullseye
            new Relationship(new UUID(0, 7), new UUID(0, 4)) // Christine - SCCS
        }) {
            relationships.add(x);
        }
    }

    public static List<Relationship> getAll() {
        return Collections.unmodifiableList(relationships);
    }

    public static List<Supplier> findSuppliersOfCustomer(UUID custPk) {
        List<Supplier> rv = new LinkedList<Supplier>();
        for (Relationship r : relationships) {
            if (r.getCustomer().equals(custPk)) {
                rv.add(SupplierTable.findByPrimaryKey(r.getSupplier()));
            }
        }
        return rv;
    }

    public static List<Customer> findCustomersOfSupplier(UUID supplierPk) {
        List<Customer> rv = new LinkedList<Customer>();
        for (Relationship r : relationships) {
            if (r.getSupplier().equals(supplierPk)) {
                rv.add(CustomerTable.findByPrimaryKey(r.getCustomer()));
            }
        }
        return rv;
    }
}
