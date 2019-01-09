package fr.amu.projetADA.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.connectedUser.ConnectedUser;

@ManagedBean(name = "personView")
@SessionScoped
public class PersonViewController {

	@EJB
	private ConnectedUser connectedUser;
	
	public void login(String login, String pwd) {
		connectedUser.login(login, pwd);
	}
	
	public void logout() {
		connectedUser.logout();
	}
	
	public Person getPerson() {
		return connectedUser.getPersonLogged();
	}
	
}
