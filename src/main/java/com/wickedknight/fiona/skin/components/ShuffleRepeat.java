package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class ShuffleRepeat extends ActiveComponent
{
  protected int shuffleState;
  protected int repeatState;
  protected Rectangle shuffle;
  protected Rectangle repeat;
  private final int BUTTON_OFF = 0;
  private final int BUTTON_ON = 1;
  private final int BUTTON_HIGHLIGHT = 2;

  public ShuffleRepeat(Skin paramSkin)
  {
    this.panel = paramSkin.getShuffleRepeatPanel();
    this.shuffleState = 0;
    this.repeatState = 0;
    this.boundary = new Rectangle(164, 89, 73, 14);
    this.shuffle = new Rectangle(164, 89, 46, 14);
    this.repeat = new Rectangle(210, 89, 27, 14);
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      switch (this.shuffleState)
      {
      case 0:
        paramGraphics2D.drawImage(this.panel, 164, 89, 210, 103, 28, 0, 74, 14, null);
        break;
      case 2:
      case 3:
        paramGraphics2D.drawImage(this.panel, 164, 89, 210, 103, 28, 15, 74, 29, null);
        break;
      case 1:
        paramGraphics2D.drawImage(this.panel, 164, 89, 210, 103, 28, 30, 74, 44, null);
      }
      switch (this.repeatState)
      {
      case 0:
        paramGraphics2D.drawImage(this.panel, 210, 89, 237, 103, 0, 0, 27, 14, null);
        break;
      case 2:
      case 3:
        paramGraphics2D.drawImage(this.panel, 210, 89, 237, 103, 0, 15, 27, 29, null);
        break;
      case 1:
        paramGraphics2D.drawImage(this.panel, 210, 89, 237, 103, 0, 30, 27, 44, null);
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (((this.shuffleState | this.repeatState) & 0x2) == 0))
    {
      if (this.shuffle.contains(paramMouseEvent.getPoint()))
        this.shuffleState |= 2;
      else if (this.repeat.contains(paramMouseEvent.getPoint()))
        this.repeatState |= 2;
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (((this.shuffleState | this.repeatState) & 0x2) > 0))
    {
      if (this.shuffle.contains(paramMouseEvent.getPoint()))
      {
        this.shuffleState ^= 2;
        this.shuffleState = (this.shuffleState == 0 ? 1 : 0);
      }
      else if (this.repeat.contains(paramMouseEvent.getPoint()))
      {
        this.repeatState ^= 2;
        this.repeatState = (this.repeatState == 0 ? 1 : 0);
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public synchronized void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (((this.shuffleState | this.repeatState) & 0x2) > 0)
    {
      if (((this.shuffleState & 0x2) > 0) && (!this.shuffle.contains(paramMouseEvent.getPoint())))
        this.shuffleState ^= 2;
      if (((this.repeatState & 0x2) > 0) && (!this.repeat.contains(paramMouseEvent.getPoint())))
        this.repeatState ^= 2;
      fireEvent(new FionaEvent(this, 2001));
    }
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.ShuffleRepeat
 * JD-Core Version:    0.6.2
 */