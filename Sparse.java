

import java.io.*;
import java.util.*;

public class Sparse
{
	public static void main(String[] args) throws IOException
	{
		Scanner in;
		PrintWriter out;
		String l;
		int n, a , b = 0;
		int r, c = 0;
		double v = 0;
		Matrix A, B;
		
		if(args.length < 2)
		{
			System.out.println("Unpoper Usage of program.");
			System.exit(1);
		}
		
		in = new Scanner(new File(args[0]));
		out = new PrintWriter(new FileWriter(args[1]));
		
		while(in.hasNextInt())
		{
			n = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			
			A = new Matrix(n);
			B = new Matrix(n);
			
			for(int i = 1; i <=a; i++)
			{
				r = in.nextInt();
				c = in.nextInt();
				v = in.nextDouble();
				A.changeEntry(r, c, v);
			}
			
			for(int j = 1; j <= b; j++)
			{
				r = in.nextInt();
				c = in.nextInt();
				v = in.nextDouble();
				B.changeEntry(r, c, v);
			}
			
			//Prints out A
	        out.println("A has " + A.nnz + " non-zero entries:");
	        out.println(A);

	        //Prints out B
	        out.println("B has " + B.nnz + " non-zero entries:");
	        out.println(B);

	        //Prints out (1.5)*A
	        out.println("(1.5)*A =");
	        out.println(A.scalarMul(1.5));

	        //Prints out A+B  
	        out.println("A+B =");
	        out.println(A.add(B));

	        //Prints out A+A  
	        out.println("A+A =");
	        out.println(A.add(A));

	        //Prints out B-A  
	        out.println("B-A =");
	        out.println(B.sub(A));

	        //Prints out A-A  
	        out.println("A-A =");
	        out.println(A.sub(A));

	        //Prints out Transpose(A)  
	        out.println("Transpose(A) =");
	        out.println(A.transpose());

	        //Prints out A*B  
	        out.println("A*B =");
	        out.println(A.mult(B));

	        //Prints out B*B  
	        out.println("B*B =");
	        out.print(B.mult(B));		
			
	        in.close();
	        out.close();
		}
		
		
		
	}
 }
