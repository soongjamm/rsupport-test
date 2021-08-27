package com.rsupport.soongjamm.notice.interfaces;

import com.rsupport.soongjamm.notice.application.DeleteNoticeTarget;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteNoticeRequest {
	private String author;

	public DeleteNoticeTarget toTarget(long noticeId) {
		return new DeleteNoticeTarget(noticeId, author);
	}
}
