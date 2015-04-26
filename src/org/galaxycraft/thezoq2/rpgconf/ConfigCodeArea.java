package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.*;

/**
 * Created by frans on 26/04/15.
 */
public class ConfigCodeArea extends JTextArea implements CodeUpdateListener
{
    private ConfigCode code;

    ConfigCodeArea(int width, int height, ConfigCode code)
    {
        super(width, height);

        this.code = code;

        super.setText(code.toString());
    }

    @Override
    public void onCodeUpdate()
    {
        super.setText(code.toString());
    }
}
