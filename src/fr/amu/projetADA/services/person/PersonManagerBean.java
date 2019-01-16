package fr.amu.projetADA.services.person;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.amu.projetADA.services.person.PersonManager;
import fr.amu.projetADA.beans.person.Person;

@Stateless
public class PersonManagerBean implements PersonManager{

	@PersistenceContext(unitName = "myData")
    private EntityManager em;

	
	@Override
	public Person findPerson(long id) {
		return em.find(Person.class, id);
	}
	
	@Override
	public void addPerson(Person person) {
		em.persist(person);
	}

	@Override
	public void removePerson(Person person) {
		em.remove(em.contains(person) ? person : em.merge(person));
	}

	@Override
	public Person updatePerson(Person person) {
		return em.merge(person);
	}

	@Override
	public List<Person> findAllPerson(int limit, int offset) {
		TypedQuery<Person> q = em.createNamedQuery("findAllPersons", Person.class);
		q.setFirstResult(offset);
		
		if(limit > 0) q.setMaxResults(limit);
		
		return q.getResultList();
	}

	@Override
	public List<Person> findByFirstName(String firstName){
		TypedQuery<Person> q = em.createNamedQuery("findByFirstName", Person.class).setParameter("firstName", firstName);
		return q.getResultList();		
	}
	
	@Override
	public List<Person> findByName(String name){
		TypedQuery<Person> q = em.createNamedQuery("findByNames", Person.class).setParameter("name", name);
		return q.getResultList();		
	}
	
	public Person findByemail(String email) {
		TypedQuery<Person> q = em.createNamedQuery("findByEmail", Person.class).setParameter("email", email);	
		
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Person findByEmailAndPassword(String email,String pwd) {
		TypedQuery<Person> q = em.createNamedQuery("findByEmailAndPwd", Person.class).setParameter("email", email).setParameter("pwd", pwd);	
		
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public long countNbPerson() {
		TypedQuery<Long> q = em.createNamedQuery("countPersons", Long.class);	
		
		return q.getSingleResult().longValue();
	}
	
	
	@Override
	public List<Person> findByNamesAndFirstName(String value) {
		TypedQuery<Person> q = em.createNamedQuery("findByNamesAndFirstName", Person.class).setParameter("name", value).setParameter("fisrtName", value);

		
		return q.getResultList();
	}
	
	
	
	
}
