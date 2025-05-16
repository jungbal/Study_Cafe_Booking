package kr.or.iei.comment.model.service;

import java.sql.Connection;
import java.util.List;
import kr.or.iei.comment.model.dao.CommentDao;
import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentService {
    private CommentDao dao = new CommentDao();

    public List<Comment> selectMyCommentsByType(String userId, String type) {
        Connection conn = JDBCTemplate.getConnection();
        List<Comment> list = dao.selectMyCommentsByType(conn, userId, type);
        JDBCTemplate.close(conn);
        return list;
    }

    public Comment selectCommentById(String commentId) {
        Connection conn = JDBCTemplate.getConnection();
        Comment c = dao.selectCommentById(conn, commentId);
        JDBCTemplate.close(conn);
        return c;
    }

    public int updateComment(String commentId, String content) {
        Connection conn = JDBCTemplate.getConnection();
        int result = dao.updateComment(conn, commentId, content);
        if (result > 0) JDBCTemplate.commit(conn);
        else           JDBCTemplate.rollback(conn);
        JDBCTemplate.close(conn);
        return result;
    }

    public int deleteComment(String commentId) {
        Connection conn = JDBCTemplate.getConnection();
        int result = dao.deleteComment(conn, commentId);
        if (result > 0) JDBCTemplate.commit(conn);
        else           JDBCTemplate.rollback(conn);
        JDBCTemplate.close(conn);
        return result;
    }
}
