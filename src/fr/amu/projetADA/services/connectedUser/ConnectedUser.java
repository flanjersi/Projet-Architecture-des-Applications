package fr.amu.projetADA.services.connectedUser;

import javax.ejb.Local;

import fr.amu.projetADA.beans.person.Person;

@Local
public interface ConnectedUser {
	
	boolean login(String login, String pwd);
	void logout();
	
	Person getPersonLogged();
	
}
