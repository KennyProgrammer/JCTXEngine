package com.kenny.craftix.utils.data;

public class DataFixManager 
{
	private static void registerFixes(DataFixer fixer)
	{
		//fixer.registerFix(FixTypes.OPTIONS, new ForceVboOn());
		//fixer.registerFix(FixTypes.OPTIONS, new OptionsLanguage());
	}
	
	public static DataFixer createFixer()
	{
		DataFixer datafixer = new DataFixer(0001);
		registerFixes(datafixer);
			return datafixer;
	}
}
