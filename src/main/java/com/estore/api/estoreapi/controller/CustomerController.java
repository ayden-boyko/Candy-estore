package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.CandyDAO;
import com.estore.api.estoreapi.persistence.CustomerDAO;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Candy;


/**
 * Handles the REST API requests for the Customer resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("customers")
public class CustomerController {
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());
    private CustomerDAO CustomerDao;
    private CandyDAO candyDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param CustomerDao The {@link CustomerDAO Customer Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CustomerController(CustomerDAO CustomerDao, CandyDAO candyDAO) {
        this.CustomerDao = CustomerDao;
        this.candyDAO = candyDAO;
    }


    /**
     * Responds to the GET request for a {@linkplain Customer Customer} for the given id
     * 
     * @param id The id used to locate the {@link Customer Customer}
     * 
     * @return ResponseEntity with {@link Customer Customer} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        LOG.info("GET /customers/" + id);
        try {
            Customer customer = CustomerDao.getCustomer(id);
            if (customer != null)
                return new ResponseEntity<Customer>(customer,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Candy Candies}
     * 
     * @return ResponseEntity with array of {@link Candy Candy} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Customer[]> getCustomers() {
        LOG.info("GET /customers");

        try {
            Customer[] customers = CustomerDao.getCustomers();
            return new ResponseEntity<Customer[]>(customers,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     TODO
     customer by name
     */
    @GetMapping("/")
    public ResponseEntity<Customer> searchCustomers(@RequestParam String name) {
        LOG.info("GET /customers/?name="+name);
        try{
            Customer customer[] = CustomerDao.findCustomers(name);
            for (int i = 0; i < customer.length; i++){
                if (customer[i].getUsername().equals(name)){
                    return new ResponseEntity<Customer>(customer[i], HttpStatus.OK);
                }
            } 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Customer Customer} with the provided Customer object
     * 
     * @param Customer - The {@link Customer Customer} to create
     * 
     * @return ResponseEntity with created {@link Customer Customer} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Customer Customer} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        LOG.info("POST /customers/" + customer);
        /*
        try {
            Customer[] checkAgainst = CustomerDao.findCustomers(customer.getUsername());
            Boolean alreadyExists = false;
            for (int i = 0; i<checkAgainst.length; i++){
                if (checkAgainst[i].getUsername().equals(customer.getUsername())){
                    alreadyExists = true;
                    break;
                }
            }
            if (alreadyExists){
                return new ResponseEntity<Customer>(HttpStatus.CONFLICT);
            }
            else{
            Customer newCustomer = CustomerDao.createCustomer(customer); 
            return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        */
         
        try {
            Customer newCustomer = CustomerDao.createCustomer(customer);
            // when productFound is false, meaning product is not found
            if (newCustomer != null) {
                return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Updates the {@linkplain Customer Customer} with the provided {@linkplain Customer Customer} object, if it exists
     * 
     * @param Customer The {@link Customer Customer} to update
     * 
     * @return ResponseEntity with updated {@link Customer Customer} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        LOG.info("PUT /customers " + customer);

        try {
            Customer customer_updated = CustomerDao.updateCustomer(customer);
            if (customer_updated != null)
                return new ResponseEntity<Customer>(customer_updated,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Customer Customer} with the given id
     * 
     * @param id The id of the {@link Customer Customer} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        LOG.info("DELETE/customer/"+id);
            try{
                if(CustomerDao.deleteCustomer(id)){
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }catch(IOException e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Customer> checkoutCart(@PathVariable int id) {
        LOG.info("CHECKOUT/customer/"+id);
        int lbox_counter = 0;
        int sbox_counter = 0;
        try {
            SecureRandom numgen = new SecureRandom();
            Customer customer = CustomerDao.getCustomer(id);
            if (customer != null){
                Map<Integer,Integer> cart = customer.getCart();
                if (cart.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    for (int key : cart.keySet()) {
                        int value = cart.get(key);
                        Candy candy = candyDAO.getCandy(key);
                        if (candy.getAmount() - value < 0 || value < 0) {
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        }
                    }
                    ArrayList<Candy> tbpurchased = new ArrayList<>();
                    for (int key : cart.keySet()) {
                        if (key == -1){
                            sbox_counter += cart.get(key);
                        }
                        else if (key == -2){
                            lbox_counter += cart.get(key);
                        }
                        else {
                            Candy candy = candyDAO.getCandy(key);
                            tbpurchased.add(candy);
                        } 
                    }
                    // Check for enough candies available
                    int cart_total = 0; int candy_total = 0;
                    for (int i = 0; i < tbpurchased.size(); i++) 
                        cart_total += cart.get(tbpurchased.get(i).getId());
                    cart_total += (10 * sbox_counter) + (20 * lbox_counter);
                    for (Candy c : candyDAO.getCandies())
                        if (c.getId() >= 0) candy_total += c.getAmount();
                    if (cart_total > candy_total) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    // Update candy numbers if valid
                    if (cart.containsKey(-1)) {
                        Candy candy = candyDAO.getCandy(-1);
                        candy.setAmount(candy.getAmount()-cart.get(-1));
                        candyDAO.updateCandy(candy);
                        cart.remove(-1);
                    }
                    if (cart.containsKey(-2)) {
                        Candy candy = candyDAO.getCandy(-2);
                        candy.setAmount(candy.getAmount()-cart.get(-2));
                        candyDAO.updateCandy(candy);
                        cart.remove(-2);
                    }
                    for (Candy c : tbpurchased) {
                        c.setAmount(c.getAmount()-cart.get(c.getId()));
                        candyDAO.updateCandy(c);
                    }
                    // Generate random candies
                    while(sbox_counter > 0){
                        for (int i = 0; i < 10; i++) {
                            Candy gencandy;
                            int randomcandy = numgen.nextInt(candyDAO.getNextId());
                            while (true) {
                                if (candyDAO.getCandy(randomcandy) != null) {
                                    gencandy = candyDAO.getCandy(randomcandy);
                                    if (gencandy.getAmount() > 0) {
                                        break;
                                    }
                                }
                                randomcandy = numgen.nextInt(candyDAO.getNextId());
                            }
                            gencandy.setAmount(gencandy.getAmount()-1);
                            Integer num = cart.get(gencandy.getId());
                            if (num == null) {cart.put(gencandy.getId(), 1);}
                            else {cart.put(gencandy.getId(), cart.get(gencandy.getId()) + 1);}
                            candyDAO.updateCandy(gencandy);
                        }
                        sbox_counter--;
                    }
                    while(lbox_counter > 0){
                        for (int i = 0; i < 20; i++){
                            Candy gencandy;
                            int randomcandy = numgen.nextInt(candyDAO.getNextId());
                            while (true) {
                                if (candyDAO.getCandy(randomcandy) != null) {
                                    gencandy = candyDAO.getCandy(randomcandy);
                                    if (gencandy.getAmount() > 0) {
                                        break;
                                    }
                                }
                                randomcandy = numgen.nextInt(candyDAO.getNextId());
                            }
                            gencandy.setAmount(gencandy.getAmount()-1);
                            Integer num = cart.get(gencandy.getId());
                            if (num == null) {cart.put(gencandy.getId(), 1);}
                            else {cart.put(gencandy.getId(), cart.get(gencandy.getId()) + 1);}
                            candyDAO.updateCandy(gencandy);
                        }
                        lbox_counter--;
                    }
                    customer.setpreviousOrder(cart);
                    Map<Integer,Integer> newCart = new HashMap<Integer,Integer>();
                    customer.setCart(newCart);
                    updateCustomer(customer);
                    return new ResponseEntity<Customer>(customer,HttpStatus.OK);
                }
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}