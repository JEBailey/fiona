package com.wickedknight.fiona.skin;

import java.awt.Image;
import java.util.Map;

public class Skin {
    private static final String MAIN = "main.bmp";
    private static final String BUTTONS = "cbuttons.bmp";
    private static final String TITLEBAR = "titlebar.bmp";
    private static final String NUMBERS = "numbers.bmp";
    private static final String NUMBERS_EX = "nums_ex.bmp";
    private static final String STEREO_MODE = "monoster.bmp";
    private static final String VOLUME = "volume.bmp";
    private static final String PLAY_PAUSE = "playpaus.bmp";
    private static final String PLAY_LIST = "pledit.bmp";
    private static final String SHUFFLE_REPEAT = "shufrep.bmp";
    private static final String POSITION_BAR = "posbar.bmp";
    private static final String balance = "balance.bmp";
    private static final String plinfo = "pledit.txt";
    private SkinLoader skl;
    private Map<String,Object> images;

    public Skin(String paramString) {
        this.skl = new SkinLoader(paramString);
        try {
            this.skl.loadImages();
            this.images = this.skl.toHash();
        } catch (Exception localException) {
            System.out.println(localException);
        }
        this.skl = null;
    }

    public Image getMainPanel() {
        return getImage(MAIN);
    }

    public Image getButtonPanel() {
        return getImage(BUTTONS);
    }

    public Image getTitlePanel() {
        return getImage(TITLEBAR);
    }

    public Image getNumberPanel() {
        return getImage(NUMBERS);
    }

    public Image getNumbersSmallPanel() {
        return getImage(NUMBERS_EX);
    }

    public Image getPositionBarPanel() {
        return getImage(POSITION_BAR);
    }

    public Image getStereoPanel() {
        return getImage(STEREO_MODE);
    }

    public Image getVolumePanel() {
        return getImage(VOLUME);
    }

    public Image getPlayPanel() {
        return getImage(PLAY_PAUSE);
    }

    public Image getShuffleRepeatPanel() {
        return getImage(SHUFFLE_REPEAT);
    }

    public Image getEqPlPanel() {
        return getImage(SHUFFLE_REPEAT);
    }

    public Image getPlaylistPanel() {
        return getImage(PLAY_LIST);
    }

    public Image getBalance() {
        return getImage("balance.bmp");
    }

    public String getPlaylistInfo() {
        return (String) getContent(plinfo);
    }

    private Image getImage(String paramString) {
        if (!this.images.containsKey(paramString))
            System.out.println("key " + paramString + " does not exist");
        return (Image) this.images.get(paramString);
    }

    private Object getContent(String paramString) {
        return this.images.get(paramString);
    }
}
