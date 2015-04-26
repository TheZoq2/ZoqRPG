package org.galaxycraft.thezoq2.rpgconf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frans on 25/04/15.
 */
public class StructPrototype
{
    private String baseName = "";
    private List<String> additionalParameters;

    public StructPrototype()
    {
        this.additionalParameters = new ArrayList<>();
    }

    public void setBaseName(String baseName)
    {
        this.baseName = baseName;
    }

    public void addParameter(String param)
    {
        this.additionalParameters.add(param);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("base = ");
        sb.append(baseName);
        sb.append(";\n");

        for(String var : additionalParameters)
        {
            sb.append(var);
            sb.append(" = ;\n");
        }

        return sb.toString();
    }
}
