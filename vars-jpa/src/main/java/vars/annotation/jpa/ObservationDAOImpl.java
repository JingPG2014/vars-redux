package vars.annotation.jpa;

import vars.jpa.DAO;
import vars.annotation.ObservationDAO;
import vars.annotation.Observation;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.ConceptName;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;

import com.google.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:40:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObservationDAOImpl extends DAO implements ObservationDAO {

    private final ConceptDAO conceptDAO;

    @Inject
    public ObservationDAOImpl(EntityManager entityManager, ConceptDAO conceptDao) {
        super(entityManager);
        this.conceptDAO = conceptDao;
    }

    public List<Observation> findAllByConceptName(String conceptName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("conceptName", conceptName);
        return findByNamedQuery("Observation.findByConceptName", map);
    }

    public List<Observation> findAllByConcept(final Concept concept, final boolean cascade) {

        startTransaction();

        Collection<ConceptName> conceptNames = null;
        if (cascade) {
            conceptNames = conceptDAO.findDescendentNames(concept);
        }
        else {
            conceptNames = new HashSet<ConceptName>();
            conceptNames.addAll(concept.getConceptNames());
        }

        String jpql = "SELECT o FROM Observation o WHERE o.conceptName IN (";
        int n = 0;
        for (ConceptName cn : conceptNames) {
            jpql += cn.getName();
            if (n < conceptNames.size() - 1) {
                jpql += ", ";
            }
        }
        jpql += ")";

        startTransaction();
        Query query = getEntityManager().createQuery(jpql);
        List<Observation> observations = query.getResultList();
        endTransaction();

        return observations;
    }

    public List<String> findAllConceptNamesUsedInAnnotations() {

        final EntityManager entityManager = getEntityManager();
        startTransaction();

        // ---- Step 1: Fetch from Observation
        String sql = "SELECT DISTINCT conceptName FROM Observation";
        Query query = entityManager.createNativeQuery(sql);
        List<String> conceptNames = query.getResultList();

        // ---- Step 2: Fetch from Association
        sql = "SELECT DISTINCT toConcept FROM Association";
        query = entityManager.createNativeQuery(sql);
        conceptNames.addAll(query.getResultList());

        endTransaction();

        return conceptNames; 
    }

    public void validateName(Observation object) {
        Concept concept = conceptDAO.findByName(object.getConceptName());
        if (concept != null) {
            object.setConceptName(concept.getPrimaryConceptName().getName());
        }
        else {
            log.warn(object + " contains a 'conceptName', " + object.getConceptName() + " that was not found in the knowlegebase");
        }
    }
    
}
