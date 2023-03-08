package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the Candy class
 * 
 * @author Aitan Bachrach
 */
@Tag("Model-tier")
public class CandyTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 50;
        String expected_name = "Sponge Candy";
        int expected_amount = 5;
        double expected_price = 3.50;
        String expected_description = "A honeycomb candy";
        int expected_rating_total = 50;
        int expected_rating_num = 50;

        // Invoke
        Candy candy = new Candy(expected_id, expected_name, expected_amount, expected_price, 
            expected_description, expected_rating_total, expected_rating_num);

        // Analyze
        assertEquals(expected_name, candy.getName());
        assertEquals(expected_id, candy.getId());
        assertEquals(expected_amount, candy.getAmount());
        assertEquals(expected_price, candy.getPrice());
        assertEquals(expected_description, candy.getDescription());
        assertEquals(expected_rating_total, candy.getRatingTotal());
        assertEquals(expected_rating_num, candy.getRatingNum());
    }

    @Test
    public void testSets(){
        // Setup
        int id = 50;
        String name = "Sponge Candy";
        int amount = 5;
        double price = 3.50;
        String description = "A honeycomb candy";
        int rating_total = 0;
        int rating_num = 0;
        Candy candy = new Candy(id, name, amount, price, description, rating_total, rating_num);
        
        String expected_name = "Sponge Candy 12 pc";
        double expected_price = 11.99;
        int expected_rating_total = 50;
        int expected_rating_num = 50;

        // Invoke
        candy.setName(expected_name);
        candy.setPrice(expected_price);
        candy.setRatingTotal(expected_rating_total);
        candy.setRatingNum(expected_rating_num);

        // Analyze
        assertEquals(expected_name, candy.getName());
        assertEquals(expected_price, candy.getPrice());
        assertEquals(expected_rating_total, candy.getRatingTotal());
        assertEquals(expected_rating_num, candy.getRatingNum());
    }

    @Test
    public void testToString(){
        // Setup
        int id = 50;
        String name = "Sponge Candy";
        int amount = 5;
        double price = 3.50;
        String description = "A honeycomb candy";
        double rating_total = 0.0;
        double rating_num = 0.0;
        
        String expected_string = String.format(Candy.STRING_FORMAT, id, name, amount, price, 
            description, rating_total, rating_num); 
        Candy candy = new Candy(id, name, amount, price, description, rating_total, rating_num);
        
        // Invoke
        String actual_string = candy.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

}