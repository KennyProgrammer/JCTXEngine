package CTXEngine.Core.EventSystem;

import static CTXEngine.Core.CoreBase.BIT;

import CTXEngine.Core.EventSystem.ClientEvent.RenderEvent;
import CTXEngine.Core.EventSystem.ClientEvent.TickEvent;
import CTXEngine.Core.EventSystem.ClientEvent.UpdateEvent;
import CTXEngine.Core.EventSystem.ClientEvent.WindowCloseEvent;
import CTXEngine.Core.EventSystem.ClientEvent.WindowResizeEvent;
import CTXEngine.Core.EventSystem.KeyEvent.KeyPressedEvent;
import CTXEngine.Core.EventSystem.KeyEvent.KeyReleasedEvent;
import CTXEngine.Core.EventSystem.KeyEvent.KeyTypedEvent;
import CTXEngine.Core.EventSystem.MouseEvent.MouseButtonHoldEvent;
import CTXEngine.Core.EventSystem.MouseEvent.MouseButtonPressedEvent;
import CTXEngine.Core.EventSystem.MouseEvent.MouseButtonReleasedEvent;
import CTXEngine.Core.EventSystem.MouseEvent.MouseMoveEvent;
import CTXEngine.Core.EventSystem.MouseEvent.MouseScrolledEvent;

/**
 * This is main class for all events in this game engine.
 */
public abstract class Event 
{
	/**
	 * This class enum will be control the event from the engine. Event will be
	 * occurs immediately on runtime and dead there.
	 */
	public static enum EventType
	{
		/**None by default, means nothing is happen.*/
		eNone(0),
		/**Runs when game window is closed.*/
		eOnWindowClose(1),
		/**Runs when game window is starts to resize.*/
		eOnWindowResize(2),
		/**Runs when game window is starts to move.*/
		eOnWindowMove(3),
		/**Runs when mouse focused on game window.*/
		eOnWindowFocus(4),
		/**Runs when mouse lose focus from game window.*/
		eOnWindowLostFocus(5),
		/**Runs when game passed one tick.*/
		eOnTick(6),
		/**Use when something renders on the game window.*/
		eOnRender(7),
		/**Use when something updates on the game window.*/
		eOnUpdate(8),
		/**Runs when user press on key.*/
		eOnKeyPressed(9),
		/**Runs when user release on key.*/
		eOnKeyReleased(10),
		/**Runs when user hold on key.*/
		eOnKeyHold(11),
		/**Runs when user press on mouse button.*/
		eOnMouseButtonPressed(12),
		/**Runs when user release on mouse button.*/
		eOnMouseButtonReleased(13),
		/**Runs when user hold on mouse button.*/
		eOnMouseButtonHold(14),
		/**Runs when user scroll on mouse.*/
		eOnMouseScrolled(15),
		/**Runs when user move this mouse.*/
		eOnMouseMoved(16),
		/**Runs when user move this mouse.*/
		eOnKeyTyped(17);
		
		int id;
		
		private EventType(int idIn) 
		{
			this.id = idIn;
		}
		
		int get()
		{
			return this.id;
		}
		
	};
	
	public static enum EventCategory
	{
		/**Means that event has not category's.*/
		eCtgNone((byte) 0),
		/**Means that event has part of client side of this engine.*/
		eCtgClient(BIT((byte) 0)),
		/**Means that event has part of input.*/
		eCtgInput(BIT((byte) 1)),
		/**Means that event has part of keyboard.*/
		eCtgKeyboard(BIT((byte) 2)),
		/**Means that event has part of mouse.*/
		eCtgMouse(BIT((byte) 3)),
		/**Means that event has part of mouse button.*/
		eCtgMouseButton(BIT((byte) 4));
		
		byte bit;
	
		private EventCategory(byte bit) 
		{
			this.bit = bit;
		}
		
		byte get()
		{
			return this.bit;
		}
	};
	
	public boolean eHandled = false;
	
	/**
	 * Return the EventType class.
	 */
	public abstract EventType getEventType();
	
	/**
	 * Return name of current event class.
	 */
	public abstract String getName();
	
	/**
	 * Return the flag of selected ctg.
	 */
	public abstract int getCategoryFlags();
	
	/**
	 *	Return true if user in some category, else false.
	 */
	public int isInCategory(EventCategory categoryIn)
	{
		return this.getCategoryFlags() & categoryIn.get();
	}
	
	/*
	 * 
	 */
	public static EventType getStaticType() 
	{ 
		return EventType.eNone;
	}
	
	/**
	 *	Return the name of this event in java string.
	 */
	public String toString()
	{
		return this.getName();
	};
	
	/**
	 *	Event dispatcher is just helping class to control and send events.
	 */
	public static class EventDispatcher
	{
		private Event eEvent;
		
		/**
		* Basic constructor for EventDispatcher. Input a Event abstract/virtual
		* class and set it to this Event.
		*/
		public EventDispatcher(Event eventIn) 
		{
			this.eEvent = eventIn;
		}
		
		/**
		 *	This actually dispatcher function, input a EventFn aka function and
		 *	set handled or not if input func is handled.
		 *
		 *  MAY BE ERRORS OR INSTABLE WORK!
		 */
		public <T extends Event> boolean dispatch(final T func)
		{
			if(eEvent.getEventType() == T.getStaticType())
			{
				eEvent.eHandled = func.eHandled;
				return true;
			}
			
			//if (eEvent.getEventType() == T.getStaticType())
			//{
			//	eEvent.eHandled = func((eEvent));
			//	return true;
			//}
			return false;
		}
		
		
	}
	
	/**
	 * This static event data value needs to save data from actually api callbacks. When
	 * callback be called from window class, data be rewrited. 
	 */
	public static final class EventData
	{
		public static int WindowResizeEvent_eWidth;
		public static int WindowResizeEvent_eHeight;
		public static int WindowCloseEvent_eIsCloseRequested;
		public static int TickEvent_tick;
		public static int UpdateEvent_isUpdate;
		public static int RenderEvent_isRender;
		public static int KeyPressedEvent_keyId;
		public static int KeyPressedEvent_repeatCount;
		public static int KeyReleasedEvent_keyId;
		public static int KeyReleasedEvent_repeatCount;
		public static int KeyHoldEvent_keyId;
		public static int KeyHoldEvent_repeatCount;
		public static int KeyTypedEvent_keyId;
		public static int KeyTypedEvent_repeatCount;
		public static int MouseButtonPressedEvent_buttonId;
		public static int MouseButtonReleasedEvent_buttonId;
		public static int MouseButtonHoldEvent_buttonId;		
		public static float MouseMoveEvent_eMouseX;
		public static float MouseMoveEvent_eMouseY;
		public static float MouseScrolledEvent_eOffsetX;
		public static float MouseScrolledEvent_eOffsetY;
		
		public void callback(WindowResizeEvent e)
		{
			WindowResizeEvent_eWidth = e.getWidth();
			WindowResizeEvent_eHeight = e.getWidth();
		}
		
		public void callback(KeyPressedEvent e)
		{
			KeyPressedEvent_keyId = e.getKeyId();
			KeyPressedEvent_repeatCount = e.getRepeatCount();
		}
		
		public void callback(KeyReleasedEvent e)
		{
			KeyReleasedEvent_keyId = e.getKeyId();
			KeyReleasedEvent_repeatCount = e.getKeyId();
		}
		
		public void callback(KeyTypedEvent e)
		{
			KeyTypedEvent_keyId = e.getKeyId();
			KeyTypedEvent_repeatCount = e.getKeyId();
		}
		
		public void callback(MouseScrolledEvent e)
		{
			MouseScrolledEvent_eOffsetX = e.getOffsetX();
			MouseScrolledEvent_eOffsetY = e.getOffsetY();
		}
		
		public void callback(MouseMoveEvent e)
		{
			MouseMoveEvent_eMouseX = e.getX();
			MouseMoveEvent_eMouseY = e.getY();
		}
		
		public void callback(MouseButtonPressedEvent e)
		{
			MouseButtonPressedEvent_buttonId = e.getMouseButton();
		}
		
		public void callback(MouseButtonReleasedEvent e)
		{
			MouseButtonReleasedEvent_buttonId = e.getMouseButton();
		}
		
		public void callback(MouseButtonHoldEvent e)
		{
			MouseButtonHoldEvent_buttonId = e.getMouseButton();
		}
		
		public void callback(WindowCloseEvent e)
		{
			WindowCloseEvent_eIsCloseRequested = e.getCategoryFlags();
		}
		
		public void callback(TickEvent e)
		{
			TickEvent_tick = e.getCategoryFlags();
		}
		
		public void callback(UpdateEvent e)
		{
			UpdateEvent_isUpdate = e.getCategoryFlags();
		}
		
		public void callback(RenderEvent e)
		{
			RenderEvent_isRender = e.getCategoryFlags();
		}
	}
}
