package fr.amu.projetADA.services.activity;

import java.util.List;

import javax.ejb.Local;

import fr.amu.projetADA.beans.cv.Activity;

@Local
public interface ActivityManager {

	public Activity findActivity(long id);
	
	public void removeActivity(Activity activity);
	
	public Activity updateActivity(Activity activity);
	
	public List<Activity> findAllActivity();

	public List<Activity> findByTitle(String title);
}
