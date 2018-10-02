package com.wickedknight.fiona;

import com.wickedknight.fiona.configure.Config;
import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.event.FionaEventListener;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.fiona.skin.components.ActiveComponent;
import com.wickedknight.fiona.skin.components.ActiveDraggable;
import com.wickedknight.fiona.skin.components.Balance;
import com.wickedknight.fiona.skin.components.Buttons;
import com.wickedknight.fiona.skin.components.EqPlButtons;
import com.wickedknight.fiona.skin.components.Main;
import com.wickedknight.fiona.skin.components.MonoStereo;
import com.wickedknight.fiona.skin.components.PositionBar;
import com.wickedknight.fiona.skin.components.ShuffleRepeat;
import com.wickedknight.fiona.skin.components.TitleBar;
import com.wickedknight.fiona.skin.components.VolumeBar;
import com.wickedknight.graphics.BufferedGraphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class Player extends JFrame
  implements FionaEventListener, WindowListener, KeyListener
{
  private BufferedGraphics offscreenImage;
  private ActiveComponent main;
  private ActiveComponent title;
  private ActiveComponent posbar;
  private ActiveComponent buttons;
  private ActiveComponent shuffleRepeat;
  private ActiveComponent eqPl;
  private ActiveComponent monoStereo;
  private ActiveComponent volume;
  private ActiveComponent balance;
  private PlayListEditor playlist;
  private Skin skin;
  private static Config config = Config.getInstance();

  public Player(String paramString)
  {
    super(paramString);
    init();
  }

  public void init()
  {
    this.skin = new Skin(config.getSkin());
    setUndecorated(true);
    setLocation(config.getX(), config.getY());
    setSize(this.skin.getMainPanel().getWidth(null), this.skin.getMainPanel().getHeight(null));
    this.offscreenImage = new BufferedGraphics(getContentPane());
    addWindowListener(this);
    this.playlist = new PlayListEditor(this, this.skin);
    initMain();
    initTitle();
    initPosBar();
    initButtons();
    initShufRep();
    initEqPl();
    initMonoStereo();
    initVolume();
    initBalance();
    show();
    this.skin = null;
  }

  public void initMain()
  {
    this.main = new Main(this.skin);
    addMouseListener(this.main);
    addMouseMotionListener(this.main);
  }

  public void initTitle()
  {
    this.title = new TitleBar(this.skin);
    initActiveComponent(this.title);
  }

  private void initPosBar()
  {
    this.posbar = new PositionBar(this.skin);
    initActiveComponent(this.posbar);
  }

  private void initButtons()
  {
    this.buttons = new Buttons(this.skin);
    initActiveComponent(this.buttons);
  }

  public void initShufRep()
  {
    this.shuffleRepeat = new ShuffleRepeat(this.skin);
    initActiveComponent(this.shuffleRepeat);
  }

  public void initEqPl()
  {
    this.eqPl = new EqPlButtons(this.skin);
    initActiveComponent(this.eqPl);
  }

  public void initMonoStereo()
  {
    this.monoStereo = new MonoStereo(this.skin);
    initActiveComponent(this.monoStereo);
  }

  public void initVolume()
  {
    this.volume = new VolumeBar(this.skin);
    initActiveComponent(this.volume);
  }

  public void initBalance()
  {
    this.balance = new Balance(this.skin);
    initActiveComponent(this.balance);
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
    this.main.paint(localGraphics2D);
    this.title.paint(localGraphics2D);
    this.posbar.paint(localGraphics2D);
    this.buttons.paint(localGraphics2D);
    this.shuffleRepeat.paint(localGraphics2D);
    this.eqPl.paint(localGraphics2D);
    this.monoStereo.paint(localGraphics2D);
    this.volume.paint(localGraphics2D);
    this.balance.paint(localGraphics2D);
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
      this.playlist.setLocation(i, k);
      config.setX(i);
      config.setY(j);
      break;
    case 2002:
      setState(1);
      break;
    case 2003:
      config.save();
      dispose();
      System.exit(0);
      break;
    case 2020:
      this.playlist.show();
      break;
    case 2021:
      this.playlist.hide();
    }
  }

  public void windowActivated(WindowEvent paramWindowEvent)
  {
    this.title.setEnabled(true);
  }

  public void windowClosed(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosing(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeactivated(WindowEvent paramWindowEvent)
  {
    this.title.setEnabled(false);
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

  public void keyPressed(KeyEvent paramKeyEvent)
  {
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
  }

  public void keyTyped(KeyEvent paramKeyEvent)
  {
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.Player
 * JD-Core Version:    0.6.2
 */