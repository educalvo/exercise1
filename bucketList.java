package nl.ru.ai.educalvo.exercise1;

import java.util.ArrayList;

public class bucketList {
	@SuppressWarnings("rawtypes")
	static ArrayList[] buckets= new ArrayList[10];
	
	public static void initialize(){
		for (int x = 0; x<10;x++)
			buckets[x] = new ArrayList<String>();			
	}
}
