package CTXEngine.Core.EventSystem;

public abstract class MouseEvent extends Event
{
	public MouseEvent() 
	{
		super();
	}
	
	/**
	 *	This event controls the input from mouse when its actually moving.
	 */
	public static final class MouseMoveEvent extends MouseEvent
	{
		/**Event position of mouse on x axis.*/
		private float eMouseX;
		/**Event position of mouse on y axis.*/
		private float eMouseY;
		
		public MouseMoveEvent(float x, float y)
		{
			this.eMouseX = x;
			this.eMouseY = y;
		}
	
		/**
		 *	Return event mouse pos X.
		 */
		public final float getX()
		{
			return this.eMouseX;
		}
		
		/**
		 *	Return event mouse pos Y.
		 */
		public final float getY()
		{
			return this.eMouseY;
		}
	
	
		@Override
		public final String toString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("mouseMoveEvent: ").append(this.eMouseX).append(", ").append(this.eMouseY);
			return ss.toString();
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "eOnMouseMoved";
		}
		
		@Override
		public int getCategoryFlags() 
		{

			return EventCategory.eCtgKeyboard.get() | EventCategory.eCtgInput.get();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnMouseMoved; 
		}	
	};
	
	/**
	 *	This event controls the input from mouse when its actually scrolling.
	 */
	public static final class MouseScrolledEvent extends MouseEvent
	{
		/**Event offset of mouse on x axis.*/
		private float eOffsetX;
		/**Event offset of mouse on x axis.*/
		private float eOffsetY;
		
		public MouseScrolledEvent(float offX, float offY)
		{
			this.eOffsetX = offX;
			this.eOffsetY = offY;
		}
	
		/**
		 *	Return event offset pos X.
		 */
		public final float getOffsetX()
		{
			return this.eOffsetX;
		}
		
		/**
		 *	Return event mouse offset Y.
		 */
		public final float getOffsetY()
		{
			return this.eOffsetY;
		}
	
	
		@Override
		public final String toString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("mouseScroldesEvent: ").append(this.eOffsetX).append(", ").append(this.eOffsetY);
			return ss.toString();
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "eOnMouseScrolled";
		}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgMouse.get() | EventCategory.eCtgInput.get();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnMouseScrolled; 
		}	
	};
	
	/**
	 *	This event controls the input from mouse when use click on button.
	 */
	public static abstract class MouseButtonEvent extends MouseEvent
	{
		/**Button currently pressed, released or hold.*/
		protected int eButton;
		
		public MouseButtonEvent(int buttonIn)
		{
			this.eButton = buttonIn;
		}
		
		/**
		 *	Return the currently used mouse button.
		 */
		public final int getMouseButton()
		{
			return this.eButton;
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
	};
	
	/**
	 *	This event controls the input from mouse when use pressed on button.
	 */
	public static final class MouseButtonPressedEvent extends MouseButtonEvent
	{
		public MouseButtonPressedEvent(int buttonId)
		{
			super(buttonId);
		}
		
		@Override
		public final String toString() 
		{
			StringBuilder ss = new StringBuilder();
			ss.append("mouseButtonPressedEvent: ").append(this.eButton);
			return ss.toString();
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "mouseButtonPressedEvent";
		}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgMouse.get() | EventCategory.eCtgInput.get();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnMouseButtonPressed; 
		}	
	};
	
	/**
	 *	This event controls the input from mouse when use released on button.
	 */
	public static final class MouseButtonReleasedEvent extends MouseButtonEvent
	{
		public MouseButtonReleasedEvent(int buttonId)
		{
			super(buttonId);
		}
		
		@Override
		public final String toString() 
		{
			StringBuilder ss = new StringBuilder();
			ss.append("mouseButtonReleaseEvent: ").append(this.eButton);
			return ss.toString();
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "mouseButtonReleaseEvent";
		}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgMouse.get() | EventCategory.eCtgInput.get();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnMouseButtonReleased; 
		}	
	};
	
	/**
	 *	This event controls the input from mouse when use hold on button.
	 */
	public static final class MouseButtonHoldEvent extends MouseButtonEvent
	{
		public MouseButtonHoldEvent(int buttonId)
		{
			super(buttonId);
		}
		
		@Override
		public final String toString() 
		{
			StringBuilder ss = new StringBuilder();
			ss.append("mouseButtonHoldEvent: ").append(this.eButton);
			return ss.toString();
		}
		
		@Override
		public EventType getEventType()
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "mouseButtonHoldEvent";
		}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgMouse.get() | EventCategory.eCtgInput.get();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnMouseButtonHold; 
		}	
	};
	
}
