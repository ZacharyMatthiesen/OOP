import java.util.ArrayList;
import java.util.InputMismatchException;
/* Lexer: This class contains the necessary methods to evaluate the user entered String and catch some errors before
 * 	the parser or the token class become involved.
 *  It works by parsing the input String, looking for delimiters, concatenating multiple characters into
 *   identifiers and  and checking for bad input.
 * Author: Zachary Matthiesen
 * Executive Contributers: Tom Fuller, Sam Meier 
 */
public class Lexer {
	Token current;
	int currIndex;
	ArrayList<Token> tokenArrayList = new ArrayList<Token>();
	public Lexer(String sendToLexer){
		String s = sendToLexer.replaceAll("\\s+", "");;
		String sSeq = "";
		currIndex = 0;
		try{
			for(char q : s.toCharArray()){
				if(q == '=' ||q == '+' || q == '-' || q == '/' || q == '*' || q == '%'|| q == '(' || q == '^' || q == ')'){
					if(sSeq.length() > 0){
						tokenArrayList.add(new Token(sSeq));
					}
					tokenArrayList.add(new Token(String.valueOf(q)));
					sSeq = "";
				}
				else{
					sSeq = sSeq.concat(String.valueOf(q));
				}
			}
		if(sSeq.length() > 0){
			tokenArrayList.add(new Token(sSeq));
		}
		current = tokenArrayList.get(0);
		}catch(InputMismatchException e){
			e.printStackTrace();
		}
	}
	public Token getNextToken() throws ArrayIndexOutOfBoundsException{
		if(currIndex >= tokenArrayList.size()){
			current = tokenArrayList.get(currIndex++);
		}
		else{
			ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException();
			throw e;
		}
	    return current;
	}
	public Token getNextToken(String nextStr){
		if(nextStr.startsWith("+")||nextStr.startsWith("-")|nextStr.startsWith("/")|nextStr.startsWith("*")){
			return new Token(String.valueOf(nextStr.charAt(0)));
		}
		else{
			return new Token(nextStr);
		}
	}
}
