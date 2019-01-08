package fr.amu.projetADA.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import fr.amu.projetADA.services.activity.ActivityManager;
import fr.amu.projetADA.services.curriculumVitae.CurriculumVitaeManager;
import fr.amu.projetADA.services.person.PersonManager;

@Transactional
public class ActivityManagerTest {

	@EJB
    ActivityManager activityManager;

	@EJB
    CurriculumVitaeManager curriculumVitaeManager;

	@EJB
    PersonManager personManager;

	private Person person;
	
	private CurriculumVitae curriculumVitae;
	
	private Activity activity;
	
	@Before
    public void setUp() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    
        person = new Person("J�r�my", "Gros", new Date(System.currentTimeMillis()), "1234", "example@example.com");	
    
        curriculumVitae = new CurriculumVitae();
        
        //Add curriculum vitae to person
        person.setCurriculumVitae(curriculumVitae);
        
    	personManager.addPerson(person);
	
    	activity = new Activity(2018, "Formation", "Master 2 Informatique");
    	
    	curriculumVitae.addActivity(activity);
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	activity = curriculumVitae.getActivities().get(0);
	}

    @After
    public void tearDown() throws Exception {
    	personManager.removePerson(person);
    	
    	EJBContainer.createEJBContainer().close();
    }

    @Test
    public void testFindById() {
    	Assert.assertNotNull(activityManager.findActivity(activity.getId()));
    	Assert.assertNull(activityManager.findActivity(-1));
    }
    
    @Test
    public void testUpdateActivity() {
    	activity.setTitle("Master 2 Informatique ILD");
    	
    	activityManager.updateActivity(activity);
    
    	Assert.assertTrue(activity.equals(activityManager.findActivity(activity.getId())));
    }
    
    @Test
    public void testRemoveActivity() {
    	
    	Activity activityAdded = new Activity(2017, "Formation", "Master 1 Informatique");
    	
    	curriculumVitae.addActivity(activityAdded);
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	
    	long id = activityAdded.getId();
    	
    	activityManager.removeActivity(activityAdded);
    	
    	Assert.assertNull(activityManager.findActivity(id));
    	
    	CurriculumVitae cv = curriculumVitaeManager.findCurriculumVitae(curriculumVitae.getId());
    	
    	Assert.assertFalse(new ArrayList<>(cv.getActivities()).contains(activityAdded));
    }
    
    @Test
    public void testFindAll() {
    	// Add other activity
    	Activity activity = new Activity(2017, "Formation", "Master 1 Informatique");
    	
    	curriculumVitae.addActivity(activity);
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	
    	
    	Assert.assertTrue(activityManager.findAllActivity().size() >= 2);
    }
    
    @Test
    public void findActivitiesByTitle() {
    	Activity activity = new Activity(2014, "Formation", "Licence Informatique");
    	
    	curriculumVitae.addActivity(activity);
    	curriculumVitae = curriculumVitaeManager.updateCurriculumVitae(curriculumVitae);
    	
    	List<Activity> activities = activityManager.findByTitle("Licence");
    	
    	Assert.assertEquals(1, activities.size());
    	
    	activities = activityManager.findByTitle("Licences");
    	
    	Assert.assertEquals(0, activities.size());
    }

	
}
