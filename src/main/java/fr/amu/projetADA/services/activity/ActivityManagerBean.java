package fr.amu.projetADA.services.activity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.projetADA.beans.cv.Activity;
import fr.amu.projetADA.beans.cv.CurriculumVitae;

@Stateless
public class ActivityManagerBean implements ActivityManager{

	@PersistenceContext(unitName = "myData")
    private EntityManager em;

	@Override
	public Activity findActivity(long id) {
		return em.find(Activity.class, id);
	}

	@Override
	public void removeActivity(Activity activity) {
		em.remove(em.contains(activity) ? activity : em.merge(activity));
	}

	@Override
	public Activity updateActivity(Activity activity) {
		return em.merge(activity);
	}

	@Override
	public List<Activity> findAllActivity() {
		TypedQuery<Activity> q = em.createNamedQuery("findAllActivity", Activity.class);
	    
		return q.getResultList();
	}

	@Override
	public List<Activity> findByTitle(String title) {
		TypedQuery<Activity> q = em.createNamedQuery("findByTitle", Activity.class)
				.setParameter("title", title);
	    
		return q.getResultList();
	}
}
