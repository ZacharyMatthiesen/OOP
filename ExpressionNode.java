/*
 * ExpressionNode is the basic unit of the parse tree. It has two children and knows its operation (+, - ect.)
 * It returns its value by calling its children and then calculating the appropriate double value.
 * 
 * 
 */
public class ExpressionNode {
	static ExpressionNode left;
	static ExpressionNode right;
	static Double dNodeVal;
	
	public ExpressionNode(ExpressionNode l, ExpressionNode r){
		left = l;
		right = r;
	}
	public ExpressionNode(Double data){
		left = null;
		right = null;
		dNodeVal = data;
	}
	public double value(){
		return dNodeVal;
	}
	
}
