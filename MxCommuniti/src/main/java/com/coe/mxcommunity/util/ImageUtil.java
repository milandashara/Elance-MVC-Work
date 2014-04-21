package com.coe.mxcommunity.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil {
    public static void cutZoomImage(String src, String dest, int w, int h) throws IOException {
        File srcFile = new File(src);  
        BufferedImage bufImg = ImageIO.read(srcFile);  
        int srcWidth =bufImg.getWidth();
        int srcHeight = bufImg.getHeight();
        
        double zoomWidth = w * 1.0 / srcWidth;
        double zoomHeight = h * 1.0 / srcHeight;
        double zoom = zoomWidth > zoomHeight ? zoomWidth : zoomHeight;
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoom, zoom), null);  
        BufferedImage imge = ato.filter(bufImg, null);
        BufferedImage tagertImge = imge.getSubimage((imge.getWidth()-w)/2, (imge.getHeight()-h)/2, w, h);
         
        ImageIO.write(tagertImge, dest.substring(dest.lastIndexOf(".")+1), new File(dest));
    }
    
    public static InputStream cutZoomImageToJPG(InputStream srcFile, int w, int h) throws IOException { 
        BufferedImage bufImg = ImageIO.read(srcFile);  
        int srcWidth = bufImg.getWidth();
        int srcHeight = bufImg.getHeight();
        
        double zoomWidth = w * 1.0 / srcWidth;
        double zoomHeight = h * 1.0 / srcHeight;
        double zoom = zoomWidth > zoomHeight ? zoomWidth : zoomHeight;
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoom, zoom), null);  
        BufferedImage imge = ato.filter(bufImg, null);
        BufferedImage tagertImge = imge.getSubimage((imge.getWidth()-w)/2, (imge.getHeight()-h)/2, w, h);    
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(tagertImge, "jpg", out);
        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());

        return input;
    }
    
    public static void cutZoomImage(InputStream srcFile, String dest, int w, int h) throws IOException { 
        BufferedImage bufImg = ImageIO.read(srcFile);  
        int srcWidth =bufImg.getWidth();
        int srcHeight = bufImg.getHeight();
        
        double zoomWidth = w * 1.0 / srcWidth;
        double zoomHeight = h * 1.0 / srcHeight;
        double zoom = zoomWidth > zoomHeight ? zoomWidth : zoomHeight;
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoom, zoom), null);  
        BufferedImage imge = ato.filter(bufImg, null);
        BufferedImage tagertImge = imge.getSubimage((imge.getWidth()-w)/2, (imge.getHeight()-h)/2, w, h);
         
        ImageIO.write(tagertImge, dest.substring(dest.lastIndexOf(".")+1), new File(dest));
    }
    
    public static void zoomImageByWidth(InputStream srcFile, String dest, int w) throws IOException { 
        BufferedImage bufImg = ImageIO.read(srcFile);  
        int srcWidth =bufImg.getWidth();
        int srcHeight = bufImg.getHeight();
        
        double zoom = w * 1.0 / srcWidth;
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoom, zoom), null);  
        BufferedImage imge = ato.filter(bufImg, null);
         
        ImageIO.write(imge, dest.substring(dest.lastIndexOf(".")+1), new File(dest));
    }
    
    public static InputStream zoomImageByWidthToJPG(InputStream srcFile, int w) throws IOException {    
        return zoomImageByWidthToJPG(srcFile, w, true);
    }
    
    public static InputStream zoomImageByWidthToJPG(InputStream srcFile, int w, boolean enlarge) throws IOException { 
        BufferedImage bufImg = ImageIO.read(srcFile);  
        int srcWidth =bufImg.getWidth();
        
        double zoom = w * 1.0 / srcWidth;
        if (!enlarge && zoom > 1.0){
        	zoom = 1.0;
        }
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(zoom, zoom), null);  
        BufferedImage imge = ato.filter(bufImg, null);
         
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(imge, "jpg", out);
        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
        
        return input;
    }
}
