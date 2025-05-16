package kr.or.iei.cafe.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class History {
	
	private String historyId;
	private String historyStart;
	private String historyEnd;
	private String historyCafeNo;
	private String historySeatNo;
	private String historyTicketId;
	private String historyUserId;
}
