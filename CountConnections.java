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

public class CountConnections
{
	private static final String[][] keywords = new String[][]
	{
		{"New York Times", "The New York Times", "NY Times", "NYTimes", "The Times"},
		{"United States", "U.S.", "US", "U.S.A.", "USA"},
		{"New York", "New York City", "NY", "N.Y.", "NYC", "N.Y.C."},
		{"Ford"},
		{"Obama", "Barack Obama", "Barack H. Obama", "President Obama", "Obama Administration", "The Obama Administration", "Obama White House", "The Obama White House"},
		{"China", "Chinese"},
		{"America", "American", "Americans"},
		{"Russia", "Russian"},
		{"Ukraine", "Ukrainian"},
		{"Honda"},
		{"Nissan"},
		{"Toyota"},
		{"Washington", "DC", "Washington DC"},
		{"Microsoft"},
		{"Facebook"},
		{"General Motors", "GM"},
		{"Los Angeles", "LA"},
		{"Audi"},
		{"California", "Californian", "Californians"},
		{"Manhattan"},
		{"Mazda"},
		{"Europe", "European"},
		{"Hyundai"},
		{"BMW"},
		{"Libya", "Libyan"},
		{"Chrysler"},
		{"San Francisco", "SF"},
		{"Brooklyn"},
		{"London"},
		{"Israel", "Israeli"}
	};

	private static boolean matches(int i, String str)
	{
		String[] forms = keywords[i];

		for(String form : forms)
			if(str.equalsIgnoreCase(form))
				return true;

		return false;
	}

	private static int getIndex(String str)
	{
		for(int i = 0; i < keywords.length; i++)
			if(matches(i, str))
				return i;

		return -1;
	}

	private static List<Integer> getIndexes(String[] array)
	{
		List<Integer> list = new LinkedList<Integer>();

		for(String str : array)
		{
			int i = getIndex(str);

			if(i != -1 && !list.contains(i))
				list.add(i);
		}

		return list;
	}
	
	private static String getKey(int i1, int i2)
	{
		int first = i1;
		int second = i2;

		if(i1 > i2)
		{
			first = i2;
			second = i1;
		}

		return first + "," + second;
	}

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

			List<String> list = new LinkedList<String>();
			String line = "";

			while((line = b.readLine()) != null) 
				list.add(line);

			List<Integer> indexes = getIndexes(list.toArray(new String[0]));

			for(int x = 0; x < indexes.size(); x++)
			{
				System.out.print(indexes.get(x) + " ");

				for(int y = x + 1; y < indexes.size(); y++)
				{
					int i1 = indexes.get(x);
					int i2 = indexes.get(y);

					String key = getKey(i1, i2);

					if(counts.containsKey(key))
						counts.put(key, counts.get(key) + 1);
					else
						counts.put(key, 1);
				}
			}

			System.out.println();
		}

		Map<String, Integer> sortedCounts = sortByValue(counts);

		String countsFile = "/home/dan/data/storytelling/connections/connections.txt";
		PrintWriter writer = new PrintWriter(countsFile, "UTF-8");
			
		for(String str : sortedCounts.keySet())
		{
			String[] array = str.split(",");

			int i1 = Integer.valueOf(array[0]);
			int i2 = Integer.valueOf(array[1]);

			String keyword1 = keywords[i1][0];
			String keyword2 = keywords[i2][0];

			System.out.println(keyword1 + "," + keyword2 + "," + sortedCounts.get(str));
			writer.println(str + "," + sortedCounts.get(str));
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