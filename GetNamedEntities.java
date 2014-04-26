import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Runtime;
import java.lang.Process;
import java.lang.InterruptedException;

public class GetNamedEntities
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Runtime r = Runtime.getRuntime();
		Process p = r.exec("python ~/workspace/storytelling/GetArticles.py");
		p.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";

		while((line = b.readLine()) != null) 
		{
			System.out.println(line);
		}

		r = Runtime.getRuntime();
		p = r.exec("/opt/stanford-ner/ner.sh /opt/stanford-ner/sample.txt");
		p.waitFor();
		b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		line = "";

		while((line = b.readLine()) != null) 
		{
			//System.out.println(line);

			String[] array = line.split(" ");

			String currentTag = "O";
			String currentEntity = "";

			for(String word : array)
			{
				//System.out.println(word);

				String[] parts = word.split("/");

				String value = parts[0];
				String tag = parts[1];

				//System.out.println(tag + ": " + value);

				if(!tag.equals("O"))
				{
					if(currentTag.equals("O"))
					{
						currentTag = tag;
						currentEntity = value;
					}
					else
					{
						currentEntity += " " + value;
					}
				}
				else
				{
					if(!currentEntity.isEmpty())
						System.out.println(currentEntity);

					currentTag = "O";
					currentEntity = "";
				}
			}
		}
	}
}