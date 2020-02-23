package com.kenny.craftix.client.language;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kenny.craftix.utils.JsonUtils;

public class LanguageMetadataSectionSerializer 
{
	public LanguageMetadataSection deserialize(JsonElement deserializer1, Type deserializer2,
			JsonDeserializationContext deserializer3) throws JsonParseException
	{
		JsonObject jsonObject = deserializer1.getAsJsonObject();
		Set<Language> set = Sets.<Language>newHashSet();
		
		for(Entry<String, JsonElement> entry : jsonObject.entrySet())
		{
			String s = entry.getKey();
			
			if(s.length() > 16)
			{
				throw new JsonParseException("Invalid language->'" + s + "': language code must not be more than " + 16 + "characters lorg!");
			}
			
			JsonObject jsonObject1 = JsonUtils.getJsonObject(entry.getValue(), "language");
			String s1 = JsonUtils.getString(jsonObject1, "region");
			String s2 = JsonUtils.getString(jsonObject1, "name");
			boolean flag = JsonUtils.getBoolean(jsonObject1, "bidirectional", false);
			
			 if (s1.isEmpty())
	            {
	                throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
	            }

	            if (s2.isEmpty())
	            {
	                throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
	            }

	            //if (!set.add(new Language(s, s1, s2, flag)))
	            //{
	           //     throw new JsonParseException("Duplicate language->'" + s + "' defined");
	           // }
		}
		
		return new LanguageMetadataSection(set);
	}
	
	public String getSectionName()
	{
		return "language";
	}
}
