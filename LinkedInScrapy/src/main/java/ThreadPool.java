

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {

	private int maxThreadPool;

	private AtomicInteger poolSize = new AtomicInteger();
	private ExecutorService executor;

	private Lock lock = new ReentrantLock();

	private Condition cons = lock.newCondition();

	public ThreadPool(int maxPoolSize) {
		this.maxThreadPool = maxPoolSize;

		this.executor = Executors.newCachedThreadPool();
	}

	public Future execute(final Runnable run) {
		if (poolSize.get() >= maxThreadPool) {
			try {
				lock.lock();
				while (poolSize.get() >= maxThreadPool) {
					try {
						cons.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} finally {
				lock.unlock();
			}
		}

		this.poolSize.incrementAndGet();
		return this.executor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					run.run();
				} finally {
					try {
						lock.lock();
						poolSize.decrementAndGet();
						cons.signal();
					} finally {
						lock.unlock();
					}
				}
			}

		});
	}
}
