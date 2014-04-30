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
		int articleCount = Integer.valueOf(args[0]);
		String dataLocation = args[1];

		//int articleCount = 2630;

		Map<String, Integer> counts = new HashMap<String, Integer>();

		for(int i = 0; i < articleCount; i++)
		{
			//String filename = "/home/dan/data/storytelling/keywords/keywords_" + i + ".txt";
			String filename = dataLocation + "/keywords/keywords_" + i + ".txt";
			String cmd = "cat " + filename;

			System.out.println(filename);

			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

			List<String> list = new LinkedList<String>();

			String line = "";

			while((line = b.readLine()) != null) 
			{
				String keyword = line.toLowerCase();

				if(!list.contains(keyword))
					list.add(keyword);
			}

			for(String keyword : list)
			{
				if(counts.containsKey(keyword))
					counts.put(keyword, counts.get(keyword) + 1);
				else
					counts.put(keyword, 1);
			}
		}

		Map<String, Integer> sortedCounts = sortByValue(counts);

		String countsFile = dataLocation + "/counts.csv";
		PrintWriter writer = new PrintWriter(countsFile, "UTF-8");
			
		for(String str : sortedCounts.keySet())
			writer.println("\"" + str + "\"," + sortedCounts.get(str));

		writer.close();

		System.out.println(sortedCounts.size() + " keywords counted in " + countsFile);
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
