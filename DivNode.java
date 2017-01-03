
public class DivNode extends ExpressionNode{
	public DivNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return left.value() / right.value();
	}
}
