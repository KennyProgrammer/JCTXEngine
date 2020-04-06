package Craftix.Scenes;

import static CTXEngine.Core.InputSystem.InputCodes.*;
import static CTXEngine.Core.SimplePrint.*;

import static CTXEngine.Core.PrimitiveSizes.*;

import org.lwjgl.system.NonnullDefault;
import CTXEngine.Core.Delta;
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

public class ExampleScene extends Scene
{
	/**Vertex Array Object.*/
	public VertexArrayObject vertexArray;
	/**Vertex Buffer Object.*/
	public VertexBufferObject vertexBuffer;
	/**Index Buffer Object.*/
	public IndexBufferObject indexBuffer;
	
	int vao, vbo, ibo;
	
	float[] vertices = new float[]
			{
					0.5f, 0.5f, 0.0f,
					-0.5f, 0.5f, 0.0f,
					-0.5f, -0.5f, 0.0f,
					0.5f, -0.5f, 0.0f
			};
	
	int[] indices = new int[]
			{
				0, 1, 2, 0, 2, 3	
			}; 
	
	/**
	 * Default constructor.
	 */
	public ExampleScene() { super(); }
	
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
			new VertexAttribute(ShaderDataType.t_float3, "v_position"))
		);
		this.vertexArray.putBuffer(this.vertexBuffer);
		this.indexBuffer = IndexBufferObject.createReference(this.indices, this.indices.length * sizeof("int"), 1);
		this.vertexArray.putBuffer(this.indexBuffer);
		this.vertexArray.unBind();
	
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
	public Scene onUpdate(Delta sceneTimeIn) 
	{
		if(Input.isButtonPressed(0))       CTX_ENGINE_INFO("Mouse button 0 is pressed!");
		
		if(Input.isKeyPressed(CTX_KEY_A))  CTX_ENGINE_INFO("Key A is pressed!");

		if(Input.isKeyPressed(CTX_KEY_D))  CTX_ENGINE_INFO("Key D is pressed!");
	
		
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
			this.vertexArray.bind();
			RenderEngineHelper.drawIndices(this.vertexArray);
			this.vertexArray.unBind();
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

	/**
	 * Simple test draw quad function.
	 * 
	 * @param vao - its packed data, position, and etc.
	 * @param indices - indexes to connect vertices in right order.
	 */
	public void drawQuad(int vao, int... indices)
	{
		//glBindVertexArray(this.vaoList);
		//glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_INT, 0);
		//glBindVertexArray(this.vaoList);
	}
}
