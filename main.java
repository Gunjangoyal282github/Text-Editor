
package text_editor;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class Text_Editor extends JFrame implements ActionListener 
{
    private static JTextArea area;
    private static JFrame frame;
    private static int returnValue = 0;
    Clipboard clipboard = getToolkit().getSystemClipboard();
    Font fr;
    
    // CONSTRUCTOR
    
    public Text_Editor() 
    { 
        run(); 
    }
    
    // METHOD
    public void run() 
    {
        frame = new JFrame("Text Editor");

        // Set attributes of the app window
        
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        frame.setVisible(true);

        // MENU BAR
      
        JMenuBar menu_main = new JMenuBar();
        
        // MENU

        JMenu menu_file = new JMenu("File");
        JMenu menu_edit = new JMenu("Edit");
        JMenu menu_font = new JMenu("Font");
        
        menu_main.add(menu_file);
        menu_main.add(menu_edit);
        menu_main.add(menu_font);
        
        // FILE ITEMS
        
        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);
        
        // EDIT ITEMS
        
        JMenuItem menuitem_cut = new JMenuItem("Cut");
        JMenuItem menuitem_copy = new JMenuItem("Copy");
        JMenuItem menuitem_paste = new JMenuItem("Paste");

        menuitem_cut.addActionListener(this);
        menuitem_copy.addActionListener(this);
        menuitem_paste.addActionListener(this);

        menu_edit.add(menuitem_cut);
        menu_edit.add(menuitem_copy);
        menu_edit.add(menuitem_paste);
        
        // FONT ITEMS
        
        JMenuItem menuitem_courier = new JMenuItem("Courier");
        JMenuItem menuitem_consolas = new JMenuItem("Consolas");
        JMenuItem menuitem_monospaced = new JMenuItem("Monospaced");
        JMenuItem menuitem_arial = new JMenuItem("Arial");
        
        menuitem_courier.addActionListener(this);
        menuitem_consolas.addActionListener(this);
        menuitem_monospaced.addActionListener(this);
        menuitem_arial.addActionListener(this);
        
        menu_font.add(menuitem_courier);
        menu_font.add(menuitem_consolas);
        menu_font.add(menuitem_monospaced);
        menu_font.add(menuitem_arial);
        
        // ADD TO FRAME
        
        frame.setJMenuBar(menu_main);
       
    }
    
    @Override
    
    public void actionPerformed(ActionEvent e) 
    {
        String input = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        String ae = e.getActionCommand();
        
    // OPEN
        if (ae.equals("Open")) 
        {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) 
            {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try
                {
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
                    while(scan.hasNextLine())
                    {
                        String line = scan.nextLine() + "\n";
                        input = line;
                    }
                    area.setText(input);
                }
                catch ( FileNotFoundException ex) 
                { 
                    ex.printStackTrace(); 
                }
            }
        }
        
    // SAVE
        else if (ae.equals("Save")) 
        {
            returnValue = jfc.showSaveDialog(null);
            try 
            {
                File f = new File(jfc.getSelectedFile().getAbsolutePath() + ".txt");
                FileWriter out = new FileWriter(f);
                out.write(area.getText());
                out.close();
            } 
            catch (FileNotFoundException ex) 
            {
                Component f = null;
                JOptionPane.showMessageDialog(f,"File not found.");
            } 
            catch (IOException ex) 
            {
                Component f = null;
                JOptionPane.showMessageDialog(f,"Error.");
            }
        } 
        
    // NEW
        else if (ae.equals("New")) 
        {
            area.setText("");
        } 
        
    // QUIT
        else if (ae.equals("Quit")) 
        { 
            System.exit(0); 
        }
        
    // CUT
        else if (ae.equals("Cut"))
        {
            String cutString = area.getSelectedText();
            StringSelection cutSelection = new StringSelection(cutString);
            clipboard.setContents(cutSelection, cutSelection);
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());    
        }
        
    // COPY
        else if (ae.equals("Copy"))
        {
            String copyText = area.getSelectedText();
            StringSelection copySelection = new StringSelection(copyText);
            clipboard.setContents(copySelection, copySelection);
        }
        
    // PASTE
        else if (ae.equals("Paste"))
        {
           try
           {
               Transferable pasteText = clipboard.getContents(Text_Editor.this);
               String s = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
               area.replaceRange(s, area.getSelectionStart(), area.getSelectionEnd()); 
           }
           catch (Exception ex)
           {
               System.out.println("Error.");
           }
        }
        
    // COURIER
        else if (ae.equals("Courier")) 
        {
            
            fr=new Font("Calibri",Font.PLAIN,20); // Default Font
            area.setFont(fr);
            
            int fontSize = fr.getSize();
            int fontStyle = fr.getStyle();

            fr = new Font("Courier", fontStyle, fontSize);
            area.setFont(fr);
        }
        
    // CONSOLAS
        else if (ae.equals("Consolas"))
        {
            
            int fontSize = fr.getSize();
            int fontStyle = fr.getStyle();

            fr = new Font("Consolas", fontStyle, fontSize);
            area.setFont(fr);
        }
    
    // MONOSPACED
        else if (ae.equals("Monospaced")) 
        {
            
            int fontSize = fr.getSize();
            int fontStyle = fr.getStyle();

            fr = new Font("Monospaced", fontStyle, fontSize);
            area.setFont(fr);
        }
        
    // ARIAL
        else if (ae.equals("Arial")) 
        {
            
            int fontSize = fr.getSize();
            int fontStyle = fr.getStyle();

            fr = new Font("Arial", fontStyle, fontSize);
            area.setFont(fr);
        }
    }

    // MAIN FUNCTION
    public static void main(String[] args) 
    {
        new Text_Editor();
    }
}

