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
	private String Content;
	private String CommentTime;
	private String CommentUserId;
	private String CommentCafeNo;
	private String CafeNo;
	private String CommentParent;
	
	// sql 작업 위해 추가
	private String userRole;
	

}
