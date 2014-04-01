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
package org.geotools.tutorial.swt;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContent;
import org.geotools.map.MapContext;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.swt.MapLayerComposite;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.utils.ImageCache;

import rcp_gt_swt.GtSwtPlugin;

/**
 * The map view.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class MapView extends ViewPart {
    public static final String ID = "rcp-gt-swt.view"; //$NON-NLS-1$

    public static boolean showLayerTable = false;

    private SwtMapPane mapPane;

    public void createPartControl( Composite parent ) {
        handleImages();

        // create the default mapcontext
//        MapContext context = new DefaultMapContext();
//        context.layers();
        MapContent content = new MapContent();
        content.layers();
        // create the main composite, with or without layer panel
        Composite mainComposite = null;
        if (showLayerTable) {
            SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL | SWT.NULL);
            mainComposite = sashForm;
            MapLayerComposite mapLayerTable = new MapLayerComposite(mainComposite, SWT.BORDER);
            mapPane = new SwtMapPane(mainComposite, SWT.BORDER | SWT.NO_BACKGROUND);
            MyMouseEvent mouseEvent = new MyMouseEvent();
            mapPane.addMouseListener(mouseEvent);
            mapPane.setMapContent(content);
            mapLayerTable.setMapPane(mapPane);
            sashForm.setWeights(new int[]{1, 3});
        } else {
            mainComposite = parent;
            mapPane = new SwtMapPane(mainComposite, SWT.BORDER | SWT.NO_BACKGROUND);
            mapPane.setMapContent(content);
        }

        mapPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        // set the renderer
        StreamingRenderer renderer = new StreamingRenderer();
        mapPane.setRenderer(renderer);
    }

    public SwtMapPane getMapPane() {
        return mapPane;
    }

    private void handleImages() {
        // get the image cache
        ImageCache imageCache = ImageCache.getInstance();
        // get all the relative paths needed by the cache
        List<String> relativePaths = imageCache.getRelativePaths();
        /*
         * Create all the needed images. The images have to reside in the plugin root 
         * in the same folder structure as in the gt-swt resources folder. 
         * 
         * The icons of the gt-swt module are currently located in /icons/
         */
        for( String path : relativePaths ) {
            Image image = AbstractUIPlugin.imageDescriptorFromPlugin(GtSwtPlugin.PLUGIN_ID, path).createImage();
            // feed the image into the cache for further use
            imageCache.addImage(path, image);
        }
    }
    
    public void setFocus() {
    }
}