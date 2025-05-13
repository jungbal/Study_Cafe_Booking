package kr.or.iei.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	private String userId;
	private String userPw;
	private String userPhone;
	private String userStatus;
	private String userImage;
	private int userRole;
}
