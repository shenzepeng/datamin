package DataMining_CART;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
//	public static void main(String[] args){
//		String filePath = "C:\\Users\\lyq\\Desktop\\icon\\input.txt";
//		CARTTool tool = new CARTTool(filePath);
//		tool.startBuildingTree();
//	}

	public static void startTime(int times,String filePath){
		String opPath=new String(filePath+ "/DataMining_CART/input.txt");
		long startTime=System.currentTimeMillis();
		for (int i=0;i<times;i++){
			CARTTool tool = new CARTTool(opPath);
			tool.startBuildingTree();
		}
		long endTime=System.currentTimeMillis();
		System.out.println("cart:"+(endTime-startTime));
	}


	public static void startTimeWithThread(final int times, String filePath) throws InterruptedException {
		final String opPath=new String(filePath+ "/DataMining_CART/input.txt");
		long startTime=System.currentTimeMillis();
		final CountDownLatch countDownLatch=new CountDownLatch(times);
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (int i=0;i<10;i++){
			new Thread(new Runnable() {
				public void run() {
					for (int i=0;i<times/10;i++){
						CARTTool tool = new CARTTool(opPath);
						tool.startBuildingTree();
						countDownLatch.countDown();
					}
				}
			}).start();
		}
		countDownLatch.await(10, TimeUnit.MINUTES);
		long endTime=System.currentTimeMillis();
		System.out.println("cart:"+(endTime-startTime));
	}
}
