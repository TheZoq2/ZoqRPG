package org.galaxycraft.thezoq2.zoqrpg;

/**
 * Interface for a class that can be cloned in order to create copies that behave identically to the cloned object.
 */
public interface CloneableObject
{
    /**
     * Creates a new copy of the object that behaves identically
     * @return a new instance of the spcific object being cloned
     */
    CloneableObject cloneObject();
}
