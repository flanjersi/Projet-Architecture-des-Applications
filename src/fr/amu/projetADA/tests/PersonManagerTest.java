package fr.amu.projetADA.tests;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@Transactional
public class PersonManagerTest {

	@EJB
    PersonManager personManager;

	@Before
    public void setUp() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    }

    @After
    public void tearDown() throws Exception {
        EJBContainer.createEJBContainer().close();
    }

    
    @Test
    public void testCRUD() {
    	
    	Person person = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example@example.com");	
    	
    	personManager.addPerson(person);
    
    	Person personAdded = personManager.findPerson(person.getId());
    	
    	Assert.assertTrue(personAdded.equals(person));;
     
    	personAdded.setFirstName("Simon");
    	
    	personManager.updatePerson(personAdded);
    	
    	Person personUpdate = personManager.findPerson(personAdded.getId());
    	
    	Assert.assertTrue(personUpdate.equals(personAdded));
    	
    	long idRemoved = personUpdate.getId();
    	
    	personManager.removePerson(personUpdate);
    	
    	Person personDeleted = personManager.findPerson(idRemoved);
    	
    	Assert.assertNull(personDeleted);
    }
    
    @Test(expected = EJBException.class)
    public void testUniqueEmailContraint() {
    	Person person = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example2@example.com");
    	Person person2 = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example2@example.com");
  
    	personManager.addPerson(person2);
    	personManager.addPerson(person);
    }
    
    @Test
    public void testFindAll() {
    	// Add a another cv 
    	
    	Person person = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example10@example.com");	
    	Person person2 = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example11@example.com");	
    	Person person3 = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example12@example.com");	
        
    	personManager.addPerson(person);
    	personManager.addPerson(person2);
    	personManager.addPerson(person3);
  	  	
    	// Test find all
    	List<Person> persons = personManager.findAllPerson(-1, 0);
    	Assert.assertNotNull(persons);    	
    	Assert.assertTrue(persons.size() >= 3);
    
    
    	personManager.removePerson(person);
    	personManager.removePerson(person2);
    	personManager.removePerson(person3);
    }
    
    
    @Test
    public void testfindByFirstName() {
    	Person person = new Person("Ramzi", "Guellil", new Date(System.currentTimeMillis()), "1234", "example32@example.com");	
    	Person person2 = new Person("Ramzi", "lilleuG", new Date(System.currentTimeMillis()), "4321", "example43@example.com");
    	personManager.addPerson(person);
    	personManager.addPerson(person2);
    	List<Person> persons= personManager.findByFirstName("Ramzi");
    	Assert.assertEquals(2, persons.size());
    	personManager.removePerson(person);
    	personManager.removePerson(person2);
    }
    
    
    
    @Test
    public void testfindByName() {
    	
    	Person person1 = new Person("Youcef", "Guellil", new Date(System.currentTimeMillis()), "1234", "example3@example.com");	
    	Person person2 = new Person("Youcef", "llileuG", new Date(System.currentTimeMillis()), "4321", "example4@example.com");
    	Person person3 = new Person("Youcef", "Guellllllil", new Date(System.currentTimeMillis()), "1234", "example5@example.com");	
    	Person person4 = new Person("Youcef", "llgueli", new Date(System.currentTimeMillis()), "4321", "example6@example.com");
    	personManager.addPerson(person1);
    	personManager.addPerson(person2);
    	personManager.addPerson(person3);
    	personManager.addPerson(person4);
    	List<Person> persons= personManager.findByName("ll");
    	List<Person> persons2= personManager.findByName("llg");
    	List<Person> persons3= personManager.findByName("lig");
    	Assert.assertEquals(0, persons3.size());
    	Assert.assertEquals(1, persons2.size());
    	Assert.assertEquals(4, persons.size());
    	personManager.removePerson(person1);
    	personManager.removePerson(person2);
    	personManager.removePerson(person3);
    	personManager.removePerson(person4);
    	
    }
    
    @Test
    public void testfindByemail() {
    	
    	Person person1 = new Person("Youcef", "Guellil", new Date(System.currentTimeMillis()), "1234", "example34@example.com");	
    	
    	personManager.addPerson(person1);
    	
    	Assert.assertEquals(person1, personManager.findByemail("example34@example.com"));
    	Assert.assertNull(personManager.findByemail("example34@example34.com"));
    	
    	personManager.removePerson(person1);
    }
    
    @Test
    public void testfindByEmailAndPwd() {
    	Person person1 = new Person("Youcef", "Guelil", new Date(System.currentTimeMillis()), "1234", "example340@example.com");	
    
    	personManager.addPerson(person1);

    	Assert.assertNotNull(personManager.findByEmailAndPassword(person1.getEmail(), person1.getPassword()));
    	Assert.assertNull(personManager.findByEmailAndPassword("example666@exampletestauth.com", "1234"));
    	
    	personManager.removePerson(person1);	
    	
    }
    
    
    @Test
    public void testfindByNamesAndFirstName() {
    	Person person1 = new Person("Youcef", "Guelil", new Date(System.currentTimeMillis()), "1234", "example340@example.com");
    	Person person2 = new Person("Rami", "Guelzil", new Date(System.currentTimeMillis()), "1234", "example3400@example.com");
    	Person person3 = new Person("Ramzi", "Guelil", new Date(System.currentTimeMillis()), "1234", "example3434@example.com");
    	
    	personManager.addPerson(person1);
    	personManager.addPerson(person2);
    	personManager.addPerson(person3);

    	Assert.assertEquals(2, personManager.findByNamesAndFirstName("z", -1, 0).size());
    	Assert.assertEquals(0, personManager.findByNamesAndFirstName("w", -1, 0).size());
    	
    	personManager.removePerson(person1);	
    	personManager.removePerson(person2);	
    	personManager.removePerson(person3);	
    	
    }
    
    
    
    
    
    
    
}
