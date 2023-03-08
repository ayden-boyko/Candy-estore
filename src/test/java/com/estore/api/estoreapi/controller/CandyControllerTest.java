package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Candy;
import com.estore.api.estoreapi.persistence.CandyDAO;

/**
 * Test the Candy Controller class
 * 
 * @author Liang Chu, Ayden Boyko
 */

@Tag("Controller-tier")

public class CandyControllerTest {
    private CandyDAO mockCandyDAO;
    private CandyController candyController;
    
    @BeforeEach
    public void setupCandyFileDAO(){
        mockCandyDAO = mock(CandyDAO.class);
        candyController = new CandyController(mockCandyDAO);
    }

    @Test
    public void testGetCandy() throws IOException{ // getCandy may throw IOException
        //setup
        Candy candy = new Candy(99, "Gummy Bears", 10, 10, "a url", 0, 0);

        when(mockCandyDAO.getCandy(candy.getId())).thenReturn(candy);

        // Invoke
        ResponseEntity<Candy> response = candyController.getCandy(candy.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(candy, response.getBody());
    }

    @Test
    public void testGetCandyNotFound() throws Exception { // getCandy may throw IOException
        // Setup
        int candyId = 99;
        // When the same id is passed in, our mock Candy DAO will return null, simulating
        // no candy found
        when(mockCandyDAO.getCandy(candyId)).thenReturn(null);

        // Invoke
        ResponseEntity<Candy> response = candyController.getCandy(candyId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetCandyHandleException() throws Exception { // getCandy may throw IOException
        // Setup
        int candyId = 99;
        // When getCandy is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).getCandy(candyId);

        // Invoke
        ResponseEntity<Candy> response = candyController.getCandy(candyId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
    @Test
    public void testCreateCandy() throws IOException {  // createCandy may throw IOException
        // Setup
         Candy candy = new Candy(99, "Gummy Bears", 10, 10, "a url", 0, 0);    
        // when createCandy is called, return true simulating successful
        // creation and save
        when(mockCandyDAO.createCandy(candy)).thenReturn(candy);

        // Invoke
        ResponseEntity<Candy> response = candyController.createCandy(candy);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(candy,response.getBody());
    }

    @Test
    public void testCreateCandyFailed() throws IOException {  // createCandy may throw IOException
        // Setup
         Candy candy = new Candy(99, "Chocolate bar", 10, 10, "a url", 0, 0);
        // when createCandy is called, return false simulating failed
        // creation and save
        when(mockCandyDAO.createCandy(candy)).thenReturn(null);

        // Invoke
        ResponseEntity<Candy> response = candyController.createCandy(candy);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateCandyHandleException() throws IOException {  // createCandy may throw IOException
        // Setup
         Candy candy = new Candy(99, "Gumballs", 10, 10, "a url", 0, 0);

        // When createCandy is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).createCandy(candy);

        // Invoke
        ResponseEntity<Candy> response = candyController.createCandy(candy);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testUpdateCandy() throws IOException { // updateCandy may throw IOException
        // Setup
        Candy candy = new Candy(99, "Chocolate bar", 10, 10, "a url", 0, 0);
        // when updateCandy is called, return true simulating successful
        // update and save
        when(mockCandyDAO.updateCandy(candy)).thenReturn(candy);
        ResponseEntity<Candy> response = candyController.updateCandy(candy);
        candy.setName("Hard Candy");

        // Invoke
        response = candyController.updateCandy(candy);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(candy,response.getBody());
    }

    @Test
    public void testUpdateCandyFailed() throws IOException { // updateCandy may throw IOException
        // Setup
        Candy candy = new Candy(99, "Gummy Candy", 10, 10, "a url", 0, 0);
        // when updateCandy is called, return true simulating successful
        // update and save
        when(mockCandyDAO.updateCandy(candy)).thenReturn(null);

        // Invoke
        ResponseEntity<Candy> response = candyController.updateCandy(candy);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCandyHandleException() throws IOException { // updateCandy may throw IOException
        // Setup
        Candy candy = new Candy(99, "Jelly Beans", 10, 10, "a url", 0, 0);
        // When updateCandy is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).updateCandy(candy);

        // Invoke
        ResponseEntity<Candy> response = candyController.updateCandy(candy);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testGetCandies() throws IOException { // getCandies may throw IOException
        // Setup
        Candy[] candies = new Candy[2];
        candies[0] = new Candy(99, "Gummy Candy", 10, 10, "a url", 0, 0);
        candies[1] = new Candy(100, "Hard Candy", 10, 10, "a url", 0, 0);
        // When getCandies is called return the heroes created above
        when(mockCandyDAO.getCandies()).thenReturn(candies);

        // Invoke
        ResponseEntity<Candy[]> response = candyController.getCandies();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(candies,response.getBody());
    }

    @Test
    public void testGetCandiesHandleException() throws IOException { // getCandies may throw IOException
        // Setup
        // When getCandies is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).getCandies();

        // Invoke
        ResponseEntity<Candy[]> response = candyController.getCandies();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testSearchCandies() throws IOException { // findCandies may throw IOException
        // Setup
        String searchString = "an";
        Candy[] candies = new Candy[2];
        candies[0] = new Candy(99, "Gummy Candy", 10, 10, "a url", 0, 0);
        candies[1] = new Candy(100, "Hard Candy", 10, 10, "a url", 0, 0);
        // When findCandies is called with the search string, return the two
        // candies above
        when(mockCandyDAO.findCandies(searchString)).thenReturn(candies);

        // Invoke
        ResponseEntity<Candy[]> response = candyController.searchCandies(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(candies,response.getBody());
    }

    @Test
    public void testSearchCandiesHandleException() throws IOException { // findCandies may throw IOException
        // Setup
        String searchString = "al";
        // When createCandy is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).findCandies(searchString);

        // Invoke
        ResponseEntity<Candy[]> response = candyController.searchCandies(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    
    @Test
    public void testDeleteCandy() throws IOException { // deleteCandy may throw IOException
        // Setup
        int candyId = 99;
        // when deleteCandy is called return true, simulating successful deletion
        when(mockCandyDAO.deleteCandy(candyId)).thenReturn(true);

        // Invoke
        ResponseEntity<Candy> response = candyController.deleteCandy(candyId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCandyNotFound() throws IOException { // deleteCandy may throw IOException
        // Setup
        int candyId = 99;
        // when deleteCandy is called return false, simulating failed deletion
        when(mockCandyDAO.deleteCandy(candyId)).thenReturn(false);

        // Invoke
        ResponseEntity<Candy> response = candyController.deleteCandy(candyId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteCandyHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        int candyId = 99;
        // When deleteCandy is called on the Mock Candy DAO, throw an IOException
        doThrow(new IOException()).when(mockCandyDAO).deleteCandy(candyId);

        // Invoke
        ResponseEntity<Candy> response = candyController.deleteCandy(candyId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
}
