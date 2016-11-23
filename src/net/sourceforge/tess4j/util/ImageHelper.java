/**
 * Copyright @ 2008 Quan Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.tess4j.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.*;
import javax.imageio.IIOImage;

public class ImageHelper {

	/**
	 * Convenience method that returns a scaled instance of the provided
	 * {@code BufferedImage}.
	 *
	 * @param image
	 *            the original image to be scaled
	 * @param targetWidth
	 *            the desired width of the scaled instance, in pixels
	 * @param targetHeight
	 *            the desired height of the scaled instance, in pixels
	 * @return a scaled version of the original {@code BufferedImage}
	 */
	public static BufferedImage getScaledInstance(BufferedImage image, int targetWidth, int targetHeight) {
		int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, type);
		Graphics2D g2 = tmp.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.drawImage(image, 0, 0, targetWidth, targetHeight, null);
		g2.dispose();
		return tmp;
	}

	/**
	 * Convenience method that returns a scaled instance of the provided
	 * {@code IIOImage}.
	 *
	 * @param iioSource
	 *            the original image to be scaled
	 * @param scale
	 *            the desired scale
	 * @return a scaled version of the original {@code IIOImage}
	 */
	public static IIOImage getScaledInstance(IIOImage iioSource, float scale) {
		if (!(iioSource.getRenderedImage() instanceof BufferedImage)) {
			throw new IllegalArgumentException("RenderedImage in IIOImage must be BufferedImage");
		}

		if (Math.abs(scale - 1.0) < 0.001) {
			return iioSource;
		}

		BufferedImage source = (BufferedImage) iioSource.getRenderedImage();
		BufferedImage target = getScaledInstance(source, (int) (scale * source.getWidth()),
				(int) (scale * source.getHeight()));
		return new IIOImage(target, null, null);
	}

	/**
	 * A replacement for the standard <code>BufferedImage.getSubimage</code>
	 * method.
	 *
	 * @param image
	 * @param x
	 *            the X coordinate of the upper-left corner of the specified
	 *            rectangular region
	 * @param y
	 *            the Y coordinate of the upper-left corner of the specified
	 *            rectangular region
	 * @param width
	 *            the width of the specified rectangular region
	 * @param height
	 *            the height of the specified rectangular region
	 * @return a BufferedImage that is the subimage of <code>image</code>.
	 */
	public static BufferedImage getSubImage(BufferedImage image, int x, int y, int width, int height) {
		int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage tmp = new BufferedImage(width, height, type);
		Graphics2D g2 = tmp.createGraphics();
		g2.drawImage(image.getSubimage(x, y, width, height), 0, 0, null);
		g2.dispose();
		return tmp;
	}

	/**
	 * A simple method to convert an image to binary or B/W image.
	 *
	 * @param image
	 *            input image
	 * @return a monochrome image
	 */
	public static BufferedImage convertImageToBinary(BufferedImage image) {
		BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g2 = tmp.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return tmp;
	}

	/**
	 * A simple method to convert an image to binary or B/W image.
	 *
	 * @param image
	 *            input image
	 * @return a monochrome image
	 * @deprecated As of release 1.1, renamed to
	 *             {@link #convertImageToBinary(BufferedImage image)}
	 */
	@Deprecated
	public static BufferedImage convertImage2Binary(BufferedImage image) {
		return convertImageToBinary(image);
	}

	/**
	 * A simple method to convert an image to gray scale.
	 *
	 * @param image
	 *            input image
	 * @return a monochrome image
	 */
	public static BufferedImage convertImageToGrayscale(BufferedImage image) {
		BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2 = tmp.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return tmp;
	}

	private static final short[] invertTable;

	static {
		invertTable = new short[256];
		for (int i = 0; i < 256; i++) {
			invertTable[i] = (short) (255 - i);
		}
	}

	/**
	 * Inverts image color.
	 *
	 * @param image
	 *            input image
	 * @return an inverted-color image
	 */
	public static BufferedImage invertImageColor(BufferedImage image) {
		BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		BufferedImageOp invertOp = new LookupOp(new ShortLookupTable(0, invertTable), null);
		return invertOp.filter(image, tmp);
	}

	/**
	 * Rotates an image.
	 *
	 * @param image
	 *            the original image
	 * @param angle
	 *            the degree of rotation
	 * @return a rotated image
	 */
	public static BufferedImage rotateImage(BufferedImage image, double angle) {
		double theta = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(theta));
		double cos = Math.abs(Math.cos(theta));
		int w = image.getWidth();
		int h = image.getHeight();
		int newW = (int) Math.floor(w * cos + h * sin);
		int newH = (int) Math.floor(h * cos + w * sin);

		BufferedImage tmp = new BufferedImage(newW, newH, image.getType());
		Graphics2D g2d = tmp.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.translate((newW - w) / 2, (newH - h) / 2);
		g2d.rotate(theta, w / 2, h / 2);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return tmp;
	}

	/**
	 * Gets an image from Clipboard.
	 *
	 * @return image
	 */
	public static Image getClipboardImage() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			return (Image) clipboard.getData(DataFlavor.imageFlavor);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Clones an image.
	 * http://stackoverflow.com/questions/3514158/how-do-you-clone-a-
	 * bufferedimage
	 *
	 * @param bi
	 * @return
	 */
	public static BufferedImage cloneImage(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage replaceWithWhiteColor(BufferedImage bi) {

		int[] rgb = new int[3];

		int width = bi.getWidth();

		int height = bi.getHeight();

		int minx = bi.getMinX();

		int miny = bi.getMinY();

		/**
		 * 
		 * 遍历图片的像素，为处理图片上的杂色，所以要把指定像素上的颜色换成目标白色 用二层循环遍历长和宽上的每个像素
		 * 
		 */

		int hitCount = 0;

		for (int i = minx; i < width - 1; i++) {

			for (int j = miny; j < height; j++) {

				/**
				 * 
				 * 得到指定像素（i,j)上的RGB值，
				 * 
				 */

				int pixel = bi.getRGB(i, j);

				int pixelNext = bi.getRGB(i + 1, j);

				/**
				 * 
				 * 分别进行位操作得到 r g b上的值
				 * 
				 */

				rgb[0] = (pixel & 0xff0000) >> 16;

				rgb[1] = (pixel & 0xff00) >> 8;

				rgb[2] = (pixel & 0xff);

				/**
				 * 
				 * 进行换色操作，我这里是要换成白底，那么就判断图片中rgb值是否在范围内的像素
				 * 
				 */

				// 经过不断尝试，RGB数值相互间相差15以内的都基本上是灰色，

				// 对以身份证来说特别是介于73到78之间，还有大于100的部分RGB值都是干扰色，将它们一次性转变成白色

				if ((Math.abs(rgb[0] - rgb[1]) < 15)

						&& (Math.abs(rgb[0] - rgb[2]) < 15)

						&& (Math.abs(rgb[1] - rgb[2]) < 15) &&

						(((rgb[0] > 73) && (rgb[0] < 78)) || (rgb[0] > 100))) {

					// 进行换色操作,0xffffff是白色

					bi.setRGB(i, j, 0xffffff);

				}

			}

		}

		return bi;

	}
	
	
	public static BufferedImage keithTest(BufferedImage bufferedImage){

        int h = bufferedImage.getHeight();  
        int w = bufferedImage.getWidth();  
  
        // 灰度化  
        int[][] gray = new int[w][h];  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                int argb = bufferedImage.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);  
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);  
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);  
                if (r >= 255)  
                {  
                    r = 255;  
                }  
                if (g >= 255)  
                {  
                    g = 255;  
                }  
                if (b >= 255)  
                {  
                    b = 255;  
                }  
                gray[x][y] = (int) Math  
                        .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)  
                                * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);  
            }  
        }  
  
        // 二值化  
        int threshold = ostu(gray, w, h);  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,  
                BufferedImage.TYPE_BYTE_BINARY);  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                if (gray[x][y] > threshold)  
                {  
                    gray[x][y] |= 0x00FFFF;  
                } else  
                {  
                    gray[x][y] &= 0xFF0000;  
                }  
                binaryBufferedImage.setRGB(x, y, gray[x][y]);  
            }  
        }  
  
        // 矩阵打印  
        for (int y = 0; y < h; y++)  
        {  
            for (int x = 0; x < w; x++)  
            {  
                if (isBlack(binaryBufferedImage.getRGB(x, y)))  
                {  
                    System.out.print("*");  
                } else  
                {  
                    System.out.print(" ");  
                }  
            }  
            System.out.println();  
        }  
  
        return binaryBufferedImage;  
    }
	
	
	public static void keithTest001(BufferedImage bufferedImage){
		
		int maxR = 0;
		int minR = 255;
		int maxG = 0;
		int minG = 255;
		int maxB = 0;
		int minB = 255;
		int maxSumRGB = 0;
		int minSumRGB = 765;
		
        int h = bufferedImage.getHeight();  
        int w = bufferedImage.getWidth();  
  

        
        for (int y = 0; y < h; y++)  
        {  
            for (int x = 0; x < w; x++)  
            {  
            	
                int argb = bufferedImage.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF));
                if(r>maxR)maxR = r;
                if(r<minR)minR = r;
                int g = (int) (((argb >> 8) & 0xFF));
                if(g>maxG)maxG = g;
                if(g<minG)minG = g;
                int b = (int) (((argb >> 0) & 0xFF));
                if(r>maxB)maxB = b;
                if(r<minB)minB = b;
            	
                int sumRGB = r + g + b;
                if(sumRGB>maxSumRGB)maxSumRGB = sumRGB;
                if(sumRGB<minSumRGB)minSumRGB = sumRGB;
                
                System.out.print("["+r+" "+g+" "+b+"]");
                
                	
            }  
            System.out.println();  
        }  
		
		
		System.out.println(String.format("minR:%d, minG:%d, minB:%d", minR,minG,minB));
		System.out.println(String.format("maxR:%d, maxG:%d, maxB:%d", maxR,maxG,maxB));
		System.out.println(String.format("minSumRGB:%d, maxSumRGB:%d", minSumRGB,maxSumRGB));
		
	}
	
	
	
	
	public static BufferedImage keithTest002(BufferedImage bufferedImage,int vsRGB){
		
		int maxR = 0;
		int minR = 255;
		int maxG = 0;
		int minG = 255;
		int maxB = 0;
		int minB = 255;
		int maxSumRGB = 0;
		int minSumRGB = 765;
		
        int h = bufferedImage.getHeight();  
        int w = bufferedImage.getWidth();  
  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,  
                BufferedImage.TYPE_BYTE_BINARY);  
        
        for (int y = 0; y < h; y++)  
        {  
            for (int x = 0; x < w; x++)  
            {  
            	
                int argb = bufferedImage.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF));
                if(r>maxR)maxR = r;
                if(r<minR)minR = r;
                int g = (int) (((argb >> 8) & 0xFF));
                if(g>maxG)maxG = g;
                if(g<minG)minG = g;
                int b = (int) (((argb >> 0) & 0xFF));
                if(r>maxB)maxB = b;
                if(r<minB)minB = b;
            	
                int sumRGB = r + g + b;
                if(sumRGB>maxSumRGB)maxSumRGB = sumRGB;
                if(sumRGB<minSumRGB)minSumRGB = sumRGB;
                
                if(sumRGB<=vsRGB){
                	binaryBufferedImage.setRGB(x, y, 0x000000);
                }else{
                	binaryBufferedImage.setRGB(x, y, 0xFFFFFF);
                }
                
                System.out.print("["+r+" "+g+" "+b+"]");
                
                	
            }  
            System.out.println();  
        }  
		
		
		System.out.println(String.format("minR:%d, minG:%d, minB:%d", minR,minG,minB));
		System.out.println(String.format("maxR:%d, maxG:%d, maxB:%d", maxR,maxG,maxB));
		System.out.println(String.format("minSumRGB:%d, maxSumRGB:%d", minSumRGB,maxSumRGB));
		
		return binaryBufferedImage;
		
	}

	
	

	
    	


	public static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
			return true;
		}
		return false;
	}

	public static boolean isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
			return true;
		}
		return false;
	}

	public static int isBlackOrWhite(int colorInt) {
		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
			return 1;
		}
		return 0;
	}

	public static int getColorBright(int colorInt) {
		Color color = new Color(colorInt);
		return color.getRed() + color.getGreen() + color.getBlue();
	}

	public static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}

		// Total number of pixels
		int total = w * h;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;

			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		return threshold;
	}

}
