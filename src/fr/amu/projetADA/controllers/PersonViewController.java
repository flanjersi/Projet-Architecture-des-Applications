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
	
	
	public void checkIsLogged() throws IOException {
		if(connectedUser.getPersonLogged() == null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        ec.redirect(ec.getRequestContextPath() + "/signin.xhtml");
		}
	}
	
	public boolean login(String login, String pwd) {	
		return connectedUser.login(login, pwd);
	}
	
	public void logout() throws IOException {
		connectedUser.logout();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/signin.xhtml");
	}
	
	public void delete() throws IOException {
		connectedUser.logout();
		personManager.removePerson(connectedUser.getPersonLogged());
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/signin.xhtml");
	}
	
	public Person getPerson() {
		return connectedUser.getPersonLogged();
	}
	
	public void setPerson(Person person) {
		connectedUser.setPersonLogged(person);
	}	
}
