
public class MulNode extends ExpressionNode{
	public MulNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return right.value()*left.value();
	}
}
