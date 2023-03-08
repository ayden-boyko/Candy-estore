package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Customer;

/**
 * Implements the functionality for JSON file-based peristance for customers
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Aitan Bachrach, Ayden Boyko
 */
@Component
public class CustomerFileDAO implements CustomerDAO {
    private static final Logger LOG = Logger.getLogger(CustomerFileDAO.class.getName());
    Map<Integer,Customer> customers;   // Provides a local cache of the customer objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Customer
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new customer
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Customer File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CustomerFileDAO(@Value("${customers.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the customers from the file
    }

    /**
     * Generates the next id for a new {@linkplain Customer customer}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Customer customers} from the tree map
     * 
     * @return  The array of {@link Customer customers}, may be empty
     */
    private Customer[] getcustomersArray() {
        return getcustomersArray(null);
    }

    /**
     * Generates an array of {@linkplain Customer customers} from the tree map for any
     * {@linkplain Customer customers} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Customer customers}
     * in the tree map
     * 
     * @return  The array of {@link Customer customers}, may be empty
     */
    private Customer[] getcustomersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Customer> customerArrayList = new ArrayList<>();

        for (Customer customer : customers.values()) {
            if (containsText == null || customer.getUsername().contains(containsText)) {
                customerArrayList.add(customer);
            }
        }

        Customer[] customerArray = new Customer[customerArrayList.size()];
        customerArrayList.toArray(customerArray);
        return customerArray;
    }

    /**
     * Saves the {@linkplain Customer customers} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Customer customers} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Customer[] customerArray = getcustomersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),customerArray);
        return true;
    }

    /**
     * Loads {@linkplain Customer customers} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        customers = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of customers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Customer[] customerArray = objectMapper.readValue(new File(filename),Customer[].class);

        // Add each customer to the tree map and keep track of the greatest id
        for (Customer customer : customerArray) {
            customers.put(customer.getId(),customer);
            if (customer.getId() > nextId)
                nextId = customer.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Customer[] getCustomers() {
        synchronized(customers) {
            return getcustomersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Customer[] findCustomers(String containsText) {
        synchronized(customers) {
            return getcustomersArray(containsText);
        }
    }


    /**
    ** {@inheritDoc}
     */
    @Override
    public Customer getCustomer(int id) {
        synchronized(customers) {
            if (customers.containsKey(id))
                return customers.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Customer createCustomer(Customer customer) throws IOException {
        synchronized(customers) {
            // We create a new customer object because the id field is immutable
            // and we need to assign the next unique id
            Customer newcustomer = new Customer(nextId(),customer.getUsername(), customer.getCart());
            customers.put(newcustomer.getId(), newcustomer);
            save(); // may throw an IOException
            return newcustomer;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Customer updateCustomer(Customer customer) throws IOException {
        synchronized(customers) {
            if (customers.containsKey(customer.getId()) == false)
                return null;  // customer does not exist

            customers.put(customer.getId(),customer);
            save(); // may throw an IOException
            return customer;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteCustomer(int id) throws IOException {
        synchronized(customers) {
            if (customers.containsKey(id)) {
                customers.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
