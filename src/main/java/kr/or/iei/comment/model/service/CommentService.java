package kr.or.iei.comment.model.service;

import java.sql.Connection;
import java.util.List;

import kr.or.iei.comment.model.dao.CommentDao;
import kr.or.iei.comment.model.vo.Comment;
import kr.or.iei.common.JDBCTemplate;

public class CommentService {

	public List<Comment> selectMyCommentsByType(String userId, String type) {
        Connection conn = JDBCTemplate.getConnection();
        CommentDao dao = new CommentDao();
        List<Comment> list = dao.selectMyCommentsByType(conn, userId, type);
        JDBCTemplate.close(conn);
        return list;
    }
	
	public int deleteComment(String commentId) {
	    Connection conn = JDBCTemplate.getConnection();
	    CommentDao dao = new CommentDao();
	    int result = dao.deleteComment(conn, commentId);
	    if (result > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }
	    JDBCTemplate.close(conn);
	    return result;
	}

	public int updateComment(String commentId, String content) {
	    Connection conn = JDBCTemplate.getConnection();
	    CommentDao dao = new CommentDao();
	    int result = dao.updateComment(conn, commentId, content);
	    if (result > 0) {
	        JDBCTemplate.commit(conn);
	    } else {
	        JDBCTemplate.rollback(conn);
	    }
	    JDBCTemplate.close(conn);
	    return result;
	}

}
