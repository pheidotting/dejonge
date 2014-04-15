//package nl.dias.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import nl.dias.domein.Bijlage;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.polis.Polis;
//import nl.dias.exception.NietIngelogdException;
//import nl.dias.service.BijlageService;
//import nl.dias.service.GebruikerService;
//import nl.dias.web.AbstractController;
//
//import com.google.gson.Gson;
//
//@WebServlet("/BijlagenBijPolis")
//public class BijlagenBIjPolis extends AbstractController {
//	@EJB
//	private BijlageService bijlageService;
//	@EJB
//	private GebruikerService gebruikerService;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		try {
//			checkIngelogd(request);
//
//			String strPolisid = request.getParameter("polisId");
//			String strRelatieid = request.getParameter("relatieId");
//
//			Long polisId = Long.parseLong(strPolisid);
//			Long relatieId = Long.parseLong(strRelatieid);
//
//			Relatie relatie = (Relatie) gebruikerService.lees(relatieId);
//
//			Polis polis = null;
//			for (Polis p : relatie.getPolissen()) {
//				if (p.getId().equals(polisId)) {
//					polis = p;
//					break;
//				}
//			}
//
//			List<Bijlage> bijlages = bijlageService.zoekBijlagenBijPolis(polis);
//
//			List<String> json = new ArrayList<>();
//			for (Bijlage b : bijlages) {
//				json.add(new String(b.getId().toString()));
//			}
//
//			Gson gson = new Gson();
//
//			String messages = gson.toJson(json);
//			out.println(messages);
//		} catch (NietIngelogdException e) {
//			out.println(e.getMessage());
//		} finally {
//			out.close();
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// if (!ServletFileUpload.isMultipartContent(request)) {
//		// throw new
//		// ServletException("Content type is not multipart/form-data");
//		// }
//		//
//		// response.setContentType("text/html");
//		// PrintWriter out = response.getWriter();
//		// out.write("<html><head></head><body>");
//		// try {
//		// List<FileItem> fileItemsList = uploader.parseRequest(request);
//		// Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
//		// while (fileItemsIterator.hasNext()) {
//		// FileItem fileItem = fileItemsIterator.next();
//		// System.out.println("FieldName=" + fileItem.getFieldName());
//		// System.out.println("FileName=" + fileItem.getName());
//		// System.out.println("ContentType=" + fileItem.getContentType());
//		// System.out.println("Size in bytes=" + fileItem.getSize());
//		//
//		// File file = new
//		// File(request.getServletContext().getAttribute("FILES_DIR") +
//		// File.separator + fileItem.getName());
//		// System.out.println("Absolute Path at server=" +
//		// file.getAbsolutePath());
//		// fileItem.write(file);
//		// out.write("File " + fileItem.getName() + " uploaded successfully.");
//		// out.write("<br>");
//		// out.write("<a href=\"UploadDownloadFileServlet?fileName=" +
//		// fileItem.getName() + "\">Download " + fileItem.getName() + "</a>");
//		// }
//		// } catch (FileUploadException e) {
//		// out.write("Exception in uploading file.");
//		// } catch (Exception e) {
//		// out.write("Exception in uploading file.");
//		// }
//		// out.write("</body></html>");
//	}
//
//	public BijlageService getBijlageService() {
//		return bijlageService;
//	}
//
//	public void setBijlageService(BijlageService bijlageService) {
//		this.bijlageService = bijlageService;
//	}
// }
