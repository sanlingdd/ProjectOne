package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run1 r1 = new run1();
		run2 r2 = new run2();
		r1.start();
		r2.start();

		new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();

		List<String> list = new ArrayList<String>();
		list.add("Googe");
		list.forEach((name) -> System.out.print(name));

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				String left = (String) o1;
				String right = (String) o2;
				return left.compareTo(right);
			}

		});
	}

}
