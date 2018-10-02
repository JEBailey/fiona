package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import com.wickedknight.util.FulcrumSlide;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

public class Balance extends ActiveComponent
{
  private boolean buttonExists;
  private int pos;
  private int maxpos;
  private int xoffset;
  protected Rectangle button;
  protected Rectangle buttonUp;
  protected Rectangle buttonDown;
  protected Rectangle background;
  protected Rectangle start;
  protected FulcrumSlide slide;
  private static final int BUTTON_UP = 0;
  private static final int BUTTON_DOWN = 1;

  public Balance(Skin paramSkin)
  {
    this.panel = paramSkin.getBalance();
    this.state = 0;
    this.slide = new FulcrumSlide(178, 200);
    this.slide.setFulcrum(189);
    this.boundary = new Rectangle(177, 57, 37, 13);
    this.buttonDown = new Rectangle(0, 420, 14, 7);
    this.buttonUp = new Rectangle(15, 420, 14, 7);
    this.slide.setButton(new Rectangle(189, 56, 14, 7));
    if (this.panel.getHeight(null) > 418)
      this.buttonExists = true;
    this.background = new Rectangle(9, 0, 37, 13);
    this.maxpos = 27;
    this.pos = 0;
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      copyImage(paramGraphics2D, this.boundary, this.background);
      if (this.buttonExists)
        switch (this.state)
        {
        case 1:
          copyImage(paramGraphics2D, this.slide.getButton(), this.buttonDown);
          break;
        case 0:
          copyImage(paramGraphics2D, this.slide.getButton(), this.buttonUp);
          break;
        }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((this.boundary.contains(paramMouseEvent.getPoint())) && ((!this.buttonExists) || (this.slide.getButton().contains(paramMouseEvent.getPoint()))) && (this.state == 0))
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
      setPos();
      this.background.setY(this.pos * 15);
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void setPos()
  {
    int i = (int)(this.slide.getPercentage() * this.maxpos);
    this.pos = (i < 0 ? 0 - i : i);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.Balance
 * JD-Core Version:    0.6.2
 */