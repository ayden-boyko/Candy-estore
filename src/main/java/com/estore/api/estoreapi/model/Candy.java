package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Candy entity
 * 
 * @author SWEN Faculty
 */
public class Candy {
    private static final Logger LOG = Logger.getLogger(Candy.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Candy [id=%d, name=%s, amount=%d, price=%.2f, description=%s, ratingTotal=%.2f, ratingNum=%.2f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("amount") private int amount;
    @JsonProperty("price") private double price;
    @JsonProperty("description") private String description;
    @JsonProperty("ratingTotal") private double ratingTotal;
    @JsonProperty("ratingNum") private double ratingNum;

    /**
     * Create a Candy with the given id and name
     * @param id The id of the Candy
     * @param name The name of the Candy
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Candy(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("amount") int amount, 
        @JsonProperty("price") double price, @JsonProperty("description") String description, 
        @JsonProperty("ratingTotal") double ratingTotal, @JsonProperty("ratingNum") double ratingNum) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.ratingTotal = ratingTotal;
        this.ratingNum = ratingNum;
    }

    /**
     * Retrieves the id of the Candy
     * @return The id of the Candy
     */
    public int getId() {return id;}

    /**
     * Sets the name of the Candy - necessary for JSON object to Java object deserialization
     * @param name The name of the Candy
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the Candy
     * @return The name of the Candy
     */
    public String getName() {return name;}

    /**
     * Changes the amount in stock of the Candy
     * @return The amount of the Candy
     */
    public void setAmount(int amount) {this.amount=amount;}

    /**
     * Retrieves the amount in stock of the Candy
     * @return The amount of the Candy
     */
    public int getAmount() {return amount;}

    /**
     * Sets the price of the Candy
     * @param price The price of the Candy 
     */
    public void setPrice(double price) {this.price = price;}
    
    /**
     * Retrieves the price of the Candy
     * @return The price of the Candy
     */
    public double getPrice() {return price;}

    /**
     * Retrieves the description of the Candy
     * @return The description of the Candy
     */
    public String getDescription() {return description;}

    /**
     * Set the rating total of the Candy
     * @param int the new rating total
     */
    public void setRatingTotal(double ratingTotal) {this.ratingTotal = ratingTotal;}

    /**
     * Retrieves the price of the Candy
     * @return The price of the Candy
     */
    public double getRatingTotal() {return ratingTotal;}

    /**
     * Set the number of ratings of the Candy
     * @param int the new number of ratings
     */
    public void setRatingNum(double ratingNum) {this.ratingNum = ratingNum;}

    /**
     * Retrieves the price of the Candy
     * @return The price of the Candy
     */
    public double getRatingNum() {return ratingNum;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,amount,price,description,ratingTotal,ratingNum);
    }
}