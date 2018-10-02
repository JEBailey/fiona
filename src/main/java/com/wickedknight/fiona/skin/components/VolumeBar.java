package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.configure.Config;
import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import com.wickedknight.util.HorizontalSlide;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.PrintStream;

public class VolumeBar extends ActiveComponent
{
  private boolean buttonExists;
  private int pos;
  private int minpos;
  private int maxpos;
  private int xoffset;
  private Rectangle button;
  private Rectangle buttonUp;
  private Rectangle buttonDown;
  private Rectangle background;
  private Rectangle start;
  private HorizontalSlide slide;
  private final int BUTTON_UP = 0;
  private final int BUTTON_DOWN = 1;
  private static Config config = Config.getInstance();

  public VolumeBar(Skin paramSkin)
  {
    this.panel = paramSkin.getVolumePanel();
    this.state = 0;
    this.slide = new HorizontalSlide(107, 158);
    this.boundary = new Rectangle(107, 57, 68, 13);
    this.buttonUp = new Rectangle(15, 420, 14, 7);
    this.buttonDown = new Rectangle(0, 420, 14, 7);
    this.slide.setButton(new Rectangle(107, 56, 14, 7));
    if (this.panel.getHeight(null) > 418)
      this.buttonExists = true;
    this.background = new Rectangle(0, 0, 68, 13);
    this.minpos = 0;
    this.maxpos = 27;
    setPos(config.getVolume());
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
    this.pos = ((int)(this.slide.getPercentage() * this.maxpos));
  }

  public void setPos(int paramInt)
  {
    System.out.println("setting volume to:" + paramInt);
    this.slide.setPercentage(paramInt);
    setPos();
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.VolumeBar
 * JD-Core Version:    0.6.2
 */