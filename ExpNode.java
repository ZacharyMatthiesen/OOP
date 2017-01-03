
public class ExpNode extends ExpressionNode{
	public ExpNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return Math.pow(left.value(), right.value());
	}
}
