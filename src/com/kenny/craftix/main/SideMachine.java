package com.kenny.craftix.main;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target( {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface SideMachine 
{
	Side value();
}
