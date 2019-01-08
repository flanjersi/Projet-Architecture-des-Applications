package fr.amu.projetADA.services.curriculumVitae;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.projetADA.beans.cv.CurriculumVitae;
import fr.amu.projetADA.beans.person.Person;

@Stateless
public class CurriculumVitaeManagerBean implements CurriculumVitaeManager{
	
	@PersistenceContext(unitName = "myData")
    private EntityManager em;

	@Override
	public CurriculumVitae findCurriculumVitae(long id) {
		return em.find(CurriculumVitae.class, id);
	}

	@Override
	public void removeCurriculumVitae(CurriculumVitae curriculumVitae) {
		Person person = curriculumVitae.getPerson();
		person.setCurriculumVitae(null);
		person = em.merge(person);	
		curriculumVitae.setPerson(null);
		em.remove(em.merge(curriculumVitae));
	}

	@Override
	public CurriculumVitae updateCurriculumVitae(CurriculumVitae curriculumVitae) {
		return em.merge(curriculumVitae);
	}

	@Override
	public List<CurriculumVitae> findAllCurriculumVitae() {
		TypedQuery<CurriculumVitae> q = em.createNamedQuery("findAllCurriculumVitae", CurriculumVitae.class);
	    
		return q.getResultList();
	}
	
	
}
