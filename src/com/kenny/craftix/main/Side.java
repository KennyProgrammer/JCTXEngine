package com.kenny.craftix.main;

public enum Side
{
    CLIENT,
    SERVER,
	EDITOR;

    public boolean isServer()
    {
        return !this.isClient() && !this.isEditor();
    }

    public boolean isClient()
    {
        return this == CLIENT;
    }
    
    public boolean isEditor()
    {
    	return this == EDITOR;
    }
}