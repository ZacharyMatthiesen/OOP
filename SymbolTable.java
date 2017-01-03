import java.util.TreeMap;

public class SymbolTable {
	TreeMap<String, Double> idVal; 
	public SymbolTable(){
		idVal = new TreeMap<String, Double>();
	}
	public void addSymbol(String s, Double d){
		idVal.put(s, d);
	}
	public Double findSymbol(String s){
		return idVal.get(s);
	}
}
