package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class EqPlButtons extends ActiveComponent
{
  protected static int eqButtonState;
  protected static int plButtonState;
  protected static Rectangle eqButton;
  protected static Rectangle plButton;
  private static final int BUTTON_OFF = 0;
  private static final int BUTTON_ON = 1;
  private static final int BUTTON_HIGHLIGHT = 2;

  public EqPlButtons(Skin paramSkin)
  {
    eqButtonState = 0;
    plButtonState = 0;
    this.panel = paramSkin.getEqPlPanel();
    this.boundary = new Rectangle(220, 58, 46, 12);
    eqButton = new Rectangle(220, 58, 23, 12);
    plButton = new Rectangle(243, 58, 23, 12);
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      switch (eqButtonState)
      {
      case 0:
        paramGraphics2D.drawImage(this.panel, 219, 58, 242, 70, 0, 61, 23, 73, null);
        break;
      case 2:
        paramGraphics2D.drawImage(this.panel, 219, 58, 242, 70, 46, 61, 69, 73, null);
        break;
      case 3:
        paramGraphics2D.drawImage(this.panel, 219, 58, 242, 70, 46, 73, 69, 85, null);
        break;
      case 1:
        paramGraphics2D.drawImage(this.panel, 219, 58, 242, 70, 0, 73, 23, 85, null);
      }
      switch (plButtonState)
      {
      case 0:
        paramGraphics2D.drawImage(this.panel, 242, 58, 265, 70, 23, 61, 46, 73, null);
        break;
      case 2:
        paramGraphics2D.drawImage(this.panel, 242, 58, 265, 70, 69, 61, 92, 73, null);
        break;
      case 3:
        paramGraphics2D.drawImage(this.panel, 242, 58, 265, 70, 69, 73, 92, 85, null);
        break;
      case 1:
        paramGraphics2D.drawImage(this.panel, 242, 58, 265, 70, 23, 73, 46, 85, null);
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (((eqButtonState | plButtonState) & 0x2) == 0))
    {
      if (eqButton.contains(paramMouseEvent.getPoint()))
        eqButtonState |= 2;
      else if (plButton.contains(paramMouseEvent.getPoint()))
        plButtonState |= 2;
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (((eqButtonState | plButtonState) & 0x2) > 0))
    {
      if (eqButton.contains(paramMouseEvent.getPoint()))
      {
        eqButtonState ^= 2;
        eqButtonState = eqButtonState == 0 ? 1 : 0;
      }
      else if (plButton.contains(paramMouseEvent.getPoint()))
      {
        plButtonState ^= 2;
        plButtonState = plButtonState == 0 ? 1 : 0;
        fireEvent(new FionaEvent(this, plButtonState == 1 ? 2020 : 2021));
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public synchronized void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (((eqButtonState | plButtonState) & 0x2) > 0)
    {
      if (((eqButtonState & 0x2) > 0) && (!eqButton.contains(paramMouseEvent.getPoint())))
        eqButtonState ^= 2;
      if (((plButtonState & 0x2) > 0) && (!plButton.contains(paramMouseEvent.getPoint())))
        plButtonState ^= 2;
      fireEvent(new FionaEvent(this, 2001));
    }
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.EqPlButtons
 * JD-Core Version:    0.6.2
 */