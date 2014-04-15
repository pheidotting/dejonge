//package nl.dias.servlet;
//
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.imageio.ImageIO;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import nl.dias.domein.Bijlage;
//import nl.dias.service.BijlageService;
//import nl.dias.utils.PDFToImage;
//import nl.dias.utils.Utils;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.log4j.Logger;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//@WebServlet("/Upload")
//// @MultipartConfig
//public class Uploaden extends HttpServlet {
//	private static final long serialVersionUID = -3209661083757370257L;
//	private Logger logger = Logger.getLogger(this.getClass());
//
//	// private ServletFileUpload uploader = null;
//	private File uploadFolder;
//	@EJB
//	private BijlageService bijlageService;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");
//
//		PrintWriter out = response.getWriter();
//		uploadFolder = new File(Utils.getUploadPad() + File.pathSeparator + "DIAS" + File.pathSeparator + Utils.getOmgeving() + File.pathSeparator);
//
//		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
//		if (!isMultipartContent) {
//			return;
//		}
//
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		try {
//			@SuppressWarnings("unchecked")
//			List<FileItem> fields = upload.parseRequest(request);
//			Iterator<FileItem> it = fields.iterator();
//			if (!it.hasNext()) {
//				return;
//			}
//
//			Returnable files = new Returnable();
//
//			while (it.hasNext()) {
//				FileItem fileItem = it.next();
//
//				String fileName = FilenameUtils.getName(fileItem.getName());
//				// String fileNamePrefix = FilenameUtils.getBaseName(fileName) +
//				// "_";
//				String fileNameSuffix = "." + FilenameUtils.getExtension(fileName);
//				File file = File.createTempFile("dias_", fileNameSuffix, uploadFolder);
//
//				String extension = "";
//
//				int i = fileName.lastIndexOf('.');
//				if (i > 0) {
//					extension = fileName.substring(i + 1);
//				}
//
//				if (!extension.equals("") && extension.equalsIgnoreCase("pdf")) {
//
//					try {
//						fileItem.write(file);
//					} catch (Exception e) {
//						logger.warn(e.getMessage());
//					}
//
//					Bijlage bijlage = new Bijlage();
//					bijlage.setBestandsNaam(file.toString().replace(".pdf", "_1.jpg"));
//					bijlage.setPolis(null);
//
//					bijlageService.opslaan(bijlage);
//
//					String url = request.getServerName() + request.getServerPort() + request.getServletPath() + "?id=" + bijlage.getId();
//
//					PDFToImage.converteer(file.toString(), file.toString().replace(".pdf", "_"));
//
//					maakThumbnail(file.toString().replace(".pdf", "_1.jpg"));
//
//					files.getFileReturns().add(new FileReturn(fileName, fileItem.getSize() + "", url, null));
//				} else {
//					files.getFileReturns().add(new FileReturn(fileName, fileItem.getSize() + "", null, "Verkeerd bestandsformaat."));
//				}
//			}
//			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//
//			out.println(gson.toJson(files));
//		} catch (FileUploadException e) {
//		}
//	}
//
//	private void maakThumbnail(String bestand) throws IOException {
//		BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//		img.createGraphics().drawImage(ImageIO.read(new File(bestand)).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
//		ImageIO.write(img, "jpg", new File(bestand.replace("_1.jpg", "_1t.jpg")));
//	}
//
//	private class FileReturn {
//		@SuppressWarnings("unused")
//		private String name;
//		@SuppressWarnings("unused")
//		private String size;
//		@SuppressWarnings("unused")
//		private String url;
//		@SuppressWarnings("unused")
//		private String error;
//		@SuppressWarnings("unused")
//		private String thumbnailUrl;
//
//		public FileReturn(String name, String size, String url, String error) {
//			super();
//			this.name = name;
//			this.size = size;
//			this.url = url + "&pdf=true";
//			this.error = error;
//			this.thumbnailUrl = url + "&thumb=true";
//		}
//	}
//
//	private class Returnable {
//		private List<FileReturn> files;
//
//		public List<FileReturn> getFileReturns() {
//			if (files == null) {
//				files = new ArrayList<>();
//			}
//			return files;
//		}
//	}
// }