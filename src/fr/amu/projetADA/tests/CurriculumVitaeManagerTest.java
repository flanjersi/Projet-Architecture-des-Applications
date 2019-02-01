package fr.amu.projetADA.tests;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    

        person = new Person("Jeremy", "Gros", getNowDate(), "1234", "exampleTestCV@example.com");	

        curriculumVitae = new CurriculumVitae();
        curriculumVitae.setCreatedIn(getNowDate());
        curriculumVitae.setTitle("Example cv");
        
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
    @Ignore
    public void testUpdateCurriculumVitae() {
    	Activity activity = new Activity("Formation", "Master 2 Informatiquee", getNowDate(), getNowDate());
    	
    	curriculumVitae.addActivity(activity);
    	
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	
    	CurriculumVitae curriculumVitaeUpdate = curriculumVitaeManager.findCurriculumVitae(curriculumVitae.getId());
    
    	System.out.println(curriculumVitaeUpdate);
    	System.out.println(curriculumVitae);
    	
    	Assert.assertTrue(curriculumVitaeUpdate.equals(curriculumVitae));
    }
    
    @Test
    @Ignore
    public void testRemoveCurriculumVitae() {
    	
    	long id = curriculumVitae.getId();
    	
    	curriculumVitaeManager.removeCurriculumVitae(curriculumVitae);
    	
    	Person personFound = personManager.findPerson(person.getId()); 
    	
    	Assert.assertNull(curriculumVitaeManager.findCurriculumVitae(id));
    	Assert.assertNull(personFound.getCurriculumVitae());
    }
    
    @Test
    @Ignore
    public void testFindAll() {
    	// Add a another cv 
    	

    	Person person = new Person("Jeremy", "Gros", getNowDate(), "1234", "exampleTestFindAllCV@example.com");	

        
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setCreatedIn(getNowDate());
        curriculumVitae.setTitle("Example cv");
        
        person.setCurriculumVitae(curriculumVitae);
        
    	personManager.addPerson(person);
  	  	
    	// Test find all
    	
    	List<CurriculumVitae> cvs = curriculumVitaeManager.findAllCurriculumVitae(-1, 0);
    	Assert.assertNotNull(cvs);    	
    	Assert.assertTrue(cvs.size() >= 2);
    	
    	personManager.removePerson(person);
    }
    
    @Test
    @Ignore
    public void testfindCvByTitle() {
    	
    	Person person = new Person("youcef", "guellil", getNowDate(), "1234", "example3@example.com");	
    	Person person2 = new Person("youcef", "guellil", getNowDate(), "1234", "example33@example.com");
    	Person person3 = new Person("youcef", "guellil", getNowDate(), "1234", "example333@example.com");
    	
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        CurriculumVitae curriculumVitae1 = new CurriculumVitae();
        CurriculumVitae curriculumVitae2 = new CurriculumVitae();
        curriculumVitae.setCreatedIn(getNowDate());
        curriculumVitae1.setCreatedIn(getNowDate());
        curriculumVitae2.setCreatedIn(getNowDate());
        curriculumVitae.setTitle("Example cv1");
        curriculumVitae.setTitle("Example cv1");
        curriculumVitae.setTitle("Example cv2");
        
        person.setCurriculumVitae(curriculumVitae);
        person2.setCurriculumVitae(curriculumVitae1);
        person3.setCurriculumVitae(curriculumVitae2);
        
    	personManager.addPerson(person);
    	personManager.addPerson(person2);
    	personManager.addPerson(person3);
    	
    	List<CurriculumVitae> cvs = curriculumVitaeManager.findCvByTitle("Example cv1",-1, 0);   	
    	Assert.assertTrue(cvs.size() == 2);
    	
    	List<CurriculumVitae> cvs2 = curriculumVitaeManager.findCvByTitle("Example cv2",-1, 0);   	
    	Assert.assertTrue(cvs.size() == 1);
    }
    
    
    
    private Date getNowDate() {
    	Date date = new Date();
    	 
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	 
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	 
    	date = cal.getTime();
    	
    	return date;
    }
    
}
