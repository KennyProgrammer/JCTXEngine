package CTXEngine.Core.EventSystem;

public abstract class ClientEvent extends Event
{
	public ClientEvent() 
	{
		super();
	}

	public static final class WindowResizeEvent extends ClientEvent
	{
		private int eWidth;
		private int eHeight;
		
		public WindowResizeEvent(int width, int height) 
		{
			this.eWidth = width;
			this.eHeight = height;
		}
		
		public final int getWidth()
		{
			return eWidth;
		}

		public final int getHeight()
		{
			return eHeight;
		}
		
		public final String getString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("WindowResizeEvent: ").append(this.eWidth).append(", ").append(this.eHeight);
			return ss.toString();
		}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgClient.get();
		}
		
		@Override
		public String getName() 
		{
			return "eOnWindowResize";
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnWindowResize; 
		}
	}
	
	public static final class WindowCloseEvent extends ClientEvent
	{
		public WindowCloseEvent() {}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgClient.get();
		}
		
		@Override
		public String getName() 
		{
			return "eOnWindowClose";
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnWindowClose; 
		}
	}
	
	public static final class TickEvent extends ClientEvent
	{
		public TickEvent() {}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgClient.get();
		}
		
		@Override
		public String getName() 
		{
			return "eOnTick";
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnTick; 
		}
	}
	
	public static final class UpdateEvent extends ClientEvent
	{
		public UpdateEvent() {}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgClient.get();
		}
		
		@Override
		public String getName() 
		{
			return "eOnUpdate";
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnUpdate; 
		}
	}
	
	public static final class RenderEvent extends ClientEvent
	{
		public RenderEvent() {}
		
		@Override
		public int getCategoryFlags() 
		{
			return EventCategory.eCtgClient.get();
		}
		
		@Override
		public String getName() 
		{
			return "eOnRender";
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnRender; 
		}
	}
}
