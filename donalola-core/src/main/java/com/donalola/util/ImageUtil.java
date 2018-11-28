package com.donalola.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    private static final int IMG_WIDTH = 250;
    private static final int IMG_HEIGHT = 250;
    private static final String PNG_PREFIX = "data:image/jpeg;base64,";


    public static String encodeToBase64(File imageFile) {
        try {
            byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
            return PNG_PREFIX + Base64.encodeBase64String(imageBytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static File decodeFromBase64(String source) {
        String[] parts = source.split(",");
        if (ArrayUtils.getLength(parts) != 2) {
            throw new IllegalArgumentException("Image Base64 bad format");
        }
        try {
            String imageStr = parts[1];
            byte[] decodedBytes = Base64.decodeBase64(imageStr);
            File imageFile = File.createTempFile("dona-lola-img", "tmp");
            FileUtils.writeByteArrayToFile(imageFile, decodedBytes);
            return imageFile;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static File resizeImage(File imageFile, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = resizeImageWithHint(originalImage, type, width, height);
            File resizedImageFile = File.createTempFile("dona-lola-img", "tmp");
            ImageIO.write(resizedImage, "jpeg", resizedImageFile);
            return resizedImageFile;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {
        return resizeImageWithHint(originalImage, type, IMG_WIDTH, IMG_HEIGHT);
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height) {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

}
