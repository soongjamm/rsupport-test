package com.rsupport.soongjamm.notice.interfaces.impl;

import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.NoticeController;
import com.rsupport.soongjamm.notice.interfaces.NoticeDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class NoticeControllerImpl implements NoticeController {

	private NoticeService noticeService;

	public NoticeControllerImpl(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
	public ResponseEntity<?> createNotice(CreateNoticeRequest dto) {
		NoticeDetail noticeDetail = NoticeDetail.from(noticeService.createNotice(dto.toTarget()));
		return ResponseEntity.created(URI.create("/api/notices/" + noticeDetail.getId())).body(noticeDetail);
	}
}
