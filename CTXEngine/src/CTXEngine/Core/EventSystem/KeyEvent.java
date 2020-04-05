package CTXEngine.Core.EventSystem;

public abstract class KeyEvent extends Event
{	
	/**Id of the key.*/
	protected int keyId;
	
	public KeyEvent(int keyIdIn) 
	{
		this.keyId = keyIdIn;
	}
	
	@Override
	public int getCategoryFlags() 
	{
		return EventCategory.eCtgClient.get() | EventCategory.eCtgInput.get();
	}
	
	public final int getKeyId() 
	{
		return this.keyId;
	}
	
	public static final class KeyPressedEvent extends KeyEvent
	{
		private int eRepeatCount;	
		
		/**
		 * Basic constructor for KeyPressedEvent with id and count need to
		 * repeat.
		 */
		public KeyPressedEvent(int keyIdIn, int repeatCountIn) 
		{
			super(keyIdIn);
			this.eRepeatCount = repeatCountIn;
		}
		
		/*
			Return the repeat event count.
		*/
		public final int getRepeatCount()
		{
			return this.eRepeatCount;
		}
		
		@Override
		public final String toString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("keyPressedEvent: ").append(this.keyId).append(" (").append(this.eRepeatCount).append(" reapets)");
			return ss.toString();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnKeyPressed; 
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "eOnKeyPressed";
		}
	};
	
	public static final class KeyReleasedEvent extends KeyEvent
	{
		public KeyReleasedEvent(int keyIdIn) 
		{
			super(keyIdIn);
		}
		
		@Override
		public final String toString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("keyReleasedEvent: ").append(this.keyId);
			return ss.toString();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnKeyReleased; 
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "eOnKeyReleased";
		}
		
	};
	
	public static final class KeyTypedEvent extends KeyEvent
	{
		public KeyTypedEvent(int keyIdIn) 
		{
			super(keyIdIn);
		}
		
		@Override
		public final String toString()
		{
			StringBuilder ss = new StringBuilder();
			ss.append("keyTypedEvent: ").append(this.keyId);
			return ss.toString();
		}
		
		public static EventType getStaticType() 
		{ 
			return EventType.eOnKeyTyped; 
		}
		
		@Override
		public EventType getEventType() 
		{
			return getStaticType();
		}
		
		@Override
		public String getName() 
		{
			return "eOnKeyTyped";
		}
		
	}
}
