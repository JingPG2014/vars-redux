/*
 * @(#)JXObservationTable.java   2009.11.12 at 10:11:19 PST
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package vars.annotation.ui.table;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JViewport;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vars.annotation.Observation;
import vars.annotation.VideoArchive;
import vars.annotation.VideoFrame;

/**  
 */
@SuppressWarnings("serial")
public class JXObservationTable extends JXTable implements IObservationTable {

    private final Logger log = LoggerFactory.getLogger(JXObservationTable.class);

    /**
     * Constructs ...
     */
    public JXObservationTable() {
        super();

        final TableColumnModel tableColumnModel = new JXObservationTableColumnModel();
        final TableModel model = new JXObservationTableModel(tableColumnModel);

        setModel(model);
        setColumnModel(tableColumnModel);
        super.setHighlighters(new Highlighter[] { HighlighterFactory.createAlternateStriping() });
        setRowHeightEnabled(true);
    }

    /**
     *
     * @param observation
     */
    public void addObservation(final Observation observation) {
        ((IObservationTableModel) getModel()).addObservation(observation);

        if (log.isDebugEnabled()) {
            log.debug("Adding " + observation + " to the table model");
        }
    }

    /**
     *
     * @param columnClass
     * @return
     */
    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
        TableCellRenderer renderer = super.getDefaultRenderer(columnClass);

        if (renderer == null) {
            renderer = super.getDefaultRenderer(Object.class);
        }

        return renderer;
    }

    /**
     *
     * @param row
     * @return
     */
    public Observation getObservationAt(final int row) {
        final IObservationTableModel model = (IObservationTableModel) getModel();

        return model.getObservationAt(convertRowIndexToModel(row));
    }

    /**
     *
     * @param rowIndex
     * @param margin
     * @return
     */
    public int getPreferredRowHeight(final int rowIndex, final int margin) {

        // Get the current default height for all rows
        int height = getRowHeight();

        // Determine highest cell in the row
        for (int c = 0; c < getColumnCount(); c++) {
            final TableCellRenderer renderer = getCellRenderer(rowIndex, c);
            final Component comp = prepareRenderer(renderer, rowIndex, c);
            final int h = comp.getPreferredSize().height + 2 * margin;

            height = Math.max(height, h);
        }

        return height;
    }

    /**
     *
     * @param videoArchive
     */
    @SuppressWarnings("unchecked")
    public void populateWithObservations(final VideoArchive videoArchive) {

        // Get the TableModel
        final IObservationTableModel model = (IObservationTableModel) getModel();

        // Remove the current contents
        model.clear();

        // Repopulate it with the contents of the new VideoArchive
        if (videoArchive != null) {
            final VideoArchive va = videoArchive;

            /*
             * Use copies of collections to avoid synchronization issues
             */
            final Collection<VideoFrame> vfs = new HashSet<VideoFrame>(va.getVideoFrames());

            for (VideoFrame videoFrame : vfs) {
                final Collection<Observation> observations = new HashSet<Observation>(videoFrame.getObservations());

                for (Observation observation : observations) {
                    model.addObservation(observation);
                }
            }
        }

    }

    /**
     */
    public void redrawAll() {
        ((IObservationTableModel) getModel()).redrawAll();
    }

    /**
     *
     * @param row
     */
    public void redrawRow(final int row) {
        ((IObservationTableModel) getModel()).redrawRow(row);
    }

    /**
     *
     * @param observation
     */
    public void removeObservation(final Observation observation) {
        ((IObservationTableModel) getModel()).removeObservation(observation);
    }

    /**
     * Scrool to the corect row and column
     * @param rowIndex
     * @param vColIndex
     */
    public void scrollToVisible(final int rowIndex, final int vColIndex) {
        if (!(getParent() instanceof JViewport)) {
            return;
        }

        final JViewport viewport = (JViewport) getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        final Rectangle rect = getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        final Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x - pt.x, rect.y - pt.y);

        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
    }

    /**
     *
     * @param obs
     */
    public void setSelectedObservation(final Observation obs) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the observation selected in the table to " + obs);
        }

        final int row = convertRowIndexToView(((JXObservationTableModel) getModel()).getObservationRow(obs));

        getSelectionModel().setSelectionInterval(row, row);
        scrollCellToVisible(row, 0);
    }
}
