package com.wickedknight.fiona.event;

public class FionaEvent {
    public static final int UPDATE_DISPLAY = 2001;
    public static final int ICONIFY = 2002;
    public static final int EXIT = 2003;
    public static final int MOVE = 2004;
    public static final int PLAY = 2005;
    public static final int PAUSE = 2006;
    public static final int STOP = 2007;
    public static final int REWIND = 2008;
    public static final int FFWD = 2009;
    public static final int DRAG = 2010;
    public static final int ADD_FILE = 2011;
    public static final int SHOW_PLAYLIST = 2020;
    public static final int HIDE_PLAYLIST = 2021;
    public static final int FIRST = 2001;
    public static final int LAST = 2021;
    private Object source;
    private int type;
    private String action = "";
    private int modifiers;

    public FionaEvent() {
    }

    public FionaEvent(Object paramObject, int paramInt) {
        if (withinRange(paramInt)) {
            this.source = paramObject;
            this.type = paramInt;
        }
    }

    public FionaEvent(Object paramObject, int paramInt, String paramString) {
        if (withinRange(paramInt)) {
            this.source = paramObject;
            this.type = paramInt;
            this.action = paramString;
        }
    }

    public FionaEvent(Object paramObject, int paramInt1, String paramString, int paramInt2) {
        if (withinRange(paramInt1)) {
            this.source = paramObject;
            this.type = paramInt1;
            this.action = paramString;
            this.modifiers = paramInt2;
        }
    }

    public int getID() {
        return this.type;
    }

    public Object getSource() {
        return this.source;
    }

    private boolean withinRange(int paramInt) {
        return paramInt >= 2001;
    }
}

/*
 * Location: C:\Users\jabail\Desktop\work_folder\jad\fiona.jar Qualified Name:
 * com.wickedknight.fiona.event.FionaEvent JD-Core Version: 0.6.2
 */