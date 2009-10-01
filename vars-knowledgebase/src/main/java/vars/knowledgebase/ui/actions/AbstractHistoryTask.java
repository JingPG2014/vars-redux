package vars.knowledgebase.ui.actions;

import vars.knowledgebase.History;
import vars.knowledgebase.ConceptMetadata;
import vars.knowledgebase.ConceptMetadataDAO;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.ui.Lookup;
import vars.UserAccount;
import org.bushe.swing.event.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;

public abstract class AbstractHistoryTask {

    final KnowledgebaseDAOFactory knowledgebaseDAOFactory;
    final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    public AbstractHistoryTask(KnowledgebaseDAOFactory knowledgebaseDAOFactory) {
        this.knowledgebaseDAOFactory = knowledgebaseDAOFactory;
    }

    /**
     * Do the taks on the history then
     * @param userAccount
     * @param history
     * @return
     */
    public abstract void doTask(final UserAccount userAccount, final History history);
    
    protected void dropHistory(final History h, final String msg) {
        EventBus.publish(Lookup.TOPIC_NONFATAL_ERROR, msg);

        final ConceptMetadata conceptMetadata = h.getConceptMetadata();
        conceptMetadata.removeHistory(h);
        ConceptMetadataDAO conceptMetadataDAO = knowledgebaseDAOFactory.newConceptMetadataDAO();
        conceptMetadataDAO.update(conceptMetadata);

    }
    
    protected static boolean canDo(final UserAccount userAccount, final History history) {
        return userAccount != null && userAccount.isAdministrator() && history != null;
    }

}
