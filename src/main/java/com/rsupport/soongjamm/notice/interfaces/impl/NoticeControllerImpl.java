package com.rsupport.soongjamm.notice.interfaces.impl;

import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.application.impl.UnauthorizedTaskException;
import com.rsupport.soongjamm.notice.interfaces.*;
import org.springframework.http.HttpStatus;
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

	@Override
	public ResponseEntity<?> updateNotice(UpdateNoticeRequest dto, Long noticeId) {
		try {
			NoticeDetail noticeDetail = NoticeDetail.from(noticeService.updateNotice(dto.toTarget(noticeId)));
			return ResponseEntity.ok().body(noticeDetail);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
		} catch (UnauthorizedTaskException exception) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> deleteNotice(DeleteNoticeRequest dto, Long noticeId) {
		try {
			noticeService.deleteNotice(dto.toTarget(noticeId));
			return ResponseEntity.noContent().build();
		} catch (UnauthorizedTaskException exception) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
		}
	}
}
