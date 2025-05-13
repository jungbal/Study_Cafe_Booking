package kr.or.iei.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReport {
	private String reportId;
	private String reporterId;
	private String targetCommentId;
	private String reportCodeId;
	
}
