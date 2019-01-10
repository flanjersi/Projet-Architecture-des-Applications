package fr.amu.projetADA.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "signInView")
@SessionScoped
public class SignInViewController {

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;
	
	private String email;
	private String password;
	
	public String login() {
		if(personViewController.login(email, password)) {
			return "profil";
		}
		
		return "signin";
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public PersonViewController getPersonViewController() {
		return personViewController;
	}


	public void setPersonViewController(PersonViewController personViewController) {
		this.personViewController = personViewController;
	}
	
}
