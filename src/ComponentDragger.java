import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class ComponentDragger implements MouseListener, MouseMotionListener
{
	private Component component;
	private Point lastPoint = null;
	
	public static void enableDragging( Component c )
	{
		ComponentDragger wd = new ComponentDragger( c );
		c.addMouseListener( wd );
		c.addMouseMotionListener( wd );
	}
	
	private ComponentDragger( Component c )
	{
		component = c;
	}
	
	public void mouseDragged( MouseEvent e )
	{
		if ( lastPoint == null ) return;
		
		Point point = e.getLocationOnScreen();
		point.translate( -lastPoint.x, -lastPoint.y );
		Point appLocation = component.getLocation();
		appLocation.translate(point.x, point.y);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if ( appLocation.x < 10 )
			appLocation.x = 0;
		else if ( appLocation.x + component.getWidth() > screenSize.width - 10 )
			appLocation.x = screenSize.width - component.getWidth();
		else
			lastPoint.x = e.getLocationOnScreen().x;
		
		if ( appLocation.y < 10 )
			appLocation.y = 0;
		else if ( appLocation.y + component.getHeight() > screenSize.height - 10  )
			appLocation.y = screenSize.height - component.getHeight();
		else
			lastPoint.y = e.getLocationOnScreen().y;
		
		component.setLocation( appLocation );
	}
	
	public void mousePressed(MouseEvent e) {
		lastPoint = e.getLocationOnScreen();
	}
	
	public void mouseReleased(MouseEvent e) {}
	public void mouseMoved( MouseEvent e ) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
