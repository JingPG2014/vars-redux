package vars.knowledgebase;


import java.util.ResourceBundle;
import java.util.Collection;
import java.util.ArrayList;

import vars.QueryableImpl;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Sep 29, 2009
 * Time: 12:58:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgebaseDAOImpl extends QueryableImpl implements KnowledgebaseDAO {

    private static final String jdbcPassword;
    private static final String jdbcUrl;
    private static final String jdbcUsername;
    private static final String jdbcDriver;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("knowledgebase-dao");
        jdbcUrl = bundle.getString("jdbc.url");
        jdbcUsername = bundle.getString("jdbc.username");
        jdbcPassword = bundle.getString("jdbc.password");
        jdbcDriver = bundle.getString("jdbc.driver");
    }

    /**
     * Constructs ...
     */
    public KnowledgebaseDAOImpl() {
        super(jdbcUrl, jdbcUsername, jdbcPassword, jdbcDriver);
    }

    public void updateConceptNameUsedByAnnotations(Concept concept) {

        String primaryName = concept.getPrimaryConceptName().getName();

        /*
         * Update the Observation table
         */
        Collection<ConceptName> conceptNames = new ArrayList<ConceptName>(concept.getConceptNames());
        conceptNames.remove(concept.getPrimaryConceptName());

        for (ConceptName conceptName : concept.getConceptNames()) {

            // Update Observations
            String sql = "UPDATE Observation SET ConceptName = '" +
                primaryName + "' WHERE ConceptName = '" +
                conceptName.getName() + "'";
            executeUpdate(sql);

            // Update Associations
            sql = "UPDATE Association SET ToConcept = '" +
                primaryName + "' WHERE ToConcept = '" +
                conceptName.getName() + "'";
            executeUpdate(sql);


            // Update LinkTemplates
            sql = "UPDATE LinkTemplate SET ToConcept = '" +
                primaryName + "' WHERE ToConcept = '" +
                conceptName.getName() + "'";
            executeUpdate(sql);

        }

    }


}
