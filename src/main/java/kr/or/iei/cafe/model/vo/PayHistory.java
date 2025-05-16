package kr.or.iei.cafe.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayHistory {
	
	private String payId;
	private String payTime;
	private String PayStatus;
	private String PayAmount;
	private String PayMethod;
	private String PayUserId;
}
