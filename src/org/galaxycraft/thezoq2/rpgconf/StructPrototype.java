package org.galaxycraft.thezoq2.rpgconf;

import java.util.ArrayList;
import java.util.List;

/**
 * A prototype for a base spell module struct in a config file. Keeps track of all variables within the struct asweell
 * as the base name. Also has a method for represeting the object as a string and as code.
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

    public String getCode()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("base = ");
        sb.append(baseName);
        sb.append(";\n");

        for(String variable : additionalParameters)
        {
            sb.append(variable);
            sb.append(" = ;\n");
        }

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return baseName;
    }
}
