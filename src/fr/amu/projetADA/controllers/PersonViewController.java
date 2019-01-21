package fr.amu.projetADA.controllers;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.connectedUser.ConnectedUser;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "personView")
@SessionScoped
public class PersonViewController {

	@EJB
	private ConnectedUser connectedUser;
	
	@EJB
	private PersonManager personManager;
	
	@PostConstruct
	public void init() {
		if(personManager.findByemail("admin@admin.fr") == null) {
			Person person = new Person("admin", "admin", new Date(System.currentTimeMillis()), "admin", "admin@admin.fr");
			person.setLastConnexion(new Date(System.currentTimeMillis()));
			personManager.addPerson(person);	
		}
		
	}
	
	public boolean login(String login, String pwd) {	
		Person p = personManager.findByEmailAndPassword(login, pwd);
		
		if(p == null)
			return false;
		
		p.setLastConnexion(new Date(System.currentTimeMillis()));
		p = personManager.updatePerson(p);
		connectedUser.setPersonLogged(p);
		
		return true;
	}
	
	public String logout() throws IOException {
		connectedUser.setPersonLogged(null);

		return "profil";
	}
	
	public String delete() throws IOException {
		personManager.removePerson(connectedUser.getPersonLogged());
		connectedUser.setPersonLogged(null);
		
		return "profil";
	}
	
	public boolean isLogged()  {
		return connectedUser.getPersonLogged() != null;
	}
	
	public Person getPerson() {
		return connectedUser.getPersonLogged();
	}
	
	public void setPerson(Person person) {
		connectedUser.setPersonLogged(person);
	}	
	
	public void edit() {
		setPerson(personManager.updatePerson(getPerson()));
	}
	
	
}
