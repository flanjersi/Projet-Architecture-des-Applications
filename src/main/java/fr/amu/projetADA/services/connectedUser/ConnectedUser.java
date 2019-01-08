package fr.amu.projetADA.services.connectedUser;

import javax.ejb.Local;

@Local
public interface ConnectedUser {
	
	boolean login(String login, String pwd);
	void logout();
	String getLogin();
	
}
