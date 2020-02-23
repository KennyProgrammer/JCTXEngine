package com.kenny.craftix.client.renderer;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

import com.kenny.craftix.client.Craftix;
//import com.kenny.craftix.client.font.GuiEngineText;

/**
 * This class help check error if happens on opengl.
 * 
 * @author Kenny 
 */
public class GlHelperError 
{
	public static class Error
	{
		public static class VertexBuffer
		{
			private static boolean isArbVboSupported;
			private static boolean isVboSupported;
		}
		
		public static class Textures
		{
			private static boolean isMultitextureSupported;
			private static boolean isTextureEnvCombineSupported;
		}
		
		public static class Shaders
		{
			private static boolean isShaderSupported;
			private static boolean isShadersAvalible;
			public static boolean isGeometryShaderSupported;
		}
		
		public static class Framebuffers
		{
			private static boolean isFramebufferSupported;
		}
		
		public static class Multisample
		{
			public static boolean isMultisampleAntiAnalisingSupported;
		}
		
		public static class OpenGL
		{
			private static boolean openGl14;
			private static boolean openGl21;
		}
	}
	
	/**
	 * Init all componet connect with a Open Gl 2.9.3. 
	 */
	public static void initOpenGlErrorManager()
	{
		ContextCapabilities capabilities = GLContext.getCapabilities();
		Error.Textures.isMultitextureSupported = capabilities.GL_ARB_multitexture & !capabilities.OpenGL13;
		Error.Textures.isTextureEnvCombineSupported = capabilities.GL_ARB_texture_env_combine && !capabilities.OpenGL13;
		
		if(Error.Textures.isMultitextureSupported)
		{
			Craftix.LOGGER.info("Using ARB multitexturing!");
		} else {
			Craftix.LOGGER.info("Using Open Gl 1.3 multitexturing!");
		}
		
		if(Error.Textures.isTextureEnvCombineSupported)
		{
			Craftix.LOGGER.info("Using ARB Env Combine!");
		} else {
			Craftix.LOGGER.info("Using Open Gl 1.3 Env Combine!");
		}
		
		Error.OpenGL.openGl14 = capabilities.OpenGL14 || capabilities.GL_EXT_blend_func_separate;
		Error.Framebuffers.isFramebufferSupported = Error.OpenGL.openGl14 && (capabilities.GL_ARB_framebuffer_object || capabilities.GL_EXT_framebuffer_object || capabilities.OpenGL30);
		
		if(Error.Framebuffers.isFramebufferSupported)
		{
			Craftix.LOGGER.info("Using frameBuffer Object because: ");
			if(capabilities.OpenGL30)
			{
				Craftix.LOGGER.info("OpenGl 3.0 + is supported and sepurate blend is supported");
			}
		}
		else
		{
			Craftix.LOGGER.warn("Not using framebuffers because ");
			Craftix.LOGGER.warn("Open GL 1.4 is " + (capabilities.OpenGL14 ? "" : "not ") + "supported, ");
			Craftix.LOGGER.warn("EXT_blend_func_separate is " + (capabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ");
			Craftix.LOGGER.warn("Open GL 3.0 is " + (capabilities.OpenGL30 ? "" : "not ") + "supported, ");
	        Craftix.LOGGER.warn("ARB_framebuffer_object is " + (capabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ");
	        Craftix.LOGGER.warn("EXT_framebuffer_object is " + (capabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.");
		}
		
		Error.OpenGL.openGl21 = capabilities.OpenGL21;
		Error.Shaders.isShadersAvalible = Error.OpenGL.openGl21 || capabilities.GL_ARB_fragment_shader && capabilities.GL_ARB_vertex_shader && capabilities.GL_ARB_shader_objects;
		Craftix.LOGGER.info("Shader are " + (Error.Shaders.isShadersAvalible ? "" : "not ") + "avalible because ");
		
		if(Error.Shaders.isShadersAvalible)
		{
			if(capabilities.OpenGL21)
			{
				Craftix.LOGGER.info("Open GL 2.1 is supported.");
			}
			else
			{
				Craftix.LOGGER.warn("OpenGL 2.1 is " + (capabilities.OpenGL21 ? "" : "not ") + "supported, ");
				Craftix.LOGGER.warn("ARB_shader_objects is " + (capabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ");
				Craftix.LOGGER.warn("ARB_vertex_shader is " + (capabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ");
				Craftix.LOGGER.warn("ARB_fragment_shader is " + (capabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n");
			}
		}
		
		Error.Shaders.isShaderSupported = Error.Framebuffers.isFramebufferSupported && Error.Shaders.isShadersAvalible;
		Error.VertexBuffer.isArbVboSupported = !capabilities.OpenGL15 && capabilities.GL_ARB_vertex_buffer_object;
		Error.VertexBuffer.isVboSupported = capabilities.OpenGL15 || Error.VertexBuffer.isArbVboSupported;
		Craftix.LOGGER.info("Vbo are " + (Error.VertexBuffer.isVboSupported ? "" : "not ") + "available because ");
		
		if(Error.VertexBuffer.isVboSupported) 
		{
			if(Error.VertexBuffer.isArbVboSupported)
			{
				Craftix.LOGGER.info("ARB_vertex_buffer_object is supported.");
			}
			else
			{
				Craftix.LOGGER.info("Open Gl 1.5 is supported.");
			}
		}
	}
	
	/**
	 * Return true if shaders has supported.
	 */
	public static boolean isShaderSupported()
	{
		return Error.Shaders.isShaderSupported;
	}
	
	/**
	 * Return true if framebuffer object has supported.
	 */
	public static boolean isFramebufferSupported()
	{
		return Error.Framebuffers.isFramebufferSupported;
	}
	
	/**
	 * Return true if arb type of vbo is supported.
	 */
	public static boolean isArbVboSupported()
	{
		return Error.VertexBuffer.isArbVboSupported;
	}
	
	public static boolean isVboSupported()
	{
		return Error.VertexBuffer.isVboSupported;
	}
	
	public static boolean isMultitextureSupported()
	{
		return Error.Textures.isMultitextureSupported;
	}
	
	public static boolean isTextureCombineSupported()
	{
		return Error.Textures.isTextureEnvCombineSupported;
	}
	
	public static boolean versionGl14()
	{
		return Error.OpenGL.openGl14;
	}
	
	public static boolean versionGl21()
	{
		return Error.OpenGL.openGl21;
	}
	
	/**
	 * If on your computer has installed old graphic video card, or drivers. This error has be
	 * showing on the screen.
	 */
	//public static void oldOpenGlGraphicCardError(GuiEngineText text)
	//{
		//if(!GLContext.getCapabilities().OpenGL20 && !isShaderSupported())
		//{
		//	text.show();
		//} else {
		//	text.hide();
		//}
	//}
	
}
