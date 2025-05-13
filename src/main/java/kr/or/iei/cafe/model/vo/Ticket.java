package kr.or.iei.cafe.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
	private String ticketId;
	private String ticketType;
	private String ticketHour;
	private String ticketPrice;
	private String ticketRegDate;
	private String cafeNo;

}
