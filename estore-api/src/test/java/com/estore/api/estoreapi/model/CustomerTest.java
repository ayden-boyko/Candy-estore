package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Candy;


/**
 * The unit test suite for the Customer class
 * 
 * @author Aitan Bachrach, Ayden Boyko
 */
@Tag("Model-tier")
public class CustomerTest {

    @Test
    public void testCtor() {
        // Setup
        int expected_id = 50;
        String expected_name = "a new Customer";
        Map<Integer, Integer> expected_map = new HashMap<>();

        // Invoke
        Customer customer = new Customer(expected_id, expected_name, expected_map);

        // Analyze
        assertEquals(expected_name, customer.getUsername());
        assertEquals(expected_id, customer.getId());
    }

    @Test
    public void testSets() {
        // Setup
        int id = 50;
        String name = "a new Customer";
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(id, name, map);

        String expected_name = "a changed Customer";
        Map<Integer, Integer> expected_map = new HashMap<>();
        expected_map.put(1, 3);

        // Invoke
        customer.setUsername(expected_name);
        customer.setCart(expected_map);

        // Analyze
        assertEquals(expected_name, customer.getUsername());
        assertEquals(expected_map, customer.getCart());
    }

    @Test
    public void testToString(){
        // Setup
        int id = 50;
        String name = "a new Customer";
        Map<Integer, Integer> expected_map = new HashMap<>();
        
        String expected_string = String.format(Customer.STRING_FORMAT, id, name, new JSONObject(expected_map)); 
        Customer customer = new Customer(id, name, expected_map);
        
        // Invoke
        String actual_string = customer.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }




}