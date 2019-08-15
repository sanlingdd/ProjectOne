package com.linkedin.automation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class BubleSort {

	static <T extends Comparable<? super T>> void bubleSort(List<T> list)
	{
		
	}
	static <T extends Comparable<? super T>> void sort(List<T> list)
	{
		for(int i=0; i< list.size();i++)
			for(int j=i+1;j<list.size();j++)
			{
				if(list.get(i).compareTo(list.get(j)) < 0  )
				{
					T temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		
	}
	
	 public static void filter(List names, Predicate condition) {
		    names.stream().filter((name) -> (condition.test(name)))
		        .forEach((name) -> {System.out.println(name + " ");
		    });
		 }
	
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(65,5,100,1,89,2,4,8,9,34);
		
		BubleSort.sort(numbers);
		
		numbers.forEach(e ->{ System.out.println(e);});
		
		new Thread(()-> {}); 
		
		List<String> strings = new ArrayList<String>();
		
		Predicate<String> startsWithJ = (n) -> n.startsWith("J");
		Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		
		BubleSort.filter(strings, fourLetterLong.and(startsWithJ));
		   
		 
	 
		
		
	}

}
