package fr.amu.projetADA.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "signUpView")
@SessionScoped
public class SignUpViewController {

	
	
	
	public String dateNow() {
		return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}	
}
