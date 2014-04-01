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

import java.awt.geom.AffineTransform;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.event.MapBoundsEvent;
import org.geotools.map.event.MapBoundsListener;
import org.geotools.swt.SwtMapPane;
import org.geotools.tutorial.swt.MapView;

/**
 * The draw shapes action of the map view.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class DrawShapesAction implements IViewActionDelegate, MapBoundsListener {

    private SwtMapPane mapPane;
    private static boolean odd;

    public void init( IViewPart view ) {
        mapPane = ((MapView) view).getMapPane();

        // listen to map bound changes
        mapPane.getMapContent().addMapBoundsListener(this);
    }

    public void run( IAction action ) {
        Rectangle visibleRect = mapPane.getVisibleRect();
        ReferencedEnvelope displayArea = mapPane.getDisplayArea();
        drawShapes(visibleRect, displayArea, false);

        /*
         * to switch of drawing, simply do
         */
        // mapPane.setOverlay(null, null, false);
        // mapPane.redraw();
    }

    /**
     * Draws shapes on the map.
     * 
     * @param visibleRect the rectangle in teh screen space.
     * @param areaOfInterest the area of interest in world coordinates.
     */
    private void drawShapes( Rectangle visibleRect, ReferencedEnvelope areaOfInterest, boolean boundsChanged ) {
        Display display = Display.getDefault();

        /*
         * create an image with transparent color (this can be done better?)
         */
        Color white = display.getSystemColor(SWT.COLOR_WHITE);
        PaletteData palette = new PaletteData(new RGB[]{white.getRGB()});
        final ImageData sourceData = new ImageData(visibleRect.width, visibleRect.height, 1, palette);
        sourceData.transparentPixel = 0;

        // create the image to draw on
        Image img = new Image(display, sourceData);
        GC gc = new GC(img);
        gc.setAntialias(SWT.ON);

        // example lat/long coordinates to draw
        double[] worldCoords;
        if (odd) {
            worldCoords = new double[]{10.0, 40.0, 11.2, 43.3, 11.3, 45.2, 11.4, 46.5};
        } else {
            worldCoords = new double[]{11.0, 41.0, 12.2, 44.3, 11.3, 45.2, 11.4, 46.5};
        }
        odd = !odd;
        
        // get the world to screen transform
        double[] screenCoords = new double[8];
        AffineTransform worldToScreenTransform = mapPane.getWorldToScreenTransform();
        // do the transform
        worldToScreenTransform.transform(worldCoords, 0, screenCoords, 0, worldCoords.length / 2);

        // draw lines
        gc.setForeground(display.getSystemColor(SWT.COLOR_MAGENTA));
        gc.setLineWidth(2);
        gc.drawLine((int) screenCoords[0], (int) screenCoords[1], (int) screenCoords[2], (int) screenCoords[3]);
        gc.drawLine((int) screenCoords[2], (int) screenCoords[3], (int) screenCoords[4], (int) screenCoords[5]);

        // draw dots
        int size = 10;
        gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
        gc.fillOval((int) screenCoords[0] - size / 2, (int) screenCoords[1] - size / 2, size, size);
        gc.fillOval((int) screenCoords[2] - size / 2, (int) screenCoords[3] - size / 2, size, size);
        gc.fillOval((int) screenCoords[4] - size / 2, (int) screenCoords[5] - size / 2, size, size);
        gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
        gc.drawOval((int) screenCoords[0] - size / 2, (int) screenCoords[1] - size / 2, size, size);
        gc.drawOval((int) screenCoords[2] - size / 2, (int) screenCoords[3] - size / 2, size, size);
        gc.drawOval((int) screenCoords[4] - size / 2, (int) screenCoords[5] - size / 2, size, size);

        gc.setAntialias(SWT.OFF);
        gc.dispose();
        
        System.out.println(mapPane.getDisplayArea());

        // overlay the image
        mapPane.setOverlay(img, areaOfInterest, false, boundsChanged);
        mapPane.redraw();
    }

    public void selectionChanged( IAction action, ISelection selection ) {
    }

    public void mapBoundsChanged( MapBoundsEvent event ) {
        /*
         * every time the bounds change (zoom, etc...), the drawing 
         * has to occurr again on the new bounds
         */
        ReferencedEnvelope newAreaOfInterest = event.getNewAreaOfInterest();
        Rectangle visibleRect = mapPane.getVisibleRect();
        drawShapes(visibleRect, newAreaOfInterest, true);
    }

}
