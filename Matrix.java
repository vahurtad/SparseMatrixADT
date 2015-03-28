public class Matrix
{
  private class Entry
  {
    int column;
    double value;

    Entry(int c, double v)
    {
      column = c;
      value = v;
    }

    public boolean equals(Object x)
    {
      Entry newEntry = (Entry)x;
      return ((value == newEntry.value) && (column == newEntry.column));
    }

    public String toString()
    {
      return ("(" + column + " , " + value + ")");
    }
  }

  int size;
  int nnz;
  List[] row;

  // Makes a new n x n zero Matrix. pre: n>=1
  Matrix(int n)
  {
    if(n >= 1)
    {
      size = n;
      row = new List[size];
      for(int i = 0; i < size; i++)
        row[i] = new List();
      nnz = 0;
      
    }
    else
    throw new RuntimeException("Matrix is invalid");
  }

  // Access functions
  // Returns n, the number of rows and columns of this Matrix
  int getSize()
  {
    return size;
  }

  // Returns the number of non-zero entries in this Matrix
  int getNNZ()
  {
    return nnz;
  }
  
  // overrides Object's equals() method
  public boolean equals(Object x)
  {
	  boolean flag;		
	  Matrix newMatrix = (Matrix)x;
	  if(size == newMatrix.getSize())
	  {
		 flag = true;
		 int i = 1;
		 while(i < size && flag)
		 {
			 flag = (row[i].equals(newMatrix.row[i]));
			 i++;
		 }
		 return flag;
	  }
	  else
		  return false;
  }

  // Manipulation Procedures
  //sets matrix to the zero state
  void makeZero()
  {
    for(int i = 0; i < size; i++)
      row[i].clear();
      nnz = 0;
  }

  //returns a new Matrix having the same entries as this Matrix
  Matrix copy()
  {  	
	  Matrix newMatrix = new Matrix(getSize());
	  Entry currentEntry;
	  
	  for(int i = 0; i < row.length; i++)
	  {
		  List currentList = row[i];
		  List newList = newMatrix.row[i];
		  
		  currentList.moveTo(0);
		  for(; currentList.getIndex() >= 0; currentList.moveNext())
		  {
			  currentEntry = (Entry) currentList.getElement();
			  Entry newEntry = new Entry(currentEntry.column, currentEntry.value);
		      newList.append(newEntry);
		  }
	  }
	  newMatrix.nnz = nnz;
	  return newMatrix;		 
  }

  // changes ith row, jth column of this Matrix to x
   // pre: 1<=i<=getSize(), 1<=j<=getSize()
   void changeEntry(int i, int j , double x)
   {
     if(!( 1 <= i && i <= getSize()))
    	 throw new RuntimeException("Matrix is invalid");
     if(!(1 <= j && j <= getSize()))
    	 throw new RuntimeException("Matrix ix invalid");
     
     List temp = row[i];     
     Entry newEntry = null;
     
     temp.moveTo(0);
     for(; temp.getIndex() >= 0; temp.moveNext())
     {
    	 newEntry = (Entry) temp.getElement(); 
    	 if(newEntry.column < j)
    		 continue;
    	break;
     }
     
     if(newEntry != null && newEntry.column == j)
     {
    	 if(x == 0)
    	 {
    		 temp.delete();
    		 nnz--;
    	 }
    	 else if(x != 0)
    	 {
    		 temp.insertBefore(new Entry(j,x));
    		 nnz++;
    	 }
     }
     else if(temp.getIndex() == -1 && x != 0)
     {
    	 temp.append(new Entry(j,x));
    	 nnz++;
     }
   }

   // returns a new Matrix that is the scalar product of this Matrix with x
   Matrix scalarMul(double x)
   {
	   double v;
	   Matrix result = copy();
	   
	   for(int i = 1; i < row.length; i++)
	   {
		   result.row[i].moveTo(0);
		   
		   while(result.row[i].getIndex() >= 0)
		   {
			   v = ((Entry) result.row[i].getElement()).value * x;
			   ((Entry) result.row[i].getElement()).value = v;
			   result.row[i].moveNext();
		   }
	   }
	   return result;
   }

   // returns a new Matrix that is the sum of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix add(Matrix M)
   {
	   Matrix result = new Matrix(getSize());
	   Entry t;

	   if(M.getSize() != getSize())
		   throw new RuntimeException("Matrix is invalid: different dimension.");  	
	  	   
	   for(int i = 1; i < row.length; i++)
	   {
		   List A = row[i];
		   List B = M.row[i];
		   List l = result.row[i];
		   
		   A.moveTo(0);
		   B.moveTo(0);	 
		   
		   while(!(A.getIndex() < 0) && !(B.getIndex() < 0))
		   {
			   Entry aEntry = (Entry) A.getElement();
			   Entry bEntry = (Entry) B.getElement();
			   
			   if(aEntry.column < bEntry.column)
			   {
				   t = new Entry(aEntry.column, aEntry.value);
				   l.append(t);
				   A.moveNext();
			   }
			   
			   else if(aEntry.column > bEntry.column)
			   {
				   t = new Entry(bEntry.column, bEntry.value);
				   l.append(t);
				   B.moveNext();
			   }
			   else if(aEntry.column == bEntry.column)
			   {
					   t = new Entry(aEntry.column, aEntry.value + bEntry.value);
					   l.append(t);
					   B.moveNext(); 
			   }			   
		   }
	   }
	   return result;
   }

   // returns a new Matrix that is the difference of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix sub(Matrix M)
   {
	   if(M.getSize() != getSize())
		   throw new RuntimeException("Matrix is invalid: different dimension.");
	  
	   Matrix result = copy();
	   Entry currentEntry;
	   Entry newEntry;
	   
	   for(int i = 1; i < M.getSize(); i++)
	   {
		   if(M.row[i].length() == 1)
			   continue;
		   
		   for(int j = 1; j < M.row[i].length(); j++)
		   {
			   M.row[i].moveTo(j);
			   for(int t = 1; t < row[i].length(); t++)
			   {
				   row[i].moveTo(t);
				   currentEntry = (Entry) row[i].getElement();
				   newEntry = (Entry) M.row[i].getElement();
				   
				   if(currentEntry.column == newEntry.column)
				   {
					   double r = currentEntry.value - newEntry.value;
					   result.changeEntry(i, newEntry.column, r);
					   break;
				   }
				   
				   if(currentEntry.column > newEntry.column)
				   {
					   result.changeEntry(i, newEntry.column, 0 - newEntry.value);
					   break;
				   }
				   
				   if(t == row[i].length()-1 && t < M.row[i].length()-1)
				   {
					   t++;
					   while(t < M.row[i].length())
					   {
						   result.changeEntry(i, newEntry.column, 0 - newEntry.value);
						   t++;
					   }
				   }
			   }
		   }
	   }  
	   return result;
   }

   // returns a new Matrix that is the transpose of this Matrix
   Matrix transpose()
   {
	   Matrix result = new Matrix(size);
	   for(int i = 1; i < size; i++)
	   {
		   if(row[i].length() > 0)
			   row[i].moveTo(0);
		   else continue;
		   
		   for(int j = 0; j < row[i].length(); j++)
		   {
			   Entry t = (Entry) row[i].getElement();
			   result.changeEntry(t.column, i, t.value);
			   if(j < row[i].length()-1)
				   row[i].moveNext();
		   }
	   }
	   return result;
   }

   // returns a new Matrix that is the product of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix mult(Matrix M)
   {
	   double r;
	   Matrix result = new Matrix(row.length);
	   Matrix t = M.transpose();
	   
	   if(getSize() != M.getSize())
		   throw new RuntimeException("Matrix is invalid.");
	   
	   for(int i = 1; i < row.length; i++)
	   {
		   for(int j = 1; j < getSize(); j++)
		   {
			   r = dot(row[i], M.row[j]);
			   result.changeEntry(i, j, r);
		   }
	   }
	   return result;
   }

   // Other Functions
   // overrides Object's toString() method
   public String toString()
   {
     String result = "";
     for(int i = 0; i < getSize(); i++)
     {
       if(row[i].length() != 0)
       {
         result += String.valueOf(i) + ": ";
         result += row[i].toString() + "\n";
       }
     }
     return result;
   }

   // Helper function for mul()
   private static double dot(List P, List Q)
   {
	   double result = 0;
	   P.moveTo(0);
	   Q.moveTo(0);
	   
	   while((P.getIndex() >= 0) && (Q.getIndex() >= 0))
	   {
		   Entry pEntry = (Entry) P.getElement();
		   Entry qEntry = (Entry) Q.getElement();
		   
		   if(pEntry.column < qEntry.column)
			   P.moveNext();
		   else if(pEntry.column > qEntry.column)
			   Q.moveNext();
		   else if(pEntry.column == qEntry.column)
		   {
			   result += pEntry.value * qEntry.value;
			   P.moveNext();
			   Q.moveNext();
		   }
	   }
	   return result;	   
   }
}
