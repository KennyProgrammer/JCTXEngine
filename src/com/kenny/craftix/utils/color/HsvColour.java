package com.kenny.craftix.utils.color;

public class HsvColour
{
	  private float hue;
	  private float saturation;
	  private float value;
	  
	  public HsvColour(float hue, float saturation, float value)
	  {
	    this.hue = hue;
	    this.saturation = saturation;
	    this.value = value;
	  }
	  
	  public float getHue()
	  {
	    return this.hue;
	  }
	  
	  public void setHue(float hue)
	  {
	    this.hue = hue;
	  }
	  
	  public float getSaturation()
	  {
	    return this.saturation;
	  }
	  
	  public void setSaturation(float saturation)
	  {
	    this.saturation = saturation;
	  }
	  
	  public float getValue()
	  {
	    return this.value;
	  }
	  
	  public void setValue(float value)
	  {
	    this.value = value;
	  }
}
