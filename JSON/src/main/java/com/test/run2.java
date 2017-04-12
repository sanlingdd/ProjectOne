package com.test;

public class run2 extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("run1:        2");
		}
	}
}
