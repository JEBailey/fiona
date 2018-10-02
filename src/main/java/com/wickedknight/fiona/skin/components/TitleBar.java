package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Point;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class TitleBar extends ActiveDraggable
{
  protected Point current;
  protected Point dragged;
  protected Rectangle minimize;
  protected Rectangle shade;
  protected Rectangle exit;
  protected Rectangle background;
  protected Rectangle activeTitle;
  protected Rectangle inactiveTitle;
  private final int BUTTON_UP = 0;
  private final int BUTTON_DOWN = 1;
  private final int MIN_PRESSED = 2;
  private final int SMALL_PRESSED = 4;
  private final int EXIT_PRESSED = 8;
  private long time;

  public TitleBar(Skin paramSkin)
  {
    this.panel = paramSkin.getTitlePanel();
    this.state = 0;
    this.boundary = new Rectangle(0, 0, 274, 14);
    this.minimize = new Rectangle(244, 2, 9, 9);
    this.shade = new Rectangle(254, 2, 9, 9);
    this.exit = new Rectangle(264, 2, 9, 9);
    this.background = new Rectangle(0, 0, 274, 14);
    this.activeTitle = new Rectangle(27, 0, 274, 14);
    this.inactiveTitle = new Rectangle(27, 15, 274, 14);
    this.current = new Rectangle();
    this.dragged = new Rectangle();
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      copyImage(paramGraphics2D, this.background, this.enabled ? this.activeTitle : this.inactiveTitle);
      switch (this.state)
      {
      case 2:
        paramGraphics2D.drawImage(this.panel, 244, 2, 252, 10, 9, 8, 17, 16, null);
        break;
      case 4:
        paramGraphics2D.drawImage(this.panel, 254, 2, 252, 10, 9, 26, 17, 34, null);
        break;
      case 8:
        paramGraphics2D.drawImage(this.panel, 264, 2, 272, 10, 18, 8, 26, 16, null);
        break;
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (this.state == 0))
    {
      this.state = 1;
      this.current.update(paramMouseEvent.getX(), paramMouseEvent.getY());
      if (this.minimize.contains(paramMouseEvent.getPoint()))
        this.state = 2;
      else if (this.shade.contains(paramMouseEvent.getPoint()))
        this.state = 4;
      else if (this.exit.contains(paramMouseEvent.getPoint()))
        this.state = 8;
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (this.boundary.contains(paramMouseEvent.getPoint()))
    {
      switch (this.state)
      {
      case 1:
        this.state = 0;
        break;
      case 2:
        this.state = 0;
        fireEvent(new FionaEvent(this, 2002));
        break;
      case 4:
        this.state = 0;
        break;
      case 8:
        this.state = 0;
        fireEvent(new FionaEvent(this, 2003));
        break;
      case 3:
      case 5:
      case 6:
      case 7:
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (this.state != 0)
    {
      switch (this.state)
      {
      case 1:
        long l = paramMouseEvent.getWhen();
        if ((l - this.time > 15L) && (System.currentTimeMillis() - l < 5L))
        {
          paramMouseEvent.translatePoint(-this.current.getX(), -this.current.getY());
          this.dragged.update(paramMouseEvent.getPoint());
          fireEvent(new FionaEvent(this, 2010));
          this.time = l;
        }
        break;
      case 2:
        if (!this.minimize.contains(paramMouseEvent.getPoint()))
          this.state = 0;
        break;
      case 4:
        if (!this.shade.contains(paramMouseEvent.getPoint()))
          this.state = 0;
        break;
      case 8:
        if (!this.exit.contains(paramMouseEvent.getPoint()))
          this.state = 0;
        break;
      case 3:
      case 5:
      case 6:
      case 7:
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public int getDeltaX()
  {
    return this.dragged.getX();
  }

  public int getDeltaY()
  {
    return this.dragged.getY();
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    fireEvent(new FionaEvent(this, 2001));
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.TitleBar
 * JD-Core Version:    0.6.2
 */