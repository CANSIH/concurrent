package concurrent.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test01 {
	public static void main(String[] args) {
		final Test01Container container = new Test01Container();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("add Object to container " + i);
					container.add(new Object());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (container.size() == 5) {
						System.out.println("thread 2 end...");
						break;
					}
				}
			}
		}).start();
	}
}

/**
 * 功能：自定义容器类
 *
 * @author hecg
 * @version 2019年3月2日 下午12:32:36
 */
class Test01Container {
	volatile List<Object> list = new ArrayList<>();

	public void add(Object obj) {
		list.add(obj);
	}

	public int size() {
		return list.size();
	}

}