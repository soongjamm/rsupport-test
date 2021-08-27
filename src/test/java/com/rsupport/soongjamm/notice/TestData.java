package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;

public class TestData {
	public static CreateNoticeRequest.CreateNoticeRequestBuilder createNoticeRequest() {
		return CreateNoticeRequest.builder()
				.title("제목")
				.author("작성자")
				.content("내용");
	}

	public static CreateNoticeTarget.CreateNoticeTargetBuilder createNoticeTarget() {
		CreateNoticeRequest request = createNoticeRequest().build();
		return CreateNoticeTarget.builder()
				.title(request.getTitle())
				.author(request.getAuthor())
				.content(request.getContent());
	}
}
