package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 14/04/15.
 */
public abstract class BaseDataValue implements DataValue
{
    //Private because there is no reason for children to edit these
    private String varName;
    private StructValue parentStruct;

    BaseDataValue(String varName, StructValue parentStruct)
    {
        this.varName = varName;
        this.parentStruct = parentStruct;
    }

    @Override
    public void setVariableName(String structName)
    {
        this.varName = structName;
    }

    @Override
    public void setParentStruct(StructValue parentStruct)
    {
        this.parentStruct = parentStruct;
    }

    @Override
    public String getFullPath()
    {
        StringBuilder path = new StringBuilder();
        if(parentStruct != null)
        {
            path.append(parentStruct.getFullPath());
            path.append(".");
        }

        path.append(this.varName);

        return path.toString();
    }
}
