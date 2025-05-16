package kr.or.iei.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.dao.PayDao;
import kr.or.iei.member.model.vo.PayHistory;

public class PayService {
	private PayDao dao;

	public PayService() {
		dao = new PayDao();
	}

	public ArrayList<PayHistory> selectPayHistoryByUser(String userId) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<PayHistory> list = dao.selectPayHistoryByUser(conn, userId);
		JDBCTemplate.close(conn);
		return list;
	}
}
