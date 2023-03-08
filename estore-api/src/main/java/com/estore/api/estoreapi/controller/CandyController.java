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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.CandyDAO;
import com.estore.api.estoreapi.model.Candy;

/**
 * Handles the REST API requests for the Candy resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("candies")
public class CandyController {
    private static final Logger LOG = Logger.getLogger(CandyController.class.getName());
    private CandyDAO CandyDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param CandyDao The {@link CandyDAO Candy Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CandyController(CandyDAO CandyDao) {
        this.CandyDao = CandyDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Candy Candy} for the given id
     * 
     * @param id The id used to locate the {@link Candy Candy}
     * 
     * @return ResponseEntity with {@link Candy Candy} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Candy> getCandy(@PathVariable int id) {
        LOG.info("GET /candies/" + id);
        try {
            Candy candy = CandyDao.getCandy(id);
            if (candy != null)
                return new ResponseEntity<Candy>(candy,HttpStatus.OK);
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
    public ResponseEntity<Candy[]> getCandies() {
        LOG.info("GET /candies");

        try {
            Candy[] candies = CandyDao.getCandies();
            return new ResponseEntity<Candy[]>(candies,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Candy Candies} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Candy Candies}
     * 
     * @return ResponseEntity with array of {@link Candy Candy} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Candies that contain the text "ma"
     * GET http://localhost:8080/Candies/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Candy[]> searchCandies(@RequestParam String name) {
        LOG.info("GET /candies/?name="+name);
        try{
            Candy[] candies = CandyDao.findCandies(name);
            return new ResponseEntity<>(candies, HttpStatus.OK);
        }
        catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Candy Candy} with the provided Candy object
     * 
     * @param Candy - The {@link Candy Candy} to create
     * 
     * @return ResponseEntity with created {@link Candy Candy} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Candy Candy} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Candy> createCandy(@RequestBody Candy candy) {
        LOG.info("POST /candies/" + candy);
        /*
        try {
            Candy[] checkAgainst = CandyDao.findCandies(candy.getName());
            Boolean alreadyExists = false;
            for (int i = 0; i<checkAgainst.length; i++){
                if (checkAgainst[i].getName().equals(candy.getName())){
                    alreadyExists = true;
                    break;
                }
            }
            if (alreadyExists){
                return new ResponseEntity<Candy>(HttpStatus.CONFLICT);
            }
            else{
            Candy newCandy = CandyDao.createCandy(candy); 
            return new ResponseEntity<Candy>(newCandy, HttpStatus.CREATED);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        */
        try {
            Candy newCandy = CandyDao.createCandy(candy);
            // when productFound is false, meaning product is not found
            if (newCandy != null) {
                return new ResponseEntity<>(newCandy, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Candy Candy} with the provided {@linkplain Candy Candy} object, if it exists
     * 
     * @param Candy The {@link Candy Candy} to update
     * 
     * @return ResponseEntity with updated {@link Candy Candy} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Candy> updateCandy(@RequestBody Candy Candy) {
        LOG.info("PUT /candies " + Candy);

        try {
            Candy candy_updated = CandyDao.updateCandy(Candy);
            if (candy_updated != null)
                return new ResponseEntity<Candy>(candy_updated,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Candy Candy} with the given id
     * 
     * @param id The id of the {@link Candy Candy} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Candy> deleteCandy(@PathVariable int id) {
        LOG.info("DELETE/candy/"+id);
            try{
                if(CandyDao.deleteCandy(id)){
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }catch(IOException e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    
}
