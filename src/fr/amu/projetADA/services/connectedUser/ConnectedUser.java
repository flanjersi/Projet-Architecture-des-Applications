package fr.amu.projetADA.services.connectedUser;

import javax.ejb.Local;

import fr.amu.projetADA.beans.person.Person;

@Local
public interface ConnectedUser {
	
	void setPersonLogged(Person person);
	Person getPersonLogged();
	
}
