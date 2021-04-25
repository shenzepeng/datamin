package DataMining_ID3;

import java.util.ArrayList;


public class AttrNode {

	private String attrName;

	private String parentAttrValue;

	private AttrNode[] childAttrNode;

	private ArrayList<String> childDataIndex;

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public AttrNode[] getChildAttrNode() {
		return childAttrNode;
	}

	public void setChildAttrNode(AttrNode[] childAttrNode) {
		this.childAttrNode = childAttrNode;
	}

	public String getParentAttrValue() {
		return parentAttrValue;
	}

	public void setParentAttrValue(String parentAttrValue) {
		this.parentAttrValue = parentAttrValue;
	}

	public ArrayList<String> getChildDataIndex() {
		return childDataIndex;
	}

	public void setChildDataIndex(ArrayList<String> childDataIndex) {
		this.childDataIndex = childDataIndex;
	}
}
