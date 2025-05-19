package kr.or.iei.member.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 매개변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Data				// getter, setter, toString 생성
public class PayHistory {

	private int payId;
	private Date payTime;
	private String payStatus;
	private String payAmount;
	private String payMethod;
	private String payUserId;
}
