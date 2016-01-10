package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Contains functions commonly used by DataValue objects.
 *
 * @see DataValue
 */
//I don't know why an abstract class would need to contain abstract methods. I use them to provide an implementation
//of some of the methods without requiring all of them
public abstract class BaseDataValue implements DataValue
{
    //Private because there is no reason for children to edit these
    private String variableName;
    private StructValue parentStruct;

    BaseDataValue(String variableName, StructValue parentStruct)
    {
        this.variableName = variableName;
        this.parentStruct = parentStruct;
    }

    @Override
    public void setVariableName(String structName)
    {
        this.variableName = structName;
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

        path.append(this.variableName);

        return path.toString();
    }
}
