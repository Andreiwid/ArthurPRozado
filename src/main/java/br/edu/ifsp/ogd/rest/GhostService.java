package br.edu.ifsp.ogd.rest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.edu.ifsp.ogd.client.GhostClient;
import br.edu.ifsp.ogd.util.Constants;

@Path(Constants.REST_GHOST_PATH)
public class GhostService {

	/**
	 * This method is used to convert a pdf file to a list of images, which will be later
	 * returned to the user in order to pick which images will be converted by Tabula Extractor.
	 * 
	 * It does expects that the file will be inside the /results/pdf/ directory, so make sure you
	 * have successfully added the file into the directory, otherwise it will return an empty array,
	 * meaning that the conversion was not done properly.
	 * 
	 * 
	 * @param fileName This parameter specifies what is the file name inside the /results/pdf directory. Be
	 * 					sure to specify only the file name, without its extension.
	 * 
	 * @return This method returns the converted images in a String array format, so all the images and its absolutes
	 * 			paths will be inside the returned String array.
	 * 
	 * Example usage:
	 * 	Request: GET /convert?fileName=exampleFile
	 * 	Response: ["filename_1.png", "filename_2.png", "filename_3.png", ..., "filename_{n}.png"]
	 * 
	 */
	@GET
	@Path("/convert")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> pdfToImages(@QueryParam("fileName") String fileName) {
		ArrayList<String> imageList = GhostClient.pdfToImage(fileName);
		
		return imageList;
	}
}
