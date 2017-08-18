package net.minecraft.server;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;

public class ServerGUI extends JComponent
{
  private static final Font a = new Font("Monospaced", 0, 12);
  private static final org.apache.logging.log4j.Logger b = org.apache.logging.log4j.LogManager.getLogger();
  
  private DedicatedServer c;
  
  public static void a(DedicatedServer paramDedicatedServer)
  {
    try
    {
      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException) {}
    
    ServerGUI localServerGUI = new ServerGUI(paramDedicatedServer);
    JFrame localJFrame = new JFrame("Minecraft server");
    localJFrame.add(localServerGUI);
    localJFrame.pack();
    localJFrame.setLocationRelativeTo(null);
    localJFrame.setVisible(true);
    localJFrame.addWindowListener(new ServerWindowAdapter(paramDedicatedServer));
  }
  












  public ServerGUI(DedicatedServer paramDedicatedServer)
  {
    this.c = paramDedicatedServer;
    setPreferredSize(new java.awt.Dimension(854, 480));
    
    setLayout(new BorderLayout());
    try {
      add(c(), "Center");
      add(a(), "West");
    } catch (Exception localException) {
      b.error("Couldn't build server GUI", localException);
    }
  }
  
  private JComponent a() {
    JPanel localJPanel = new JPanel(new BorderLayout());
    localJPanel.add(new GuiStatsComponent(this.c), "North");
    localJPanel.add(b(), "Center");
    localJPanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
    return localJPanel;
  }
  
  private JComponent b() {
    PlayerListBox localPlayerListBox = new PlayerListBox(this.c);
    JScrollPane localJScrollPane = new JScrollPane(localPlayerListBox, 22, 30);
    localJScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
    
    return localJScrollPane;
  }
  
  private JComponent c() {
    JPanel localJPanel = new JPanel(new BorderLayout());
    JTextArea localJTextArea = new JTextArea();
    JScrollPane localJScrollPane = new JScrollPane(localJTextArea, 22, 30);
    localJTextArea.setEditable(false);
    localJTextArea.setFont(a);
    
    JTextField localJTextField = new JTextField();
    localJTextField.addActionListener(new ServerGuiCommandListener(this, localJTextField));
    









    localJTextArea.addFocusListener(new ServerGuiFocusAdapter(this));
    




    localJPanel.add(localJScrollPane, "Center");
    localJPanel.add(localJTextField, "South");
    localJPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
    
    Thread localThread = new Thread(new ServerGuiThreadRunnable(this, localJTextArea, localJScrollPane));
    







    localThread.setDaemon(true);
    localThread.start();
    
    return localJPanel;
  }
  
  public void a(JTextArea paramJTextArea, JScrollPane paramJScrollPane, String paramString) {
    if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
      javax.swing.SwingUtilities.invokeLater(new ServerGuiInvokeRunnable(this, paramJTextArea, paramJScrollPane, paramString));
      




      return;
    }
    
    Document localDocument = paramJTextArea.getDocument();
    JScrollBar localJScrollBar = paramJScrollPane.getVerticalScrollBar();
    int i = 0;
    
    if (paramJScrollPane.getViewport().getView() == paramJTextArea) {
      i = localJScrollBar.getValue() + localJScrollBar.getSize().getHeight() + a.getSize() * 4 > localJScrollBar.getMaximum() ? 1 : 0;
    }
    try
    {
      localDocument.insertString(localDocument.getLength(), paramString, null);
    }
    catch (javax.swing.text.BadLocationException localBadLocationException) {}
    if (i != 0) {
      localJScrollBar.setValue(Integer.MAX_VALUE);
    }
  }
}
