package CTXEngine.Graphics;

import org.joml.Matrix4f;


/**
* Now I've made an class for rendering various engine
* 3d components. Here will be the most basic points that are needed
* to display objects, such as the creation of various viewable
* matrices and so on. Now this class will be the parent for the
* entire rendering system, because it engine.
*/
public final class RenderEngine 
{
	public static class SceneData_tmp
	{
		public Matrix4f viewProjectionMatrix = new Matrix4f();
	};
	
	private static RenderEngine instance;
	private static RenderEngine.SceneData_tmp sceneData_tmp = new SceneData_tmp();
	
	public RenderEngine() { RenderEngine.instance = this; }
	
	/**
	 * It will install all our engine for rendering and connect all
	 * the necessary components for it to work in the future.
	 */
	public static void init()
	{
		RenderEngineHelper.init();
		//RenderEngine2D.init();
	}
	
	/**
	 * Destroy render engine.
	 */
	public static void destroy() {}
	
	/**
	 * This event be resize window frame buffers.
	 */
	public static void onWindowResize(int width, int height)
	{
		RenderEngineHelper.setViewport(0, 0, width, height);
	}
	
	/**
	 * Begin rendering / drawing the scene. This method start to binding
	 * or connect render objects render functionality, like uniforms,
	 * shaders, effects, and after call this all stuff can be
	 * add, and it be rendered properly.	
	 */
	public static void begin(Camera cameraIn)
	{
		sceneData_tmp.viewProjectionMatrix = cameraIn.getViewProjectionMatrix();
	}
	
	/**
	 * End rendering / drawing the scene. This method start to unbinds
	 * or disconnect render objects render functionality, like uniforms,
	 * shaders, effects, and after call this all stuff object not be
	 * rendered.
	 */
	public static void end()
	{
		
	}
	
	
	/**
	 * Render GameObject / Mesh / Material into the scene.
	 */
	public static void renderGameObject(final Shader shaderIn, final VertexArrayObject gameObjectVao, 
			final Matrix4f modelMatrix)
	{
		shaderIn.bind();
		shaderIn.setUniformMat4("u_ViewProjectionMatrix", sceneData_tmp.viewProjectionMatrix); // cast to GLShader
		shaderIn.setUniformMat4("u_ModelMatrix", modelMatrix); // cast to GLShader
		gameObjectVao.bind();
		RenderEngineHelper.drawIndices(gameObjectVao);
		gameObjectVao.unBind();
	}
	
	/**
	 * Return instance of RenderEngine class.
	 */
	public static final RenderEngine get()
	{
		return RenderEngine.instance;
	}
	
	/**
	 * Return RenderEngineAPI static instance.
	 */
	public static RenderEngineAPI.Api getAPI()
	{
		return RenderEngineAPI.getAPI();
	}
}
