package kr.or.iei.cafe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cafe.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentDao {

	// 특정 카페의 카페 리뷰 / Q&A list 조회
	public ArrayList<Comment> selectComment(Connection conn, String cafeNo, String rvqa) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    ArrayList<Comment> commentList = new ArrayList<Comment>();

	    String query = "SELECT c.*, u.user_role " +
	                   "FROM tbl_comment c " +
	                   "JOIN tbl_user u ON c.comment_user_id = u.user_id " +
	                   "WHERE c.comment_cafe_no = ? AND c.target_type = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, cafeNo);
	        pstmt.setString(2, rvqa); // review인지, Q&A인지 구별 (review일 경우, RV값이 / Q&A일 경우 QA 값이 담겨서 넘어옴)
	        rset = pstmt.executeQuery();

	        while (rset.next()) {
	            Comment comment = new Comment();
	            comment.setCommentId(rset.getString("comment_id"));
	            comment.setTargetType(rset.getString("target_type"));
	            comment.setContent(rset.getString("content"));
	            comment.setCommentTime(rset.getString("comment_time"));
	            comment.setCommentUserId(rset.getString("comment_user_id"));
	            comment.setCommentCafeNo(rset.getString("comment_cafe_no"));
	            comment.setCommentParent(rset.getString("comment_parent"));
	            comment.setUserRole(rset.getString("user_role")); // 또는 getString, JSP와 맞춰서
	            commentList.add(comment);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rset != null) rset.close(); } catch (SQLException e) {}
	        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
	    }

	    return commentList;
	}


}
