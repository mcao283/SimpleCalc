import java.util.List;		// used by expression evaluator
import java.util.ArrayList;
/**
 *	Program which accepts input from user and works as a calculator. can save vars using var = num , can call on vars by typing the var name in the equation.
 *
 *	@author	Marcus Cao
 *	@since	2/28/2023
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	// constructor	
	public SimpleCalc() {
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
	}
	
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		String expression = "";
		List<String> tokens = new ArrayList<String>();
		do
		{
			expression = Prompt.getString(" " );
			if(expression.equals("h"))
				printHelp();
			else if( !expression.equals("q"))
			{
				System.out.println( evaluateExpression((utils.tokenizeExpression(expression))));
			}
				
		}while(!expression.equals("q"));
		
		for(int i = 0; i < tokens.size() ; i ++)
			System.out.print(tokens.get(i));
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {
		double value = 0;
		int counter = 0;
		double firstVal = 0.0, secondVal = 0.0 , val = 0.0, tempVal = 0.0;
		char token = ' ';
		char operator = ' ';
		while( counter < tokens.size())
		{
			token = tokens.get(counter).charAt(0);
			if(Character.isDigit(token))
			{
				val = Double.parseDouble(tokens.get(counter));
				valueStack.push(val);
			}
			if(token == '(')
				operatorStack.push(tokens.get(counter));
			if(token == ')')
			{
				while(!(operatorStack.isEmpty()) && operatorStack.peek().charAt(0) != '(')
				{
					secondVal = valueStack.pop();
					firstVal = valueStack.pop();
					operator = operatorStack.pop().charAt(0);
					
					switch(operator)
					{
						case '+': tempVal = firstVal + secondVal;
							break;
						case '-': tempVal = firstVal - secondVal;
							break;
						case '*': tempVal = firstVal * secondVal;
							break;
						case '/': tempVal = firstVal / secondVal;
							break;
						case '^': tempVal = Math.pow(firstVal,secondVal);
							break;
						case '%': tempVal = firstVal % secondVal;
							break;
					}
					valueStack.push(tempVal);
				}
				
				if(operatorStack.peek().charAt(0) == '(')
					operatorStack.pop();
			}
			
			if(!Character.isDigit(token) && token != ')' && token != '(')
			{
				if(operatorStack.isEmpty() || operatorStack.peek().charAt(0) == '(')
					operatorStack.push(tokens.get(counter));
				else
				{
					while(!operatorStack.isEmpty() && operatorStack.peek().charAt(0) != '(' && hasPrecedence(tokens.get(counter),operatorStack.peek()))
					{
						secondVal = valueStack.pop();
						firstVal = valueStack.pop();
						operator = operatorStack.pop().charAt(0);
					
						switch(operator)
						{
							case '+': tempVal = firstVal + secondVal;
								break;
							case '-': tempVal = firstVal - secondVal;
								break;
							case '*': tempVal = firstVal * secondVal;
								break;
							case '/': tempVal = firstVal / secondVal;
								break;
							case '^': tempVal = Math.pow(firstVal,secondVal);
								break;
							case '%': tempVal = firstVal % secondVal;
								break;
						}
						valueStack.push(tempVal);
					}
					operatorStack.push(tokens.get(counter)); 
					
				}
			}
			counter++;
		}
		
		while(!operatorStack.isEmpty())
		{
			secondVal = valueStack.pop();
						firstVal = valueStack.pop();
						operator = operatorStack.pop().charAt(0);
					
						switch(operator)
						{
							case '+': tempVal = firstVal + secondVal;
								break;
							case '-': tempVal = firstVal - secondVal;
								break;
							case '*': tempVal = firstVal * secondVal;
								break;
							case '/': tempVal = firstVal / secondVal;
								break;
							case '^': tempVal = Math.pow(firstVal,secondVal);
								break;
							case '%': tempVal = firstVal % secondVal;
								break;
						}
						valueStack.push(tempVal);
		}
		
		
		return valueStack.pop();
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	 
}
