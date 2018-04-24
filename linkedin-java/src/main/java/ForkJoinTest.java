

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest implements Runnable {

	public class Worker extends RecursiveTask<Long> {

		private Long start;
		private Long end;

		public Worker(Long start, Long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {

			if (end - start > 200) {
				Long mid = (start + end) / 2;
				Worker w1 = new Worker(start, mid);
				Worker w2 = new Worker(mid + 1, end);
				w1.fork();
				w2.fork();

				return w1.join() + w2.join();
			} else {
				Long result = 0L;
				for (Long i = start; i <= end; i++) {
					result += i;
				}
				return result;
			}
		}
	}

	public static void main(String[] args) {
		ForkJoinTest ft = new ForkJoinTest();
		new Thread(ft).start();
	}

	@Override
	public void run() {

		try {
			ForkJoinPool fjp = new ForkJoinPool();
			Worker w = new Worker(0L, 1000000L);
			Future<Long> result = fjp.submit(w);
			try {
				System.out.println(result.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fjp.shutdown();

		} finally {

		}

	}

}
