package kr.or.iei.comment.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 매개변수 있는 생성자
@NoArgsConstructor // 매개변수 없는 기본 생성자
@Data				// getter, setter, toString 생성
public class Comment {
	private String commentId;
    private String targetType;
    private String content;
    private Date commentTime;
    private String commentUserId;
    private String commentCafeNo;
    private String commentParent;
    private String cafeName;
}
