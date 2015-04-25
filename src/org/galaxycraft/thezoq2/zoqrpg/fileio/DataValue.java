package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 3/31/15.
 */
public interface DataValue
{
    String TYPE_NAME = "generic";

    void setVariableName(String structName);

    void setParentStruct(StructValue parentStruct);

    String getFullPath();
}
