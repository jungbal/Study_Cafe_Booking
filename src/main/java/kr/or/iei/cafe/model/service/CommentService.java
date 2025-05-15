package kr.or.iei.cafe.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cafe.model.dao.CommentDao;
import kr.or.iei.cafe.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentService {
	
	private CommentDao dao;
	
	public CommentService() {
		dao = new CommentDao();
	}

	public ArrayList<Comment> selectComment(String cafeNo, String RVQA) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Comment> commentList = dao.selectComment(conn, cafeNo, RVQA);
		JDBCTemplate.close(conn);
	      return commentList;
	}

	public int insertComment(Comment comment, String RVQA) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.insertComment(conn, comment, RVQA);
		
		if(result>0) {
	         JDBCTemplate.commit(conn);
	      }else {
	         JDBCTemplate.rollback(conn);
	      }
	      JDBCTemplate.close(conn);
	      
	      return result;
	}

}
