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

import com.estore.api.estoreapi.model.Candy;

/**
 * Implements the functionality for JSON file-based peristance for candies
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class CandyFileDAO implements CandyDAO {
    private static final Logger LOG = Logger.getLogger(CandyFileDAO.class.getName());
    Map<Integer,Candy> candies;   // Provides a local cache of the candy objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Candy
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new candy
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Candy File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CandyFileDAO(@Value("${candies.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the candies from the file
    }

    /**
     * Generates the next id for a new {@linkplain Candy candy}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Candy candies} from the tree map
     * 
     * @return  The array of {@link Candy candies}, may be empty
     */
    private Candy[] getcandiesArray() {
        return getcandiesArray(null);
    }

    /**
     * Generates an array of {@linkplain Candy candies} from the tree map for any
     * {@linkplain Candy candies} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Candy candies}
     * in the tree map
     * 
     * @return  The array of {@link Candy candies}, may be empty
     */
    private Candy[] getcandiesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Candy> candyArrayList = new ArrayList<>();

        for (Candy candy : candies.values()) {
            if (containsText == null || candy.getName().contains(containsText)) {
                candyArrayList.add(candy);
            }
        }

        Candy[] candyArray = new Candy[candyArrayList.size()];
        candyArrayList.toArray(candyArray);
        return candyArray;
    }

    /**
     * Saves the {@linkplain Candy candies} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Candy candies} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Candy[] candyArray = getcandiesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),candyArray);
        return true;
    }

    /**
     * Loads {@linkplain Candy candies} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        candies = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of candies
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Candy[] candyArray = objectMapper.readValue(new File(filename),Candy[].class);

        // Add each candy to the tree map and keep track of the greatest id
        for (Candy candy : candyArray) {
            candies.put(candy.getId(),candy);
            if (candy.getId() > nextId)
                nextId = candy.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Candy[] getCandies() {
        synchronized(candies) {
            return getcandiesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Candy[] findCandies(String containsText) {
        synchronized(candies) {
            return getcandiesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Candy getCandy(int id) {
        synchronized(candies) {
            if (candies.containsKey(id))
                return candies.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Candy createCandy(Candy candy) throws IOException {
        synchronized(candies) {
            // We create a new candy object because the id field is immutable
            // and we need to assign the next unique id
            Candy newcandy = new Candy(nextId(),candy.getName(),candy.getAmount(), candy.getPrice(), 
                candy.getDescription(), candy.getRatingTotal(), candy.getRatingNum());
            candies.put(newcandy.getId(),newcandy);
            save(); // may throw an IOException
            return newcandy;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Candy updateCandy(Candy candy) throws IOException {
        synchronized(candies) {
            if (candies.containsKey(candy.getId()) == false)
                return null;  // candy does not exist

            candies.put(candy.getId(),candy);
            save(); // may throw an IOException
            return candy;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteCandy(int id) throws IOException {
        synchronized(candies) {
            if (candies.containsKey(id)) {
                candies.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNextId() {return nextId;}
}
