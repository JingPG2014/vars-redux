/*
 * Copyright 2005 MBARI
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 2.1
 * (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.mbari.vars.annotation.ui;

import javax.swing.ImageIcon;
import org.mbari.vars.annotation.ui.actions.AddPopulationPropAction;

/**
 * <p>
 * Adds a 'population of' association to the currently selected observations
 * </p>
 *
 * @author <a href="http://www.mbari.org">MBARI</a>
 * @version $Id: PopulationPropButton.java 314 2006-07-10 02:38:46Z hohonuuli $
 * @see org.mbari.vars.annotation.ui.actions.AddPopulationPropAction
 */
public class PopulationPropButton extends PropButton {

    /**
     *
     */
    private static final long serialVersionUID = -4737835322654961290L;

    /**
     *      Constructor
     */
    public PopulationPropButton() {
        super();
        setAction(new AddPopulationPropAction());
        setIcon(new ImageIcon(getClass().getResource("/images/vars/annotation/nbutton.png")));
        setToolTipText("population, 2 or more");
        setEnabled(false);
    }
}
