package fr.amu.projetADA.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;


@ManagedBean(name = "signUpView")
@SessionScoped
public class SignUpViewController {

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private Person person = new Person();

	public String save() {
		if(personManager.findByemail(person.getEmail()) != null)
			return "profil";

		personManager.addPerson(person);

		/*if(personViewController.getPerson() == null) {
			personViewController.setPerson(person);

		}*/


		person = new Person();

		return "profil";
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
