import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by Jens on 11.04.2016.
 */
public class textEditor extends JFrame implements ActionListener{
    private JTextArea txtField;

    //FileMenu
    private JMenuItem fileOpen;
    private JMenuItem fileNew;
    private JMenuItem fileSave;
    private JMenuItem fileExit;

    //EditMenu
    private JMenuItem editUndo;
    private JMenuItem editCut;
    private JMenuItem editCopy;
    private JMenuItem editPaste;
    private JMenuItem editDelete;

    //FormatMenu
    private JMenuItem formatTxtWrap;
    private JMenu mnuFontSize;
    private JMenuItem [] mnuItemFontSize = new JMenuItem[9];

    //ShowMenu
    private JMenuItem showStatBar;

    //HelpMenu
    private JMenuItem helpAbout;
    private int [] fontSize = {8, 10, 12 ,14, 16, 18, 20, 30, 40};
    private String undo, temp;

    public textEditor(){
        super("Uten navn - Text Editor");
        initializeUserInterface();
    }

    public void initializeUserInterface(){

        initializeMenuBar();


        txtField = new JTextArea(100, 250);
        add(txtField);
        txtField.setLineWrap(true);
        txtField.setWrapStyleWord(true);
        JScrollPane scroller = new JScrollPane(txtField);
        add(scroller, BorderLayout.CENTER);

        setSize(900,700);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initializeMenuBar(){

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Creates our menus
        JMenu mnuFile = new JMenu("Fil");
        menuBar.add(mnuFile);

        JMenu mnuEdit = new JMenu("Rediger");
        menuBar.add(mnuEdit);

        JMenu mnuFormat = new JMenu("Format");
        menuBar.add(mnuFormat);

        JMenu mnuShow = new JMenu("Vis");
        menuBar.add(mnuShow);

        JMenu mnuHelp = new JMenu("Hjelp");
        menuBar.add(mnuHelp);

        mnuFontSize = new JMenu("Skriftstørrelse");

        // Creates our menu functions

            //File - Fil
        fileNew = new JMenuItem("Ny");
        fileOpen = new JMenuItem("Åpne");
        fileSave = new JMenuItem("Lagre");
        fileExit = new JMenuItem("Avslutt");
        mnuFile.add(fileOpen);
        mnuFile.add(fileNew);
        mnuFile.add(fileSave);
        mnuFile.add(fileExit);

            //Edit - Rediger
        editUndo = new JMenuItem("Angre");
        editCut = new JMenuItem("Klipp ut");
        editCopy = new JMenuItem("Kopier");
        editPaste = new JMenuItem("Lim inn");
        editDelete = new JMenuItem("Rensk");
        mnuEdit.add(editUndo);
        mnuEdit.add(editCut);
        mnuEdit.add(editCopy);
        mnuEdit.add(editPaste);
        mnuEdit.add(editDelete);

            //Format - Format
        formatTxtWrap = new JMenuItem("Tekstbryting");
        for (int i = 0; i < fontSize.length; i++) {
            mnuItemFontSize[i] = new JMenuItem("" + fontSize[i]);
            mnuFontSize.add(mnuItemFontSize[i]);
            mnuItemFontSize[i].addActionListener(this);
        }
        mnuFormat.add(formatTxtWrap);
        mnuFormat.add(mnuFontSize);


            //Show - Vis
        showStatBar = new JMenuItem("Statuslinje");
        mnuShow.add(showStatBar);

            //Help - Hjelp
        helpAbout = new JMenuItem("Om Text Editor");
        mnuHelp.add(helpAbout);

        fileOpen.addActionListener(this);
        fileNew.addActionListener(this);
        fileSave.addActionListener(this);
        fileExit.addActionListener(this);

        editUndo.addActionListener(this);
        editCut.addActionListener(this);
        editCopy.addActionListener(this);
        editPaste.addActionListener(this);
        editDelete.addActionListener(this);

        formatTxtWrap.addActionListener(this);

        showStatBar.addActionListener(this);

        helpAbout.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JMenuItem source = (JMenuItem)e.getSource();

        if(source == fileOpen){
         undo = txtField.getText();
            if (txtField.getText().length() > 0) {
                int choice = JOptionPane.showConfirmDialog(textEditor.this, "Vil du lagre først?");
                if(choice == JOptionPane.YES_OPTION){
                    save();
                }

            }
            txtField.setText("");
            try{
                String filename = JOptionPane.showInputDialog(textEditor.this, "Filnavn");
                Scanner fileIn = new Scanner(new File(filename));
                while(fileIn.hasNext()){
                    String data = fileIn.nextLine();
                    txtField.append(data + "\n");
                }
            }catch(IOException ioexception){
                JOptionPane.showMessageDialog(textEditor.this, "I/O-feil: " + ioexception.getMessage());
            }
        }else if (source == fileNew){
            undo = txtField.getText();
            if(txtField.getText().length()>0){
                int choice = JOptionPane.showConfirmDialog(textEditor.this, "Vil du lagre først?");
                if(choice == JOptionPane.YES_OPTION){
                    save();
                }
            }
            txtField.setText("");
            setTitle("Uten navn - Text Editor ");
        }else if (source == fileSave){
            save();
        }else if (source == fileExit){
            if(txtField.getText().length()>0){
                int choice = JOptionPane.showConfirmDialog(textEditor.this, "Vil du lagre først?");
                if(choice == JOptionPane.YES_OPTION){
                    save();
                }
            }
            System.exit(0);
        }else if (source == editUndo){
            temp = txtField.getText();
            txtField.setText(temp);
            undo = temp;

        }else if (source == editDelete){
            undo = txtField.getText();
            if(txtField.getText().length()>0){
                int choice = JOptionPane.showConfirmDialog(textEditor.this, "Vil du lagre først?");
                if(choice == JOptionPane.YES_OPTION){
                    save();
                }
            }
        }else if (source == editCut){
            undo = txtField.getText();
            if (txtField.getText().length()>0){
                temp = txtField.getSelectedText();
                txtField.cut();
            }
        }else if (source == editCopy){
            undo = txtField.getText();
            if (txtField.getText().length()>0){
                temp = txtField.getSelectedText();
                txtField.copy();
            }
        }else if (source == editPaste){
            undo = txtField.getText();
            txtField.paste();

        }else if (source == showStatBar){
            int nmbrOfLines = txtField.getLineCount();
            int nmbrOfCharacters = txtField.getText().length();
            JOptionPane.showMessageDialog(textEditor.this, "Antall linjer: " + nmbrOfLines + "\n" + "Antall tegn: " + nmbrOfCharacters);
        }else if (source == helpAbout){
            JOptionPane.showMessageDialog(textEditor.this, "Dette er en Text Editor laget av Jens Svendsen Thorbjørnsen");
        }else if (source == formatTxtWrap){
            if(txtField.getLineWrap()){
                txtField.setLineWrap(false);
            }else{
                txtField.setLineWrap(true);
            }
        }else{
            for (int i = 0; i<fontSize.length; i++){
                if(source == mnuItemFontSize[i]){
                    txtField.setFont(new Font("", Font.PLAIN, fontSize[i]));
                    return;
                }
            }
        }



    }

    public void save(){
        try {
            String fileName = JOptionPane.showInputDialog(textEditor.this, "Filnavn");
            PrintStream fileOut = new PrintStream(new File(fileName));
            fileOut.println(txtField.getText());
            fileOut.close();
        }catch (IOException ioexception){
            JOptionPane.showMessageDialog(textEditor.this, "I/O-feil: " + ioexception.getMessage());
        }

    }
}
