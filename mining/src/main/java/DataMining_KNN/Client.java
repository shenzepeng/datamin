package DataMining_KNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * k最近邻算法场景类型
 *
 *
 */
public class Client {
	public static void main(String[] args){
		String trainDataPath = "C:\\rainInput.txt";
		String testDataPath = "C:\\testinput.txt";

		KNNTool tool = new KNNTool(trainDataPath, testDataPath);
		tool.knnCompute(3);
		
	}
	


}
