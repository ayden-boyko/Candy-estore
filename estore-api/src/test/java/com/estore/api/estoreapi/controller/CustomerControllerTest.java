package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Candy;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.CandyDAO;
import com.estore.api.estoreapi.persistence.CustomerDAO;

/**
 * Test the Customer Controller class
 * 
 * @author Liang Chu, Brendan Hoey, Ayden Boyko
 */

@Tag("Controller-tier")
public class CustomerControllerTest {
    private CustomerDAO mockCustomerDAO;
    private CandyDAO candyDAO;
    private CustomerController customerController;
    private CandyController candyController;


    @BeforeEach
    public void setUp(){
        mockCustomerDAO = mock(CustomerDAO.class);
        candyDAO = mock(CandyDAO.class);
        customerController = new CustomerController(mockCustomerDAO, candyDAO);
        candyController = new CandyController(candyDAO);
    }

    @Test
    public void testGetCustomer() throws IOException{ // getCustomer may throw IOException
         //setup
         Map<Integer, Integer> map = new HashMap<>();
         Customer customer = new Customer(99, "John Doe", map);

         when(mockCustomerDAO.getCustomer(customer.getId())).thenReturn(customer);
 
         // Invoke
         ResponseEntity<Customer> response = customerController.getCustomer(customer.getId());
 
         // Analyze
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(customer, response.getBody());
    }

    @Test
    public void testGetCustomerNotFound() throws Exception { // getCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When the same id is passed in, our mock Customer DAO will return null, simulating
        // no customer found
        when(mockCustomerDAO.getCustomer(customerId)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetCustomerHandleException() throws Exception { // getCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When getCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).getCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateCustomer() throws IOException {  // createCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "John Doe", map);

        // when createCustomer is called, return true simulating successful
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test
    public void testCreateCustomerFailed() throws IOException {  // createCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "Jim Smith", map);

        // when createCustomer is called, return false simulating failed
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateCustomerHandleException() throws IOException {  // createCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "Amy White", map);

        // When createCustomer is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).createCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() throws IOException { // updateCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "John Doe", map);
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);
        customer.setUsername("Sean Evans");

        // Invoke
        response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test
    public void testUpdateCustomerFailed() throws IOException { // updateCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "John Doe", map);
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerHandleException() throws IOException { // updateCustomer may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer customer = new Customer(99, "John Doe", map);
        // When updateCustomer is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).updateCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testGetCustomers() throws IOException { // getCustomers may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(99, "John Doe", map);
        customers[1] = new Customer(100, "Amy White", map);
        // When getCustomers is called return the heroes created above
        when(mockCustomerDAO.getCustomers()).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers,response.getBody());
    }

    @Test
    public void testGetCustomersHandleException() throws IOException { // getCustomers may throw IOException
        // Setup
        // When getCustomers is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).getCustomers();

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testSearchCustomers() throws IOException { // findCustomers may throw IOException
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        String searchString = "Amy White";
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(99, "John Doe", map);
        customers[1] = new Customer(100, "Amy White", map);
        // When findCustomers is called with the search string, return the two
        // candies above
        when(mockCustomerDAO.findCustomers(searchString)).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer> response = customerController.searchCustomers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers[1],response.getBody());
    }

    @Test
    public void testSearchCustomersHandleException() throws IOException { // findCustomers may throw IOException
        // Setup
        String searchString = "al";
        // When findCustomers is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).findCustomers(searchString);

        // Invoke
        ResponseEntity<Customer> response = customerController.searchCustomers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchCustomersNotFound() throws IOException {
        //Setup
        String search = "a";
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(0, -1);
        Customer customer = new Customer(1, "test cust", map);
        Customer customers[] = new Customer[1];
        customers[0] = customer;

        when(mockCustomerDAO.findCustomers(search)).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer> response = customerController.searchCustomers(search);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    
    @Test
    public void testDeleteCustomer() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return true, simulating successful deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(true);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerNotFound() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return false, simulating failed deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(false);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerHandleException() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When deleteCustomer is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).deleteCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCheckoutCartInvalidCustomer() throws IOException{
        // Setup
        int customerId = 99;

       // Invoke
       ResponseEntity<Customer> response = customerController.checkoutCart(customerId);
       
       // Analyze
       assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCheckoutCartEmpty() throws IOException{
        // Setup
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 3);
        Customer customer = new Customer(3, "John Doe", map);

        when(mockCustomerDAO.createCustomer(customer)).thenReturn(customer);
        // Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(customer.getId());
 
        // Analyze
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCheckoutNotEnoughInventory() throws IOException{
        //Setup
        Candy candy = new Candy(0, "test", 0, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(0, 1);
        Customer customer = new Customer(1, "test cust", map);
        
        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(0)).thenReturn(candy);

        
        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);

        //Analyze
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCheckoutWithEmptyCart() throws IOException {
        //Setup
        Map<Integer, Integer> map = new HashMap<>(); 
        Customer customer = new Customer(1, "test cust", map);
        
        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);

        //Analyze
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test 
    public void testSuccesfulCheckout() throws IOException {
        //Setup
        Candy candy = new Candy(0, "test", 1, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(0, 1);
        Customer customer = new Customer(1, "test cust", map);
        Map<Integer, Integer> map2 = new HashMap<>(); 

        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(0)).thenReturn(candy);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);
        customer.setCart(map2);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
        
    }

    @Test 
    public void testSuccesfulSmallMysteryBoxCheckout() throws IOException {
        //Setup
        Candy candy = new Candy(-1, "test", 10, 1, "test small mystery box", 0, 0);
        Candy candy2 = new Candy(1, "test", 10, 1, "test candy", 0, 0);
        Candy candy3 = new Candy(0, "test", 10, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(-1, 1);
        Customer customer = new Customer(1, "test cust", map);
        Map<Integer, Integer> map2 = new HashMap<>();

        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(-1)).thenReturn(candy);
        when(candyDAO.getNextId()).thenReturn(2);
        when(candyDAO.getCandy(1)).thenReturn(candy2);
        when(candyDAO.getCandy(0)).thenReturn(candy3);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);
        customer.setCart(map2);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
        
    }

    @Test 
    public void testSuccesfulSmallMysteryBoxCheckout2() throws IOException {
        //Setup
        Candy candy = new Candy(-1, "test", 1, 1, "test small mystery box", 0, 0);
        Candy candy2 = new Candy(1, "test", 0, 1, "test candy", 0, 0);
        Candy candy3 = new Candy(0, "test", 100, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(-1, 1);
        Customer customer = new Customer(1, "test cust", map);
        Map<Integer, Integer> map2 = new HashMap<>();

        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(-1)).thenReturn(candy);
        when(candyDAO.getNextId()).thenReturn(4);
        when(candyDAO.getCandy(1)).thenReturn(candy2);
        when(candyDAO.getCandy(0)).thenReturn(candy3);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);
        customer.setCart(map2);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
        
    }

    @Test 
    public void testSuccesfulLargeMysteryBoxCheckout() throws IOException {
        //Setup
        Candy candy = new Candy(-2, "test", 10, 1, "test large mystery box", 0, 0);
        Candy candy2 = new Candy(1, "test", 20, 1, "test candy", 0, 0);
        Candy candy3 = new Candy(0, "test", 20, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(-2, 1);
        Customer customer = new Customer(1, "test cust", map);
        Map<Integer, Integer> map2 = new HashMap<>();

        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(-2)).thenReturn(candy);
        when(candyDAO.getNextId()).thenReturn(2);
        when(candyDAO.getCandy(1)).thenReturn(candy2);
        when(candyDAO.getCandy(0)).thenReturn(candy3);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);
        customer.setCart(map2);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test 
    public void testSuccesfullLargeMysteryBoxCheckout2() throws IOException {
        //Setup
        Candy candy = new Candy(-2, "test", 1, 1, "test small mystery box", 0, 0);
        Candy candy2 = new Candy(1, "test", 0, 1, "test candy", 0, 0);
        Candy candy3 = new Candy(0, "test", 100, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(-2, 1);
        Customer customer = new Customer(1, "test cust", map);
        Map<Integer, Integer> map2 = new HashMap<>();

        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(-2)).thenReturn(candy);
        when(candyDAO.getNextId()).thenReturn(4);
        when(candyDAO.getCandy(1)).thenReturn(candy2);
        when(candyDAO.getCandy(0)).thenReturn(candy3);

        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);
        customer.setCart(map2);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
        
    }


    @Test
    public void testCheckoutNegativeAmount() throws IOException {
        //Setup
        Candy candy = new Candy(0, "test", 0, 1, "test candy", 0, 0);
        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(0, -1);
        Customer customer = new Customer(1, "test cust", map);
        
        when(mockCustomerDAO.getCustomer(1)).thenReturn(customer);
        when(candyDAO.getCandy(0)).thenReturn(candy);

        
        //Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(1);

        //Analyze
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCheckoutHandleException() throws IOException { 
        // Setup
        int customerId = 1;
        doThrow(new IOException()).when(mockCustomerDAO).getCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.checkoutCart(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
    

}
