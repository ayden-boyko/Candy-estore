package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Candy;

/**
 * Defines the interface for Candy object persistence
 * 
 * @author SWEN Faculty
 */
public interface CandyDAO {
    /**
     * Retrieves all {@linkplain Candy Candies}
     * 
     * @return An array of {@link Candy candy} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Candy[] getCandies() throws IOException;

    /**
     * Finds all {@linkplain Candy Candies} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Candy Candies} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Candy[] findCandies(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Candy candy} with the given id
     * 
     * @param id The id of the {@link Candy candy} to get
     * 
     * @return a {@link Candy candy} object with the matching id
     * <br>
     * null if no {@link Candy candy} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Candy getCandy(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Candy candy}
     * 
     * @param candy {@linkplain Candy candy} object to be created and saved
     * <br>
     * The id of the candy object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Candy candy} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Candy createCandy(Candy candy) throws IOException;

    /**
     * Updates and saves a {@linkplain Candy candy}
     * 
     * @param {@link Candy candy} object to be updated and saved
     * 
     * @return updated {@link Candy candy} if successful, null if
     * {@link Candy candy} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Candy updateCandy(Candy candy) throws IOException;

    /**
     * Deletes a {@linkplain Candy candy} with the given id
     * 
     * @param id The id of the {@link Candy candy}
     * 
     * @return true if the {@link Candy candy} was deleted
     * <br>
     * false if candy with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCandy(int id) throws IOException;

    /**
     * Returns the next id of a {@linkplain Candy candy}
     * 
     * @return int the next id for a created candy
     */
    int getNextId();
}
