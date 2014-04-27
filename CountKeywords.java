import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Runtime;
import java.lang.Process;
import java.lang.InterruptedException;

public class CountKeywords
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		int articleCount = 1121;

		for(int i = 0; i < articleCount; i++)
		{
			String filename = "keywords_" + i;
			String cmd = "help";

			System.out.println(cmd);

			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			

			String line = "";

			while((line = b.readLine()) != null) 
			{
				System.out.println(line);
			}

			writer.close();
		}

		String keywordsFile = "/home/dan/data/storytelling/keywords/keywords_" + i + ".txt";
		PrintWriter writer = new PrintWriter(keywordsFile, "UTF-8");
	}
}