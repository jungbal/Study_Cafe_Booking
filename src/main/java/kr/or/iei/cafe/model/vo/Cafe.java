package kr.or.iei.cafe.model.vo;

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
	
	// 카페 관리 상태 나타내는 변수 추가.
	private String cafeManageStatus;
	
	// 카페 이미지 나타내는 변수 추가
	private String cafeImageNo;
	private String cafeImagePath;
	private String cafeImageName;
	
	// 카페 신청 시 승인/반려 여부, 반려 사유
	private String cafeRejectReason;
	private String cafeApplyStatus;

}
