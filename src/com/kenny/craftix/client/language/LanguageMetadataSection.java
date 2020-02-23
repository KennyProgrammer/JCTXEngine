package com.kenny.craftix.client.language;

import java.util.Collection;

public class LanguageMetadataSection
{
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn)
    {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages()
    {
        return this.languages;
    }
}