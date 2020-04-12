package CTXEngine.Graphics;

import static CTXEngine.Core.CoreBase.*;
import static CTXEngine.Core.CAndCppOperations.*;

import java.nio.ByteBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import CTXEngine.Graphics.BufferObject.BufferLayout;
import CTXEngine.Graphics.BufferObject.IndexBufferObject;
import CTXEngine.Graphics.BufferObject.ShaderDataType;
import CTXEngine.Graphics.BufferObject.VertexAttribute;
import CTXEngine.Graphics.BufferObject.VertexBufferObject;

/**
* Now I've made an class for rendering various engine
* 2d components. Here will be the most basic points that are needed
* to display objects, such as the creation of various viewable
* matrices and so on. Now this class will be the parent for the
* entire 2d rendering system, because it engine.
*/
public final class RenderEngine2D
{
	/**Static reference to Rectangle2DMesh object.*/
	static Rectangle2D rectangle2D;
	
	/**
	 * A Rectangle 2D mesh defined by X and Y position, width and
	 * height. Basically rectangle mesh will be one on hole 2D render
	 * engine, because for it RenderEngine 2D need only one rectangle.
	 */
	static final class Rectangle2D extends Mesh
	{
		/*Vao mesh data.*/
		public VertexArrayObject  vertexArray;
		/*Vbo mesh data.*/
		public VertexBufferObject vertexBuffer;
		/*Ibo indices mesh data.*/
		public IndexBufferObject  indexBuffer;
		/*Texture Shader for this rectangle.*/
		public Shader             shader;
		/*White texture.*/
		public Texture2D          whiteTexture;
	
		public float v_positions[] = new float[]
		{
			//			   positions          uvs
						 -0.5f,-0.5f,  0,  0.0f, 0.0f,
						  0.5f, -0.5f,  0,  1.0f, 0.0f,
						  0.5f, 0.5f,   0,  1.0f, 1.0f,
						 -0.5f, 0.5f,   0,  0.0f, 1.0f
		};
		public int v_indices[] = new int[] { 0, 1, 2,  0, 2, 3 };
	
		/**
		 * Create rectangle textured object.
		 */
		public final void create()
		{
			this.shader = Shader.create("Resources/Assets/AssetEngine/shaders/GameObject.glsl");
			this.shader.bind();
			this.shader.setUniformInt("u_textureSampler", 0);
			this.shader.setUniformFloat("u_TilingMode", Texture.CTX_TEXTURE_TILING_NONE);
			this.shader.unBind();
			
			this.vertexArray = VertexArrayObject.createReference();
			this.vertexBuffer = VertexBufferObject.createReferenceStatic(this.v_positions, CTX_SIZE_F(this.v_positions));
			this.vertexBuffer.setLayout
			(
				new BufferLayout
				(
					new VertexAttribute(ShaderDataType.t_float3, "v_position"), 
					new VertexAttribute(ShaderDataType.t_float2, "v_uv")
				)
			);
			
			this.vertexArray.putBuffer(this.vertexBuffer);
			this.indexBuffer = IndexBufferObject.createReference(this.v_indices, CTX_SIZE_I(this.v_indices));
			this.vertexArray.putBuffer(this.indexBuffer);
			this.vertexArray.unBind();
			
			this.whiteTexture = Texture2D.create(1, 1);
			ByteBuffer whiteTextureData = null;
			try(MemoryStack stack = MemoryStack.stackPush())
			{
				whiteTextureData = stack.malloc(1);
				whiteTextureData.putInt(0xffffffff);
			}
			this.whiteTexture.setTextureData(whiteTextureData, sizeof("int"));
		}
	};
	
	/**
	 * It will install all our engine for rendering 2d and connect all
	 * the necessary components for it to work in the future.
	 */
	public static void init()
	{
		rectangle2D = new Rectangle2D();
		rectangle2D.create();
	}
	
	public static void destroy()
	{
		rectangle2D = null;
	}
	
	/**
	 * Begin rendering / drawing 2d. This method start to binding
	 * or connect render objects render functionality, like uniforms,
	 * shaders, effects, and after call this all stuff can be
	 * add, and it be rendered properly.
	 */
	public static void begin(final Camera cameraIn)
	{
		rectangle2D.shader.bind();
		rectangle2D.shader.setUniformMat4("u_ViewProjectionMatrix", cameraIn.getViewProjectionMatrix());
	}
	
	/**
	 * End rendering / drawing 2d. This method start to unbinds
	 * or disconnect render objects render functionality, like uniforms,
	 * shaders, effects, and after call this all stuff object not be
	 * rendered.
	 */
	public static void end()
	{
		rectangle2D.shader.unBind();
	}
	
	/**
	 * This event be resize window frame buffers.
	 */
	public static void onWindowResize(int width, int height)
	{
		RenderEngineHelper.setViewport(0, 0, width, height);
	}
	
	/**
	 * This method will be actually render game object structure itself, with one of
	 * draw commands. Basically for render engine 2d its always quads.
	 */
	public static void renderGameObject(final Vector3f position, final float rotation, final Vector2f scale,
		final Texture2D textureIn, final Vector4f tintColour)
	{
		RenderEngineHelper.enableAlphaBlending();
		RenderEngineHelper.disableDepthTesting();
	
		if (textureIn != null) textureIn.bind();
	
		rectangle2D.shader.bind();
	
		if (rotation == 0)
		{
			rectangle2D.shader.setUniformMat4("u_ModelMatrix", MatrixHelper.calculateModelMatrix2D(position, scale));
		} else {
			rectangle2D.shader.setUniformMat4("u_ModelMatrix", MatrixHelper.calculateModelMatrix2D(position, rotation, scale));
		}
	
		rectangle2D.shader.setUniformFloat("u_TilingMode", textureIn.getTiling());
		rectangle2D.shader.setUniformFloat("u_TilingFactor", textureIn.getTilingFactor());
		rectangle2D.shader.setUniformVec4("u_Colour", tintColour);
		rectangle2D.shader.setUniformFloat("u_Scale", scale.x);
		rectangle2D.vertexArray.bind();
		RenderEngineHelper.drawIndices(rectangle2D.vertexArray);
		rectangle2D.vertexArray.unBind();
	
		if (textureIn != null) textureIn.unBind();
	
		RenderEngineHelper.disableAlphaBlending();
		RenderEngineHelper.enableDepthTesting();
	}
	
	/**
	 * This method will be actually render game object structure itself, with one of
	 * draw commands. Basically for render engine 2d its always quads.
	 */
	public static void renderGameObject(final Vector3f position, final float rotation, final Vector2f scale,
		final Texture2D textureIn)
	{
		RenderEngine2D.renderGameObject(position, rotation, scale, textureIn, new Vector4f(1, 1, 1, 1));
	}
	
	/**
	 * This method will be actually render game object structure itself, with one of
	 * draw commands. Basically for render engine 2d its always quads.
	 */
	public static void renderGameObject(final Vector2f position, final float rotation, final Vector2f scale,
		final Texture2D textureIn)
	{
		RenderEngine2D.renderGameObject(new Vector3f(position.x, position.y, 0.0f), rotation, scale,
			textureIn, new Vector4f(1, 1, 1, 1));
	}
	
	/**
	 * This method will be actually render game object structure itself, with one of
	 * draw commands. Basically for render engine 2d its always quads.
	 */
	public static void renderGameObject(final Vector2f position, final float rotation, final Vector2f scale,
		final Vector4f colourIn)
	{
		RenderEngine2D.renderGameObject(new Vector3f(position.x, position.y, 0.0f), rotation, scale, colourIn);
	}
	
	/**
	 * This method will be actually render game object structure itself, with one of
	 * draw commands. Basically for render engine 2d its always quads.
	 */
	public static void renderGameObject(final Vector3f position, final float rotation, final Vector2f scale,
		 final Vector4f colour)
	{
		RenderEngineHelper.enableAlphaBlending();
		RenderEngineHelper.disableDepthTesting();
	
		rectangle2D.whiteTexture.bind();
		rectangle2D.shader.bind();
	
		if (rotation == 0)
		{
			rectangle2D.shader.setUniformMat4("u_ModelMatrix", MatrixHelper.calculateModelMatrix2D(position, scale));
		} else {
			rectangle2D.shader.setUniformMat4("u_ModelMatrix", MatrixHelper.calculateModelMatrix2D(position, rotation, scale));
		}
	
		rectangle2D.shader.setUniformVec4("u_Colour", colour);
		rectangle2D.shader.setUniformFloat("u_Scale", scale.x);
		rectangle2D.vertexArray.bind();
		RenderEngineHelper.drawIndices(rectangle2D.vertexArray);
		rectangle2D.vertexArray.unBind();
		rectangle2D.whiteTexture.unBind();
	
		RenderEngineHelper.disableAlphaBlending();
		RenderEngineHelper.enableDepthTesting();
	}
	
}
