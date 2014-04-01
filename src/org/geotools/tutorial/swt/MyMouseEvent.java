package org.geotools.tutorial.swt;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.swt.event.MapMouseAdapter;
import org.geotools.swt.event.MapMouseEvent;

public class MyMouseEvent extends MapMouseAdapter {
    
	@Override
	public void onMouseClicked(MapMouseEvent ev) {
        DirectPosition2D pos = ev.getMapPosition();
        double x = pos.x;
        double y = pos.y;
	};
	
	@Override
	public void onMouseDragged(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMouseEntered(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMouseExited(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMouseMoved(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMousePressed(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMouseReleased(MapMouseEvent ev) {
		
	};
	
	@Override
	public void onMouseWheelMoved(MapMouseEvent ev) {
		
	}
}
