package fr.amu.projetADA.controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import fr.amu.projetADA.beans.person.Person;
import fr.amu.projetADA.services.person.PersonManager;

@ManagedBean(name = "signInView")
@ViewScoped
public class SignInViewController {

	@ManagedProperty("#{personView}")
	private PersonViewController personViewController;

	@EJB
	private PersonManager personManager;

	private String email;
	private String password;

	private String passwordFirstConnexion;

	public void login() throws IOException {
		Person p = personManager.findByemail(email);

		if(p != null && p.getLastConnexion() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "It's your first connexion, set your new password", "It's your first connexion, set your new password");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().executeScript("PF('firstConnexion').show();");

			return;
		}

		if(personViewController.login(email, password)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("profil.xhtml");
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknow email or password", "Unknow email or password");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return;
	}

	public void firstConnexion() throws IOException {
		Person person = personManager.findByemail(email);

		person.setPassword(passwordFirstConnexion);
		person.setLastConnexion(new Date(System.currentTimeMillis()));

		person = personManager.updatePerson(person);

		personViewController.login(email, passwordFirstConnexion);	

		FacesContext.getCurrentInstance().getExternalContext().redirect("profil.xhtml");
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
