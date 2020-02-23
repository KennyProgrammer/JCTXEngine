package com.kenny.craftix.utils.color;

import java.awt.Color;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public class Colour
{
	  private Vector3f col = new Vector3f();
	  private float a = 1.0F;
	  
	  public Colour(float r, float g, float b)
	  {
	    this.col.set(r, g, b);
	  }
	  
	  public Colour(Vector3f colour)
	  {
	    this.col.set(colour);
	  }
	  
	  public Colour(float r, float g, float b, float a)
	  {
	    this.col.set(r, g, b);
	    this.a = a;
	  }
	  
	  public Colour(float r, float g, float b, boolean convert)
	  {
	    if (convert) {
	      this.col.set(r / 255.0F, g / 255.0F, b / 255.0F);
	    } else {
	      this.col.set(r, g, b);
	    }
	  }
	  
	  public Colour() {}
	  
	  public Vector3f getVector()
	  {
	    return this.col;
	  }
	  
	  public FloatBuffer getAsFloatBuffer()
	  {
	    FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
	    buffer.put(new float[] { this.col.x, this.col.y, this.col.z, this.a });
	    buffer.flip();
	    return buffer;
	  }
	  
	  public float getR()
	  {
	    return this.col.x;
	  }
	  
	  public float getG()
	  {
	    return this.col.y;
	  }
	  
	  public float getB()
	  {
	    return this.col.z;
	  }
	  
	  public Colour duplicate()
	  {
	    return new Colour(this.col.x, this.col.y, this.col.z);
	  }
	  
	  public void multiplyBy(Colour colour)
	  {
	    this.col.x *= colour.col.x;
	    this.col.y *= colour.col.y;
	    this.col.z *= colour.col.z;
	  }
	  
	  public void setColour(float r, float g, float b)
	  {
	    this.col.set(r, g, b);
	  }
	  
	  public void setColour(Vector3f colour)
	  {
	    this.col.set(colour);
	  }
	  
	  public void setColour(Colour colour)
	  {
	    this.col.set(colour.col);
	  }
	  
	  public void setColour(float r, float g, float b, float a)
	  {
	    this.col.set(r, g, b);
	    this.a = a;
	  }
	  
	  public void setR(float r)
	  {
	    this.col.x = r;
	  }
	  
	  public void setG(float g)
	  {
	    this.col.y = g;
	  }
	  
	  public void setB(float b)
	  {
	    this.col.z = b;
	  }
	  
	  public boolean isEqualTo(Colour colour)
	  {
	    return (this.col.x == colour.col.x) && (this.col.y == colour.col.y) && (this.col.z == colour.col.z);
	  }
	  
	  public Colour scale(float value)
	  {
	    this.col.scale(value);
	    return this;
	  }
	  
	  public String toString()
	  {
	    return "(" + this.col.x + ", " + this.col.y + ", " + this.col.z + ")";
	  }
	  
	  public static Colour sub(Colour colLeft, Colour colRight, Colour dest)
	  {
	    if (dest == null) {
	      return new Colour(Vector3f.sub(colLeft.col, colRight.col, null));
	    }
	    Vector3f.sub(colLeft.col, colRight.col, dest.col);
	    return dest;
	  }
	  
	  public static float calculateDifference(Colour colA, Colour colB)
	  {
	    return sub(colB, colA, null).length();
	  }
	  
	  public Colour getUnit()
	  {
	    Colour colour = new Colour();
	    if ((this.col.x == 0.0F) && (this.col.y == 0.0F) && (this.col.z == 0.0F)) {
	      return colour;
	    }
	    colour.setColour(this);
	    colour.scale(1.0F / length());
	    return colour;
	  }
	  
	  public float length()
	  {
	    return (float)Math.sqrt(lengthSquared());
	  }
	  
	  public float lengthSquared()
	  {
	    return this.col.lengthSquared();
	  }
	  
	  public void setColour(HsvColour hsv)
	  {
	    setColour(hsvToRgb(hsv.getHue(), hsv.getSaturation(), hsv.getValue()));
	  }
	  
	  public void setHsvColour(float hue, float sat, float value)
	  {
	    setColour(hsvToRgb(hue, sat, value));
	  }
	  
	  public HsvColour getHsvColour()
	  {
	    float[] hsv = new float[3];
	    Color.RGBtoHSB((int)(this.col.x * 255.0F), (int)(this.col.y * 255.0F), (int)(this.col.z * 255.0F), hsv);
	    return new HsvColour(hsv[0], hsv[1], hsv[2]);
	  }
	  
	  public static Colour hsvToRgb(float hue, float saturation, float value)
	  {
	    int h = (int)(hue * 6.0F);
	    float f = hue * 6.0F - h;
	    float p = value * (1.0F - saturation);
	    float q = value * (1.0F - f * saturation);
	    float t = value * (1.0F - (1.0F - f) * saturation);
	    switch (h)
	    {
	    case 0: 
	      return new Colour(value, t, p);
	    case 1: 
	      return new Colour(q, value, p);
	    case 2: 
	      return new Colour(p, value, t);
	    case 3: 
	      return new Colour(p, q, value);
	    case 4: 
	      return new Colour(t, p, value);
	    case 5: 
	      return new Colour(value, p, q);
	    }
	    return new Colour();
	  }
	  
	  public static Colour interpolateColours(Colour colour1, Colour colour2, float blend, Colour dest)
	  {
	    float colour1Weight = 1.0F - blend;
	    float r = colour1Weight * colour1.col.x + blend * colour2.col.x;
	    float g = colour1Weight * colour1.col.y + blend * colour2.col.y;
	    float b = colour1Weight * colour1.col.z + blend * colour2.col.z;
	    if (dest == null) {
	      return new Colour(r, g, b);
	    }
	    dest.setColour(r, g, b);
	    return dest;
	  }
	  
	  public static Colour add(Colour colour1, Colour colour2, Colour dest)
	  {
	    if (dest == null) {
	      return new Colour(Vector3f.add(colour1.col, colour2.col, null));
	    }
	    Vector3f.add(colour1.col, colour2.col, dest.col);
	    return dest;
	  }
}
