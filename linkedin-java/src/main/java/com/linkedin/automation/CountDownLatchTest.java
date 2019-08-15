package com.linkedin.automation;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest implements Runnable{
	private  CountDownLatch orgnizer = new CountDownLatch(4);
	private  CountDownLatch allVoter = new CountDownLatch(100);
	private ExecutorService service = Executors.newCachedThreadPool();

	private class Orginizer extends Thread {

		private CountDownLatch orgnizer;
		private CountDownLatch voter;
		private String name ;

		public Orginizer(CountDownLatch orgnizer, CountDownLatch voter, String name) {
			this.orgnizer = orgnizer;
			this.voter = voter;
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println("I have come " + this.name);

			this.doWorkOrgnizeVoting();
			// as a orginizer, I am coming
			orgnizer.countDown();
			
//			if(orgnizer.getCount() == 0)
//			{
//				System.out.print("abc");
//			}
			try {
				voter.await();
			} catch (InterruptedException e) {
			}
			
			//begin to count the vote
			this.doWorkOrignizeCountingVote();
		}

		private void doWorkOrgnizeVoting() {

			System.out.println("Orginze voting " + this.name);
		}

		private void doWorkOrignizeCountingVote() {
			System.out.println("count voting " + this.name);
		}

	}

	private class Voter extends Thread {
		private CountDownLatch voter;
		private CountDownLatch orgnizer;
		private String name;
		public Voter(CountDownLatch Voter, CountDownLatch orgnizer, String name) {
			this.voter = voter;
			this.orgnizer = orgnizer;
			this.name = name;
		}

		private void doVoteWork() {
			System.out.println("I have voted " + this.name);
		}

		@Override
		public void run() {
			System.out.println("I have come " + this.name);
			// try {
			// this.orgnizer.await();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			this.doVoteWork();
			// I have voted
			allVoter.countDown();

		}

	}
	

	public static void main(String [] args)
	{
		CountDownLatchTest test = new CountDownLatchTest();
		new Thread(test).run();
	}

	@Override
	public void run() {
		
//		int i = 0;
//		for(; i < 25;i++)
//		{
//			Voter voter = new Voter(orgnizer,this.allVoter,"Voter NO." + i);
//			voter.start();
//		}
//		
//		int j = 0;
//
//		for(; i < 50;i++)
//		{
//			Voter voter = new Voter(orgnizer,this.allVoter,"Voter NO." + i);
//			voter.start();
//		}
//		
//		
//		for(; i < 100;i++)
//		{
//			Voter voter = new Voter(orgnizer,this.allVoter,"Voter NO." + i);
//			voter.start();
//		}	
//		
//		for(; j < 4; j++ )
//		{
//			Orginizer org = new Orginizer(orgnizer,this.allVoter,"Org NO." + j);
//			org.start();
//		}
//		
//		for(; j < 2; j++ )
//		{
//			Orginizer org = new Orginizer(orgnizer,this.allVoter,"Org NO." + j);
//			org.start();
//		}
		
		

		System.out.print("abc");
	}
}
