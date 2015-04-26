package org.galaxycraft.thezoq2.rpgconf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by frans on 25/04/15.
 */
public class MainFrame
{
    private static final int TEXT_FIELD_SIZE = 20;
    private static final int TEXT_AREA_WIDTH = 50;
    private static final int TEXT_AREA_HEIGHT = 40;
    private JFrame frame;

    private JButton addStructButton;
    private JButton addValueButton;

    private JTextField varNameText;
    private JTextField varValueText;

    private ConfigCodeArea codeArea;

    private AbstractAction addStructAction = new AbstractAction("Add struct")
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
    };

    public MainFrame(ConfigCode code)
    {
        this.frame = new JFrame();
        //codeArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        codeArea = new ConfigCodeArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT, code);

        //Make the code listen for changes in the text
        codeArea.getDocument().addDocumentListener(code);

        frame.setLayout(new BorderLayout());

        createMenus();

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        addStructButton = new JButton(addStructAction);
        addStructButton.setSize(addStructButton.getPreferredSize());
        addValueButton = new JButton(addVarAction);
        addValueButton.setSize(addValueButton.getPreferredSize());

        varNameText = new JTextField(TEXT_FIELD_SIZE);
        varValueText = new JTextField(TEXT_FIELD_SIZE);
        buttonPanel.add(varNameText);
        buttonPanel.add(addStructButton);
        buttonPanel.add(varValueText);
        buttonPanel.add(addValueButton);
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
