package org.geotools.tutorial.swt.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.tool.InfoTool;
import org.geotools.swt.tool.PanTool;
import org.geotools.tutorial.swt.MapView;

public class InfoCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        MapView mapView = (MapView) activePage.findView(MapView.ID);

        SwtMapPane mapPane = mapView.getMapPane();
        mapPane.setCursorTool(new InfoTool());
		return null;
	}

}
