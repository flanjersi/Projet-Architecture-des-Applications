package fr.amu.projetADA.services.connectedUser;

import javax.ejb.Stateful;

@Stateful(name = "connectedUser")
public class ConnectedUserBean implements ConnectedUser{

	@Override
	public boolean login(String login, String pwd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
