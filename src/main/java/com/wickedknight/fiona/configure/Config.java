package com.wickedknight.fiona.configure;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class Config {
    private static String home = System.getProperty("user.home");
    private static final String fionaDir = ".fiona";
    private static String propFile = "fiona.config";
    private static File configFile;
    private static Config config = null;
    private Properties props = new Properties();
    public static int ATTACH_PLAYLIST = 1;
    private static final String URL = "url";
    private static final String DIR = "dir";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String SKIN = "skin";
    private static final String EXTENSIONS = "extensions";
    private static final String PLAYLIST = "playlist";
    private static final String PROXY_SERVER = "proxy.server";
    private static final String PROXY_PORT = "proxy.port";
    private static final String PROXY_LOGIN = "proxy.login";
    private static final String PROXY_PASSWORD = "proxy.password";
    private static final String SHUFFLE_ENABLED = "shuffle";
    private static final String REPEAT_ENABLED = "repeat";
    private static final String EQUALIZER_ENABLED = "equalizer";
    private static final String PLAYLIST_ENABLED = "pl";
    private static final String PLAYLIST_SIZE = "pl.size";
    private static final String PLAYLIST_ATTCH = "pl.attched";
    private static final String VOLUME = "volume";
    private String extensions = "mp3,ogg";
    private String url = "";
    private String dir = "";
    private String skin = "";
    private String plfilename = "fiona.m3u";
    private int x = 10;
    private int y = 10;
    private String proxyServer = "";
    private String proxyLogin = "";
    private String proxyPassword = "";
    private int proxyPort = -1;
    private boolean pl = false;
    private boolean shuffle = false;
    private boolean repeat = false;
    private boolean equalizer = false;
    private int[] location = { 120, 120 };

    private Config() {
        init();
    }

    public static Config getInstance() {
        if (config == null)
            config = new Config();
        return config;
    }

    public boolean getBoolean(String paramString) {
        return true;
    }

    public boolean getBoolean(String paramString1, String paramString2) {
        return true;
    }

    private void init() {
        File localFile = new File(home, fionaDir);
        System.out.println(localFile.getAbsolutePath());
        configFile = new File(localFile, propFile);
        if (!localFile.exists())
            try {
                if (!localFile.mkdir()) {
                    System.out.println("unable to create .fiona directory in");
                    System.out.println(localFile.getAbsolutePath());
                    System.exit(1);
                }
            } catch (Exception localException1) {
                System.out.println("unable to create .fiona directory in");
                System.out.println(localFile.getAbsolutePath());
                System.out.println(localException1);
                System.exit(1);
            }
        if (!localFile.isDirectory()) {
            System.out.println("config directory is not a directory");
            System.out.println(localFile.getAbsolutePath());
            System.exit(1);
        }
        if (!configFile.exists())
            try {
                configFile.createNewFile();
                createConfig(configFile);
            } catch (Exception localException2) {
                System.exit(1);
            }
        try {
            this.props.load(new FileInputStream(configFile));
        } catch (Exception localException3) {
            System.exit(1);
        }
    }

    private void createConfig(File paramFile) throws Exception {
        this.props.put("url", "");
        this.props.put("dir", System.getProperty("user.home"));
        this.props.put("x", "120");
        this.props.put("y", "120");
        this.props.put(SKIN, "skins/metrix.wsz");
        this.props.put(EXTENSIONS, "mp3,ogg");
        this.props.put(VOLUME, "100");
        this.props.store(new FileOutputStream(paramFile), "#config file");
    }

    private void loadConfig(File paramFile) throws Exception {
        this.props.load(new FileInputStream(paramFile));
    }

    private String getString(boolean paramBoolean) {
        return Boolean.toString(paramBoolean);
    }

    private String getString(int[] paramArrayOfInt) {
        StringBuilder localStringBuffer = new StringBuilder();
        localStringBuffer.append("{ ");
        for (int i = 0; i < paramArrayOfInt.length - 1; i++) {
            localStringBuffer.append(paramArrayOfInt[i]);
            localStringBuffer.append(", ");
        }
        localStringBuffer.append(paramArrayOfInt[(paramArrayOfInt.length - 1)]);
        localStringBuffer.append(" }");
        return localStringBuffer.toString();
    }

    public int getX() {
        return Integer.parseInt(this.props.getProperty("x"));
    }

    public int getY() {
        return Integer.parseInt(this.props.getProperty("y"));
    }

    public int getVolume() {
        return Integer.parseInt(this.props.getProperty(VOLUME, "100"));
    }

    public void setX(int paramInt) {
        this.props.put(X, String.valueOf(paramInt));
    }

    public void setY(int paramInt) {
        this.props.put(Y, String.valueOf(paramInt));
    }

    public void setVolume(int paramInt) {
        this.props.put(VOLUME, String.valueOf(paramInt));
    }

    public String getSkin() {
        return this.props.getProperty("skin");
    }

    public void setPlayerPos(Point paramPoint) {
        this.props.put("player_position", convert(paramPoint));
    }

    public void setPlayListPos(Point paramPoint) {
        this.props.put("playlist_position", convert(paramPoint));
    }

    public void save() {
        try {
            this.props.store(new FileOutputStream(configFile), "#config file");
        } catch (Exception localException) {
            System.exit(1);
        }
    }

    public void finalize() throws Throwable {
        save();
        super.finalize();
    }

    private String convert(boolean paramBoolean) {
        return String.valueOf(paramBoolean);
    }

    private String convert(int paramInt) {
        return String.valueOf(paramInt);
    }

    private String convert(Point paramPoint) {
        StringBuilder localStringBuffer = new StringBuilder();
        localStringBuffer.append("{ ");
        localStringBuffer.append(paramPoint.getX());
        localStringBuffer.append(" , ");
        localStringBuffer.append(paramPoint.getY());
        localStringBuffer.append(" }");
        return localStringBuffer.toString();
    }

    private Point toPoint(String paramString) {
        int i = paramString.indexOf('{');
        int j = paramString.indexOf(',');
        int k = paramString.indexOf('}');
        int m = Integer.parseInt(paramString.substring(i + 1, j));
        int n = Integer.parseInt(paramString.substring(j + 1, k));
        return new Point(m, n);
    }
}

/*
 * Location: C:\Users\jabail\Desktop\work_folder\jad\fiona.jar Qualified Name:
 * com.wickedknight.fiona.configure.Config JD-Core Version: 0.6.2
 */