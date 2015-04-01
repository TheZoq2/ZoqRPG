package org.galaxycraft.thezoq2.zoqrpg.fileio;

/**
 * Created by frans on 3/31/15.
 */
public class DataVariable
{
    private String name;
    private DataValue data;

    public DataVariable(String name, DataValue data)
    {
        this.name = name;
        this.data = data;
    }

    public String getName()
    {
        return name;
    }

    public DataValue getData()
    {
        return data;
    }
}
