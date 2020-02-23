package com.kenny.craftix.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.kenny.craftix.utils.ResourceLocation;

public interface IResourceManager
{
    Set<String> getResourceDomains();

    IResource getResource(ResourceLocation location) throws IOException;

    List<IResource> getAllResources(ResourceLocation location) throws IOException;
}