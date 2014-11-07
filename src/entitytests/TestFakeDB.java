package entitytests;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import entity.Customer;
import entity.CustomerTable;
import entity.RelationshipTable;
import entity.Supplier;
import entity.SupplierTable;

public class TestFakeDB {

    @Test
    public void testCustomerDataFullSet() {
        List<Customer> lc = CustomerTable.getAll();
        Assert.assertEquals("Sample DB has 7 elements", 7, lc.size());
    }

    @Test
    public void testGetCustomer1ByPK() {
        Customer c = CustomerTable.findByPrimaryKey(new UUID(0, 1));
        Assert.assertEquals("Entry with ID 1 should be Fred Jones",
                "Fred Jones", c.getName());
    }

    @Test
    public void testGetCustomer5ByPK() {
        Customer c = CustomerTable.findByPrimaryKey(new UUID(0, 5));
        Assert.assertEquals("Entry with ID 5 should be Ella Barnard",
                "Ella Barnard", c.getName());
        Assert.assertEquals("Entry with ID 5 should have credit of 800",
                800, c.getCreditLimit());
    }

    @Test(expected=NoSuchElementException.class)
    public void testGetCustomer8ByPK() {
        CustomerTable.findByPrimaryKey(new UUID(0, 8));
    }

    @Test
    public void testSuppliersOfFred() {
        List<Supplier> ls = RelationshipTable.findSuppliersOfCustomer(new UUID(0, 1));
        Assert.assertEquals("Fred has 2 suppliers ", 2, ls.size());
        Assert.assertTrue("Fred buys from Floor Mart", ls.contains(SupplierTable.findByPrimaryKey(new UUID(0, 1))));
        Assert.assertTrue("Fred buys from Garden Depot", ls.contains(SupplierTable.findByPrimaryKey(new UUID(0, 3))));
        Assert.assertFalse("Fred does not buy from Bullseye", ls.contains(SupplierTable.findByPrimaryKey(new UUID(0, 2))));
        Assert.assertFalse("Fred does not buy SCCS", ls.contains(SupplierTable.findByPrimaryKey(new UUID(0, 4))));
    }

    @Test
    public void testCustomersOfSCCS() {
        List<Customer> lc = RelationshipTable.findCustomersOfSupplier(new UUID(0, 4));
        Assert.assertEquals("SCCS has 3 customers", 3, lc.size());
        Assert.assertTrue("SCCS sells to Bill", lc.contains(CustomerTable.findByPrimaryKey(new UUID(0, 3))));
        Assert.assertTrue("SCCS sells to Ella", lc.contains(CustomerTable.findByPrimaryKey(new UUID(0, 5))));
        Assert.assertTrue("SCCS sells to Christine", lc.contains(CustomerTable.findByPrimaryKey(new UUID(0, 7))));
    }
    
    @Test
    public void testFindByName() {
        List<Customer> lc = CustomerTable.findByFieldMatch("name", "eq", "Fred Jones");
        Assert.assertEquals("eq match for name Fred Jones should find one element", 1, lc.size());
        Assert.assertEquals("eq match for name Fred Jones should find Fred Jones", "Fred Jones", lc.get(0).getName());
    }
        
    @Test
    public void testInsertCustomer() {
        Customer c = new Customer(null, "Freddy For Testing", "Over the rainbow", 100_000);
        UUID newKey = CustomerTable.updateOrInsert(c);
        Assert.assertTrue("updateOrInsert should return new UUID", newKey != null);
        Assert.assertTrue("updateOrInsert should set new UUID in record", c.getId().equals(newKey));
        Customer c1 = CustomerTable.findByPrimaryKey(newKey);
        Assert.assertNotNull("Get of new record should not return null or throw exception", c1);
        CustomerTable.removeByPrimaryKey(newKey); // clean up lest modified set break other tests
    }
    
    @Test
    public void testUpdateCustomer() {
    	final String NEW_ADDRESS = "Over there";
        Customer c = new Customer(null, "Freddy For More Testing", "Over the rainbow", 1_000);
        UUID key = CustomerTable.updateOrInsert(c);
        Assert.assertNotNull("Insert should not return null key", key);
        c.setAddress(NEW_ADDRESS);
        UUID newKey = CustomerTable.updateOrInsert(c);
        Assert.assertNull("Update should not return a key", newKey);
        
        // re-fetch the customer
        c = CustomerTable.findByPrimaryKey(key);
        Assert.assertEquals("Refetching should get new address", NEW_ADDRESS, c.getAddress());
    }
    
    @Test
    public void testDeleteCustomer() {
        Customer c = new Customer(null, "Nowhere Man For Deletion", "Nowhere", 0);
        UUID newKey = CustomerTable.updateOrInsert(c);
        Customer c1 = CustomerTable.findByPrimaryKey(newKey);
        Assert.assertNotNull("Get of new record should not return null or throw exception", c1);
        CustomerTable.removeByPrimaryKey(newKey);
        // This should fail by throwing an exception
        boolean removed = false;
        try {
            CustomerTable.findByPrimaryKey(newKey);        
        } catch(NoSuchElementException nsee) {
        	removed = true;
        }
        Assert.assertTrue("Element should be removed", removed);
    }
    
    //============== Suppliers ===============
    
    @Test
    public void testSupplierDataFullSet() {
        List<Supplier> ls = SupplierTable.getAll();
        Assert.assertEquals("Sample DB has 4 elements", 4, ls.size());
    }

    @Test
    public void testGetSupplier1ByPK() {
        Supplier s = SupplierTable.findByPrimaryKey(new UUID(0, 1));
        Assert.assertEquals("Entry with ID 1 should be Floor Mart",
                "Floor Mart", s.getName());
    }

    @Test(expected=NoSuchElementException.class)
    public void testGetSupplier8ByPK() {
        SupplierTable.findByPrimaryKey(new UUID(0, 8));
    }

    @Test
    public void testCustomersOfFloorMart() {
        List<Customer> ls = RelationshipTable.findCustomersOfSupplier(new UUID(0, 1));
        Assert.assertEquals("Floor Mart  has 3 customers ", 3, ls.size());
        Assert.assertTrue("Floor sells to Fred", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 1))));
        Assert.assertTrue("Floor sells to Bill", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 3))));
        Assert.assertTrue("Floor sells to Sheila", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 4))));
        
        Assert.assertFalse("Floor does not sell to Jim", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 2))));
        Assert.assertFalse("Floor does not sell to Ella", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 5))));
        Assert.assertFalse("Floor does not sell to Christine", ls.contains(CustomerTable.findByPrimaryKey(new UUID(0, 7))));
    }

    @Test
    public void testInsertSupplier() {
        Supplier c = new Supplier(null, "Bodgitt and Scarper");
        UUID newKey = SupplierTable.updateOrInsert(c);
        Assert.assertTrue("updateOrInsert should return new UUID", newKey != null);
        Assert.assertTrue("updateOrInsert should set new UUID in record", c.getId().equals(newKey));
        Supplier s1 = SupplierTable.findByPrimaryKey(newKey);
        Assert.assertNotNull("Get of new record should not return null or throw exception", s1);
        SupplierTable.removeByPrimaryKey(newKey);
    }
    
    @Test
    public void testUpdate() {
    	final String NEW_NAME = "Trusty Team";
        Supplier s = new Supplier(null, "Dubbious Derek");
        UUID key = SupplierTable.updateOrInsert(s);
        Assert.assertNotNull("Insert should not return null key", key);
        s.setName(NEW_NAME);
        UUID newKey = SupplierTable.updateOrInsert(s);
        Assert.assertNull("Update should not return a key", newKey);
        
        // re-fetch the customer
        s = SupplierTable.findByPrimaryKey(key);
        Assert.assertEquals("Refetching should get new name", NEW_NAME, s.getName());
    }
    
    @Test
    public void testDelete() {
        Supplier s = new Supplier(null, "Bodgitt and Scarper");
        UUID newKey = SupplierTable.updateOrInsert(s);
        Supplier s1 = SupplierTable.findByPrimaryKey(newKey);
        Assert.assertNotNull("Get of new record should not return null or throw exception", s1);
        SupplierTable.removeByPrimaryKey(newKey);
        // This should fail by throwing an exception
        boolean removed = false;
        try {
        	SupplierTable.findByPrimaryKey(newKey);        
        } catch(NoSuchElementException nsee) {
        	removed = true;
        }
        Assert.assertTrue("Element should be removed", removed);
    }
}