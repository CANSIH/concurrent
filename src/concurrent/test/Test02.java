package concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test02 {
	public static void main(String[] args) {
		final Test02Container container = new Test02Container();
		final Object lock = new Object();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					if (container.size() != 5) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("size = 5");
					lock.notifyAll(); // 唤醒其他等待线程
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {

					for (int i = 0; i < 10; i++) {
						System.out.println("add Object to container " + i);
						container.add(new Object());
						if (container.size() == 5) {
							lock.notifyAll(); // 唤醒其他线程
							try {
								lock.wait(); // 当前线程等待
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
}

class Test02Container {
	List<Object> list = new ArrayList<>();

	public void add(Object obj) {
		list.add(obj);
	}

	public int size() {
		return list.size();
	}
}
