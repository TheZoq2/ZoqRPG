package org.galaxycraft.thezoq2.rpgconf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.*; //Bad practice but IDEA doesn't find the propper imports and im lazy
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Class for reading an XML file that contains information about what module structs can be created and
 * what variables those structs have. Parses an XML file and creates maps that contain lists of valid modules as
 * struct prototypes.
 *
 * @see StructPrototype
 */
public class StructXMLReader
{
    /*private List<StructPrototype> boonList;
    private List<StructPrototype> spellList;
    private List<StructPrototype> moverList;
    private List<StructPrototype> volList;
    private List<StructPrototype> visList;*/


    //Not static because the map will contain lists that are non static

    private enum ParsingState{
        IN_ROOT,
        READING_INSTANCES,
        READING_PARAMETERS,
        IN_EXTRA_DEPTH,
    }

    private Map<StructType, List<StructPrototype>> structLists;
    private final Map<String, List<StructPrototype>> listNameMap;

    public StructXMLReader(InputStream xmlInputStream) throws XMLStreamException, IOException
    {
        structLists = new HashMap<>();
        //Creating the list
        for(int i = 0; i < StructType.values().length; i++)
        {
            structLists.put(StructType.values()[i], new ArrayList<StructPrototype>());
        }

        listNameMap = new HashMap<>();
        listNameMap.put("boons", structLists.get(StructType.BOON));
        listNameMap.put("spells", structLists.get(StructType.SPELL));
        listNameMap.put("movers", structLists.get(StructType.MOVER));
        listNameMap.put("volumes", structLists.get(StructType.VOLUME));
        listNameMap.put("visualisers", structLists.get(StructType.VISUALISER));

        readXMLFile(xmlInputStream);
    }

    public List<StructPrototype> getStructList(StructType type)
    {
        return structLists.get(type);
    }

    private void readXMLFile(InputStream xmlInputStream) throws XMLStreamException, IOException
    {


        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        int extraDepth = 0;

        try(BufferedReader br = new BufferedReader(new InputStreamReader(xmlInputStream)))
        {
            XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(xmlInputStream);
            ParsingState pState = ParsingState.IN_ROOT;

            List<StructPrototype> targetList = null;
            StructPrototype targetStructPrototype = null;

            //Start parsing the file
            while(eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement())
                {
                    StartElement element = event.asStartElement();

                    String elementName = element.getName().getLocalPart();

                    //Could use switch statement for this but it wont work because I can't change the
                    //state we are in within a switch-case
                    if(pState == ParsingState.IN_ROOT)
                    {
                        //If this is the start of an instance list
                        if(listNameMap.containsKey(elementName))
                        {
                            //Figguring out where to put the instances we find
                            targetList = listNameMap.get(elementName);

                            pState = ParsingState.READING_INSTANCES;
                        }
                    }
                    else if(pState == ParsingState.READING_INSTANCES)
                    {
                        //If the list is null, we will be trying to save structs to a nonexistent list, if this happens,
                        //something has gone terribly wrong
                        assert(targetList != null);

                        if(elementName.equals("instance"))
                        {
                            targetStructPrototype = new StructPrototype();

                            pState = ParsingState.READING_PARAMETERS;
                        }
                    }
                    else if(pState == ParsingState.READING_PARAMETERS)
                    {
                        //Same reasoning as above
                        assert(targetStructPrototype != null);

                        if(element.getName().getLocalPart().equals("base"))
                        {
                            //Get the next event and check if it is a value
                            XMLEvent newEvent = eventReader.nextEvent();
                            if(newEvent.isCharacters())
                            {
                                String baseName = newEvent.asCharacters().getData();
                                targetStructPrototype.setBaseName(baseName);
                            }
                        }
                        else if(element.getName().getLocalPart().equals("additional"))
                        {
                            //Get the next event and check if it is a value
                            XMLEvent newEvent = eventReader.nextEvent();
                            if(newEvent.isCharacters())
                            {
                                String addName = newEvent.asCharacters().getData();
                                targetStructPrototype.addParameter(addName);
                            }
                        }

                        //Every time we find a new element within an instance, we go one element deeper into "extra depth"
                        extraDepth++;
                        pState = ParsingState.IN_EXTRA_DEPTH;
                    }
                }
                else if(event.isEndElement())
                {
                    if(pState == ParsingState.READING_INSTANCES)
                    {
                        pState = ParsingState.IN_ROOT;
                    }
                    if(pState == ParsingState.READING_PARAMETERS)
                    {
                        pState = ParsingState.READING_INSTANCES;
                        targetList.add(targetStructPrototype);
                    }
                    if(pState == ParsingState.IN_EXTRA_DEPTH)
                    {
                        extraDepth--;

                        if(extraDepth == 0)
                        {
                            pState = ParsingState.READING_PARAMETERS;
                        }
                    }
                }
            }
        } catch (XMLStreamException e)
        {
            Logger.getGlobal().log(Level.SEVERE, "Failed to load configuration XML file");
            e.printStackTrace();

            throw e;
        } catch (IOException e)
        {
            Logger.getGlobal().log(Level.SEVERE, "Failed to read configuration XML file");
            e.printStackTrace();

            throw e;
        }

    }
}
