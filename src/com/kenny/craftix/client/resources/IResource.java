package com.kenny.craftix.client.resources;

import java.io.Closeable;
import java.io.InputStream;

import com.kenny.craftix.utils.ResourceLocation;


public interface IResource extends Closeable
{
    ResourceLocation getResourceLocation();

    InputStream getInputStream();

    boolean hasMetadata();

    //<T extends IMetadataSection> T getMetadata(String sectionName);

    String getResourcePackName();
}