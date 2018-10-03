package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.event.FionaEventListener;
import com.wickedknight.geom.Point;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class ActiveComponent
  implements MouseListener, MouseMotionListener
{
  protected Rectangle boundary;
  protected Point position;
  protected Image panel;
  protected boolean enabled;
  protected boolean visible = true;
  protected int MAX_LISTENERS = 5;
  protected FionaEventListener[] actionListeners = new FionaEventListener[this.MAX_LISTENERS];
  protected int state;

  public abstract void paint(Graphics2D paramGraphics2D);

  public void addActionListener(FionaEventListener paramFionaEventListener)
  {
    if (paramFionaEventListener != null)
      for (int i = 0; i < this.MAX_LISTENERS; i++)
        if (this.actionListeners[i] == null)
        {
          this.actionListeners[i] = paramFionaEventListener;
          return;
        }
  }

  public void fireEvent(FionaEvent paramFionaEvent)
  {
    for (int i = 0; i < this.MAX_LISTENERS; i++)
      if (this.actionListeners[i] != null)
        this.actionListeners[i].fionaActionPerformed(paramFionaEvent);
  }

  public void setVisible(boolean paramBoolean)
  {
    this.visible = paramBoolean;
  }

  public boolean isVisible()
  {
    return this.visible;
  }

  public void setEnabled(boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }

  public boolean isEnabled()
  {
    return this.enabled;
  }

  public void copyImage(Graphics2D paramGraphics2D, Rectangle paramRectangle1, Rectangle paramRectangle2)
  {
    paramGraphics2D.drawImage(this.panel, paramRectangle1.getX(), paramRectangle1.getY(), paramRectangle1.getMaxX(), paramRectangle1.getMaxY(), paramRectangle2.getX(), paramRectangle2.getY(), paramRectangle2.getMaxX(), paramRectangle2.getMaxY(), null);
  }

  public boolean contains(Point paramPoint)
  {
    return this.boundary.contains(paramPoint);
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
  }


  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.ActiveComponent
 * JD-Core Version:    0.6.2
 */