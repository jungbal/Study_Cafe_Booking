package kr.or.iei.member.model.service;

import java.sql.Connection;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;

public class MemberService {

	private MemberDao dao;

	public MemberService() {
		dao = new MemberDao();
	}

	
	public Member selectOneMember(String userId, String userPw) {
		Connection conn = JDBCTemplate.getConnection();
		Member m = dao.selectOneMember(conn, userId, userPw);
		JDBCTemplate.close(conn);
		return m;
	}

	
	public int updateMember(Member updMember) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateMember(conn, updMember);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
}
