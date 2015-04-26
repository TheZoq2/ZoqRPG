package org.galaxycraft.thezoq2.rpgconf;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by frans on 25/04/15.
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

    /*private AbstractAction addStructAction = new AbstractAction("Add struct")
    {
        {
            putValue(SHORT_DESCRIPTION, "Add a struct at the cursor");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        }
        @Override
        public void actionPerformed(ActionEvent event)
        {
            addStruct();
        }
    };
    private AbstractAction addVarAction = new AbstractAction("Add variable")
    {
        {
            putValue(SHORT_DESCRIPTION, "Add a variable at the cursor");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_V));
        }
        @Override
        public void actionPerformed(ActionEvent event)
        {
            addVariable();
        }
    };*/

    public MainFrame(final ConfigCode code, StructXMLReader xmlReader)
    {
        this.frame = new JFrame();
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
        final JMenu file = new JMenu("File");
        file.add(new JMenuItem("Open", 'O'));
        file.add(new JMenuItem("Save", 'S'));
        file.addSeparator();
        file.add(new JMenuItem("Exit"));

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        frame.setJMenuBar(menuBar);
    }

    private void addStruct()
    {
        addTextAtCursor(getVarName() + "=\n{\n}");
    }
    private void addVariable()
    {
        addTextAtCursor(getVarName() + " = " + getVarValue() + ";");
    }

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
