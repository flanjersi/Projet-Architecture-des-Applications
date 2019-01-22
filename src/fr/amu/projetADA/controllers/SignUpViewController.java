package fr.amu.projetADA.controllers;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;


@ManagedBean(name = "signUpView")
@RequestScoped
public class SignUpViewController {

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private Person person = new Person();

	public void save() {
		if(!personViewController.isLogged()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not logged ! Please sign in before add people", "You are not logged ! Please sign in before add people");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
	        return;			
		}
		
		
		if(personManager.findByemail(person.getEmail()) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already use", "Email already use");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
	        return;
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Person added", "Person added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("My name is " + person.getFirstName() + " " + person.getName() + ". I was born in " + person.getBirthDay() + ".");
		
		person.setDescription(stringBuilder.toString());
		
		CurriculumVitae curriculumVitae = new CurriculumVitae();
		curriculumVitae.setTitle("Mon premier CV");
		
		person.setCurriculumVitae(curriculumVitae);
		
		personManager.addPerson(person);

		person = new Person();

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonViewController getPersonViewController() {
		return personViewController;
	}


	public void setPersonViewController(PersonViewController personViewController) {
		this.personViewController = personViewController;
	}
}
