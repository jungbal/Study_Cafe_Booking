package kr.or.iei.host.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.or.iei.host.model.service.HostService;
import kr.or.iei.host.model.vo.Cafe;

/**
 * Servlet implementation class ChangeCafeServlet
 */
@MultipartConfig(
       fileSizeThreshold = 1024 * 1024, // 1MB
       maxFileSize = 1024 * 1024 * 10,  // 10MB
       maxRequestSize = 1024 * 1024 * 50 // 50MB
   )
   @WebServlet("/host/update")
public class ChangeCafeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeCafeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String cafeNo = request.getParameter("cafeNo");
      String cafeName = request.getParameter("cafeName");
      String cafePhone = request.getParameter("cafePhone");
      String cafeAddr = request.getParameter("cafeAddr");
      String cafeBiznum = request.getParameter("cafeBiznum");
      String cafeIntroduce = request.getParameter("cafeIntroduce");
      String cafeStartHour = request.getParameter("cafeStartHour");
      String cafeEndHour = request.getParameter("cafeEndHour");
      String cafeStatus = request.getParameter("cafeStatus");
      String cafeIntroDetail = request.getParameter("cafeIntroDetail");
      String hostId = request.getParameter("hostId");
      
      // 기존 이미지 값 받기
      String originalImageName = request.getParameter("cafeImageName");
      String originalImagePath = request.getParameter("cafeImagePath");
      
       HostService service = new HostService();
      
    // 이미지 파일 처리
       Part filePart = request.getPart("userImage");
       String fileName = null;
       //String filePath = null;
       String fileWebPath = null;
       if (filePart != null && filePart.getSize() > 0) {
           fileName = extractFileName(filePart);	// 업로드된 파일의 이름 추출
           
           // 저장 경로
           String saveDir = getServletContext().getRealPath("/resources/cafeImage");
           File saveDirFile = new File(saveDir);
           if (!saveDirFile.exists()) {
               saveDirFile.mkdirs();
           }

           //System.out.println("saveDir : " + saveDir);
           String filePath = saveDir + File.separator + fileName;
           filePart.write(filePath);
           //웹에서 접근할 수 있는 경로 저장
           fileWebPath = "/resources/cafeImage/" + fileName;
       }else {
           // 새 이미지가 업로드되지 않았을 경우 기존 이미지 유지
           fileName = originalImageName;
           fileWebPath = originalImagePath;
       }
       System.out.println("fileName : " + fileName);
       System.out.println("filePath : " + fileWebPath); 

       // 수정된 정보를 담은 Cafe 객체 생성
       Cafe cafe = new Cafe(cafeNo, cafeName, cafePhone, cafeAddr, cafeBiznum, cafeIntroduce, cafeStartHour, cafeEndHour, cafeStatus, cafeIntroDetail, hostId, null, fileWebPath, fileName);
      System.out.println("fileName : " + fileName);
      System.out.println("fileWebPath : " + fileWebPath);

      int result = service.changeCafe(cafe);
      
      //결과에 따라 응답처리 
      RequestDispatcher view = null;
      view=request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
      if(result > 0) {
    	  //성공 시 메인페이지로 리다이렉트
          request.setAttribute("title", "수정 성공");
          request.setAttribute("msg", "업체가 수정되었습니다. ");
          request.setAttribute("icon", "success");
    	  response.sendRedirect(request.getContextPath() + "/main");
    	  return;
    	  
      }else {
        //실패 시 메시지 출력 후 마이페이지로 이동
         request.setAttribute("title", "수정 실패");
         request.setAttribute("msg", "업체 수정 중, 오류가 발생하였습니다. ");
         request.setAttribute("icon", "error");
         request.setAttribute("loc", "/host/cafeInfoModi");
         view.forward(request, response);
      }
      
   }
   
   private String extractFileName(Part part) {
       String contentDisp = part.getHeader("content-disposition");
       String[] items = contentDisp.split(";");
       for (String s : items) {
           if (s.trim().startsWith("filename")) {
               return s.substring(s.indexOf('=') + 2, s.length() - 1);
           }
       }
       return "";
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}
