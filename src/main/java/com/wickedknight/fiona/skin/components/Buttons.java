package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Buttons extends ActiveComponent
{
  private static final int BUTTON_UP = 0;
  private static final int BUTTON_DOWN = 1;
  private static final int REWIND_PRESSED = 2;
  private static final int PLAY_PRESSED = 4;
  private static final int PAUSE_PRESSED = 8;
  private static final int STOP_PRESSED = 16;
  private static final int FFWD_PRESSED = 32;
  private static final int PLIST_PRESSED = 64;
  private Rectangle rewind;
  private Rectangle play;
  private Rectangle pause;
  private Rectangle stop;
  private Rectangle ffwd;
  private Rectangle plist;
  private Rectangle rewindBounds;
  private Rectangle playBounds;
  private Rectangle pauseBounds;
  private Rectangle stopBounds;
  private Rectangle ffwdBounds;
  private Rectangle plistBounds;
  private int status;

  public Buttons(Skin paramSkin)
  {
    this.panel = paramSkin.getButtonPanel();
    this.state = 0;
    this.rewind = new Rectangle(0, 18, 23, 18);
    this.play = new Rectangle(23, 18, 23, 18);
    this.pause = new Rectangle(46, 18, 23, 18);
    this.stop = new Rectangle(69, 18, 23, 18);
    this.ffwd = new Rectangle(92, 18, 22, 18);
    this.plist = new Rectangle(114, 16, 22, 16);
    this.rewindBounds = new Rectangle(16, 88, 23, 18);
    this.playBounds = new Rectangle(39, 88, 23, 18);
    this.pauseBounds = new Rectangle(62, 88, 23, 18);
    this.stopBounds = new Rectangle(85, 88, 23, 18);
    this.ffwdBounds = new Rectangle(108, 88, 22, 18);
    this.plistBounds = new Rectangle(130, 86, 22, 16);
    this.boundary = new Rectangle(16, 88, 129, 17);
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      paramGraphics2D.drawImage(this.panel, 16, 88, 129, 105, 0, 0, 113, 17, null);
      switch (this.status)
      {
      case 2:
        copyImage(paramGraphics2D, this.rewindBounds, this.rewind);
        break;
      case 4:
        copyImage(paramGraphics2D, this.playBounds, this.play);
        break;
      case 8:
        copyImage(paramGraphics2D, this.pauseBounds, this.pause);
        break;
      case 16:
        copyImage(paramGraphics2D, this.stopBounds, this.stop);
        break;
      case 32:
        copyImage(paramGraphics2D, this.ffwdBounds, this.ffwd);
        break;
      case 64:
        copyImage(paramGraphics2D, this.plistBounds, this.plist);
      }
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if (this.boundary.contains(paramMouseEvent.getPoint()))
    {
      if (this.rewindBounds.contains(paramMouseEvent.getPoint()))
        this.status = 2;
      else if (this.playBounds.contains(paramMouseEvent.getPoint()))
        this.status = 4;
      else if (this.pauseBounds.contains(paramMouseEvent.getPoint()))
        this.status = 8;
      else if (this.stopBounds.contains(paramMouseEvent.getPoint()))
        this.status = 16;
      else if (this.ffwdBounds.contains(paramMouseEvent.getPoint()))
        this.status = 32;
      else if (!this.plistBounds.contains(paramMouseEvent.getPoint()));
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.status = 0;
    fireEvent(new FionaEvent(this, 2001));
  }

  public synchronized void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (this.status != 0)
    {
      switch (this.status)
      {
      case 2:
        if (!this.rewindBounds.contains(paramMouseEvent.getPoint()))
          this.status = 0;
        break;
      case 4:
        if (!this.playBounds.contains(paramMouseEvent.getPoint()))
          this.status = 0;
        break;
      case 8:
        if (!this.pauseBounds.contains(paramMouseEvent.getPoint()))
          this.status = 0;
        break;
      case 16:
        if (!this.stopBounds.contains(paramMouseEvent.getPoint()))
          this.status = 0;
        break;
      case 32:
        if (!this.ffwdBounds.contains(paramMouseEvent.getPoint()))
          this.status = 0;
        break;
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.Buttons
 * JD-Core Version:    0.6.2
 */