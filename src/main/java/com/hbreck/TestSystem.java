package com.hbreck;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class TestSystem {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JEETUT");
	

	public static void main(String[] args) {
		
		addCustomer(1, "Sue", "Smith");
		addCustomer(2, "Sam", "Smith");
		addCustomer(3, "Sid", "Smith");
		addCustomer(4, "Sally", "Smith");
		getCustomer(1);
		getCustomers();
		changeFName(4, "Mark");
		deleteCustomer(3);    	
		
		entityManagerFactory.close();

	}
	
	public static void addCustomer(int id, String fname, String lname) {
		EntityManager eManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = null;
		try {
			entityTransaction = eManager.getTransaction();
			entityTransaction.begin();
			Customer cust = new Customer();
			cust.setId(id);
			cust.setfName(fname);
			cust.setlName(lname);
			eManager.persist(cust);
			entityTransaction.commit();
		}
		catch (Exception e) {
			
			if(entityTransaction != null) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		}
		finally {
			eManager.close();
		}
	}
	
	public static void getCustomer(int id) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	
    	// the lowercase c refers to the object
    	// :custID is a parameterized query thats value is set below
    	String query = "SELECT c FROM Customer c WHERE c.id = :custID";
    	
    	// Issue the query and get a matching Customer
    	TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
    	tq.setParameter("custID", id);
    	
    	Customer cust = null;
    	try {
    		// Get matching customer object and output
    		cust = tq.getSingleResult();
    		System.out.println(cust.getfName() + " " + cust.getlName());
    	}
    	catch(NoResultException ex) {
    		ex.printStackTrace();
    	}
    	finally {
    		em.close();
    	}
    	
    }
	
	public static void getCustomers() {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	
    	// the lowercase c refers to the object
    	// :custID is a parameterized query thats value is set below
    	String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";
    	
    	// Issue the query and get a matching Customer
    	TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
    	List<Customer> custs;
    	try {
    		// Get matching customer object and output
    		custs = tq.getResultList();
    		custs.forEach(cust->System.out.println(cust.getlName() + " " + cust.getlName()));
    	}
    	catch(NoResultException ex) {
    		ex.printStackTrace();
    	}
    	finally {
    		em.close();
    	}
    }
	
	public static void changeFName(int id, String fname) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        
    	Customer cust = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find customer and make changes
            cust = em.find(Customer.class, id);
            cust.setfName(fname);

            // Save the customer object
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }
	
	public static void deleteCustomer(int id) {
    	EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        Customer cust = null;

        try {
            et = em.getTransaction();
            et.begin();
            cust = em.find(Customer.class, id);
            em.remove(cust);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

}
