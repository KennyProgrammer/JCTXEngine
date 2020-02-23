package com.kenny.craftix.client.animation;

public abstract class ValueDriver
{
  private float currentTime = 0.0F;
  private float length;
  private boolean oneRep = false;
  
  public ValueDriver(float length) 
  {
    this.length = length;
  }
  
  public float update(float delta) 
  {
    currentTime += delta;
    if (currentTime >= length) 
    {
      currentTime %= length;
      oneRep = true;
    }
    float time = currentTime / length;
    return calculateValue(time);
  }
  
  protected abstract float calculateValue(float paramFloat);
  
  public boolean hasCompletedOnePeriod() 
  {
    return oneRep;
  }
}
