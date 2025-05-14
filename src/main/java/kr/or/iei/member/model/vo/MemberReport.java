package kr.or.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberReport {
	private String reportId;
	private String reporterId;
	private String targetCommentId;
	private String reportCodeId;
	
}
