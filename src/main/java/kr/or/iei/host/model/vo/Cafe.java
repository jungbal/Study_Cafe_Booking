package kr.or.iei.host.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 매개변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Data
public class Cafe {
	private String cafeNo;
	private String cafeName;
	private String cafePhone;
	private String cafeAddr;
	private String cafeBiznum;
	private String cafeIntroduce;
	private String cafeStartHour;
	private String cafeEndHour;
	private String cafeStatus;
	private String cafeIntroDetail;
	private String hostId;
	
	
	private String cafeImageNo;
	private String cafeImagePath;
	private String cafeImageName;

}
