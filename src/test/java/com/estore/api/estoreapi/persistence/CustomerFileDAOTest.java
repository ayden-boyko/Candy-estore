package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Customer;


/**
 * Test the Customer File DAO class
 * 
 * @author Dylan O'Hara, Ayden Boyko
 */
@Tag("Persistence-tier")
public class CustomerFileDAOTest {

    CustomerFileDAO cdao;
    Customer[] testCustomers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCustomerFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        Map<Integer, Integer> expected_map = new HashMap<>();
        testCustomers = new Customer[3];
        testCustomers[0] = new Customer(99,"Wi-Fire",expected_map);
        testCustomers[1] = new Customer(100,"Galactic Agent",expected_map);
        testCustomers[2] = new Customer(101,"Ice Gladiator", expected_map);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Customer array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Customer[].class))
                .thenReturn(testCustomers);
        cdao = new CustomerFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCustomers() {
        // Invoke
        Customer[] Customers = cdao.getCustomers();

        // Analyze
        assertEquals(Customers.length,testCustomers.length);
        for (int i = 0; i < testCustomers.length;++i)
            assertEquals(Customers[i],testCustomers[i]);
    }

    @Test
    public void testFindCustomers() {
        // Invoke
        Customer[] Customers = cdao.findCustomers("la");

        // Analyze
        assertEquals(Customers.length,2);
        assertEquals(Customers[0],testCustomers[1]);
        assertEquals(Customers[1],testCustomers[2]);
    }

    @Test
    public void testGetCustomer() {
        // Invoke
        Customer Customer = cdao.getCustomer(99);

        // Analzye
        assertEquals(Customer,testCustomers[0]);
    }

    @Test
    public void testDeleteCustomer() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cdao.deleteCustomer(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test Customers array - 1 (because of the delete)
        // Because Customers attribute of CustomerFileDAO is package private
        // we can access it directly
        assertEquals(cdao.customers.size(),testCustomers.length-1);
    }

    @Test
    public void testCreateCustomer() {
        // Setup
        Map<Integer, Integer> expected_map = new HashMap<>();
        Customer Customer = new Customer(102,"Wonder-Person", expected_map);

        // Invoke
        Customer result = assertDoesNotThrow(() -> cdao.createCustomer(Customer),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Customer actual = cdao.getCustomer(Customer.getId());
        assertEquals(actual.getId(),Customer.getId());
        assertEquals(actual.getUsername(),Customer.getUsername());
    }

    @Test
    public void testUpdateCustomer() {
        // Setup
        Map<Integer, Integer> expected_map = new HashMap<>();
        Customer Customer = new Customer(99,"Galactic Agent", expected_map);

        // Invoke
        Customer result = assertDoesNotThrow(() -> cdao.updateCustomer(Customer),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Customer actual = cdao.getCustomer(Customer.getId());
        assertEquals(actual,Customer);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Customer[].class));
        
        Map<Integer, Integer> expected_map = new HashMap<>();
        Customer Customer = new Customer(102,"Wi-Fire", expected_map);

        assertThrows(IOException.class,
                        () -> cdao.createCustomer(Customer),
                        "IOException not thrown");
    }

    @Test
    public void testGetCustomerNotFound() {
        // Invoke
        Customer Customer = cdao.getCustomer(98);

        // Analyze
        assertEquals(Customer,null);
    }

    @Test
    public void testDeleteCustomerNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cdao.deleteCustomer(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(cdao.customers.size(),testCustomers.length);
    }

    @Test
    public void testUpdateCustomerNotFound() {
        // Setup
        Map<Integer, Integer> expected_map = new HashMap<>();
        Customer Customer = new Customer(98,"Bolt", expected_map);

        // Invoke
        Customer result = assertDoesNotThrow(() -> cdao.updateCustomer(Customer),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the CustomerFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Customer[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new CustomerFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
