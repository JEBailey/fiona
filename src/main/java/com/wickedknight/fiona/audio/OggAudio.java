package com.wickedknight.fiona.audio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OggAudio implements ActionListener, AudioComponent {
    private Thread player = null;
    InputStream bitStream = null;
    static final int BUFSIZE = 8192;
    static int convsize = 16384;
    static byte[] convbuffer = new byte[convsize];
    String playlistfile = "playlist";

    byte[] buffer = null;
    int bytes = 0;
    int format;
    int rate = 0;
    int channels = 0;
    int left_vol_scale = 100;
    int right_vol_scale = 100;
    SourceDataLine outputLine = null;
    String current_source = null;
    int frameSizeInBytes;
    int bufferLengthInBytes;
    JPanel panel;

    JButton stats_button;

    public void init() {
    }




    public void stop() {
        if (this.player == null)
            try {
                this.outputLine.drain();
                this.outputLine.stop();
                this.outputLine.close();
                if (this.bitStream != null)
                    this.bitStream.close();
            } catch (Exception localException) {
                System.out.println("number 4");
            }
        this.player = null;
    }

    public void actionPerformed(ActionEvent paramActionEvent) {
        if (paramActionEvent.getSource() == this.stats_button);
            
    }


    public void stop_sound() {
        if (this.player == null)
            return;
        this.player = null;
    }


    public boolean pressFfwd() {
        return true;
    }

    public AudioInformation getInformation() {
        return null;
    }

    public boolean pressPause() {
        return true;
    }

    public boolean pressPlay() {
        return true;
    }

    public boolean pressRwnd() {
        return true;
    }

    public boolean pressSeek(long paramLong) {
        return true;
    }

    public boolean pressStop() {
        return true;
    }

    public void setInputStream(InputStream paramInputStream) {
        this.bitStream = paramInputStream;
    }

    public void setPlayer(Thread paramThread) {
        this.player = paramThread;
    }
}