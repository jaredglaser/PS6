package base;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import domain.PersonDomainModel;

public class Person_Test {
	
	private PersonDomainModel test = new PersonDomainModel();
	private UUID PersonID = UUID.randomUUID(); 
	private boolean perDeleted= false; //needed to see if the delete method was run, then we dont attempt to delete the person after

	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//add attributes to the one person
		
		test.setPersonID(PersonID);
		test.setFirstName("Jared");
		test.setLastName("Glaser");
		test.setStreet("test");
		test.setPostalCode(19717);
		test.setCity("Newark");
		test.setBirthday(new Date(0));
		
		PersonDAL.addPerson(test);
	}

	@After
	public void tearDown() throws Exception {
		if(!perDeleted)
			PersonDAL.deletePerson(PersonID);
	}

	@Test
	public void testAddAndGetPerson() { //uses the get person method to test if the add was done correctly in the before class
		//get the person we added before the tests
		PersonDomainModel gotPer = PersonDAL.getPerson(PersonID);
		
		
		
		assertTrue(gotPer.getPersonID().equals(test.getPersonID())); //see if we added the person correctly in the before class
		
		
	}
	
	@Test
	public void testDeletePerson(){
		int initSize = PersonDAL.getPersons().size();
		PersonDAL.deletePerson(PersonID); //deletes the person we added before the tests
		int finalSize = PersonDAL.getPersons().size();
		perDeleted = true; //the after method does not try to delete the person since we already deleted them
		assertTrue((initSize - 1) == finalSize); //if there is one less person, we deleted them.
	}
	
	@Test
	public void testUpdatePerson(){
		PersonDomainModel tempPer = PersonDAL.getPerson(PersonID);//grab the person we created at the beginning
		tempPer.setCity("NewCity"); //change the city
		PersonDAL.updatePerson(tempPer); //update the city of the person in the database
		PersonDomainModel upPer = PersonDAL.getPerson(PersonID); //grab the person from the database
		assertTrue(upPer.getCity().equals(tempPer.getCity()));//see if the new city matches the updated person in the database
	}
	

}
