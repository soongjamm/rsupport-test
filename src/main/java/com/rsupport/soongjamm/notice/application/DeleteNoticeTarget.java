package com.rsupport.soongjamm.notice.application;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DeleteNoticeTarget {
	private Long noticeId;
	private String author;
}
