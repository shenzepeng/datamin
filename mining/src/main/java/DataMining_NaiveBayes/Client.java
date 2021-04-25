package DataMining_NaiveBayes;


import DataMining_ID3.ID3Tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ���ر�Ҷ˹�㷨����������

 *
 */
public class Client {
//	public static void main(String[] args){
//		//ѵ��������
//		String filePath = "C:\\Users\\lyq\\Desktop\\icon\\input.txt";
//		String testData = "Youth Medium Yes Fair";
//		NaiveBayesTool tool = new NaiveBayesTool(filePath);
//		System.out.println(testData + " ���ݵķ���Ϊ:" + tool.naiveBayesClassificate(testData));
//	}


	public static void startTime(int times,String filePath){
		String opPath=new String(filePath+"/DataMining_NaiveBayes/input.txt");
		long startTime=System.currentTimeMillis();
		for (int i=0;i<times;i++){
			NaiveBayesTool tool = new NaiveBayesTool(opPath);
		}
		long endTime=System.currentTimeMillis();
		System.out.println("naiveBayes:"+(endTime-startTime));
	}


	public static void startTimeWithThread(final int times, String filePath) throws InterruptedException {
		final String opPath=new String(filePath+"/DataMining_NaiveBayes/input.txt");
		long startTime=System.currentTimeMillis();
		final CountDownLatch countDownLatch=new CountDownLatch(times);
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (int i=0;i<10;i++){
			new Thread(new Runnable() {
				public void run() {
					for (int i=0;i<times/10;i++){
						NaiveBayesTool tool = new NaiveBayesTool(opPath);
						countDownLatch.countDown();
					}
				}
			}).start();
		}
		countDownLatch.await(10, TimeUnit.MINUTES);
		long endTime=System.currentTimeMillis();
		System.out.println("naiveBayes:"+(endTime-startTime));
	}
}
