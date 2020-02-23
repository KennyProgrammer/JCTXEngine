package com.kenny.craftix.client;

public class TestClass 
{
	public Craftix cx;
	
	public TestClass()
	{
		
	}
	
	public void testMethod(Craftix craftixIn)
	{
		this.cx = craftixIn;
		
		System.out.println("Work!!" + this.cx.getTitle());
	}
}
