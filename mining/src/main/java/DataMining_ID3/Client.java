package DataMining_ID3;


import DataMining_CART.CARTTool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
//	public static void main(String[] args){
//		String filePath = "C:\\Users\\lyq\\Desktop\\icon\\input.txt";
//
//		ID3Tool tool = new ID3Tool(filePath);
//		tool.startBuildingTree(true);
//	}

	public static void startTime(int times,String filePath){
		String opPath=new String(filePath+"/DataMining_ID3/input.txt");
		long startTime=System.currentTimeMillis();
		for (int i=0;i<times;i++){
			ID3Tool tool = new ID3Tool(opPath);
			tool.startBuildingTree(true);
		}
		long endTime=System.currentTimeMillis();
		System.out.println("ids:"+(endTime-startTime));
	}

	public static void startTimeWithThread(final int times, String filePath) throws InterruptedException {
		final String opPath=new String(filePath+"/DataMining_ID3/input.txt");
		long startTime=System.currentTimeMillis();
		final CountDownLatch countDownLatch=new CountDownLatch(times);
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (int i=0;i<10;i++){
			new Thread(new Runnable() {
				public void run() {
					for (int i=0;i<times/10;i++){
						ID3Tool tool = new ID3Tool(opPath);
						tool.startBuildingTree(true);
						countDownLatch.countDown();
					}
				}
			}).start();
		}
		countDownLatch.await(10, TimeUnit.MINUTES);
		long endTime=System.currentTimeMillis();
		System.out.println("ids:"+(endTime-startTime));
	}
}
