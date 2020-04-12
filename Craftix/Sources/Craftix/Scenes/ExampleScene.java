package Craftix.Scenes;

import static CTXEngine.Core.InputSystem.InputCodes.*;
import static CTXEngine.Core.CAndCppOperations.*;

import org.joml.Vector4f;
import org.lwjgl.system.NonnullDefault;

import CTXEngine.Component.GameObject;
import CTXEngine.Core.Scene;
import CTXEngine.Core.EventSystem.Event;
import CTXEngine.Core.InputSystem.Input;
import CTXEngine.Core.LayerSystem.Layer;
import CTXEngine.Graphics.VertexArrayObject;
import CTXEngine.Graphics.BufferObject.BufferLayout;
import CTXEngine.Graphics.BufferObject.IndexBufferObject;
import CTXEngine.Graphics.BufferObject.ShaderDataType;
import CTXEngine.Graphics.BufferObject.VertexBufferObject;
import CTXEngine.Graphics.BufferObject.VertexAttribute;
import CTXEngine.Graphics.RenderEngineHelper;
import CTXEngine.Graphics.Shader;
import CTXEngine.Graphics.Texture2D;

public class ExampleScene extends Scene
{
	/**Vertex Array Object.*/
	public VertexArrayObject vertexArray;
	/**Vertex Buffer Object.*/
	public VertexBufferObject vertexBuffer;
	/**Index Buffer Object.*/
	public IndexBufferObject indexBuffer;
	/**Test Shader.*/
	public Shader shader;
	/**Test Texture.*/
	public Texture2D whiteTexture;
	/**Colour of rectangle.*/
	public Vector4f colour;
	
	public GameObject rectangle;
	
	float[] vertices = new float[]
			{
			     //  position,                 colour
				 0.5f,  0.5f, 0.0f,  1.0f, 1.0f, 1.0f, 1.0f,
				-0.5f,  0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f,
				-0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 1.0f, 1.0f,
				 0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 1.0f, 1.0f
			};
	
	int[] indices = new int[]
			{
				0, 1, 2, 0, 2, 3	
			};

	
	/**
	 * Default constructor.
	 */
	public ExampleScene() 
	{ 
		super();
		this.colour = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);
	}
	
	/**
	 * Load this scene in to memory witch initialization other game objectsand 
	 * components.
	 */
	@Override
	public void load() 
	{
		super.load();
		
		//Simple Quad Mesh
		this.vertexArray = VertexArrayObject.createReference();
		this.vertexBuffer = VertexBufferObject.createReferenceStatic(this.vertices, this.vertices.length * sizeof("float"));
		this.vertexBuffer.setLayout(new BufferLayout
		(
			new VertexAttribute(ShaderDataType.t_float3, "attrib_Position"),
			new VertexAttribute(ShaderDataType.t_float4, "attrib_Colour"  )
		   // new VertexAttribute(ShaderDataType.t_float2, "attrib_UvCoord" ),
		    //new VertexAttribute(ShaderDataType.t_float,  "attrib_UvIndex" )
		));
		this.vertexArray.putBuffer(this.vertexBuffer);
		this.indexBuffer = IndexBufferObject.createReference(this.indices, this.indices.length * sizeof("int"), 1);
		this.vertexArray.putBuffer(this.indexBuffer);
		this.vertexArray.unBind();
		
		//this.whiteTexture = Texture2D.create(1, 1);
		//int whiteTextureData = 0xffffffff; //
		//this.whiteTexture.setTextureData(whiteTextureData, sizeof("int"));
		
		this.shader = Shader.create("Rectangle","/Assets/AssetEngine/shaders/Rectangle.vert", "/Assets/AssetEngine/shaders/Rectangle.frag");
		//this.texture = Texture2D.create("Resources/Assets/AssetEngine/textures/icon_hunger.png");
		
		this.rectangle = new GameObject();
		this.rectangle.create(this.vertexArray, this.vertexBuffer, this.indexBuffer, this.shader, null);
	
		this.initialized = true;
	}
	
	/**
	 * Unload this scene from memory i.e destroy all components and objects.
	 */
	@Override
	public void unload() 
	{
		super.unload();
		
		this.initialized = false;
	}
	
	/**
	 * This event calls global (while loop), i.e updates scene.
	 */
	@Override
	@NonnullDefault
	protected Scene updateScene() 
	{
		return this;
	}

	/**
	 * This event calls every frame (while loop), i.e updates scene.
	 */
	@Override
	public Scene onUpdate(float delta) 
	{
		if(Input.isKeyPressed(CTX_KEY_R))
		{
			if(this.colour.x <= 1f)
				this.colour.x += 0.1f * delta;
			else this.colour.x = 0f;
		}
		
		if(Input.isKeyPressed(CTX_KEY_G))
		{
			if(this.colour.y <= 1f)
				this.colour.y += 0.1f * delta;
			else this.colour.y = 0f;
		}
		
		if(Input.isKeyPressed(CTX_KEY_B))
		{
			if(this.colour.z <= 1f)
				this.colour.z += 0.1f * delta;
			else this.colour.z = 0f;
		}
		
		if(Input.isKeyPressed(CTX_KEY_A))
		{
			if(this.colour.w <= 1f)
				this.colour.w += 0.1f * delta;
			else this.colour.w = 0f;
		}
		
		return this;
	}

	/**
	 *	This event calls every frame where need render some object. 
	 */
	@Override
	protected Scene onRender() 
	{
		//===== Start Clearing PrevFrame ===== 
		{
			RenderEngineHelper.clearColor(0.2f, 0.2f, 0.5f, 1.0f);
			RenderEngineHelper.clear();
		}

		//===== Begin Render World =====
		{
			this.rectangle.draw();
			return this;
		}
	}

	/**
	 * This event handle all input from current window system i.e form mouse, 
	 * keyboard, joystick.
	 */
	@Override
	protected Scene onHandleInput() 
	{
		return this;
	}

	@Override
	@Deprecated
	public void pushLayer(Layer layerIn) {}

	@Override
	@Deprecated
	public void pushOverlay(Layer layerIn) {}

	@Override
	@Deprecated
	public void pollStack(Event eventIn) {}
}
