package br.edu.ifsp.ogd.client;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import br.edu.ifsp.ogd.util.Constants;
import br.edu.ifsp.ogd.util.MD5Utils;

public class GhostClient {
	
	/** 
	 * This method is used to convert a pdf file to a list of images.
	 * @param fileName The file name without the extension
	 * @return the list containing the path of all the converted images
	 */
	public static ArrayList<String> pdfToImage(String fileName) {
		ArrayList<String> imageList = new ArrayList<String>();
		try {
			PDFDocument document = new PDFDocument();
			
			String fileNameComplete = Constants.GHOST_ORIGINAL_PATH + fileName + Constants.GHOST_ORIGINAL_EXTENSION;
			
			document.load(new File(fileNameComplete));

			// create our renderer, which will do the hard work
			SimpleRenderer renderer = new SimpleRenderer();

			// set resolution (in DPI)
			renderer.setResolution(Constants.GHOST_RENDERER_RESOLUTION);

			List<Image> images = renderer.render(document);

			imageList = saveLocal(fileName, images);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		
		return imageList;
	}
	
	private static ArrayList<String> saveLocal(String fileName, List<Image> imageList) {

		ArrayList<String> imagesPath = new ArrayList<String>();
		
		// write images to files to disk as PNG
		try {
			for (int i = 0; i < imageList.size(); i++) {
				// path would be something like: /results/png/
				
				// image id would be something like input
				String imageId = fileName + "_" + (i + 1);
				
				// path like /results/png/{imageId}.{ext}
				String path = Constants.GHOST_FINAL_PATH + imageId + Constants.GHOST_FINAL_EXTENSION;
				
				// write on disk
				ImageIO.write((RenderedImage) imageList.get(i), Constants.GHOST_FINAL_EXTENSION, new File(path));
				
				imagesPath.add(path);
			}
		} catch (IOException e) {
			System.out.println("An error occurred on saveLocal(): " + e.getMessage());
		}
		
		return imagesPath;
	}
}
