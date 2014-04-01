package org.geotools.tutorial.swt.actions;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.gce.image.WorldImageFormat;
import org.geotools.map.GridReaderLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapContext;
import org.geotools.map.MapLayer;
import org.geotools.renderer.lite.gridcoverage2d.RasterSymbolizerHelper;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactoryImpl;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.control.JFileImageChooser;
import org.geotools.swt.utils.Utils;
import org.geotools.tutorial.swt.MapView;

/**
 * Command that delegates to the gt-swt open tiffs with world image.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class OpenWorldimageCommand extends AbstractHandler {

    @Override
    public Object execute( ExecutionEvent event ) throws ExecutionException {
        IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        MapView mapView = (MapView) activePage.findView(MapView.ID);

        SwtMapPane mapPane = mapView.getMapPane();

        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        File openFile = JFileImageChooser.showOpenFile(shell);

        if (openFile != null && openFile.exists()) {
            WorldImageFormat format = new WorldImageFormat();
            AbstractGridCoverage2DReader tiffReader = format.getReader(openFile);
            StyleFactoryImpl f = new StyleFactoryImpl();
            RasterSymbolizer sym = f.getDefaultRasterSymbolizer();
            Style style = SLD.wrapSymbolizers(sym);
            MapContent mapContent = mapPane.getMapContent();
            Layer layer = new GridReaderLayer(tiffReader, style);
            layer.setTitle(openFile.getName());
            mapContent.addLayer(layer);
            mapPane.redraw();
        }
    

        return null;
    }

}
