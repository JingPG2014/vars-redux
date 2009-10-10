/*
 * @(#)AddLinkRealizationDialog.java   2009.10.05 at 03:38:31 PDT
 *
 * Copyright 2009 MBARI
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package vars.knowledgebase.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.bushe.swing.event.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vars.UserAccount;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.History;
import vars.knowledgebase.HistoryDAO;
import vars.knowledgebase.HistoryFactory;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.KnowledgebaseFactory;
import vars.knowledgebase.LinkRealization;
import vars.knowledgebase.LinkRealizationDAO;
import vars.knowledgebase.ui.KnowledgebaseApp;
import vars.knowledgebase.ui.LinkEditorPanel;
import vars.knowledgebase.ui.Lookup;
import vars.knowledgebase.ui.actions.ApproveHistoryTask;
import vars.shared.ui.OkCancelButtonPanel;

/**
 * @version
 */
public class AddLinkRealizationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(AddLinkRealizationDialog.class);
    private OkCancelButtonPanel buttonPanel = null;
    private JPanel jContentPane = null;
    private LinkEditorPanel linkEditorPanel = null;
    private final ApproveHistoryTask approveHistoryTask;
    private Concept concept;
    private final HistoryFactory historyFactory;
    private final KnowledgebaseDAOFactory knowledgebaseDAOFactory;
    private final KnowledgebaseFactory knowledgebaseFactory;

    /**
     * Constructs ...
     *
     * @param approveHistoryTask
     * @param knowledgebaseDAOFactory
     * @param knowledgebaseFactory
     */
    public AddLinkRealizationDialog(ApproveHistoryTask approveHistoryTask,
                                    KnowledgebaseDAOFactory knowledgebaseDAOFactory,
                                    KnowledgebaseFactory knowledgebaseFactory) {
        this(null, approveHistoryTask, knowledgebaseDAOFactory, knowledgebaseFactory);
    }

    /**
     * @param owner
     * @param approveHistoryTask
     * @param knowledgebaseDAOFactory
     * @param knowledgebaseFactory
     */
    public AddLinkRealizationDialog(Frame owner, ApproveHistoryTask approveHistoryTask,
                                    KnowledgebaseDAOFactory knowledgebaseDAOFactory,
                                    KnowledgebaseFactory knowledgebaseFactory) {
        super(owner);
        this.knowledgebaseDAOFactory = knowledgebaseDAOFactory;
        this.knowledgebaseFactory = knowledgebaseFactory;
        this.historyFactory = new HistoryFactory(knowledgebaseFactory);
        this.approveHistoryTask = approveHistoryTask;
        setTitle("VARS - Add Description");
        initialize();
    }

    /**
     * This method initializes buttonPanel
     * @return  javax.swing.JPanel
     */
    private OkCancelButtonPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new OkCancelButtonPanel();

            buttonPanel.getOkButton().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    onOkClick();
                }

            });

            buttonPanel.getCancelButton().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    onCancelClick();
                }

            });
        }

        return buttonPanel;
    }

    public Concept getConcept() {
        return concept;
    }

    /**
     * This method initializes jContentPane
     * @return  javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
            jContentPane.add(getLinkEditorPanel(), BorderLayout.CENTER);
        }

        return jContentPane;
    }

    /**
         * This method initializes linkEditorPanel
         * @return  javax.swing.JPanel
         */
    private LinkEditorPanel getLinkEditorPanel() {
        if (linkEditorPanel == null) {
            linkEditorPanel = new LinkEditorPanel();
            linkEditorPanel.getLinkNameField().setEditable(false);
        }

        return linkEditorPanel;
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        if (log.isDebugEnabled()) {
            log.debug("Initializing " + getClass().getName());
        }

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModal(true);
        this.setContentPane(getJContentPane());
        final Frame frame = (Frame) Lookup.getApplicationFrameDispatcher().getValueObject();
        setLocationRelativeTo(frame);
        pack();
    }

    private void onCancelClick() {
        setVisible(false);
        setConcept(null);
    }

    private void onOkClick() {
        setVisible(false);

        UserAccount userAccount = (UserAccount) KnowledgebaseApp.DISPATCHER_USERACCOUNT.getValueObject();
        if ((userAccount != null) && !userAccount.isReadOnly()) {

            /*
             * Create the linkRealization
             */
            final LinkRealization linkRealization = knowledgebaseFactory.newLinkRealization();
            final LinkEditorPanel p = getLinkEditorPanel();
            linkRealization.setLinkName(p.getLinkName());
            linkRealization.setToConcept(p.getToConcept());
            linkRealization.setLinkValue(p.getLinkValue());
            final Concept c = getConcept();
            c.getConceptMetadata().addLinkRealization(linkRealization);

            try {

                /*
                 * Use the primary concept name of the 'toConcept'
                 */
                ConceptDAO conceptDAO = knowledgebaseDAOFactory.newConceptDAO();
                Concept toConcept = conceptDAO.findByName(linkRealization.getToConcept());
                if (toConcept != null) {
                    linkRealization.setToConcept(toConcept.getPrimaryConceptName().getName());
                }

                LinkRealizationDAO linkRealizationDAO = knowledgebaseDAOFactory.newLinkRealizationDAO();
                linkRealizationDAO.makePersistent(linkRealization);

                /*
                 * Create a History
                 */
                History history = historyFactory.add(userAccount, linkRealization);
                c.getConceptMetadata().addHistory(history);
                HistoryDAO historyDAO = knowledgebaseDAOFactory.newHistoryDAO();
                historyDAO.makePersistent(history);


                if (userAccount.isAdministrator()) {
                    approveHistoryTask.approve(userAccount, history);
                }

                KnowledgebaseApp.DISPATCHER_SELECTED_CONCEPT.setValueObject(null);
                KnowledgebaseApp.DISPATCHER_SELECTED_CONCEPT.setValueObject(c);
            }
            catch (Exception e) {
                c.getConceptMetadata().removeLinkRealization(linkRealization);
                EventBus.publish(Lookup.TOPIC_WARNING, e);
            }

            setConcept(null);
        }
    }

    public void setConcept(Concept concept) {
        getLinkEditorPanel().setConcept(concept);
        this.concept = concept;
    }
}
