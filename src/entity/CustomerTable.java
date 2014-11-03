package entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class CustomerTable {

    private static Map<UUID, Customer> data = new HashMap<UUID, Customer>();

    static {
        for (Customer x : new Customer[]{
            new Customer(new UUID(0, 1), "Fred Jones", "Here", 10),
            new Customer(new UUID(0, 2), "Jim Smith", "There", 100),
            new Customer(new UUID(0, 3), "Bill Bonner", "Where", 20),
            new Customer(new UUID(0, 4), "Sheila Williams", "In Town", 5000),
            new Customer(new UUID(0, 5), "Ella Barnard", "Countryside", 800),
            new Customer(new UUID(0, 6), "Freda Fredricksson", "Uphill", 700),
            new Customer(new UUID(0, 7), "Christine Farrow", "Down Dale", 2000)
        }) {
            data.put(x.getId(), x);
        }
    }

    public static UUID updateOrInsert(Customer c) {
    	// If the input has a primary key that already exists, this is update
    	UUID key = c.getId();
    	if ((key != null) && data.containsKey(key)) {
    		data.put(key, c);
    		return null;
    	} else {
    		key = UUID.randomUUID();
    		c.setId(key);
    		data.put(key, c);
    		return key;
    	}
    }

    public static List<Customer> getAll() {
        List<Customer> rv = new LinkedList<Customer>();
        for (Map.Entry<UUID, Customer> e : data.entrySet()) {
            rv.add(e.getValue());
        }
        return rv;
    }

    public static Customer findByPrimaryKey(UUID pk) {
        Customer rv = data.get(pk);
        if (rv != null) return rv;
        throw new NoSuchElementException("No customer with PK = " + pk);
    }

    public static List<Customer> findByFieldMatch(String field, String operation, String value) {
        List<Customer> rv = new LinkedList<Customer>();
        for (Map.Entry<UUID, Customer> e : data.entrySet()) {
            Customer c = e.getValue();

            String target;
            if ("name".equals(field)) {
                target = c.getName();
            } else if ("address".equals(field)) {
                target = c.getAddress();
            } else if ("creditLimit".equals(field)) {
                target = "" + c.getCreditLimit();
            } else {
                throw new IllegalArgumentException("No such field: " + field);
            }
            boolean match = false;
            operation = operation.toLowerCase();
            if ("eq".equals(operation)) {
                match = target.equals(value);
            } else if ("ne".equals(operation)) {
                match = !target.equals(value);
            } else if ("cont".equals(operation)) {
                match = (target.indexOf(value) != -1);
            } else if ("ncont".equals(operation)) {
                match = (target.indexOf(value) == -1);
            } else {
                throw new IllegalArgumentException("No such operation: " + operation);
            }
            if (match) {
                rv.add(e.getValue());
            }
        }
        return rv;
    }

    public static boolean removeByPrimaryKey(UUID uuid) {
        return data.remove(uuid) != null;
    }
}
