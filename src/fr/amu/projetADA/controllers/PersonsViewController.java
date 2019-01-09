package fr.amu.projetADA.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;
import io.codearte.jfairy.Fairy;

@ManagedBean(name = "personViewController", eager = false)
@SessionScoped
public class PersonsViewController {

	private Person selectedPerson;

	private List<Person> persons;

	@EJB
	private PersonManager personManager;


	@PostConstruct
	public void init() {
		System.out.println("Test");
		persons = personManager.findAllPerson();
	}
	
 
	public Person getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}	
}
