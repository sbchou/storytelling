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
		final String DEFAULT_TAG = "O";

		int articleCount = 1121;

		System.out.println("Processing " + articleCount + " articles");

		for(int i = 0; i < articleCount; i++)
		{
			System.out.println("\nArticle " + (i+1) + " of " + articleCount + "\n");

			String article = "/home/dan/data/storytelling/articles/article_" + i + ".txt";
			String cmd = "/opt/stanford-ner/ner.sh " + article;

			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String keywordsFile = "/home/dan/data/storytelling/keywords/keywords_" + i + ".txt";
			PrintWriter writer = new PrintWriter(keywordsFile, "UTF-8");

			String line = "";

			while((line = b.readLine()) != null) 
			{
				String[] array = line.split(" ");

				String currentTag = DEFAULT_TAG;
				String currentEntity = "";

				for(String word : array)
				{
					String[] parts = word.split("/");

					String value = parts[0];
					String tag = parts[1];

					if(!tag.equals(DEFAULT_TAG))
					{
						if(currentTag.equals(DEFAULT_TAG))
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

						currentTag = DEFAULT_TAG;
						currentEntity = "";
					}
				}

				if(!currentEntity.isEmpty())
				{
					System.out.println(currentEntity);						

					writer.println(currentEntity);
				}
			}

			writer.close();
		}
	}
}