import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Runtime;
import java.lang.Process;
import java.lang.InterruptedException;

public class GetNamedEntities
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		int articleCount = 1121;

		for(int i = 0; i < articleCount; i++)
		{
			System.out.println("\nArticle " + i + "\n");

			Runtime r = Runtime.getRuntime();

			String article = "/home/dan/data/storytelling/articles/article_" + i + ".txt.";
			String cmd = "/opt/stanford-ner/ner.sh " + article;

			//System.out.println(cmd);

			Process p = r.exec(cmd);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String keywordsFile = "/home/dan/data/storytelling/keywords/keywords_" + i + ".txt";
			PrintWriter writer = new PrintWriter(keywordsFile, "UTF-8");

			String line = "";

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
						{
							System.out.println(currentEntity);						

							writer.println(currentEntity);
						}

						currentTag = "O";
						currentEntity = "";
					}
				}
			}

			writer.close();
		}
	}
}