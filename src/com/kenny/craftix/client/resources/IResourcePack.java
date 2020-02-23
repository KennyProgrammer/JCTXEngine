package com.kenny.craftix.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import com.kenny.craftix.utils.ResourceLocation;


public interface IResourcePack 
{
	InputStream getInputStream(ResourceLocation location) throws IOException;
	
	boolean resourceExits(ResourceLocation location);
	
	Set<String> getResourceDomains();
	
	 @Nullable
	// <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException;

	 BufferedImage getPackImage() throws IOException;

	 String getPackName();
}
