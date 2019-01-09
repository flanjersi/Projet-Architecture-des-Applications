package fr.amu.projetADA.tests;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.curriculumVitae.CurriculumVitaeManager;
import fr.amu.projetADA.services.person.PersonManager;

@Transactional
public class CurriculumVitaeManagerTest {

	@EJB
    CurriculumVitaeManager curriculumVitaeManager;

	@EJB
    PersonManager personManager;

	private Person person;
	
	private CurriculumVitae curriculumVitae;
	
	@Before
    public void setUp() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    
        person = new Person("Jérémy", "Gros", new Date(System.currentTimeMillis()), "1234", "example@example.com");	
    
        curriculumVitae = new CurriculumVitae();
        
        //Add curriculum vitae to person
        person.setCurriculumVitae(curriculumVitae);
        
    	personManager.addPerson(person);
	}

    @After
    public void tearDown() throws Exception {
       
    	personManager.removePerson(person);
    	
    	EJBContainer.createEJBContainer().close();
    }
    
    @Test
    public void testAddCurriculumVitae() {
    	Assert.assertNotNull(curriculumVitae.getPerson());
    	Assert.assertNotNull(person.getCurriculumVitae());   
    }
    

    @Test
    public void testFindById() {
    	Assert.assertNotNull(curriculumVitaeManager.findCurriculumVitae(curriculumVitae.getId()));
    	Assert.assertNull(curriculumVitaeManager.findCurriculumVitae(-1));
    }
    
    @Test
    public void testUpdateCurriculumVitae() {
    	Activity activity = new Activity(2018, "Formation", "Master 2 Informatique");
    	
    	curriculumVitae.addActivity(activity);
    	
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	
    	CurriculumVitae curriculumVitaeUpdate = curriculumVitaeManager.findCurriculumVitae(curriculumVitae.getId());
    
    	Assert.assertTrue(curriculumVitaeUpdate.equals(curriculumVitae));
    }
    
    @Test
    public void testRemoveCurriculumVitae() {
    	
    	long id = curriculumVitae.getId();
    	
    	curriculumVitaeManager.removeCurriculumVitae(curriculumVitae);
    	
    	Person personFound = personManager.findPerson(person.getId()); 
    	
    	Assert.assertNull(curriculumVitaeManager.findCurriculumVitae(id));
    	Assert.assertNull(personFound.getCurriculumVitae());
    }
    
    @Test
    public void testFindAll() {
    	// Add a another cv 
    	
    	Person person = new Person("Jérémy", "Gros", new Date(System.currentTimeMillis()), "1234", "example2@example.com");	
        
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        
        person.setCurriculumVitae(curriculumVitae);
        
    	personManager.addPerson(person);
  	  	
    	// Test find all
    	
    	List<CurriculumVitae> cvs = curriculumVitaeManager.findAllCurriculumVitae();
    	Assert.assertNotNull(cvs);    	
    	Assert.assertTrue(cvs.size() >= 2);
    }
    
}
