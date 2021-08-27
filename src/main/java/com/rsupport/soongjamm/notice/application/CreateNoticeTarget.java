package com.rsupport.soongjamm.notice.application;


import com.rsupport.soongjamm.notice.domain.Notice;
import lombok.*;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class CreateNoticeTarget {
	private String title;
	private String author;
	private String content;

	public Notice toEntity() {
		return new Notice(title, author, content);
	}
}
