//package nl.dias.utils;
//
//import java.awt.HeadlessException;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import org.apache.log4j.Logger;
//import org.apache.pdfbox.exceptions.InvalidPasswordException;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.util.PDFImageWriter;
//
//public final class PDFToImage {
//	private static Logger log = Logger.getLogger(PDFToImage.class);
//
//	// private static final String PASSWORD = "-password";
//	// private static final String START_PAGE = "-startPage";
//	// private static final String END_PAGE = "-endPage";
//	// private static final String IMAGE_FORMAT = "-imageType";
//	// private static final String OUTPUT_PREFIX = "-outputPrefix";
//	// private static final String COLOR = "-color";
//	// private static final String RESOLUTION = "-resolution";
//
//	private PDFToImage() {
//	}
//
//	public static void converteer(String pdfFile, String outputPrefixIn) {
//		String password = "";
//		String imageFormat = "jpg";
//		int startPage = 1;
//		int endPage = Integer.MAX_VALUE;
//		String color = "rgb";
//		int resolution;
//		String outputPrefix = outputPrefixIn;
//		try {
//			resolution = Toolkit.getDefaultToolkit().getScreenResolution();
//		} catch (HeadlessException e) {
//			resolution = 96;
//		}
//
//		if (outputPrefix == null) {
//			outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
//		}
//
//		PDDocument document = null;
//		try {
//			document = PDDocument.load(pdfFile);
//			// document.print();
//
//			if (document.isEncrypted()) {
//				try {
//					document.decrypt(password);
//				} catch (InvalidPasswordException e) {
//				}
//			}
//			int imageType = 24;
//			if ("bilevel".equalsIgnoreCase(color)) {
//				imageType = BufferedImage.TYPE_BYTE_BINARY;
//			} else if ("indexed".equalsIgnoreCase(color)) {
//				imageType = BufferedImage.TYPE_BYTE_INDEXED;
//			} else if ("gray".equalsIgnoreCase(color)) {
//				imageType = BufferedImage.TYPE_BYTE_GRAY;
//			} else if ("rgb".equalsIgnoreCase(color)) {
//				imageType = BufferedImage.TYPE_INT_RGB;
//			} else if ("rgba".equalsIgnoreCase(color)) {
//				imageType = BufferedImage.TYPE_INT_ARGB;
//			} else {
//				log.error("Error: the number of bits per pixel must be 1, 8 or 24.");
//			}
//
//			// Make the call
//			PDFImageWriter imageWriter = new PDFImageWriter();
//			boolean success = imageWriter.writeImage(document, imageFormat, password, startPage, endPage, outputPrefix, imageType, resolution);
//			if (!success) {
//				log.error("Error: no writer found for image format '" + imageFormat + "'");
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		} finally {
//			if (document != null) {
//				try {
//					document.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
// }
