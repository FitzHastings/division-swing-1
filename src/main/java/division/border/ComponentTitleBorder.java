package division.border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class ComponentTitleBorder implements Border, MouseListener, SwingConstants {
  private int offset = 5;
  private Component comp;
  private JComponent container;
  private Rectangle rect;
  private Border border;
  
  public ComponentTitleBorder(Component comp, JComponent container, Border border) {
    this.comp = comp; 
    this.container = container; 
    this.border = border; 
    container.addMouseListener(this); 
  } 
  
  @Override
  public boolean isBorderOpaque() {
    return true; 
  } 
    
  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Insets borderInsets = border.getBorderInsets(c); 
    Insets insets = getBorderInsets(c); 
    int temp = (insets.top-borderInsets.top)/2; 
    border.paintBorder(c, g, x, y+temp, width, height-temp); 
    Dimension size = comp.getPreferredSize(); 
    rect = new Rectangle(offset, 0, size.width, size.height); 
    SwingUtilities.paintComponent(g, comp, (Container)c, rect); 
  } 
    
  @Override
  public Insets getBorderInsets(Component c) {
    Dimension size = comp.getPreferredSize(); 
    Insets insets = border.getBorderInsets(c); 
    insets.top = Math.max(insets.top, size.height); 
    return insets; 
  } 
    
  private void dispatchEvent(MouseEvent me) {
    if(rect!=null && rect.contains(me.getX(), me.getY())) {
      Point pt = me.getPoint(); 
      pt.translate(-offset, 0); 
      comp.setBounds(rect); 
      comp.dispatchEvent(new MouseEvent(comp, me.getID() 
              , me.getWhen(), me.getModifiers() 
              , pt.x, pt.y, me.getClickCount() 
              , me.isPopupTrigger(), me.getButton())); 
      if(!comp.isValid()) 
        container.repaint(); 
    } 
  } 
    
  @Override
  public void mouseClicked(MouseEvent me) {
    dispatchEvent(me); 
  } 
    
  @Override
  public void mouseEntered(MouseEvent me) {
    dispatchEvent(me); 
  } 
    
  @Override
  public void mouseExited(MouseEvent me) {
    dispatchEvent(me); 
  } 
    
  @Override
  public void mousePressed(MouseEvent me) {
    dispatchEvent(me); 
  } 
    
  @Override
  public void mouseReleased(MouseEvent me) {
    dispatchEvent(me); 
  } 
}