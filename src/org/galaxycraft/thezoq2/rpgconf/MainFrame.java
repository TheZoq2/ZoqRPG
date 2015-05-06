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
 * @See ConfigCodeArea
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

    private ActionListener saveAction = new AbstractAction()
    {
        {
            putValue(SHORT_DESCRIPTION, "Save the current file");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Saving is not implemented");
        }
    };
    private ActionListener openAction = new AbstractAction()
    {
        {
            putValue(SHORT_DESCRIPTION, "Load a new file");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Saving is not implemented");
        }
    };
    private ActionListener exitAction = new AbstractAction()
    {
        {
            putValue(SHORT_DESCRIPTION, "Exit the program");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_E));
        }
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
        /*
        addStructButton = new JButton(addStructAction);
        addStructButton.setSize(addStructButton.getPreferredSize());
        addValueButton = new JButton(addVarAction);
        addValueButton.setSize(addValueButton.getPreferredSize());*/

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

    private void createMenus()
    {
        JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveMenuItem.addActionListener(saveAction);
        JMenuItem openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
        openMenuItem.addActionListener(openAction);
        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        exitMenuItem.addActionListener(exitAction);

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

    private AbstractAction saveButton = new AbstractAction()
    {
        {
            putValue(SHORT_DESCRIPTION, "Save the current file");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Saving is not implemented");
        }
    };
    private AbstractAction loadButton = new AbstractAction()
    {
        {
            putValue(SHORT_DESCRIPTION, "Load a new file");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.out.println("Saving is not implemented");
        }
    };

    private void addTextAtCursor(String text)
    {
        this.codeArea.insert(text, codeArea.getCaretPosition());
    }

    private String getVarName()
    {
        return this.varNameText.getText();
    }
    private String getVarValue()
    {
        return this.varValueText.getText();
    }
}
