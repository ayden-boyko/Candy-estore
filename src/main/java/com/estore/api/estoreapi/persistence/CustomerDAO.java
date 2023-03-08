package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Customer;

/**
 * Defines the interface for Customer object persistence
 * 
 * @author Aitan Bachrach
 */
public interface CustomerDAO {

    /**
     * Retrieves all {@linkplain Customer customers}
     * 
     * @return An array of {@link Customer customer} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Customer[] getCustomers() throws IOException;

    /**
     * Finds all {@linkplain Candy Candies} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Candy Candies} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Customer[] findCustomers(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Customer customer} with the given id
     * 
     * @param id The id of the {@link Customer customer} to get
     * 
     * @return a {@link Customer customer} object with the matching id
     * <br>
     * null if no {@link Customer customer} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Customer getCustomer(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Customer customer}
     * 
     * @param customer {@linkplain Customer customer} object to be created and saved
     * <br>
     * The id of the customer object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Customer customer} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Customer createCustomer(Customer customer) throws IOException;

    /**
     * Updates and saves a {@linkplain Customer customer}
     * 
     * @param {@link Customer customer} object to be updated and saved
     * 
     * @return updated {@link Customer customer} if successful, null if
     * {@link Customer customer} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Customer updateCustomer(Customer customer) throws IOException;

    /**
     * Deletes a {@linkplain Customer customer} with the given id
     * 
     * @param id The id of the {@link Customer customer}
     * 
     * @return true if the {@link Customer customer} was deleted
     * <br>
     * false if customer with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCustomer(int id) throws IOException;
}
