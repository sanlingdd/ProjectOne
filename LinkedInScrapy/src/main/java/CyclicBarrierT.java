

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierT implements Runnable {

	private CyclicBarrier foutTable = new CyclicBarrier(4);

	private class TableWorker implements Runnable {
		private CyclicBarrier barreir;
		private String name;

		public TableWorker(CyclicBarrier barreir, String name) {
			super();
			this.barreir = barreir;
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println("I have come to hotel : " + this.name);

			try {
				barreir.await();
				System.out.println("Order place from : " + this.name);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		int i = 0;
		for (; i < 2; i++) {
			TableWorker worker = new TableWorker(foutTable, "Guest"+i);
			new Thread(worker).start();
		}

		for (; i < 4; i++) {
			TableWorker worker = new TableWorker(foutTable,"Guest"+i);
			new Thread(worker).start();
		}
		
		
		System.out.println("HaaaaaaaaaaaaaaaaaaaaaaaaaaaaaH");


	}

	public static void main(String[] args) {
		CyclicBarrierT ct = new CyclicBarrierT();
		new Thread(ct).start();
	}

}
