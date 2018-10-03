package com.wickedknight.fiona.audio;

public class AudioInformation {
    private int length;
    private int timeRemaining;
    private int timePlayed;
    private int khz;
    private int kbps;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getKhz() {
        return khz;
    }

    public void setKhz(int khz) {
        this.khz = khz;
    }

    public int getKbps() {
        return kbps;
    }

    public void setKbps(int kbps) {
        this.kbps = kbps;
    }

    public boolean isStereo() {
        return stereo;
    }

    public void setStereo(boolean stereo) {
        this.stereo = stereo;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    private boolean stereo;
    private boolean stream;
}
