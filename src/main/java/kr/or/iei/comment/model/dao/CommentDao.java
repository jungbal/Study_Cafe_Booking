package kr.or.iei.comment.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentDao {

	public List<Comment> selectMyCommentsByType(Connection conn, String userId, String type) {
        List<Comment> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT C.*, F.CAFE_NAME "
        		+ "FROM TBL_COMMENT C "
        		+ "JOIN TBL_CAFE F ON C.COMMENT_CAFE_NO = F.CAFE_NO "
        		+ "WHERE C.COMMENT_USER_ID = ? AND C.TARGET_TYPE = ? "
        		+ "ORDER BY C.COMMENT_TIME DESC";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, type);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();
                c.setCafeName(rs.getString("CAFE_NAME"));
                c.setCommentId(rs.getString("COMMENT_ID"));
                c.setTargetType(rs.getString("TARGET_TYPE"));
                c.setContent(rs.getString("CONTENT"));
                c.setCommentTime(rs.getDate("COMMENT_TIME"));
                c.setCommentUserId(rs.getString("COMMENT_USER_ID"));
                c.setCommentCafeNo(rs.getString("COMMENT_CAFE_NO"));
                c.setCommentParent(rs.getString("COMMENT_PARENT"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
        }

        return list;
    }
	
	public int deleteComment(Connection conn, String commentId) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = "DELETE FROM TBL_COMMENT WHERE COMMENT_ID = ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, commentId);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public int updateComment(Connection conn, String commentId, String content) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE TBL_COMMENT SET CONTENT = ?, COMMENT_TIME = SYSDATE WHERE COMMENT_ID = ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, content);
	        pstmt.setString(2, commentId);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

}
