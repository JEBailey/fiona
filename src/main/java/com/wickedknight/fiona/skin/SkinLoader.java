package com.wickedknight.fiona.skin;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.wickedknight.image.BMP;

public class SkinLoader {
    private Map<String,Object> images = new HashMap<String,Object>();
    private ZipInputStream zis = null;

    public SkinLoader(String paramString) {
        try {
            if (paramString.toLowerCase().startsWith("http"))
                this.zis = new ZipInputStream(new URL(paramString).openStream());
            else
                this.zis = new ZipInputStream(new FileInputStream(paramString));
        } catch (Exception localException) {
            ClassLoader localClassLoader = getClass().getClassLoader();
            InputStream localInputStream = localClassLoader.getResourceAsStream("skins/metrix.wsz");
            if (localInputStream != null)
                this.zis = new ZipInputStream(localInputStream);
        }
    }

    public SkinLoader(InputStream paramInputStream) {
        this.zis = new ZipInputStream(paramInputStream);
    }

    public boolean loadImages() throws Exception {
        ZipEntry localZipEntry = zis.getNextEntry();
        while (localZipEntry != null){
            String str = localZipEntry.getName().toLowerCase();
            int i = str.lastIndexOf("/");
            if (i != -1) {
                str = str.substring(i + 1);
            }
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arrayOfByte = new byte[256];
            while (true) {
                int j = this.zis.read(arrayOfByte);
                if (j == -1)
                    break;
                localByteArrayOutputStream.write(arrayOfByte, 0, j);
            }
            localByteArrayOutputStream.close();
            if (str.matches(".*[bmp|BMP]")) {
                BMP localBMP = new BMP();
                this.images.put(str,
                        localBMP.getBMPImage(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray())));
            } else if (str.matches(".*txt")) {
                this.images.put(str, new String(localByteArrayOutputStream.toByteArray()));
            }
            localZipEntry = zis.getNextEntry();
        }
        this.zis.close();
        this.zis = null;
        return true;
    }

    public Image getImage(String paramString) {
        if (!this.images.containsKey(paramString))
            System.out.println("key " + paramString + " does not exist");
        return (Image) this.images.get(paramString);
    }

    public Object getContent(String paramString) {
        return this.images.get(paramString);
    }

    public Map<String,Object> toHash() {
        return this.images;
    }
}

/*
 * Location: C:\Users\jabail\Desktop\work_folder\jad\fiona.jar Qualified Name:
 * com.wickedknight.fiona.skin.SkinLoader JD-Core Version: 0.6.2
 */