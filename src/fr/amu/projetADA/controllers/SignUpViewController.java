package fr.amu.projetADA.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.connectedUser.ConnectedUser;
import fr.amu.projetADA.services.person.PersonManager;


@ManagedBean(name = "signUpView")
@SessionScoped
public class SignUpViewController {

	@EJB
	private ConnectedUser connectedUser;
	
	@EJB
	private PersonManager personManager;
	
	private Person person;
	
	public String save() {
		personManager.addPerson(person);
		connectedUser.setPersonLogged(person);
		System.out.println(person);
		return "profil";
	}
	
	public String dateNow() {
		return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}
	
	
}
