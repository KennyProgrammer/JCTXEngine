package CTXEngine.Component;

import static CTXEngine.Core.CAndCppOperations.*;
import static CTXEngine.Core.SimplePrint.*;

import java.nio.ByteBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import CTXEngine.Graphics.BufferObject.BufferLayout;
import CTXEngine.Graphics.BufferObject.IndexBufferObject;
import CTXEngine.Graphics.BufferObject.ShaderDataType;
import CTXEngine.Graphics.BufferObject.VertexAttribute;
import CTXEngine.Graphics.BufferObject.VertexBufferObject;
import CTXEngine.Graphics.RenderEngineAPI;
import CTXEngine.Graphics.RenderEngineHelper;
import CTXEngine.Graphics.Shader;
import CTXEngine.Graphics.Texture2D;
import CTXEngine.Graphics.VertexArrayObject;

public class GameObject
{
	//TODO: Moved to kinda of mesh.
	private static final float[] DEFAULT_VERTEX_BUFFER_DATA = new float[] {  
			 0.5f,  0.5f, 0.0f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
			-0.5f,  0.5f, 0.0f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
			-0.5f, -0.5f, 0.0f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
			 0.5f, -0.5f, 0.0f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f};
	//TODO: Moved to kinda of mesh.
	private static final int[]   DEFAULT_INDEX_BUFFER_DATA =  new int[] {
			0, 1, 2, 2, 3, 1};
	//TODO: Moved to kinda of mesh.
	private static final Shader  DEFAULT_SHADER = Shader.create("Rectangle", "/Assets/AssetEngine/shaders/Rectangle.vert", "/Assets/AssetEngine/shaders/Rectangle.frag");
	
	//basic data
	
	/**This is id of this game object.*/
	private int id = -1;
	/**This is name of this game object.*/
	public String name = "gameobject";
	/**This is tag of this game object.*/
	public String tag = "none";
	/*Check if game object created, and can be drawing.**/
	private boolean created;

	//transformation
	
	/**Position vector of game object on <b>pX, pY, pZ</b> axis.*/
	public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
	/**Rotation vector of game object on <b>rX, rZ, rZ</b> axis.*/
	public Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
	/**Scale vector of game object    on <b>sX, sY, sZ</b> axis.*/
	public Vector3f scale    = new Vector3f(1.0f, 1.0f, 1.0f);
	
	//rendering data
	
	/**This is only Open Gl object, get access to buffers (VBO, IBO).*/
	private VertexArrayObject vertexArray;
	/*Vertex Buffer contains game object vertices data, toogther simulate mesh.**/
	private VertexBufferObject vertexBuffer;
	/**Index Buffer contains game object indices data about how need connect each vertex.*/
	private IndexBufferObject indexBuffer;
	/**Basic shader, can manipulate game object data.*/
	private Shader shader;
	/**Basic texture or white texture by default on game object.*/
	private Texture2D texture;
	
	/**
	 * Default constructor.
	 */
	public GameObject() 
	{
		this.create(null, null, null, null, null);
	}
	
	/**
	 * Default constructor with basic parameters.
	 */
	public GameObject(String name, Vector3f position, Vector3f rotation, Vector3f scale) 
	{
		this.name = name;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		
		this.create(null, null, null, null, null);
	}
	
	/**
	 * Create's new GameObject from input data. After this call object can be
	 * rendered and drown on the screen.
	 */
	public final GameObject create(VertexArrayObject vertexArray, VertexBufferObject vertexBuffer, IndexBufferObject indexBuffer,
			Shader shader, Texture2D texture)
	{
		if(RenderEngineAPI.getAPI().get() == "opengl")
		{
			this.createGL(vertexArray, shader, texture);
		}
		else if(RenderEngineAPI.getAPI().get() == "vulcan")
		{
			this.createVK(vertexBuffer, indexBuffer, shader, texture);
		}
		else if(RenderEngineAPI.getAPI().get() == "directx")
		{
			this.createDX3D(vertexBuffer, indexBuffer, shader, texture);
		}
		
		this.created = true;
		
		return this;
	}
	
	/**
	 * Create Open Gl GameObject data structure.
	 */
	private void createGL(VertexArrayObject vertexArray, Shader shader, Texture2D texture)
	{
		this.vertexArray = vertexArray;
		
		if(this.vertexArray != null)
		{
			this.vertexBuffer = this.vertexArray.getVertexBuffers().get(0);
			this.indexBuffer = this.vertexArray.getIndexBuffers().get(0);
		}
		
		this.shader = shader;
		this.texture = texture;
		
		//checking vertex array 
		//is array is null then create and pull it default mesh data.
		if(this.vertexArray == null)
		{
			CTX_ENGINE_WARN("Vertex Array is null, create by default.");
			
			this.vertexArray = VertexArrayObject.createReference();
			this.vertexBuffer = VertexBufferObject.createReferenceStatic(DEFAULT_VERTEX_BUFFER_DATA, DEFAULT_VERTEX_BUFFER_DATA.length * sizeof("float"));
			this.vertexBuffer.setLayout(new BufferLayout
			(
				new VertexAttribute(ShaderDataType.t_float4, "attrib_Position"),
				new VertexAttribute(ShaderDataType.t_float4, "attrib_Colour"))
			);
			this.vertexArray.putBuffer(this.vertexBuffer);
			this.indexBuffer = IndexBufferObject.createReference(DEFAULT_INDEX_BUFFER_DATA, DEFAULT_INDEX_BUFFER_DATA.length * sizeof("int"), 1);
			this.vertexArray.putBuffer(this.indexBuffer);
			this.vertexArray.unBind();
		}
		
		//checking vertex buffer 
		if(this.vertexBuffer == null || this.vertexBuffer.getData().length == 0)
		{
			CTX_ENGINE_WARN("Vertex Array is null, create by default.");
			
			if(this.vertexBuffer != null) this.vertexBuffer.destroy();
			
			this.vertexBuffer = VertexBufferObject.createReferenceStatic(DEFAULT_VERTEX_BUFFER_DATA,  DEFAULT_VERTEX_BUFFER_DATA.length * sizeof("float"));
			this.vertexBuffer.setLayout(new BufferLayout
			(
				new VertexAttribute(ShaderDataType.t_float4, "attrib_Position"),
				new VertexAttribute(ShaderDataType.t_float4, "attrib_Colour"))
			);
		}
		
		//checking index buffer 
		if(this.indexBuffer == null || this.indexBuffer.getData().length == 0)
		{
			if(this.indexBuffer != null) this.indexBuffer.destroy();
			
			this.indexBuffer = IndexBufferObject.createReference(DEFAULT_INDEX_BUFFER_DATA, DEFAULT_INDEX_BUFFER_DATA.length * sizeof("int"), 1);
		}
		
		//checking shader
		if(this.shader == null)
		{
			this.shader = DEFAULT_SHADER;
		}
		
		//cheking texture
		if(this.texture == null)
		{
			this.texture = Texture2D.create(1, 1);
			ByteBuffer whiteTextureData = BufferUtils.createByteBuffer(1);
			
			whiteTextureData.put((byte) 0xffffffff);
			whiteTextureData.flip();
			this.texture.setTextureData(whiteTextureData, sizeof("int"));
		}
	}

	/**
	 * Create Vulcan API GameObject data structure.
	 */
	private void createVK(VertexBufferObject vertexBuffer, IndexBufferObject indexBuffer, Shader shader,
			Texture2D texture)
	{
		
	}
	
	/**
	 * Create DirectX3D GameObject data structure.
	 */
	private void createDX3D(VertexBufferObject vertexBuffer, IndexBufferObject indexBuffer, Shader shader,
			Texture2D texture)
	{
		
	}
	
	public final void draw()
	{
		this.shader.bind();
		this.vertexArray.bind();
		RenderEngineHelper.drawIndices(this.vertexArray);
		this.vertexArray.unBind();
		this.shader.unBind();
	}
	
	/**
	 * Return a integeral id of this game object.
	 */
	public int getId() 
	{
		return this.id;
	}
	
	/**
	 * Return true if this game object is created, and can be drown
	 * on the screen.
	 */
	public boolean isCreated() 
	{
		return this.created;
	}

}
