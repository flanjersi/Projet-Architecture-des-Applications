package fr.amu.projetADA.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;


@ManagedBean(name = "signUpView")
@RequestScoped
public class SignUpViewController implements Serializable{

	private static final long serialVersionUID = -8129774864627937380L;

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private Person person = new Person();

	public void save() {
		
		if(!personViewController.isLogged()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"You are not logged ! Please sign in before add people", 
					"You are not logged ! Please sign in before add people");
			
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, msg);
	        fc.validationFailed();
	        
	        person.setPassword(null);
	        
	        return;			
		}
		
		
		if(personManager.findByemail(person.getEmail()) != null) {	
			/*
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Email already use", 
					"Email already use");
	        
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, msg);
	        fc.validationFailed();
	        */
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"You are not logged ! Please sign in before add people", 
					"You are not logged ! Please sign in before add people");
			
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, msg);
	        fc.validationFailed();
	        
	        
	        person.setPassword(null);
	        
	        return;
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Person added", "Person added");
        FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, msg);
        
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("My name is " + person.getFirstName() + " " + person.getName() + ". I was born in " + person.getBirthDay() + ".");
		
		person.setDescription(stringBuilder.toString());
		
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
	
	public void handleClose() {
		this.person = new Person();
	}
}
