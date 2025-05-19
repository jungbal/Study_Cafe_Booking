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
	public ArrayList<Comment> selectComment(Connection conn, String cafeNo, String RVQA) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    ArrayList<Comment> commentList = new ArrayList<Comment>();

	    String query = "SELECT c.*, u.user_role "
	                 + "FROM tbl_comment c "
	                 + "JOIN tbl_user u ON c.comment_user_id = u.user_id "
	                 + "WHERE c.comment_cafe_no = ? AND c.target_type = ? "
	                 + "order by comment_time desc";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, cafeNo);
	        pstmt.setString(2, RVQA);
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
	            comment.setUserRole(rset.getString("user_role"));

	            commentList.add(comment);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(rset);
	        JDBCTemplate.close(pstmt);
	    }

	    return commentList;
	}

	public int insertComment(Connection conn, Comment comment, String RVQA) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    String query = "INSERT INTO TBL_COMMENT (TARGET_TYPE, CONTENT, COMMENT_TIME, COMMENT_USER_ID, COMMENT_CAFE_NO, COMMENT_PARENT) " +
	                   "VALUES (?, ?, SYSDATE, ?, ?, ?)";

	    try {
	        pstmt = conn.prepareStatement(query);

	        pstmt.setString(1, RVQA);
	        pstmt.setString(2, comment.getContent());
	        pstmt.setString(3, comment.getCommentUserId());
	        pstmt.setString(4, comment.getCommentCafeNo());

	        String parent = comment.getCommentParent();
	        if (parent != null && !parent.equals("0") && !parent.isEmpty()) {
	            pstmt.setString(5, parent);
	        } else {
	            pstmt.setNull(5, java.sql.Types.VARCHAR);
	        }

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int deleteComment(Connection conn, String commentId) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    String query = "DELETE FROM tbl_comment WHERE comment_id = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, commentId);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}





}
