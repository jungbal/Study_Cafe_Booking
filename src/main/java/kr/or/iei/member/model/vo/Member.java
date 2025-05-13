package kr.or.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 매개변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Data				// getter, setter, toString 생성
public class Member {

	private String userId;
	private String userPw;
	private int userRole;
	private String userPhone;
	private String userStatus;
	private String userImage;
}
