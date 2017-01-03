
public class CosNode extends ExpressionNode{
	public CosNode(Double data){
		super(data);
	}
	public double value(){
		return Math.cos(dNodeVal);
	}
}
