import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Runtime;
import java.lang.Process;
import java.lang.InterruptedException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class CountKeywords
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		int articleCount = 1121;

		Map<String, Integer> counts = new HashMap<String, Integer>();

		for(int i = 0; i < articleCount; i++)
		{
			String filename = "/home/dan/data/storytelling/keywords/keywords_" + i + ".txt";
			String cmd = "cat " + filename;

			System.out.println(filename);

			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";

			while((line = b.readLine()) != null) 
			{
				//System.out.println(line);

				if(counts.containsKey(line))
					counts.put(line, counts.get(line) + 1);
				else
					counts.put(line, 1);
			}
		}

		Map<String, Integer> sortedCounts = sortByValue(counts);

		String countsFile = "/home/dan/data/storytelling/counts/counts.txt";
		PrintWriter writer = new PrintWriter(countsFile, "UTF-8");
			
		for(String str : sortedCounts.keySet())
		{
			System.out.println(str + ": " + sortedCounts.get(str));
			writer.println("\"" + str + "\"," + sortedCounts.get(str));
		}

		writer.close();
	}

	static Map sortByValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	              .compareTo(((Map.Entry) (o1)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
}