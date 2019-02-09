package fr.amu.projetADA.services.generatesData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.services.person.PersonManager;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

@Singleton
@Startup
public class DataGenerator {

	private List<String> cvsTitle;
	
	private Fairy fairy; 

	@EJB
	private PersonManager personManager;
	
	@PostConstruct
	public void generateData() {
		if(personManager.countNbPerson() > 1000) {
			return;
		}
		
		setFairy(Fairy.create());
	
		generateListTitleCV();
		
		fr.amu.projetADA.beans.person.Person person;
		
		for(int indexGenerated = 0 ; indexGenerated < 5_000 ; indexGenerated++) {
			if(indexGenerated % 1000 == 0) System.out.println(indexGenerated);
			
			person = generatePeopleAndCV(indexGenerated);
			personManager.addPerson(person);
		}
	
	}
	
	public void generateListTitleCV() {
		cvsTitle = new ArrayList<>();
		
		cvsTitle.add("Stage developpement JAVA");
		cvsTitle.add("Stage developpement C#");
		cvsTitle.add("Stage developpement Python");
		cvsTitle.add("Stage developpement JAVA EE");
		cvsTitle.add("Stage developpement Jakerta EE");
		cvsTitle.add("Stage developpement C++");
		cvsTitle.add("Stage developpement C");
		cvsTitle.add("Stage developpement Ruby");
		cvsTitle.add("Stage Big data");
		cvsTitle.add("Stage Systemes embarques");
		cvsTitle.add("Stage Business analyste");
		cvsTitle.add("Stage chef de projet");
		cvsTitle.add("Stage analyse de donnees");
		cvsTitle.add("Stage service web");		
	}
		
	public fr.amu.projetADA.beans.person.Person generatePeopleAndCV(int cpt) {
		Person person = fairy.person();
		
		fr.amu.projetADA.beans.person.Person personGenerated = new fr.amu.projetADA.beans.person.Person(
				person.getFirstName(), 
				person.getLastName(), 
				person.getDateOfBirth().toDate(), 
				person.getPassword(), 
				"exampleCV" + cpt + "@generatorCV.com"
		);
		
		CurriculumVitae curriculumVitae = new CurriculumVitae(cvsTitle.get((int) (Math.random() * cvsTitle.size())));
		curriculumVitae.setCreatedIn(getNowDate());
        
		personGenerated.setCurriculumVitae(curriculumVitae);
		
		return personGenerated;
	}


	public Fairy getFairy() {
		return fairy;
	}


	public void setFairy(Fairy fairy) {
		this.fairy = fairy;
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
