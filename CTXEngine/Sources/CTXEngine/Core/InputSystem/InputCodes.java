package CTXEngine.Core.InputSystem;

public class InputCodes 
{
	/*
		This is input codes from GLFW library.
	*/
	
	/* Printable keys */
	public static final int
			CTX_KEY_SPACE      =        32,
			CTX_KEY_APOSTROPHE =        39,  /* ' */
			CTX_KEY_COMMA      =        44,
			CTX_KEY_MINUS      =        45, /* - */
			CTX_KEY_PERIOD     =        46,  /* . */
			CTX_KEY_SLASH      =        47,  /* / */
			CTX_KEY_0          =        48,
			CTX_KEY_1          =        49,
			CTX_KEY_2          =        50,
			CTX_KEY_3          =        51,
			CTX_KEY_4          =        52,
			CTX_KEY_5          =        53,
			CTX_KEY_6          =        54,
			CTX_KEY_7          =        55,
			CTX_KEY_8          =        56,
			CTX_KEY_9          =        57,
			CTX_KEY_SEMICOLON  =        59,  /* ; */
			CTX_KEY_EQUAL      =        61,  /* = */
			CTX_KEY_A          =        65,
			CTX_KEY_B          =        66,
			CTX_KEY_C          =        67,
			CTX_KEY_D          =        68,
			CTX_KEY_E          =        69,
			CTX_KEY_F          =        70,
			CTX_KEY_G          =        71,
			CTX_KEY_H          =        72,
			CTX_KEY_I          =        73,
			CTX_KEY_J          =        74,
			CTX_KEY_K          =        75,
			CTX_KEY_L          =        76,
			CTX_KEY_M          =        77,
			CTX_KEY_N          =        78,
			CTX_KEY_O          =        79,
			CTX_KEY_P          =        80,
			CTX_KEY_Q          =        81,
			CTX_KEY_R          =        82,
			CTX_KEY_S          =        83,
			CTX_KEY_T          =        84,
			CTX_KEY_U          =        85,
			CTX_KEY_V          =        86,
			CTX_KEY_W          =        87,
			CTX_KEY_X          =        88,
			CTX_KEY_Y             =        89,
			CTX_KEY_Z             =        90,
			CTX_KEY_LEFT_BRACKET  =        91, /* [ */
			CTX_KEY_BACKSLASH     =        92,  /* \ */
			CTX_KEY_RIGHT_BRACKET =        93, /* ] */
			CTX_KEY_GRAVE_ACCENT  =        96,  /* ` */
			CTX_KEY_WORLD_1       =        161, /* non-US #1 */
			CTX_KEY_WORLD_2       =        162, /* non-US #2 */
			
			/* Function keys */
			CTX_KEY_ESCAPE        =        256,
			CTX_KEY_ENTER         =        257,
			CTX_KEY_TAB           =        258,
			CTX_KEY_BACKSPACE     =        259,
			CTX_KEY_INSERT        =        260,
			CTX_KEY_DELETE        =        261,
			CTX_KEY_RIGHT         =        262,
			CTX_KEY_LEFT          =        263,
			CTX_KEY_DOWN          =        264,
			CTX_KEY_UP            =        265,
			CTX_KEY_PAGE_UP       =        266,
			CTX_KEY_PAGE_DOWN     =        267,
			CTX_KEY_HOME          =        268,
			CTX_KEY_END           =        269,
			CTX_KEY_CAPS_LOCK     =        280,
			CTX_KEY_SCROLL_LOCK   =        281,
			CTX_KEY_NUM_LOCK      =        282,
			CTX_KEY_PRINT_SCREEN  =        283,
			CTX_KEY_PAUSE      =        284,
			CTX_KEY_F1         =        290,
			CTX_KEY_F2         =        291,
			CTX_KEY_F3         =        292,
			CTX_KEY_F4         =        293,
			CTX_KEY_F5         =        294,
			CTX_KEY_F6         =        295,
			CTX_KEY_F7         =        296,
			CTX_KEY_F8         =        297,
			CTX_KEY_F9         =        298,
			CTX_KEY_F10        =        299,
			CTX_KEY_F11        =        300,
			CTX_KEY_F12        =        301,
			CTX_KEY_F13        =        302,
			CTX_KEY_F14        =        303,
			CTX_KEY_F15        =        304,
			CTX_KEY_F16        =        305,
			CTX_KEY_F17        =        306,
			CTX_KEY_F18        =        307,
			CTX_KEY_F19        =        308,
			CTX_KEY_F20        =        309,
			CTX_KEY_F21        =        310,
			CTX_KEY_F22        =        311,
			CTX_KEY_F23        =        312,
			CTX_KEY_F24        =        313,
			CTX_KEY_F25        =        314,
			CTX_KEY_KP_0       =        320,
			CTX_KEY_KP_1       =        321,
			CTX_KEY_KP_2            =        322,
			CTX_KEY_KP_3            =        323,
			CTX_KEY_KP_4            =        324,
			CTX_KEY_KP_5            =        325,
			CTX_KEY_KP_6            =        326,
			CTX_KEY_KP_7            =        327,
			CTX_KEY_KP_8            =        328,
			CTX_KEY_KP_9            =        329,
			CTX_KEY_KP_DECIMAL      =        330,
			CTX_KEY_KP_DIVIDE       =        331,
			CTX_KEY_KP_MULTIPLY     =        332,
			CTX_KEY_KP_SUBTRACT     =        333,
			CTX_KEY_KP_ADD          =        334,
			CTX_KEY_KP_ENTER        =        335,
			CTX_KEY_KP_EQUAL        =        336,
			CTX_KEY_LEFT_SHIFT      =        340,
			CTX_KEY_LEFT_CONTROL    =        341,
			CTX_KEY_LEFT_ALT        =        342,
			CTX_KEY_LEFT_SUPER      =        343,
			CTX_KEY_RIGHT_SHIFT     =        344,
			CTX_KEY_RIGHT_CONTROL   =        345,
			CTX_KEY_RIGHT_ALT       =        346,
			CTX_KEY_RIGHT_SUPER     =        347,
			CTX_KEY_MENU            =        348,	
			/*! @defgroup buttons Mouse buttons
			 *  @brief Mouse button IDs.
			 *
			 *  See [mouse button input](@ref input_mouse_button) for how these are used.
			 *
			 *  @ingroup input
			 *  @{ */
			CTX_MOUSE_BUTTON_1      =        0,
			CTX_MOUSE_BUTTON_2      =        1,
			CTX_MOUSE_BUTTON_3      =        4,
			CTX_MOUSE_BUTTON_4      =        3,
			CTX_MOUSE_BUTTON_5      =        4,
			CTX_MOUSE_BUTTON_6      =        5,
			CTX_MOUSE_BUTTON_7      =        6,
			CTX_MOUSE_BUTTON_8      =        7,
			CTX_MOUSE_BUTTON_LAST   =        CTX_MOUSE_BUTTON_8,
			CTX_MOUSE_BUTTON_LEFT   =        CTX_MOUSE_BUTTON_1,
			CTX_MOUSE_BUTTON_RIGHT  =        CTX_MOUSE_BUTTON_2,
			CTX_MOUSE_BUTTON_MIDDLE =        CTX_MOUSE_BUTTON_3;
	 /*! @} */
}
