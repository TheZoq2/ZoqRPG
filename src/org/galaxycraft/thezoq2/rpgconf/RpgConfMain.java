package org.galaxycraft.thezoq2.rpgconf;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Main class for the Configuration editor
 */
public final class RpgConfMain
{
    private RpgConfMain()
    {
    }

    private static StructXMLReader xmlReader; //If this is not initialised propperly in the main class, the program will
                    //exit

    //This is the main function of the class, it is run and idea is wrong
    public static void main(String[] args)
    {
        URL xmlURL = RpgConfMain.class.getResource("/baseList.xml");

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

        MainFrame frame = new MainFrame(new ConfigCode(), xmlReader); //Used before initialization is wrong. If the xmlReader
        //variable is wrong, the program will exit

        frame.setVisible(true);
    }
}
