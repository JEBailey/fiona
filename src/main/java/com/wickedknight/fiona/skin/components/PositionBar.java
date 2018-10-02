package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import com.wickedknight.util.HorizontalSlide;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class PositionBar extends ActiveComponent
{
  protected Rectangle buttonUp;
  protected Rectangle buttonDown;
  protected HorizontalSlide slide;
  private int xoffset;
  private static final int BUTTON_UP = 0;
  private static final int BUTTON_DOWN = 1;

  public PositionBar(Skin paramSkin)
  {
    this.panel = paramSkin.getPositionBarPanel();
    this.state = 0;
    this.slide = new HorizontalSlide(16, 235);
    this.boundary = new Rectangle(16, 72, 245, 81);
    this.buttonUp = new Rectangle(277, 0, 29, 9);
    this.buttonDown = new Rectangle(248, 0, 29, 9);
    this.slide.setButton(new Rectangle(16, 72, 30, 9));
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      paramGraphics2D.drawImage(this.panel, 16, 72, 261, 81, 0, 0, 245, 9, null);
      switch (this.state)
      {
      case 1:
        copyImage(paramGraphics2D, this.slide.getButton(), this.buttonUp);
        break;
      case 0:
        copyImage(paramGraphics2D, this.slide.getButton(), this.buttonDown);
        break;
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && (this.slide.getButton().contains(paramMouseEvent.getPoint())) && (this.state == 0))
    {
      this.xoffset = (paramMouseEvent.getX() - this.slide.getButton().getX());
      this.state = 1;
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    switch (this.state)
    {
    case 1:
      this.state = 0;
      fireEvent(new FionaEvent(this, 2001));
      break;
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (this.state == 1)
    {
      this.slide.setPos(paramMouseEvent.getX() - this.xoffset);
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void setX(int paramInt)
  {
    this.slide.setPos(paramInt);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.PositionBar
 * JD-Core Version:    0.6.2
 */