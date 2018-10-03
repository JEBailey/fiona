package com.wickedknight.fiona.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

public class OrbisPlayer implements Runnable {

    private String path;

    public void sound(File fileplay) {
        String af = fileplay.toString();
        path = af;
        initVorbis();

    }

    Thread player = null;
    InputStream bitStream = null;

    static final int BUFSIZE = 4096 * 2;
    static int convsize = BUFSIZE * 2;
    byte[] convbuffer = new byte[convsize];


    boolean icestats = false;

    SyncState sync;
    StreamState stream;
    Page page;
    Packet packet;
    Info info;
    Comment vc;
    DspState vd;
    Block vb;

    byte[] buffer = null;
    int bytes = 0;

    int format;
    int rate = 0;
    int channels = 0;
    int leftVolScale = 100;
    int rightVolScale = 100;
    SourceDataLine outputLine = null;
    String currentSource = null;

    int frameSizeInBytes;
    int bufferLengthInBytes;

    boolean playonstartup = true;

    public void start() {
        if (playonstartup) {
            if (player == null) {
                playSound();
            } else {
                stopSound();
            }
        }
    }

    void initVorbis() {
        sync = new SyncState();
        stream = new StreamState();
        page = new Page();
        packet = new Packet();

        info = new Info();
        vc = new Comment();
        vd = new DspState();
        vb = new Block(vd);

        buffer = null;
        bytes = 0;

        sync.init();
    }

    SourceDataLine getOutputLine(int channels, int rate) {
        if (outputLine == null || this.rate != rate || this.channels != channels) {
            if (outputLine != null) {
                outputLine.drain();
                outputLine.stop();
                outputLine.close();
            }
            init_audio(channels, rate);
            outputLine.start();
        }
        return outputLine;
    }

    void init_audio(int channels, int rate) {
        try {
            AudioFormat audioFormat = new AudioFormat((float) rate, 16, channels, true, // PCM_Signed
                    false // littleEndian
            );
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            if (!AudioSystem.isLineSupported(info)) {
                return;
            }

            try {
                outputLine = (SourceDataLine) AudioSystem.getLine(info);
                outputLine.open(audioFormat);
            } catch (LineUnavailableException ex) {
                System.out.println("Unable to open the sourceDataLine: " + ex);
                return;
            } catch (IllegalArgumentException ex) {
                System.out.println("Illegal Argument: " + ex);
                return;
            }

            frameSizeInBytes = audioFormat.getFrameSize();
            int bufferLengthInFrames = outputLine.getBufferSize() / frameSizeInBytes / 2;
            bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;

            this.rate = rate;
            this.channels = channels;
        } catch (Exception ee) {
            System.out.println("Error in Line 248 " + ee);
        }
    }

    public void run() {
        Thread me = Thread.currentThread();
        String item = path;

        while (true) {

            bitStream = selectSource(item);
            if (bitStream != null) {
                playStream(me);
            }
            if (player != me) {
                break;
            }
            bitStream = null;

        }
        player = null;
    }

    private void playStream(Thread me) {
        boolean chained = false;

        initVorbis();

        loop: while (true) {
            int eos = 0;

            int index = sync.buffer(BUFSIZE);
            buffer = sync.data;
            try {
                bytes = bitStream.read(buffer, index, BUFSIZE);
            } catch (Exception e) {
                System.err.println("Error in Line 301 " + e);
                return;
            }
            sync.wrote(bytes);

            if (chained) {
                chained = false;
            } else {
                if (sync.pageout(page) != 1) {
                    if (bytes < BUFSIZE)
                        break;
                    System.err.println("Input does not appear to be an Ogg bitstream.");
                    return;
                }
            } 
            stream.init(page.serialno());
            stream.reset();

            info.init();
            vc.init();

            if (stream.pagein(page) < 0) {
                // error; stream version mismatch perhaps
                System.err.println("Error reading first page of Ogg bitstream data.");
                return;
            }


            if (stream.packetout(packet) != 1) {
                // no page? must not be vorbis
                System.err.println("Error reading initial header packet.");
                break;
            }

            if (info.synthesis_headerin(vc, packet) < 0) {
                // error case; not a vorbis header
                System.err.println("This Ogg bitstream does not contain Vorbis audio data.");
                return;
            }

            int i = 0;

            while (i < 2) {
                while (i < 2) {
                    int result = sync.pageout(page);
                    if (result == 0)
                        break; // Need more data
                    if (result == 1) {
                        stream.pagein(page);
                        while (i < 2) {
                            result = stream.packetout(packet);
                            if (result == 0)
                                break;
                            if (result == -1) {
                                System.err.println("Corrupt secondary header.  Exiting.");
                                break loop;
                            }
                            info.synthesis_headerin(vc, packet);
                            i++;
                        }
                    }
                }

                index = sync.buffer(BUFSIZE);
                buffer = sync.data;
                try {
                    bytes = bitStream.read(buffer, index, BUFSIZE);
                } catch (Exception e) {
                    System.err.println("Error in Line 369 " + e);
                    return;
                }
                if (bytes == 0 && i < 2) {
                    System.err.println("End of file before finding all Vorbis headers!");
                    return;
                }
                sync.wrote(bytes);
            }

            {
                byte[][] ptr = vc.user_comments;
                StringBuffer sb = null;

                for (int j = 0; j < ptr.length; j++) {
                    if (ptr[j] == null)
                        break;
                    System.err.println("Comment: " + new String(ptr[j], 0, ptr[j].length - 1));
                    if (sb != null)
                        sb.append(" " + new String(ptr[j], 0, ptr[j].length - 1));
                }
                System.err.println("Bitstream is " + info.channels + " channel, " + info.rate + "Hz");
                System.err.println("Encoded by: " + new String(vc.vendor, 0, vc.vendor.length - 1) + "\n");
            }

            convsize = BUFSIZE / info.channels;

            vd.synthesis_init(info);
            vb.init(vd);

            double[][][] _pcm = new double[1][][];
            float[][][] _pcmf = new float[1][][];
            int[] _index = new int[info.channels];

            getOutputLine(info.channels, info.rate);

            while (eos == 0) {
                while (eos == 0) {
                    if (player != me) {
                        try {
                            bitStream.close();
                        } catch (Exception ee) {
                            System.out.println("Error in Line 394 orbisplayer ");
                        }
                        return;
                    }

                    int result = sync.pageout(page);
                    if (result == 0)
                        break; // need more data
                    if (result == -1) { // missing or corrupt data at this page position
                        // System.err.println("Corrupt or missing data in bitstream; continuing...");
                    } else {
                        stream.pagein(page);

                        if (page.granulepos() == 0) { 
                            chained = true; 
                            eos = 1; 
                            break; 
                        } //

                        while (true) {
                            result = stream.packetout(packet);
                            if (result == 0)
                                break; // need more data
                            if (result == -1) { // missing or corrupt data at this page position
                                // no reason to complain; already complained above

                                // System.err.println("no reason to complain; already complained above");
                            } else {
                                // we have a packet. Decode it
                                int samples;
                                if (vb.synthesis(packet) == 0) { // test for success!
                                    vd.synthesis_blockin(vb);
                                }
                                while ((samples = vd.synthesis_pcmout(_pcmf, _index)) > 0) {
                                    double[][] pcm = _pcm[0];
                                    float[][] pcmf = _pcmf[0];
                                    int bout = (samples < convsize ? samples : convsize);

                                    // convert doubles to 16 bit signed ints (host order) and
                                    // interleave
                                    for (i = 0; i < info.channels; i++) {
                                        int ptr = i * 2;
                                        int mono = _index[i];
                                        for (int j = 0; j < bout; j++) {
                                            int val = (int) (pcmf[i][mono + j] * 32767.);
                                            if (val > 32767) {
                                                val = 32767;
                                            }
                                            if (val < -32768) {
                                                val = -32768;
                                            }
                                            if (val < 0) {
                                                val = val | 0x8000;
                                            }
                                            convbuffer[ptr] = (byte) (val);
                                            convbuffer[ptr + 1] = (byte) (val >>> 8);
                                            ptr += 2 * (info.channels);
                                        }
                                    }
                                    outputLine.write(convbuffer, 0, 2 * info.channels * bout);
                                    vd.synthesis_read(bout);
                                }
                            }
                        }
                        if (page.eos() != 0)
                            eos = 1;
                    }
                }

                if (eos == 0) {
                    index = sync.buffer(BUFSIZE);
                    buffer = sync.data;
                    try {
                        bytes = bitStream.read(buffer, index, BUFSIZE);
                    } catch (Exception e) {
                        System.err.println("Error in Line 490 " + e);
                        return;
                    }
                    if (bytes == -1) {
                        break;
                    }
                    sync.wrote(bytes);
                    if (bytes == 0)
                        eos = 1;
                }
            }

            stream.clear();
            vb.clear();
            vd.clear();
            info.clear();
        }

        sync.clear();

        try {
            if (bitStream != null)
                bitStream.close();
        } catch (Exception e) {
            System.out.println("Error in Line 496 orbisplayer ");
        }
    }

    public void stop() {
        if (player == null) {
            try {
                outputLine.drain();
                outputLine.stop();
                outputLine.close();
                if (bitStream != null)
                    bitStream.close();
            } catch (Exception e) {
                System.err.println("Error in Line 603 OrbisPlayer" + e);

                stopSound();

            }
        }
        player = null;
    }

    public void playSound() {
        if (player != null) {
            return;
        }
        player = new Thread(this);
        player.start();
    }

    public void stopSound() {
        if (player == null) {
            return;
        }
        player = null;
    }

    InputStream selectSource(String item) {

        InputStream is = null;
        URLConnection urlc = null;
        try {
            URL url = null;
            item = path;

            url = new URL(item);
            urlc = url.openConnection();
            is = urlc.getInputStream();

        } catch (Exception ee) {
            System.err.println("Error in Line 694 Orbisplayer" + ee);
        }

        if (is == null) {
            try {
                is = new FileInputStream(path);
                currentSource = null;
            } catch (Exception ee) {
                System.err.println("Error in Line 702 " + ee);
            }
        }

        return is;
    }

}
