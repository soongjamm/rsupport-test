package com.rsupport.soongjamm.notice.application;


import com.rsupport.soongjamm.notice.domain.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateNoticeTarget {
	private String title;
	private String author;
	private LocalDateTime lastModifiedAt;
	private String content;

	public Notice toEntity() {
		return new Notice(title, author, lastModifiedAt, content);
	}
}
