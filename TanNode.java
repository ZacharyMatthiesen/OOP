
public class TanNode extends ExpressionNode{
	public TanNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	
	public double value(){
		return Math.tan(left.value());
	}
}
