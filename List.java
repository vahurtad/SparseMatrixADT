/*
 *  Name: Vanessa Hurtado
 */

public class List
{
	private class Node
	{
		Object data;
		Node next;
		Node prev;

		Node(Object data)
		{
			this.data = data;
			this.prev = null;
			this.next = null;
		}
	}

	private Node head, curr, prev, tail;

	private int cursor, length;

	//Constructor
	List()
	{
		head = null;
		tail = null;
		curr = null;
		cursor = -1;
		length = 0;
	}

	/* Returns number of elements in this list */
	int length()
	{
		return length;
	}

	/* Returns index of the cursor element in this list. */
	/* Returns -1 if cursor element is undefined */
	int getIndex()
	{
		if(isEmpty())
			cursor = -1;
		return cursor;
	}

	/* Returns front element in this list. */
	/* Pre: length() > 0 */
	Object front()
	{
		if(length() < 0)
			throw new RuntimeException("Empty List.");
		return head.data;
	}

	/* Returns bacl element in this list */
	/* Pre: length() > 0 */
	Object back()
	{
		if(length() < 0)
			throw new RuntimeException("Empty List.");
		return tail.data;
	}

	/* Returns cursor element in this list */
	/* Pre: length() > 0, getIndex() >= 0 */
	Object getElement()
	{
		if(isEmpty())
			throw new RuntimeException("Empty List.");
		if(length() > 0 && getIndex() >= 0)
			return curr.data;
		return null;
	}

	/* Returns 1 if list and list L have the same integer sequence. */
	boolean equals(List L)
	{
		Node one = head;
		Node two = L.head;

		for(int i = 0; i < length(); i++)
		{
			if(one.data != two.data)
				return false;
			one = one.next;
			two = two.next;
		}
		return true;
	}

	/* Returns true for true if this list is empty.*/
	boolean isEmpty()
	{
		boolean flag = false;
		if(length == 0)
			flag = true;
		return flag;
	}
	// Manipulation procedures ----------------------------------------------------
	/* Resets this list to the empty state */
	void clear()
	{
		curr = null;
		head = null;
		tail = null;
		length = 0;
		cursor = -1;
	}

	/* if 0<=i<=length()-1, moves cursor to element at index i.*/
	/* otherwise becomes undefined*/
	void moveTo(int i)
	{
		cursor = 0;
		if(i >= 0 && i <= length()-1)
		{
			for(Node n = head; cursor <= i; n = n.next)
			{
				if(cursor == i)
				{
					curr = n;
					break;
				}
				else
					cursor++;
			}
		}
		else
		{
			curr = null;
			cursor = -1;
		}
	}

	/* if 0<getIndex()<=length()-1, moves cursor a step toward the beginning of this list */
	/* if getIndex()==-1, cursor is undefined */
	/* if getIndex()==0, cursor is undefined */
	void movePrev()
	{
		int i = getIndex();
		if(i > 0 && i <= length() -1)
			moveTo(i - 1);
		else if(i <= 0)
		{
			curr = null;
			cursor = -1;
		}
	}

	/* if 0<=getIndex()<length()-1, moves cursor a step toward the end of this list */
	/* if getIndex()==1, cursor is undefined. */
	/* if geIndex()==length()-1, cursor is undefined */
	void moveNext()
	{
		int i = getIndex();
		if(i >= 0 && i <length() - 1)
			moveTo(i + 1);
		else
		{
			curr = null;
			cursor = -1;
		}
	}

	/* Inserts a new element before the beginnning element */
	void prepend(Object data)
	{
		Node newNode = new Node(data);

		if(isEmpty())
		{
			tail = newNode;
			head = newNode;
		}
		else
		{
			newNode.next = head;
			head.prev = newNode;
		}
		head = newNode;
		if(cursor != -1)
			cursor++;
		length++;
	}

	/* Inserts a new element after the last element */
	void append(Object data)
	{
		Node newNode = new Node(data); //create temp node

		if(isEmpty())		 //add first element
		{
				head = newNode;
				tail = newNode;
		}
		else
		{
			tail.next = newNode; // add to the back of list
			newNode.prev = tail;
		}
		tail = newNode;			 //point back to node
		length++;
	}

	/* Inserts a new element before the cursor element */
	/* Pre: length()>0, getIndex()>=0 */
	void insertBefore(Object data)
	{
		Node newNode = new Node(data);

		if(!(length() > 0 && getIndex() >= 0))
		return;

		if(curr.prev != null)
		{
			curr.prev.next = newNode;
			newNode.prev = curr.prev;
		}
		else
			head = newNode;

		curr.prev = newNode;
		newNode.next = curr;
		length++;
	}

	/* Inserts a new element after the cursor element */
	/* Pre: length()>0, getIndex()>=0*/
	void insertAfter(Object data)
	{
		Node newNode = new Node(data);
		if(!(length() > 0 && getIndex() >= 0))
			return;

		if(curr.next != null)
		{
			curr.next.prev = newNode;
			newNode.next = curr.next;
		}
		else
			tail = newNode;

		curr.next = newNode;
		newNode.prev = curr;
		length++;
	}

	/* Deletes front element in this list */
	void deleteFront()
	{
		if(isEmpty())
			throw new RuntimeException("Empty List.");
		else
		{
			head = head.next;
			head.prev = null;
			cursor = -1;
			length--;
		}
	}

	/* Deletes back element in this list */
	void deleteBack()
	{
		if(isEmpty())
				throw new RuntimeException("Stack is empty.");
		else
		{
			tail = tail.prev;
			tail.next = null;
			cursor = -1;
			length--;
		}
	}

	/* Deletes cursor element, cursor is then undefined */
	/* Pre: length()>0, getIndex()>=0*/
	void delete()
	{
		if(curr == head)
			deleteFront();
		else if(curr == tail)
			deleteBack();

		if(!isEmpty())
		{
			curr.next.prev = curr.prev;
			curr.prev.next = curr.next;
		}
			curr = null;
			cursor = -1;
	}

	// Other operations -----------------------------------------------------------

	/* Prints this list */
	public String toString()
	{
		String result = "";
		Node current = head;	//local variable equal to first
		while(current != null)
		{
			result += current.data + " ";
			current = current.next;
		}
		return result;
	}
}
