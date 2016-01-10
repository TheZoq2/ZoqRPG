package org.galaxycraft.thezoq2.rpgconf;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class that handles the GUI of the config editor. Contains a couple of buttons for updating a ConfigCode object
 * aswell as a ConfigCodeArea for editing the code manually
 *
 * @see ConfigCode
 * @see ConfigCodeArea
 */
public class MainFrame
{
    private static final int TEXT_FIELD_SIZE = 20;
    private static final int TEXT_AREA_WIDTH = 50;
    private static final int TEXT_AREA_HEIGHT = 40;
    private JFrame frame;

    private JTextField varNameText;
    private JTextField varValueText;

    private ConfigCodeArea codeArea;

    /**
     * Action that saves the current config file
     */
    private ActionListener saveAction = new AbstractAction()
    {

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Saving is not implemented");
        }
    };
    /**
     * Action that opens a new config file
     */
    private ActionListener openAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Loading is not implemented");
        }
    };
    /**
     * Action that exits the program
     */
    private ActionListener exitAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.exit(0); //Ugly
        }
    };

    public MainFrame(final ConfigCode code, StructXMLReader xmlReader)
    {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //codeArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        codeArea = new ConfigCodeArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT, code);

        //Make the code listen for changes in the text
        codeArea.getDocument().addDocumentListener(code);
        code.addUpdateListener(codeArea);

        frame.setLayout(new BorderLayout());

        createMenus();

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        //TODO: add mnemonics
        for(final StructType st : StructType.values())
        {
            final String structName = StructTypeTranslator.getStructName(st);
            final List<StructPrototype> prototypes = xmlReader.getStructList(st);
            final JFrame parentFrame = this.frame;

            AbstractAction addAction = new AbstractAction("Add " + structName)
            {
                {
                    putValue(SHORT_DESCRIPTION, String.format("add a %s", structName));
                }
                @Override
                public void actionPerformed(ActionEvent actionEvent)
                {
                    StructPrototype stp = (StructPrototype) JOptionPane.showInputDialog(
                            parentFrame,
                            "Select a base",
                            "Create " + structName,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            prototypes.toArray(),
                            prototypes.get(0));

                    //int a = 0;
                    if(stp != null)
                    {
                        code.addStruct(stp, st);
                    }
                }
            };

            JButton addButton = new JButton(addAction);
            addButton.setSize(addButton.getPreferredSize());

            buttonPanel.add(addButton);
        }

        varNameText = new JTextField(TEXT_FIELD_SIZE);
        varValueText = new JTextField(TEXT_FIELD_SIZE);
        buttonPanel.setSize(buttonPanel.getPreferredSize());

        frame.add(buttonPanel, BorderLayout.PAGE_START);

        frame.add(codeArea, BorderLayout.CENTER);

        frame.pack();
    }

    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    /**
     * Create the menubar of the frame
     */
    private void createMenus()
    {
        JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
        saveMenuItem.addActionListener(saveAction);
        JMenuItem openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
        openMenuItem.addActionListener(openAction);
        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        exitMenuItem.addActionListener(exitAction);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("control E"));

        final JMenu file = new JMenu("File");
        file.add(openMenuItem);
        file.add(saveMenuItem);
        file.addSeparator();
        file.add(exitMenuItem);
        file.setMnemonic(KeyEvent.VK_F);

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(Box.createHorizontalGlue());
        frame.setJMenuBar(menuBar);
    }
}
