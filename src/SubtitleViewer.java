import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SubtitleViewer {
	int currentLine = 0;
	JFrame app = null;
	
	public static void main (String[] args)
	{
		new SubtitleViewer();
	}

	public SubtitleViewer()
	{
		JFileChooser chooser = new JFileChooser();
		if ( chooser.showOpenDialog( null ) != JFileChooser.APPROVE_OPTION )
		{
			System.exit(0);
		}
		
		File subFile = chooser.getSelectedFile();

		final ArrayList<String> lines = SubtitleParser.readSubtitleFile( subFile );
		
		app = new JFrame();
		app.setLayout( new GridLayout(1,1) );
		app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		app.setSize( Toolkit.getDefaultToolkit().getScreenSize().width, 100 );
		app.setLocation( 0, Toolkit.getDefaultToolkit().getScreenSize().height - 100 );
		app.setUndecorated( true );
		app.setAlwaysOnTop( true );
		
		ComponentDragger.enableDragging( app );
		NativeMenuHider hider = new NativeMenuHider( app );
		
		// Antialias fonts if Mac
		System.setProperty("apple.awt.textantialiasing", "on");
		
		JPanel p = new JPanel();
		p.setBackground( Color.BLACK );
		
		final JLabel l = new JLabel();
		l.setAlignmentX( JLabel.CENTER_ALIGNMENT );
		l.setAlignmentY( JLabel.CENTER_ALIGNMENT );
		l.setFont( new Font("MS Chino", Font.BOLD, 50) );
		l.setForeground( Color.LIGHT_GRAY );
		if ( lines.size() > 0 )
			l.setText( lines.get(0) );

		p.add( l );
		app.add( p );

		app.addKeyListener( new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) System.exit(0);
				if ( e.getKeyCode() == KeyEvent.VK_LEFT ) currentLine--;
				else currentLine++;

				if ( currentLine >= lines.size() ) currentLine = lines.size() - 1;
				if ( currentLine < 0 ) currentLine = 0;
				
				if ( lines.size() > 0 )
					l.setText( lines.get(currentLine) );
			}
			public void keyTyped(KeyEvent e) {}	
			public void keyReleased(KeyEvent e) {}	
		});
		
		app.setVisible( true );
		
		hider.hideMenu();
	}
}
