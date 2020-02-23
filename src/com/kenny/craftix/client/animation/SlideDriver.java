package com.kenny.craftix.client.animation;

public class SlideDriver extends ValueDriver
{
  @SuppressWarnings("unused")
private float startValue;
  private float endValue;
  private float max = 0.0F;
  private boolean reachedTarget = false;
  
  public SlideDriver(float start, float end, float length) 
  {
    super(length);
    startValue = start;
    endValue = end;
  }
  
  protected float calculateValue(float time)
  {
    if ((!reachedTarget) && (time >= max)) 
    {
      max = time;
      //return Maths.cosInterpolate(startValue, endValue, time);
    }
    reachedTarget = true;
    return endValue;
  }
}
