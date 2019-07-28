package com.learningjava;

public class Test {

	public static void main(String[] args) {
		try {
			//badMethod();
			TestPrimitiveTypeCasting();
			System.out.print("A");
		}
		catch (Exception ex)
		{
			System.out.print("B");
		}
		finally 
		{
			System.out.print("C");
		}
		System.out.print("D");
	
	}
	
	public static void badMethod()
	{
		throw new Error();
	}
	
	public static void TestPrimitiveTypeCasting()
	{
		byte b = (byte)261;
		System.out.println(b);
		System.out.println(Integer.toBinaryString(b));
		System.out.println(Integer.toBinaryString(261));
	}
}
