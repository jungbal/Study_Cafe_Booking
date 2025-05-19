package kr.or.iei.comment.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentDao {

    // 리뷰/문의 + 답글 모두 조회
    public List<Comment> selectMyCommentsByType(Connection conn, String userId, String type) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT C.*, F.CAFE_NAME "
                   + "FROM TBL_COMMENT C "
                   + "JOIN TBL_CAFE F ON C.COMMENT_CAFE_NO = F.CAFE_NO "
                   + "WHERE (C.COMMENT_USER_ID = ? AND C.TARGET_TYPE = ?) "
                   + "   OR (C.COMMENT_PARENT IN (SELECT COMMENT_ID "
                   + "                          FROM TBL_COMMENT "
                   + "                          WHERE COMMENT_USER_ID = ? AND TARGET_TYPE = ?)) "
                   + "ORDER BY C.COMMENT_TIME DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, type);
            pstmt.setString(3, userId);
            pstmt.setString(4, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment();
                    c.setCommentId(rs.getString("COMMENT_ID"));
                    c.setTargetType(rs.getString("TARGET_TYPE"));
                    c.setContent(rs.getString("CONTENT"));
                    c.setCommentTime(rs.getDate("COMMENT_TIME"));
                    c.setCommentUserId(rs.getString("COMMENT_USER_ID"));
                    c.setCommentCafeNo(rs.getString("COMMENT_CAFE_NO"));
                    c.setCommentParent(rs.getString("COMMENT_PARENT"));
                    c.setCafeName(rs.getString("CAFE_NAME"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 특정 댓글 조회
    public Comment selectCommentById(Connection conn, String commentId) {
        Comment c = null;
        String sql = "SELECT * FROM TBL_COMMENT WHERE COMMENT_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, commentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    c = new Comment();
                    c.setCommentId(rs.getString("COMMENT_ID"));
                    c.setContent(rs.getString("CONTENT"));
                    c.setCommentTime(rs.getDate("COMMENT_TIME"));
                    c.setCommentUserId(rs.getString("COMMENT_USER_ID"));
                    c.setCommentCafeNo(rs.getString("COMMENT_CAFE_NO"));
                    c.setCommentParent(rs.getString("COMMENT_PARENT"));
                    c.setTargetType(rs.getString("TARGET_TYPE"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // 댓글 수정
    public int updateComment(Connection conn, String commentId, String content) {
        String sql = "UPDATE TBL_COMMENT SET CONTENT = ?, COMMENT_TIME = SYSDATE WHERE COMMENT_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setString(2, commentId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 댓글 삭제
    public int deleteComment(Connection conn, String commentId) {
        String sql = "DELETE FROM TBL_COMMENT WHERE COMMENT_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, commentId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
