// Rene Gagnon, 260801777

import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf



		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}



		// this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
		// the calling DTNode object as the root of a decision tree trained using the datapoints present in the
		// datalist variable
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			int bestAttr[] = new int[2];
			
			if(datalist.size() >= minSizeDatalist) {
				if(calcEntropy(datalist) == 0) {
					DTNode leaf = new DTNode();
					leaf.label = datalist.get(0).y;
					return leaf;
				}else {
					//DTNode newNode = new DTNode();
					ArrayList<Datum> dataLeft = new ArrayList<Datum>();
					ArrayList<Datum> dataRight = new ArrayList<Datum>();
					// create best attribute question
					bestAttr = findBestSplit(datalist);
					this.attribute = bestAttr[0];
					this.threshold = datalist.get(bestAttr[1]).x[bestAttr[0]];
					masterSplit(datalist, bestAttr[0], dataLeft, dataRight, bestAttr[1]);
					this.left = (new DTNode()).fillDTNode(dataLeft);
					this.right = (new DTNode()).fillDTNode(dataRight);
					this.leaf = false;
					return this;
				}
				
			}
			this.label = findMajority(datalist);
			return this;
		}



		//This is a helper method. Given a datalist, this method returns the label that has the most
		// occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist)
		{
			int l = datalist.get(0).x.length;
			int [] votes = new int[l];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			int max = -1;
			int max_index = -1;
			//find the label with the max occurrences
			for (int i = 0 ; i < l ;i++)
			{
				if (max<votes[i])
				{
					max = votes[i];
					max_index = i;
				}
			}
			return max_index;
		}

		// helper method to find the best split for a
		private int[] findBestSplit(ArrayList<Datum> datalist) {
			int[] result = new int[2];
			double bestAvgEntropy = Double.MAX_VALUE;
			int bestAttr = -1;
			double bestTreshold = -1;
			double currAvgEntropy;
			for(int i = 0; i<datalist.get(0).x.length; i++) {// go through every attribute
				for(int j = 0; j < datalist.size(); j++) {
					ArrayList<Datum> splitLeft = new ArrayList<Datum>();
					ArrayList<Datum> splitRight = new ArrayList<Datum>();
					masterSplit(datalist, i, splitLeft, splitRight, j); // split the list
					currAvgEntropy = (splitLeft.size() * calcEntropy(splitLeft) + splitRight.size() * calcEntropy(splitRight))/datalist.size(); // compute avg entropy
					if(bestAvgEntropy > currAvgEntropy) {
						bestAvgEntropy = currAvgEntropy;
						result[0] = i;// assign best attribute
						result[1] = j;// assign best threshold
					}
				}
			}
			return result;
		}

		// split a datalist
		private void masterSplit(ArrayList<Datum> datalist, int attr, ArrayList<Datum> splitLeft, ArrayList<Datum> splitRight, int indexToCut) {
			for(Datum curData: datalist) {
				if(curData.x[attr] < datalist.get(indexToCut).x[attr]) {
					splitLeft.add(curData);
				}else {
					splitRight.add(curData);
				}
			}
		}
		
		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
			if(this.leaf) {
				return this.label;
			}else {
				if(xQuery[this.attribute] < this.threshold) {
					return this.left.classifyAtNode(xQuery);
				}else {
					return this.right.classifyAtNode(xQuery);
				}
			}
		}
		
		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2)
		{
			if(this == dt2) { // both objects reference the same object and therefore must be equal
				return false;
			}		
			DTNode dt2node;
			if(dt2 != null && dt2 instanceof DTNode) {
				dt2node = (DTNode) dt2;	
				if(this.leaf && dt2node.leaf && this.label == dt2node.label) { // if both are leaf nodes and they are equal
					return true;
				}else if(!this.leaf && !dt2node.leaf && this.attribute == dt2node.attribute && this.threshold == dt2node.threshold) {
					// if both are internal nodes and are equal, return the equals of both childs
					return this.left.equals(dt2node.left) && this.right.equals(dt2node.right);
				}else {
					return false;
				}
			}
			return false;
		}
	}



	//Given a dataset, this retuns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist)
	{
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		DTNode node = this.rootDTNode;
		return node.classifyAtNode( xQuery );
	}

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance( ArrayList<Datum> datalist)
	{
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
