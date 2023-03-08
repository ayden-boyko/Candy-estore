package com.estore.api.estoreapi.model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Customer entity
 * 
 * @author Aitan Bachrach, Liang Chu
 */
public class Customer {
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Customer [id=%d, username=%s, cart=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("cart") private Map<Integer, Integer> cart;
    @JsonProperty("previousOrder") private Map<Integer, Integer> previousOrder;

    /**
     * Create a Customer with the given id and username
     * @param id The id of the Customer
     * @param username The username of the Customer
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Customer(@JsonProperty("id") int id, @JsonProperty("username") String name, @JsonProperty("cart") Map<Integer, Integer> cart) {
        this.id = id;
        this.username = name;
        this.cart = cart;
    }

    /**
     * Retrieves the id of the Customer
     * @return The id of the Customer
     */
    public int getId() {return id;}

    /**
     * Sets the username of the Customer - necessary for JSON object to Java object deserialization
     * @param name The username of the Customer
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the Customer
     * @return The name of the Customer
     */
    public String getUsername() {return username;}

    /* TODO */
    public Map<Integer,Integer> getCart() {return cart;}


    /* TODO */
    public void setCart(Map<Integer,Integer> cart){
        this.cart = cart;
    }

    public void setpreviousOrder(Map<Integer,Integer> cart){
        this.previousOrder = cart;
    }

    public Map<Integer, Integer> getPreviousOrder(){
        return previousOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,username,new JSONObject(cart));
    }
}