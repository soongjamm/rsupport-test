package com.rsupport.soongjamm.notice.application;

import lombok.*;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class UpdateNoticeTarget {
	private Long noticeId;
	private String author;
	private String title;
	private String content;

}
