import java.util.regex.*;
/* Token: This class contains the necessary constructor to break the user supplied String into atomic tokens.
 *  It works by using regular expressions to find tokens in the String passed to it by the constructor.
 * Author: Zachary Matthiesen
 * Contributers: Tom Fuller, Sam Meier 
 */
public class Token {
	public enum TokenType{
		T_ID, T_EQUALS, T_DOUBLE, T_PLUS, T_MULT, T_DIV, T_MINUS, T_UMIN, T_LEFT_PAREN, T_RIGHT_PAREN, T_MOD, T_EXP, T_TRIG_SIN,
		T_TRIG_TAN, T_TRIG_COS, T_ERROR, T_EPSILON
	}
	public TokenType t_Type;
	public String name;
	public Double value;
	
	public Token(String n){
		name = n;
		if(Pattern.matches("[0-9]*.?[0-9]+", n)){
			value = Double.parseDouble(n);
			t_Type = TokenType.T_DOUBLE;
		}
		else if(Pattern.matches("([a-z]|[A-Z])+[0-9]*", n)){
			t_Type = TokenType.T_ID;
			value = 0.0;
		}
		else if(Pattern.matches("\\^", n)){
			t_Type = TokenType.T_EXP;
			value = 0.0;
		}
		else if(Pattern.matches("\\/", n)){
			t_Type = TokenType.T_DIV;
			value = 0.0;
		}
		else if(Pattern.matches("\\*", n)){
			t_Type = TokenType.T_MULT;
			value = 0.0;
		}
		else if(Pattern.matches("\\-", n)){
			t_Type = TokenType.T_MINUS;
			value = 0.0;
		}
		else if(Pattern.matches("\\+", n)){
			t_Type = TokenType.T_PLUS;
			value = 0.0;
		}
		else if(Pattern.matches("\\(", n)){
			t_Type = TokenType.T_LEFT_PAREN;
			value = 0.0;
		}
		else if(Pattern.matches("\\)", n)){
			t_Type = TokenType.T_RIGHT_PAREN;
			value = 0.0;
		}
		else if(Pattern.matches("=", n)){
			t_Type = TokenType.T_EQUALS;
			value = 0.0;
		}
		else if(Pattern.matches("tan", n)){
			t_Type = TokenType.T_TRIG_TAN;
			value = 0.0;
		}
		else if(Pattern.matches("cos", n)){
			t_Type = TokenType.T_TRIG_COS;
			value = 0.0;
		}
		else if(Pattern.matches("sin", n)){
			t_Type = TokenType.T_TRIG_SIN;
			value = 0.0;
		}
		else if(Pattern.matches("epsilon", n)){
			t_Type = TokenType.T_EPSILON;
			value = 0.0;
		}
		else{
			t_Type = TokenType.T_ERROR;
			System.out.print("error in input string");
			value = 0.0;
		}
	}
	 public Token(Token copy) {
		    this.t_Type = copy.t_Type;
		    this.name = copy.name;
		    this.value = copy.value;
	 }

	/*Returns a formated table row for the Lexer function of the expression interpreter 
	*/
	public String toString(){
		return String.format("%-23s %-23s %-23f",t_Type.toString(),name,value);
	}
	private boolean isValid(String n, Double v){
		if(n.contains("#") || n.contains("%") || n.contains("$") || n.contains("^")) return false;
		else if (n.matches("\\w?\\d?")) return true;
		else return true;
	}
	
}
