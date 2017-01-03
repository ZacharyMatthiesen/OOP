
public class SubtractNode extends ExpressionNode{
	public SubtractNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return left.value()-right.value();
	}
}
