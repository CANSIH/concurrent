package concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 练习： 自定义容器，提供新增元素（add）和获取元素数量（size）方法。
 * 启动两个线程。线程1向容器中新增10个数据。线程2监听容器元素数量，当容器元素数量为5时，线程2输出信息并终止。
 * 
 * @author hecg
 * @version 2019年3月2日 上午11:31:03
 */
public class ConcurrentTest {
	CountDownLatch countDownLatch = new CountDownLatch(5);
	static List<Integer> list = new ArrayList<>();

	void m1() {
		for (int i = 1; i <= 10; i++) {
			if (countDownLatch.getCount() != 0) {
				System.out.println("latch count : " + countDownLatch.getCount());
				countDownLatch.countDown(); // 减门闩上的锁。
			}
			int x = i;
			list.add(i);
			System.out.println("m1 add " + list.get(--x));
			
		}
	}

	void m2() {
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("m2 end");
		
		while (true) {
			if (countDownLatch.getCount() == 0) {
				System.out.println("m2 end");
				return;
			} else {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		final ConcurrentTest ct = new ConcurrentTest();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ct.m1();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ct.m2();
			}
		}).start();
	}

}
