import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;


public class NativeMenuHider {
	private boolean hideMenuEnabled = false;

	private native void hideMenuJNI();
	private native void showMenuJNI();

	private void loadLibraryFromJAR( String name ) throws UnsatisfiedLinkError
	{
		File jnilib = new File( name );
		if ( !jnilib.exists() )
		{
		
			try {
				InputStream stream = getClass().getResourceAsStream(name);
				if ( stream == null ) return;
				
				jnilib = File.createTempFile("nmh", null);
	
				FileOutputStream writer = new FileOutputStream( jnilib );
				byte[] buffer = new byte[1024];
				while ( stream.available() > 0 )
				{
					int read = stream.read( buffer, 0, 1024);
					writer.write( buffer, 0, read );
				}
				writer.close();
			} catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		
		System.load( jnilib.getAbsolutePath() );
	}
	
	private boolean isWindows()
	{
		return (System.getProperty("os.name").toLowerCase().indexOf("win") != -1);
	}
	private boolean isMac()
	{
		return (System.getProperty("os.name").toLowerCase().indexOf("mac") != -1);		
	}
	
	public void hideMenu()
	{
		if ( hideMenuEnabled )
			hideMenuJNI();
	}
	
	public void showMenu()
	{
		if ( hideMenuEnabled )
			showMenuJNI();		
	}
	
	public NativeMenuHider( JFrame app ) {
		assert( app != null );
		
		// Hide system menu if Mac or Windows
		if ( isMac() || isWindows() )
		{
			hideMenuEnabled = true;
		
			try {
				if ( isMac() )
				{
					loadLibraryFromJAR("libNativeMenuHider.jnilib");
				}
				else if ( isWindows() )
				{
					loadLibraryFromJAR("NativeMenuHider.dll");
				}
			} catch ( UnsatisfiedLinkError e ) {
				try {
					loadLibraryFromJAR("NativeMenuHider64.dll");
				} catch ( UnsatisfiedLinkError e2 )
				{
					hideMenuEnabled = false;
					System.err.println("Error initialising JNI. Disabling native API calls.");
				}
			}
		}
		
		// Show the hidden menu when appropriate on Windows (OS X does this automatically)
		if ( isWindows() )
		{			
			app.addFocusListener( new FocusListener ()
			{
				public void focusGained(FocusEvent e) {
					hideMenu();
				}

				public void focusLost(FocusEvent e) {
					showMenu();
				}
			} );
			Runtime.getRuntime().addShutdownHook(new Thread() {
			    public void run() { showMenu(); }
			});
		}
	}
}
