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

import com.estore.api.estoreapi.model.Candy;
import com.estore.api.estoreapi.persistence.CandyFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Candy File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class CandyFileDAOTest {
    CandyFileDAO CandyFileDAO;
    Candy[] testCandies;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCandyFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCandies = new Candy[3];
        testCandies[0] = new Candy(99,"LolliPop", 3, 1, "Hard candy mounted on a stick and intended for sucking or licking.", 0, 0);
        testCandies[1] = new Candy(100,"Gummy Bears", 2, 3, "Small, bear shaped, fruit gum candies.", 0, 0);
        testCandies[2] = new Candy(101,"Chocolate Bar", 1, 2, "A bar of chocolate.", 0, 0);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Candy array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Candy[].class))
                .thenReturn(testCandies);
        CandyFileDAO = new CandyFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCandies() {
        // Invoke
        Candy[] Candies = CandyFileDAO.getCandies();

        // Analyze
        assertEquals(Candies.length,testCandies.length);
        for (int i = 0; i < testCandies.length;++i)
            assertEquals(Candies[i],testCandies[i]);
    }

    @Test
    public void testFindCandies() {
        // Invoke
        Candy[] Candies = CandyFileDAO.findCandies("ar");

        // Analyze
        assertEquals(Candies.length,2);
        assertEquals(Candies[0],testCandies[1]);
        assertEquals(Candies[1],testCandies[2]);
    }

    @Test
    public void testGetCandy() {
        // Invoke
        Candy Candy = CandyFileDAO.getCandy(99);

        // Analzye
        assertEquals(Candy,testCandies[0]);
    }

    @Test
    public void testDeleteCandy() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> CandyFileDAO.deleteCandy(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test Candies array - 1 (because of the delete)
        // Because Candies attribute of CandyFileDAO is package private
        // we can access it directly
        assertEquals(CandyFileDAO.getCandies().length,testCandies.length-1);
    }

    @Test
    public void testCreateCandy() {
        // Setup
        Candy Candy = new Candy(102,"Twinkie", 3, 2, "Golden sponge cake with a creamy vanilla filling", 0, 0);

        // Invoke
        Candy result = assertDoesNotThrow(() -> CandyFileDAO.createCandy(Candy),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Candy actual = CandyFileDAO.getCandy(Candy.getId());
        assertEquals(actual.getId(),Candy.getId());
        assertEquals(actual.getName(),Candy.getName());
    }

    @Test
    public void testUpdateCandy() {
        // Setup
        Candy Candy = new Candy(99,"Tootsie Roll", 1, 1, "A chocolate-flavored taffy", 0, 0);

        // Invoke
        Candy result = assertDoesNotThrow(() -> CandyFileDAO.updateCandy(Candy),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Candy actual = CandyFileDAO.getCandy(Candy.getId());
        assertEquals(actual,Candy);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Candy[].class));

        Candy Candy = new Candy(102,"Cotton Candy", 2, 4, "Edible cotton made from thin strands of sugar.", 0, 0);

        assertThrows(IOException.class,
                        () -> CandyFileDAO.createCandy(Candy),
                        "IOException not thrown");
    }

    @Test
    public void testGetCandyNotFound() {
        // Invoke
        Candy Candy = CandyFileDAO.getCandy(98);

        // Analyze
        assertEquals(Candy,null);
    }

    @Test
    public void testDeleteCandyNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> CandyFileDAO.deleteCandy(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(CandyFileDAO.getCandies().length,testCandies.length);
    }

    @Test
    public void testUpdateCandyNotFound() {
        // Setup
        Candy Candy = new Candy(98,"Jelly Beans", 1, 3, "Beans made of gelatin", 0, 0);

        // Invoke
        Candy result = assertDoesNotThrow(() -> CandyFileDAO.updateCandy(Candy),
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
        // from the CandyFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Candy[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new CandyFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
