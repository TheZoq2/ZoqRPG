package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.*;

/**
 * Extension of JTextArea that implements the CodeUpldateListener interface to automatically update
 * the content of the textarea whenever the code is changed
 *
 * @see CodeUpdateListener
 */
public class ConfigCodeArea extends JTextArea implements CodeUpdateListener
{
    private ConfigCode code;

    ConfigCodeArea(int width, int height, ConfigCode code)
    {
        super(width, height);

        this.code = code;

        setText(code.toString());
    }

    @Override
    public void onCodeUpdate()
    {
        setText(code.toString());
    }
}
