package com.kenny.craftix.client.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MetadataSerializer 
{
	 @SuppressWarnings("unused")
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	 /** Cached Gson instance. Set to null when more sections are registered, and then re-created from the builder. */
	 @SuppressWarnings("unused")
	private Gson gson;
	 
	// public <T extends IMetadataSection> void registerMetadataSectionType(IMetadataSectionSerializer<T> metadataSectionSerializer, Class<T> clazz)
	// {
	//        this.metadataSectionSerializerRegistry.putObject(metadataSectionSerializer.getSectionName(), new MetadataSerializer.Registration(metadataSectionSerializer, clazz));
	//        this.gsonBuilder.registerTypeAdapter(clazz, metadataSectionSerializer);
	//        this.gson = null;
	// }
}
