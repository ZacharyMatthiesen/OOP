import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
/* Parser: This class contains the necessary methods to evaluate the list of tokens supplied from the lexer.
 *  It works by parsing the token list using grammatical rules to detect errors and maintain the order of operations.
 * Author: Zachary Matthiesen
 * Contributers: Tom Fuller, Sam Meier 
 */
public class Parser {
	Integer i_CurrIndx;
	ArrayList<Token> tArrLst;
	SymbolTable stTable;
	public Parser(ArrayList<Token> tokenArrayList){
		i_CurrIndx = 0;
		tArrLst = tokenArrayList;
		tokenArrayList.add(new Token("epsilon"));
		stTable = new SymbolTable();
	}
	public ExpressionNode statement(){
		return null;
	}
	public ExpressionNode assignment(){
		return null;
	}
	public ExpressionNode expression()throws IOException{
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				/////////////////remove before release
				System.out.println("expression");
				System.out.println(tArrLst.get(i_CurrIndx).toString());
				System.out.println(i_CurrIndx);
				/////////////////remove before release
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		ExpressionNode c = term();
		return restExpression(c);
	}
	public ExpressionNode restExpression(ExpressionNode x) throws IOException{
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				/////////////////remove before release
				System.out.println("restExpression");
				System.out.println(tArrLst.get(i_CurrIndx).toString());
				System.out.println(i_CurrIndx);
				/////////////////remove before release
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		switch(tArrLst.get(i_CurrIndx).t_Type){
			case T_ERROR:
				return null;
			case T_PLUS:
				if(i_CurrIndx + 1 < tArrLst.size()){
					i_CurrIndx++;
					ExpressionNode term = term();
					System.out.println("x " + x.value() + "term " + term.value());
					return restExpression(new AddNode(x,term));
				}
				return x;
			case T_MINUS:
				if(i_CurrIndx + 1 < tArrLst.size()){
					i_CurrIndx++;
					return restExpression(new SubtractNode(x,term()));
				}
				return x;
			case T_EPSILON:
				System.out.println("Stopper");
				return new DoubleNode(tArrLst.get(0).value);
			default:
				if(i_CurrIndx + 1 < tArrLst.size()){
					System.out.println("Trowup");
					return x;
				}else{
					System.out.println("Stopper");
					return new DoubleNode(tArrLst.get(0).value);
				}
		}
	}
	public ExpressionNode term()throws IOException{
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				/////////////////remove before release
				System.out.println("term");
				System.out.println(tArrLst.get(i_CurrIndx).toString());
				System.out.println(i_CurrIndx);
				/////////////////remove before release
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		ExpressionNode c = factor();
		return restTerm(c);
	}
	public ExpressionNode restTerm(ExpressionNode x)throws IOException{
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				/////////////////remove before release
				System.out.println("restTerm");
				System.out.println(tArrLst.get(i_CurrIndx).toString());
				System.out.println(i_CurrIndx);
				/////////////////remove before release
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		switch(tArrLst.get(i_CurrIndx).t_Type){
			case T_ERROR:
				return null;
			case T_MULT: 
				if(i_CurrIndx + 1 < tArrLst.size()){
					i_CurrIndx++;
					return restTerm(new MulNode(x,term()));
				}
				return x;
			case T_DIV:
				if(i_CurrIndx + 1 < tArrLst.size()){
					i_CurrIndx++;
					return restTerm(new DivNode(x,term()));
				}
				return x;
			case T_EPSILON:
				System.out.println("Stopper");
				return new DoubleNode(tArrLst.get(0).value);
			default:
				if(i_CurrIndx + 1 < tArrLst.size()){
					System.out.println("Trowup");
					return x;
				}else{
					System.out.println("Stopper");
					return new DoubleNode(tArrLst.get(0).value);
				}
				
		}
	}
	public ExpressionNode factor()throws IOException{
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
				/////////////////remove before release
				System.out.println("factor");
				System.out.println(tArrLst.get(i_CurrIndx).toString());
				System.out.println(i_CurrIndx);
				/////////////////remove before release
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		Token t = tArrLst.get(i_CurrIndx);
		switch(t.t_Type){
			case T_PLUS:
				throw new IOException("There were two plus signs in a row");
			case T_MULT: 
				throw new IOException("There were two multiplication signs in a row");
			case T_DIV:
				throw new IOException("There were two division signs in a row");
			case T_EPSILON:
				return new DoubleNode(tArrLst.get(0).value);
			case T_ERROR:
				return null;
			case T_UMIN: 
				if(i_CurrIndx + 1 < tArrLst.size())i_CurrIndx++;
				return new DoubleNode(-t.value);
			case T_ID:
				if(i_CurrIndx + 1 < tArrLst.size())i_CurrIndx++;
				return new IDNode(t.value);
			case T_DOUBLE:
				if(i_CurrIndx + 1 < tArrLst.size())i_CurrIndx++;
				System.out.println(i_CurrIndx);
				return new DoubleNode(t.value);
			case T_LEFT_PAREN:
				if(i_CurrIndx + 1 < tArrLst.size())i_CurrIndx++;
				else return new ParenNode(new ExpressionNode(t.value), expression());
			default:
				return null;
		}
	}
}
