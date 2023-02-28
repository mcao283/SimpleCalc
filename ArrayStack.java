import java.util.List;
import java.util.ArrayList;
import java.util.EmptyStackException; 
/** Simple stack using ArrayList
  * 
  * @author Marcus Cao
  * @since Feburary 28 , 2023
  */
public class ArrayStack<E> implements Stack <E>
{
	private List<E> theStack;
	
	public ArrayStack()
	{
		theStack = new ArrayList<E>();
	}
	
	/** @return 	true if stack is empty: false otherwise */
	public boolean isEmpty(){ return theStack.isEmpty();  }
	
	/** @return 	the top element of the stack */
	public E peek()
	{
		if(theStack.isEmpty())
			throw new EmptyStackException();
		return theStack.get(theStack.size()-1);
	}
	
	/** @param obj 		the object to put at the top of the stack */
	
	public void push (E obj)
	{
		theStack.add(obj);
	}
	
	/** @return 	the obj removed from the top of the stack */
	
	public E pop()
	{
		if(theStack.isEmpty())
			throw new EmptyStackException();
		return theStack.remove(theStack.size()-1);
	}
	
}
