
public class SinNode extends ExpressionNode{
	public SinNode(Double data){
		super(data);
	}
	public double value(){
		return Math.sin(dNodeVal);
	}
}
