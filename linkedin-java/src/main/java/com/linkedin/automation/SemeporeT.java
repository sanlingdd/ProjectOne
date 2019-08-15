package com.linkedin.automation;


import java.util.concurrent.Semaphore;

public class SemeporeT implements Runnable {

	private Semaphore se = new Semaphore(10);

	private class Worker implements Runnable {

		private String name;

		public Worker(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			try {
				se.acquire();

				System.out.println("I begin my work! " + this.name);
				Thread.sleep(5000);

				System.out.println("I have done my work! " + this.name);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				se.release();
			}

		}
	}

	@Override
	public void run() {

		for (int i = 0; i < 20; i++) {
			Worker wk = new Worker("Worker" + i);
			new Thread(wk).start();
		}

	}

	public static void main(String[] args) {
		SemeporeT t = new SemeporeT();

		new Thread(t).start();
	}

}
