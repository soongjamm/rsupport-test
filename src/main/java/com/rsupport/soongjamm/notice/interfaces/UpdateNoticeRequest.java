package com.rsupport.soongjamm.notice.interfaces;

import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateNoticeRequest {
	private String author;
	private String title;
	private String content;

	public UpdateNoticeTarget toTarget(Long noticeId) {
		return new UpdateNoticeTarget(noticeId, author, title, content);
	}
}
