import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class SubtitleParser {
	private enum SRTStatus { BLANK, COUNTER, TIMER, DIALOGUE }

	private static ArrayList<String> readSrtFile( Scanner scanner )
	{
		final ArrayList<String> lines = new ArrayList<String>();
		
		String timeStampRegex = "\\d{2}:\\d{2}:\\d{2},\\d{3}";
		String timerRegex = timeStampRegex+" --> "+timeStampRegex;
		
		SRTStatus lastLineStatus = SRTStatus.BLANK;
		
		while ( scanner.hasNextLine() )
		{
			String line = scanner.nextLine();

			if ( lastLineStatus == SRTStatus.DIALOGUE && line.isEmpty() )
			{
				lastLineStatus = SRTStatus.BLANK;
			}
			else if ( lastLineStatus == SRTStatus.BLANK && line.matches("\\d+") )
			{
				lastLineStatus = SRTStatus.COUNTER;
			}
			else if ( lastLineStatus == SRTStatus.COUNTER && line.matches(timerRegex) )
			{
				lastLineStatus = SRTStatus.TIMER;						
			}
			else if ( lastLineStatus == SRTStatus.TIMER || lastLineStatus == SRTStatus.DIALOGUE )
			{
				lines.add( line );
				lastLineStatus = SRTStatus.DIALOGUE;
			}					
		}
		return lines;
	}
	private static ArrayList<String> readSubFile( Scanner scanner )
	{
		final ArrayList<String> lines = new ArrayList<String>();
		String regex = "^\\{\\d+\\}\\{\\d+\\}(.+)$";
		String regexReplace = "$1";
		
		while ( scanner.hasNextLine() )
		{
			String line = scanner.nextLine();
			if ( !line.isEmpty() )
			{
				if ( !line.matches( regex ) )
					System.err.println(".sub format error");
				else
				{
					String newLine = line.replaceAll( regex, regexReplace );
					if ( newLine.indexOf('|') != -1 )
					{
						String[] newLines = newLine.split("\\|");
						for ( String l : newLines )
							lines.add( l );
					}
					else
					{
						lines.add( newLine );
					}
				}
			}
		}	
		
		return lines;
	}
	private static ArrayList<String> readTxtFile( Scanner scanner )
	{
		final ArrayList<String> lines = new ArrayList<String>();
		while ( scanner.hasNextLine() )
		{
			String line = scanner.nextLine();
			if ( !line.isEmpty() )
				lines.add( line );
		}
		
		return lines;
	}
	
	public static ArrayList<String> readSubtitleFile( File subFile )
	{
		Scanner scanner = null;
		try {
			scanner = new Scanner( subFile, "UTF-8" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		if ( subFile.getName().endsWith( ".srt" ) )
			return readSrtFile( scanner );
		else if ( subFile.getName().endsWith( ".sub" ) )
			return readSubFile( scanner );
		else
			return readTxtFile( scanner );
	}
}
