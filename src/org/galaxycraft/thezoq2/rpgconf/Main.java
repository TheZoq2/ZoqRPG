package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by frans on 25/04/15.
 */
public final class Main
{
    private Main()
    {
    }

    private static MainFrame frame;

    private static StructXMLReader xmlReader;

    public static void main(String[] args)
    {
        URL xmlURL = Main.class.getResource("/baseList.xml");

        if(xmlURL == null)
        {
            System.out.println("Failed to read xml file, xmlURL was null");
            System.exit(-1);
        }

        //Getting the xml file
        try(InputStream xmlStream = (InputStream) xmlURL.getContent())
        {
            //Reading the xml file
            xmlReader = new StructXMLReader(xmlStream);
        } catch (IOException | XMLStreamException e)
        {
            System.exit(-1);
            e.printStackTrace();
        }

        frame = new MainFrame(new ConfigCode());

        frame.setVisible(true);
    }
}
