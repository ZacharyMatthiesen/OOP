
public class ModNode extends ExpressionNode{
	public ModNode(ExpressionNode l, ExpressionNode r){
		super(l,r);
	}
	public double value(){
		return left.value()%right.value();
	}
}
