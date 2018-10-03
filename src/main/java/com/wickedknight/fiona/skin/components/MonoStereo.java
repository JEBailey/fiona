package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class MonoStereo extends ActiveComponent {
    protected boolean mono = false;
    protected boolean stereo = false;
    private Rectangle monoPos = new Rectangle(210, 41, 28, 11);
    private Rectangle stereoPos = new Rectangle(239, 41, 28, 11);
    private Rectangle monoTrue = new Rectangle(29, 0, 28, 11);
    private Rectangle monoFalse = new Rectangle(29, 12, 28, 11);
    private Rectangle stereoTrue = new Rectangle(0, 0, 28, 11);
    private Rectangle stereoFalse = new Rectangle(0, 12, 28, 11);

    public MonoStereo(Skin paramSkin) {
        this.panel = paramSkin.getStereoPanel();
    }

    public void paint(Graphics2D paramGraphics2D) {
        if (this.visible) {
            copyImage(paramGraphics2D, this.monoPos, this.mono ? this.monoTrue : this.monoFalse);
            copyImage(paramGraphics2D, this.stereoPos, this.stereo ? this.stereoTrue : this.stereoFalse);
        }
    }

    public void setMono(boolean paramBoolean) {
        this.mono = paramBoolean;
        this.stereo = false;
    }

    public void setStereo(boolean paramBoolean) {
        this.stereo = paramBoolean;
        this.mono = false;
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
