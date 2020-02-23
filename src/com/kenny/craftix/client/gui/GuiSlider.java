package com.kenny.craftix.client.gui;

import org.lwjgl.input.Mouse;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.utils.math.Maths;

public class GuiSlider extends GuiButton
{
	private int id;
	private float slider;
	private float maxValue;
	private float minValue;
	public boolean dragging;
	private InGameSettings.Options options;
	
	public GuiSlider(int id, String fileIn, float x, float y, InGameSettings.Options optionId, String name)
	{
		this(id, fileIn, x, y, optionId, 0.0f, 1.0f, name);
	}
	
	public GuiSlider(int id, String fileIn, float x, float y, InGameSettings.Options optionId, float minValueIn, float maxValueIn, String name) 
	{
		super(id, fileIn, x, y, 4, name);
		this.slider = 1.0f;
		this.options = optionId;
		this.maxValue = maxValueIn;
		this.minValue = minValueIn;
		Craftix craftix = Craftix.getCraftixInstance();
		this.slider = optionId.normalizeValue(craftix.inGameSettings.getOptionFloatValue(optionId));
	}
	
	public void inscreaseSlider()
	{
		if(!(this.slider < this.minValue) && !(this.slider > this.maxValue))
		{
			this.slider += 0.1f;
			if(this.slider > this.maxValue)
			{
				this.slider = this.maxValue;
			}
			if(this.slider < this.minValue)
			{
				this.slider = this.minValue;
			}
		}
	}
	
	public void descreaseSlider()
	{
		if(!(this.slider < this.minValue) && !(this.slider > this.maxValue))
		{
			this.slider -= 0.1f;
			if(this.slider > this.maxValue)
			{
				this.slider = this.maxValue;
			}
			if(this.slider < this.minValue)
			{
				this.slider = this.minValue;
			}
		}
	}
	
	@Override
	protected void mouseDragged(Craftix cx) 
	{
		if(this.visible)
		{
			if(this.dragging)
			{
				this.slider = (float)(Mouse.getX() - (this.x + 0.04f)) / (float)(this.width - 0.08f);
				this.slider = Maths.clamp(this.slider, 0.0f, 1.0f);
				float f = this.options.denormalizeValue(this.slider);
				//InGameSettings.setOptionFloatValue(this.options, f);
				this.slider = this.options.normalizeValue(f);
			}
		}
	}
	
	@Override
	public boolean checkHover(Craftix cx) 
	{
		if(super.checkHover(cx))
		{
			this.slider = (Mouse.getX() - (this.x + 0.04f) / (float)(this.width - 0.08f));
			this.slider = Maths.clamp(this.slider, 0.0f, 1.0f);
			//InGameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.slider));
			this.dragging = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void onRelease(GuiButton button) 
	{
		this.dragging = false;
	}

	public float getMaxValue() 
	{
		return maxValue;
	}
	
	public float getMinValue()
	{
		return this.minValue;
	}
	
	public int getId()
	{
		return this.id;
	}

	public float getSlider() 
	{
		return slider;
	}

	public void setSlider(float slider) 
	{
		this.slider = slider;
	}
}
