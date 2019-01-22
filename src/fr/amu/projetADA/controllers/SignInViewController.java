package fr.amu.projetADA.controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "signInView")
@RequestScoped
public class SignInViewController {

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private String email;
	private String password;

	private String emailFirstConnexion;
	private String passwordFirstConnexion;

	public void login() throws IOException {
		Person p = personManager.findByemail(email);

		if(p != null && p.getLastConnexion() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "It's your first connexion, click on 'First Connexion'", "It's your first connexion, click on 'First Connexion'");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		if(personViewController.login(email, password)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("profil.xhtml");
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknow email or password", "Unknow email or password");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		return;
	}

	public void firstConnexion() {
		Person person = personManager.findByemail(emailFirstConnexion);

		if(person ==  null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknow email", "Unknow email");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else if(person.getLastConnexion() != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password already set", "Password already set");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Success");
			FacesContext.getCurrentInstance().addMessage(null, msg);		

			person.setPassword(passwordFirstConnexion);
			person.setLastConnexion(new Date(System.currentTimeMillis()));

			person = personManager.updatePerson(person);

			personViewController.login(person.getEmail(), person.getPassword());	
		}

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




	public String getEmailFirstConnexion() {
		return emailFirstConnexion;
	}

	public void setEmailFirstConnexion(String emailFirstConnexion) {
		this.emailFirstConnexion = emailFirstConnexion;
	}

	public String getPasswordFirstConnexion() {
		return passwordFirstConnexion;
	}

	public void setPasswordFirstConnexion(String passwordFirstConnexion) {
		this.passwordFirstConnexion = passwordFirstConnexion;
	}

	public void setPersonViewController(PersonViewController personViewController) {
		this.personViewController = personViewController;
	}

}
