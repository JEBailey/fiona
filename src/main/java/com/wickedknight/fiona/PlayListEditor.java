package com.wickedknight.fiona;

import com.wickedknight.fiona.aspect.Playlist;
import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.event.FionaEventListener;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.fiona.skin.components.ActiveComponent;
import com.wickedknight.fiona.skin.components.ActiveDraggable;
import com.wickedknight.fiona.skin.components.playlist.PLE;
import com.wickedknight.graphics.BufferedGraphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JWindow;

public class PlayListEditor extends JWindow
  implements FionaEventListener, WindowListener
{
  private BufferedGraphics offscreenImage;
  private Skin skin;
  private ActiveComponent main;
  private ActiveComponent buttons;
  private ActiveComponent list;
  private ActiveComponent ple;

  public PlayListEditor(JFrame paramJFrame, Skin paramSkin)
  {
    super(paramJFrame);
    this.skin = paramSkin;
    init();
  }

  private void init()
  {
    setLocation(120, 240);
    setSize(this.skin.getMainPanel().getWidth(null), this.skin.getMainPanel().getHeight(null));
    this.offscreenImage = new BufferedGraphics(getContentPane());
    initPle();
  }

  public void initPle()
  {
    this.ple = new PLE(this.skin);
    initActiveComponent(this.ple);
  }

  private void initActiveComponent(ActiveComponent paramActiveComponent)
  {
    addMouseListener(paramActiveComponent);
    addMouseMotionListener(paramActiveComponent);
    paramActiveComponent.addActionListener(this);
  }

  public void update(Graphics paramGraphics)
  {
    paint(paramGraphics);
  }

  public void paint(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)this.offscreenImage.getValidGraphics();
    this.ple.paint(localGraphics2D);
    Image localImage = this.offscreenImage.getBuffer();
    paramGraphics.drawImage(localImage, 0, 0, this);
    localGraphics2D.dispose();
  }

  public void fionaActionPerformed(FionaEvent paramFionaEvent)
  {
    switch (paramFionaEvent.getID())
    {
    case 2001:
      repaint();
      break;
    case 2010:
      ActiveDraggable localActiveDraggable = (ActiveDraggable)paramFionaEvent.getSource();
      int i = getX() + localActiveDraggable.getDeltaX();
      int j = getY() + localActiveDraggable.getDeltaY();
      int k = j + 116;
      setLocation(i, j);
      break;
    case 2002:
      break;
    case 2003:
      dispose();
      System.exit(0);
      break;
    case 2020:
      break;
    case 2021:
      break;
    case 2011:
      Playlist.addFile(this);
    case 2004:
    case 2005:
    case 2006:
    case 2007:
    case 2008:
    case 2009:
    case 2012:
    case 2013:
    case 2014:
    case 2015:
    case 2016:
    case 2017:
    case 2018:
    case 2019:
    }
  }

  public void windowActivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosed(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosing(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeactivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeiconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowIconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowOpened(WindowEvent paramWindowEvent)
  {
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.PlayListEditor
 * JD-Core Version:    0.6.2
 */