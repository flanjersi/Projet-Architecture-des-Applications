package fr.amu.projetADA.services.connectedUser;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@Stateful(name = "connectedUser")
public class ConnectedUserBean implements ConnectedUser{

	@EJB
	private PersonManager personManager;
	
	private Person personlogged;
	
	@Override
	public boolean login(String login, String pwd) {
		Person p = personManager.findByEmailAndPassword(login, pwd);
		
		if(p == null)
			return false;
		
		personlogged = p;
		return true;
	}

	@Override
	public void logout() {
		personlogged = null;
	}

	public void setPersonlogged(Person personlogged) {
		this.personlogged = personlogged;
	}

	@Override
	public Person getPersonLogged() {
		return personlogged;
	}	
}
