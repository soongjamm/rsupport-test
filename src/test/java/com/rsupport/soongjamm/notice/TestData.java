package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;

import java.time.*;

public class TestData {
	public final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, Month.AUGUST, 26, 18, 17, 1);
	public static Clock clock = Clock.fixed(Instant.from(LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC)), ZoneId.of("Asia/Seoul"));

	public static CreateNoticeRequest.CreateNoticeRequestBuilder createNoticeRequest() {
		return CreateNoticeRequest.builder()
				.title("제목")
				.author("작성자")
				.content("내용")
				.clock(clock);
	}

	public static CreateNoticeTarget.CreateNoticeTargetBuilder createNoticeTarget() {
		CreateNoticeRequest request = createNoticeRequest().build();
		return CreateNoticeTarget.builder()
				.title(request.getTitle())
				.author(request.getAuthor())
				.content(request.getContent())
				.lastModifiedAt(request.getLastModifiedAt());

	}
}
