import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
/* StringHandlerFacade: This class is the entry point for the Expression Interpreter project. It contains all the user interface
 * with the console or file i/o that the program uses. It contains methods to evaluate Strings from both files and
 * the console, and methods to list the tokens from a user supplied String. 
 * Author: Zachary Matthiesen
 * Contributers: Tom Fuller, Sam Meier 
 */

public class StringHandlerFacade {
	public Lexer hannibal;
	private  String inFileName;
	private  String outFileName;
	private  String comand_args[] = new String[2];
	
	/*
	 * Optional Constructor
	 */
	public StringHandlerFacade(String inFile, String outFile, String[] c_args){
		int n = 0;
		for (String s: c_args) {
            //System.out.println(s);
			comand_args[n] = s;
			n++;
        }
	}
	/* evaluateOneString: This method takes a String argument representing an expression to be broken into tokens
	 *  and parsed.  The end result is a Double representing the 
	 * Pre: the argument expression holds the String to be interpreted
	 * Post: expression is sent to the lexer, that returns a token list that is sent to the parser. The parser returns a
	 *  Double value.
	 * @return Double representing the value of a statement, assignment or expression
	 */
	public static Double evaluateOneString(String expression){
		Lexer h = new Lexer(expression);
		Parser parseFile = new Parser(h.tokenArrayList);
		try{
			return parseFile.expression().value();
		}catch(IOException e){
			System.out.print(e.getMessage());
			return .24424443244233442343243;
		}
        
	}
	/* evaluateOneFile: This method takes a String argument representing the file to be interpreted
	 *  and parsed.
	 * Pre: The argument 'expression' holds a String representing the name of the file to be opened
	 * Post: The file is interpreted on every non-comment line
	 * @return void
	 */
	public static void evaluateOneFile(String inFileName) throws IOException{
		String sendToLexer = " ";
		ArrayList<Token> TokenArrayList = new ArrayList<Token>();
        Scanner scanOneFile = null;
        try {
        	scanOneFile = new Scanner(new BufferedReader(new FileReader(inFileName)));
            while (scanOneFile.hasNext()) {
            	String s = scanOneFile.nextLine();
                if(s.contains("#")) //lines with an octothorpe are comments and should be ignored
                	;
                else{
                	s = s.trim();
            		Lexer hannibal = new Lexer(s);
            		Parser parseFile = new Parser(hannibal.tokenArrayList);
                	try{
                		System.out.println(String.valueOf(parseFile.expression().value()));
            		}catch(IOException e){
            			System.out.print(e.getMessage());
            		}
                }
            }
        } finally {
            if (scanOneFile != null) {
            	scanOneFile.close();
            }
        }
	}
	/* listTokens: This method prints a list of tokens supplied by the lexer after suppling the lexer with the user's
	 *  input. Not parsed.
	 * Pre: none
	 * Post: The user's expression is lexed and the tokens in the expression are listed and formated as a table
	 * @return void
	 */
	public static void listTokens(){
		Scanner inCon = new Scanner(System.in);
		System.out.print("Enter a lexable string: ");
		Lexer h = new Lexer(inCon.nextLine());
		System.out.printf("%-23s %-23s %-23s %n", "TokenType","Lexeme", "Value");
		System.out.printf("======================================================%n");
		for(Token t : h.tokenArrayList){
			System.out.println(t.toString());
		}
	}
	/* main: This method decides what to do given the command line arguments.
	 * Pre: none
	 * Post: If the argument is 'C', the console expression interpreter routine is run
	 * 		If the argument is 'F', the file expression interpreter routine is run
	 * 		If the argument is 'L', the list tokens routine is run 
	 * @return void
	 */
	public static void main(String[] args)
	{	
		/* If the user wants to input from a file, they supply a command line argument of F,
		 * if they supply an argument of C, the user will use the console for input instead.
		 */
		if(args.length > 0){
			if(args[0].compareTo("C") == 0){
				Scanner inCon = new Scanner(System.in);
				System.out.print("Enter a valid expression: ");
				String ex = inCon.nextLine(); 
				System.out.printf("The expression '%s' has value %f %n", ex ,evaluateOneString(ex));
			}
			else if(args[0].compareTo("F") == 0){
				try {
					evaluateOneFile(args[1]);
				} catch (IOException e) {
					System.out.println("error opening file");
					e.printStackTrace();
				}
			}
			else if(args[0].compareTo("L") == 0){
				listTokens();
			}
		}
		System.out.println("Enter a comand prompt in the run configurations: L to list tokens or F to interperate a file or"
				+ " C to interpret console input");
		
	}
}
