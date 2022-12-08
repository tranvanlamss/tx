package com.vietsoft.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public final class FileUtil {

    public static List<File> listImagesInFolder(String path) {

        List<File> images = new ArrayList<>();

        File file = new File(path);

        if (file.isFile() && isImage(file)) {
            images.add(file);
        } else if (file.isDirectory()) {

            File files[] = file.listFiles();

            for (File fileInFolder : files) {
                if (isImage(fileInFolder)) {
                    images.add(fileInFolder);
                }
            }
        }

        return images;
    }

    public static boolean isImage(File file) {
        String mimetype = new MimetypesFileTypeMap().getContentType(file);
        String type = mimetype.split("/")[0];
        if(type.equals("image"))
            return true;
        else
            return false;
    }

    public static String getContentType(File file) {
        return new MimetypesFileTypeMap().getContentType(file);
    }
    public static String hashImage(File file, String formatName) throws Exception {
        BufferedImage buffImg = ImageIO.read(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, formatName, outputStream);
        byte[] data = outputStream.toByteArray();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        return returnHex(hash);

    }
    public static String hash(File file) throws Exception {
        HashCode hash = Files.hash(file, Hashing.md5());
        return hash.toString().toUpperCase();
    }

    public static String returnHex(byte[] inBytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i=0; i < inBytes.length; i++) {
            stringBuilder.append(Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 ));
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
