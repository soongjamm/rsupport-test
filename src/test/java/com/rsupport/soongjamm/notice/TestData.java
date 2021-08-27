package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
import com.rsupport.soongjamm.notice.domain.Notice;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.DeleteNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.UpdateNoticeRequest;

public class TestData {
	public static Notice.NoticeBuilder notice() {
		CreateNoticeRequest noticeRequest = createNoticeRequest().build();
		return Notice.builder()
				.id(1L)
				.title(noticeRequest.getTitle())
				.author(noticeRequest.getAuthor())
				.content(noticeRequest.getContent());
	}

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

	public static UpdateNoticeRequest.UpdateNoticeRequestBuilder updateNoticeRequest() {
		return UpdateNoticeRequest.builder()
				.author("작성자")
				.title("수정된 제목")
				.content("수정된 내용");
	}

	public static UpdateNoticeTarget.UpdateNoticeTargetBuilder updateNoticeTarget() {
		UpdateNoticeRequest request = updateNoticeRequest().build();
		return UpdateNoticeTarget.builder()
				.noticeId(1L)
				.author(request.getAuthor())
				.title(request.getTitle())
				.content(request.getContent());
	}

	public static DeleteNoticeRequest.DeleteNoticeRequestBuilder deleteNoticeRequest() {
		return DeleteNoticeRequest.builder()
				.author("작성자");
	}
}
