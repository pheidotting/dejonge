//package nl.dias.servlet;
//
//import javax.ejb.EJB;
//import javax.servlet.annotation.WebServlet;
//
//import nl.dias.service.BijlageService;
//import nl.dias.web.AbstractController;
//
//@WebServlet("/Download")
//public class Downloaden extends AbstractController {
//	private static final long serialVersionUID = 1L;
//	@EJB
//	private BijlageService bijlageService;
//
//	// private ServletFileUpload uploader = null;
//
//	// @Override
//	// // // public void init() throws ServletException {
//	// // // DiskFileItemFactory fileFactory = new DiskFileItemFactory();
//	// // // File filesDir = (File)
//	// // // getServletContext().getAttribute("FILES_DIR_FILE");
//	// // // fileFactory.setRepository(filesDir);
//	// // // // this.uploader = new ServletFileUpload(fileFactory);
//	// // // }
//	// // protected void doGet(HttpServletRequest request, HttpServletResponse
//	// // response) throws ServletException, IOException {
//	// // boolean ingelogd = true;
//	// // try {
//	// // checkIngelogd(request);
//	// // } catch (NietIngelogdException e) {
//	// // ingelogd = false;
//	// // }
//	// //
//	// // if (ingelogd) {
//	// // String strId = request.getParameter("id");
//	// // String thumb = request.getParameter("thumb");
//	// // String pdf = request.getParameter("pdf");
//	// //
//	// // Bijlage bijlage = bijlageService.lees(Long.parseLong(strId));
//	// //
//	// // File file = null;
//	// // if (thumb != null && thumb.equals("true")) {
//	// // file = new File(bijlage.getBestandsNaam().replace("_1.jpg",
//	// "_1t.jpg"));
//	// // } else if (pdf != null && pdf.equals("true")) {
//	// // file = new File(bijlage.getBestandsNaam().replace(".jpg", ".pdf"));
//	// // } else {
//	// // file = new File(bijlage.getBestandsNaam());
//	// // }
//	// //
//	// // if (!file.exists()) {
//	// // throw new ServletException("File doesn't exists on server.");
//	// // }
//	// // // System.out.println("File location on server::" +
//	// // // file.getAbsolutePath());
//	// // ServletContext ctx = getServletContext();
//	// // InputStream fis = new FileInputStream(file);
//	// // String mimeType = ctx.getMimeType(file.getAbsolutePath());
//	// // response.setContentType(mimeType != null ? mimeType :
//	// // "application/octet-stream");
//	// // response.setContentLength((int) file.length());
//	// // response.setHeader("Content-Disposition", "inline; filename=\"" +
//	// // file.getName() + "\"");
//	// //
//	// // ServletOutputStream os = response.getOutputStream();
//	// // byte[] bufferData = new byte[1024];
//	// // int read = 0;
//	// // while ((read = fis.read(bufferData)) != -1) {
//	// // os.write(bufferData, 0, read);
//	// // }
//	// // os.flush();
//	// // os.close();
//	// // fis.close();
//	// // }
//	// // }
//	// protected void doPost(HttpServletRequest request, HttpServletResponse
//	// response) throws ServletException, IOException {
//	// // if (!ServletFileUpload.isMultipartContent(request)) {
//	// // throw new
//	// // ServletException("Content type is not multipart/form-data");
//	// // }
//	// //
//	// // response.setContentType("text/html");
//	// // PrintWriter out = response.getWriter();
//	// // out.write("<html><head></head><body>");
//	// // try {
//	// // List<FileItem> fileItemsList = uploader.parseRequest(request);
//	// // Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
//	// // while (fileItemsIterator.hasNext()) {
//	// // FileItem fileItem = fileItemsIterator.next();
//	// // System.out.println("FieldName=" + fileItem.getFieldName());
//	// // System.out.println("FileName=" + fileItem.getName());
//	// // System.out.println("ContentType=" + fileItem.getContentType());
//	// // System.out.println("Size in bytes=" + fileItem.getSize());
//	// //
//	// // File file = new
//	// // File(request.getServletContext().getAttribute("FILES_DIR") +
//	// // File.separator + fileItem.getName());
//	// // System.out.println("Absolute Path at server=" +
//	// // file.getAbsolutePath());
//	// // fileItem.write(file);
//	// // out.write("File " + fileItem.getName() + " uploaded successfully.");
//	// // out.write("<br>");
//	// // out.write("<a href=\"UploadDownloadFileServlet?fileName=" +
//	// // fileItem.getName() + "\">Download " + fileItem.getName() + "</a>");
//	// // }
//	// // } catch (FileUploadException e) {
//	// // out.write("Exception in uploading file.");
//	// // } catch (Exception e) {
//	// // out.write("Exception in uploading file.");
//	// // }
//	// // out.write("</body></html>");
//	// }
//	public BijlageService getBijlageService() {
//		return bijlageService;
//	}
//
//	public void setBijlageService(BijlageService bijlageService) {
//		this.bijlageService = bijlageService;
//	}
// }
