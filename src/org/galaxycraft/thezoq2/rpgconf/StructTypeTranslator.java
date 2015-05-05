package org.galaxycraft.thezoq2.rpgconf;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates between the StructType enum and strings
 *
 * @see StructType
 */
public final class StructTypeTranslator
{
    private static final Map<StructType, String> STRUCT_CONFIG_NAMES;
    private static final Map<StructType, String> STRUCT_NAMES;
    static
    {
        STRUCT_CONFIG_NAMES = new HashMap<>();
        STRUCT_NAMES = new HashMap<>();

        STRUCT_CONFIG_NAMES.put(StructType.BOON, "boons");
        STRUCT_CONFIG_NAMES.put(StructType.MOVER, "movers");
        STRUCT_CONFIG_NAMES.put(StructType.SPELL, "spells");
        STRUCT_CONFIG_NAMES.put(StructType.VOLUME, "volumes");
        STRUCT_CONFIG_NAMES.put(StructType.VISUALISER, "visualisers");

        STRUCT_NAMES.put(StructType.BOON, "boon");
        STRUCT_NAMES.put(StructType.MOVER, "mover");
        STRUCT_NAMES.put(StructType.SPELL, "spell");
        STRUCT_NAMES.put(StructType.VOLUME, "volume");
        STRUCT_NAMES.put(StructType.VISUALISER, "visualiser");
    }

    private StructTypeTranslator()
    {

    }

    public static String getStructConfigName(StructType type)
    {
        return STRUCT_CONFIG_NAMES.get(type);
    }
    public static String getStructName(StructType type)
    {
        return STRUCT_NAMES.get(type);
    }
}
