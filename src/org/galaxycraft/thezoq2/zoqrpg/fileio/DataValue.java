package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Interface for variables extracted from the config format parsed by DataFileReader
 *
 * Stores its own name aswell as a pointer to the StructValue that it belongs to. Null if it's a root struct
 *
 * @see DataFileReader
 * @see StructValue
 */
public interface DataValue
{
    String TYPE_NAME = "generic";

    void setVariableName(String structName);

    void setParentStruct(StructValue parentStruct);

    String getFullPath();
}
