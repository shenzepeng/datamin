package DataMining_ID3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class ID3Tool {

	private final String YES = "Yes";
	private final String NO = "No";


	private int attrNum;
	private String filePath;

	private String[][] data;

	private String[] attrNames;

	private HashMap<String, ArrayList<String>> attrValue;

	public ID3Tool(String filePath) {
		this.filePath = filePath;
		attrValue = new HashMap<String, ArrayList<String>>();
	}

	/**
	 * ���ļ��ж�ȡ����
	 */
	private void readDataFile() {
		File file = new File(filePath);
		ArrayList<String[]> dataArray = new ArrayList<String[]>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] tempArray;
			while ((str = in.readLine()) != null) {
				tempArray = str.split(" ");
				dataArray.add(tempArray);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

		data = new String[dataArray.size()][];
		dataArray.toArray(data);
		attrNum = data[0].length;
		attrNames = data[0];

		/*
		 * for(int i=0; i<data.length;i++){ for(int j=0; j<data[0].length; j++){
		 * System.out.print(" " + data[i][j]); }
		 * 
		 * System.out.print("\n"); }
		 */
	}


	private void initAttrValue() {
		ArrayList<String> tempValues;

		for (int j = 1; j < attrNum; j++) {

			tempValues = new ArrayList<String>();
			for (int i = 1; i < data.length; i++) {
				if (!tempValues.contains(data[i][j])) {
					tempValues.add(data[i][j]);
				}
			}


			attrValue.put(data[0][j], tempValues);
		}

		/*
		 * for(Map.Entry entry : attrValue.entrySet()){
		 * System.out.println("key:value " + entry.getKey() + ":" +
		 * entry.getValue()); }
		 */
	}


	private double computeEntropy(String[][] remainData, String attrName,
			String value, boolean isParent) {

		int total = 0;
		int posNum = 0;
		int negNum = 0;

		for (int j = 1; j < attrNames.length; j++) {
			if (attrName.equals(attrNames[j])) {
				for (int i = 1; i < remainData.length; i++) {
					if (isParent
							|| (!isParent && remainData[i][j].equals(value))) {
						if (remainData[i][attrNames.length - 1].equals(YES)) {
							posNum++;
						} else {
							negNum++;
						}
					}
				}
			}
		}

		total = posNum + negNum;
		double posProbobly = (double) posNum / total;
		double negProbobly = (double) negNum / total;

		if (posProbobly == 1 || posProbobly == 0) {
			return 0;
		}

		double entropyValue = -posProbobly * Math.log(posProbobly)
				/ Math.log(2.0) - negProbobly * Math.log(negProbobly)
				/ Math.log(2.0);

		return entropyValue;
	}


	private double computeGain(String[][] remainData, String value) {
		double gainValue = 0;
		double entropyOri = 0;

		double childEntropySum = 0;

		int childValueNum = 0;

		ArrayList<String> attrTypes = attrValue.get(value);

		HashMap<String, Integer> ratioValues = new HashMap<String, Integer>();

		for (int i = 0; i < attrTypes.size(); i++) {

			ratioValues.put(attrTypes.get(i), 0);
		}

		for (int j = 1; j < attrNames.length; j++) {

			if (value.equals(attrNames[j])) {
				for (int i = 1; i <= remainData.length - 1; i++) {
					childValueNum = ratioValues.get(remainData[i][j]);

					childValueNum++;
					ratioValues.put(remainData[i][j], childValueNum);
				}
			}
		}


		entropyOri = computeEntropy(remainData, value, null, true);
		for (int i = 0; i < attrTypes.size(); i++) {
			double ratio = (double) ratioValues.get(attrTypes.get(i))
					/ (remainData.length - 1);
			childEntropySum += ratio
					* computeEntropy(remainData, value, attrTypes.get(i), false);

			// System.out.println("ratio:value: " + ratio + " " +
			// computeEntropy(remainData, value,
			// attrTypes.get(i), false));
		}


		gainValue = entropyOri - childEntropySum;
		return gainValue;
	}


	private double computeGainRatio(String[][] remainData, String value) {
		double gain = 0;
		double spiltInfo = 0;
		int childValueNum = 0;

		ArrayList<String> attrTypes = attrValue.get(value);

		HashMap<String, Integer> ratioValues = new HashMap<String, Integer>();

		for (int i = 0; i < attrTypes.size(); i++) {
			ratioValues.put(attrTypes.get(i), 0);
		}


		for (int j = 1; j < attrNames.length; j++) {

			if (value.equals(attrNames[j])) {
				for (int i = 1; i <= remainData.length - 1; i++) {
					childValueNum = ratioValues.get(remainData[i][j]);

					childValueNum++;
					ratioValues.put(remainData[i][j], childValueNum);
				}
			}
		}


		gain = computeGain(remainData, value);

		for (int i = 0; i < attrTypes.size(); i++) {
			double ratio = (double) ratioValues.get(attrTypes.get(i))
					/ (remainData.length - 1);
			spiltInfo += -ratio * Math.log(ratio) / Math.log(2.0);
		}

		// �������Ϣ������
		return gain / spiltInfo;
	}


	private void buildDecisionTree(AttrNode node, String parentAttrValue,
			String[][] remainData, ArrayList<String> remainAttr, boolean isID3) {
		node.setParentAttrValue(parentAttrValue);

		String attrName = "";
		double gainValue = 0;
		double tempValue = 0;


		if (remainAttr.size() == 1) {
			System.out.println("attr null");
			return;
		}

		for (int i = 0; i < remainAttr.size(); i++) {

			if (isID3) {

				tempValue = computeGain(remainData, remainAttr.get(i));
			} else {
				tempValue = computeGainRatio(remainData, remainAttr.get(i));
			}

			if (tempValue > gainValue) {
				gainValue = tempValue;
				attrName = remainAttr.get(i);
			}
		}

		node.setAttrName(attrName);
		ArrayList<String> valueTypes = attrValue.get(attrName);
		remainAttr.remove(attrName);

		AttrNode[] childNode = new AttrNode[valueTypes.size()];
		String[][] rData;
		for (int i = 0; i < valueTypes.size(); i++) {

			rData = removeData(remainData, attrName, valueTypes.get(i));

			childNode[i] = new AttrNode();
			boolean sameClass = true;
			ArrayList<String> indexArray = new ArrayList<String>();
			for (int k = 1; k < rData.length; k++) {
				indexArray.add(rData[k][0]);

				if (!rData[k][attrNames.length - 1]
						.equals(rData[1][attrNames.length - 1])) {

					sameClass = false;
					break;
				}
			}

			if (!sameClass) {

				ArrayList<String> rAttr = new ArrayList<String>();
				for (String str : remainAttr) {
					rAttr.add(str);
				}

				buildDecisionTree(childNode[i], valueTypes.get(i), rData,
						rAttr, isID3);
			} else {

				childNode[i].setParentAttrValue(valueTypes.get(i));
				childNode[i].setChildDataIndex(indexArray);
			}

		}
		node.setChildAttrNode(childNode);
	}


	private String[][] removeData(String[][] srcData, String attrName,
			String valueType) {
		String[][] desDataArray;
		ArrayList<String[]> desData = new ArrayList<String[]>();

		ArrayList<String[]> selectData = new ArrayList<String[]>();
		selectData.add(attrNames);

		for (int i = 0; i < srcData.length; i++) {
			desData.add(srcData[i]);
		}

		for (int j = 1; j < attrNames.length; j++) {
			if (attrNames[j].equals(attrName)) {
				for (int i = 1; i < desData.size(); i++) {
					if (desData.get(i)[j].equals(valueType)) {
						selectData.add(desData.get(i));
					}
				}
			}
		}

		desDataArray = new String[selectData.size()][];
		selectData.toArray(desDataArray);

		return desDataArray;
	}


	public void startBuildingTree(boolean isID3) {
		readDataFile();
		initAttrValue();

		ArrayList<String> remainAttr = new ArrayList<String>();

		for (int i = 1; i < attrNames.length - 1; i++) {
			remainAttr.add(attrNames[i]);
		}

		AttrNode rootNode = new AttrNode();
		buildDecisionTree(rootNode, "", data, remainAttr, isID3);
		showDecisionTree(rootNode, 1);
	}


	private void showDecisionTree(AttrNode node, int blankNum) {
//		System.out.println();
//		for (int i = 0; i < blankNum; i++) {
//			System.out.print("\t");
//		}
//		System.out.print("--");

		if (node.getParentAttrValue() != null
				&& node.getParentAttrValue().length() > 0) {
//			System.out.print(node.getParentAttrValue());
		} else {
//			System.out.print("--");
		}
//		System.out.print("--");

		if (node.getChildDataIndex() != null
				&& node.getChildDataIndex().size() > 0) {
			String i = node.getChildDataIndex().get(0);
//			System.out.print(""
//					+ data[Integer.parseInt(i)][attrNames.length - 1]);
//			System.out.print("[");
			for (String index : node.getChildDataIndex()) {
//				System.out.print(index + ", ");
			}
//			System.out.print("]");
		} else {

//			System.out.print("��" + node.getAttrName() + "��");
			for (AttrNode childNode : node.getChildAttrNode()) {
				showDecisionTree(childNode, 2 * blankNum);
			}
		}

	}

}
