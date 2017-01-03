
public class AddNode extends ExpressionNode{
	public AddNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return left.value() + right.value();
	}
}
