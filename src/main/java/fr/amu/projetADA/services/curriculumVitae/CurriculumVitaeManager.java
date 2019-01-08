package fr.amu.projetADA.services.curriculumVitae;

import java.util.List;

import javax.ejb.Local;

import fr.amu.projetADA.beans.cv.CurriculumVitae;

@Local
public interface CurriculumVitaeManager {
	
	public CurriculumVitae findCurriculumVitae(long id);
		
	public void removeCurriculumVitae(CurriculumVitae curriculumVitae);
	
	public CurriculumVitae updateCurriculumVitae(CurriculumVitae curriculumVitae);
	
	public List<CurriculumVitae> findAllCurriculumVitae();
	
}
