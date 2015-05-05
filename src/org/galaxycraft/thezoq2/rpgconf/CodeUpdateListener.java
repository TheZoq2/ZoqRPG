package org.galaxycraft.thezoq2.rpgconf;

/**
 * Interface for objects that need to know when a ConfigCode has been updated
 *
 * @see ConfigCode
 */
public interface CodeUpdateListener
{
    void onCodeUpdate();
}
