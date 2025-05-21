package kr.or.iei.member.model.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 매개변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Data				// getter, setter, toString 생성
public class PayHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	private int payId;
	private Date payTime;
	private String payStatus;
	private String payAmount;
	private String payMethod;
	private String payUserId;
	
	// 추가 (마이페이지 결제내역 스터디카페명, 좌석 추가)
	private String payCafeName;
	private String payHistorySeatNo;
	private String payHistoryStart;
	private String payHistoryEnd;
	
	
}
