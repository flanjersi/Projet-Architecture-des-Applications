package fr.amu.projetADA.services.connectedUser;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@Stateful(name = "connectedUser")
public class ConnectedUserBean implements ConnectedUser {

	
	private Person personlogged;
	
	@Override
	public void setPersonLogged(Person person) {
		this.personlogged = person;
	}

	@Override
	public Person getPersonLogged() {
		return personlogged;
	}	
}
