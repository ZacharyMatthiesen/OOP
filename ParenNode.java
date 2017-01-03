
public class ParenNode extends ExpressionNode{
	public ParenNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return left.value();
	}
}
