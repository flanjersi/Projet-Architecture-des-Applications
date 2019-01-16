package fr.amu.projetADA.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	
	
	public boolean login(String login, String pwd) {	
		return connectedUser.login(login, pwd);
	}
	
	public void logout() throws IOException {
		connectedUser.logout();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/signin.xhtml");
	}
	
	public void delete() throws IOException {
		personManager.removePerson(connectedUser.getPersonLogged());
		connectedUser.logout();
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/signin.xhtml");
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
