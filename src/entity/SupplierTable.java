package entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class SupplierTable {

	private static Map<UUID, Supplier> data = new HashMap<UUID, Supplier>();

	static {
		for (Supplier x : new Supplier[] {
				new Supplier(new UUID(0, 1), "Floor Mart"),
				new Supplier(new UUID(0, 2), "Bullseye"),
				new Supplier(new UUID(0, 3), "Garden Depot"),
				new Supplier(new UUID(0, 4), "SCCS") }) {
			data.put(x.getId(), x);
		}
	}

	public static List<Supplier> getAll() {
		List<Supplier> rv = new LinkedList<Supplier>();
		for (Map.Entry<UUID, Supplier> e : data.entrySet()) {
			rv.add(e.getValue());
		}
		return rv;
	}

	public static Supplier findByPrimaryKey(UUID pk) {
		Supplier rv = data.get(pk);
		if (rv != null)
			return rv;
		throw new NoSuchElementException("No supplier with PK = " + pk);
	}

	public static UUID updateOrInsert(Supplier s) {
		// If the input has a primary key that already exists, this is update
		UUID key = s.getId();
		if ((key != null) && data.containsKey(key)) {
			data.put(key, s);
			return null;
		} else {
			key = UUID.randomUUID();
			s.setId(key);
			data.put(key, s);
			return key;
		}
	}

	public static boolean removeByPrimaryKey(UUID uuid) {
        return data.remove(uuid) != null;
    }
}
