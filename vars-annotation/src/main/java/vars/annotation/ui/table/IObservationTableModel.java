package vars.annotation.ui.table;

import vars.annotation.Observation;

public interface IObservationTableModel {

    /**
     *  Adds an observation to the table model.
     *
     * @param  obs The observation to be added.
     */
    void addObservation(final Observation obs);
    
    void clear();

    /**
     * Find the index of the COlumn based on it's String identifier. This method
     * delegates the call to <code>ObservationColumnModel.findColumn</code>
     *
     * @param  id Description of the Parameter
     * @return  Description of the Return Value
     */
    int findColumn(final String id);

    /**
     *  Gets the columnClass attribute of the ObservationTableModel object
     *
     * @param  columnIndex Description of the Parameter
     * @return  The columnClass value
     */
    Class getColumnClass(final int columnIndex);

    /**
     *  Gets the columnCount attribute of the ObservationTableModel object
     *
     * @return  The columnCount value
     */
    int getColumnCount();

    /**
     *  Gets the numberOfObservations attribute of the ObservationTableModel object
     *
     * @return  The numberOfObservations value
     */
     int getNumberOfObservations();

    /**
     *  Gets the observationAt attribute of the ObservationTableModel object
     *
     * @param  rowIndex Description of the Parameter
     * @return  The observationAt value
     */
     Observation getObservationAt(final int rowIndex);

    /**
     * @param  observation THe observation to find
     * @return  The row containing the observation. -1 is return if the observation is
     *                  not found or if the observation was null
     */
     int getObservationRow(final Observation observation);

    /**
     *  Gets the rowCount attribute of the ObservationTableModel object
     *
     * @return  The rowCount value
     */
     int getRowCount();

    /**
     *  Description of the Method
     */
     void redrawAll();

    /**
     *  Description of the Method
     *
     * @param  row Description of the Parameter
     */
     void redrawRow(final int row);

    /**
     * NOTE: If an observation has a videoFrame attached to it, it will NOT
     * be removed from the model. You must first remove it's videoFrame!!
     *
     *
     * @param  obs The observation to be removed.
     */
     void removeObservation(final Observation obs);

}