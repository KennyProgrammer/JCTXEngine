package com.kenny.craftix.client.resources;

import com.kenny.craftix.utils.Locale;

public class I18n
{
    private static Locale i18nLocale;

    public static void setLocale(Locale i18nLocaleIn)
    {
        i18nLocale = i18nLocaleIn;
    }

    /**
     * Translates the given string and then formats it. Equivilant to String.format(translate(key), parameters).
     */
    public static String format(String translateKey, Object... parameters)
    {
        return i18nLocale.formatMessage(translateKey, parameters);
    }

    public static boolean hasKey(String key)
    {
        return i18nLocale.hasKey(key);
    }
}