package kr.or.iei.cafe.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
	private String commentId;
	private String targetType;
	private String content;
	private String commentTime;
	private String commentUserId;
	private String commentCafeNo;
	private String commentParent;
	
	// sql 작업 위해 추가
	private String userRole;
	

}
