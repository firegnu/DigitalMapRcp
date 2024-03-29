/*
 * JGrass - Free Open Source Java GIS http://www.jgrass.org 
 * (C) HydroloGIS - www.hydrologis.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.geotools.tutorial.swt.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.tool.ZoomInTool;
import org.geotools.tutorial.swt.MapView;

/**
 * The zoomin action of the map view.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class ZoominAction implements IViewActionDelegate {

    private IViewPart view;

    public void init( IViewPart view ) {
        this.view = view;
    }

    public void run( IAction action ) {
        SwtMapPane mapPane = ((MapView) view).getMapPane();
        mapPane.setCursorTool(new ZoomInTool());
    }

    public void selectionChanged( IAction action, ISelection selection ) {
    }

}
