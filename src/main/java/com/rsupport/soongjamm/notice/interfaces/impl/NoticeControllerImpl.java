package com.rsupport.soongjamm.notice.interfaces.impl;

import com.rsupport.soongjamm.notice.Notices;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.application.UnauthorizedTaskException;
import com.rsupport.soongjamm.notice.domain.Notice;
import com.rsupport.soongjamm.notice.interfaces.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NoticeControllerImpl implements NoticeController {

	private NoticeService noticeService;

	public NoticeControllerImpl(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
	public ResponseEntity<?> getANotice(Long noticeId) {
		try {
			Notice aNotice = noticeService.getANotice(noticeId);
			return ResponseEntity.ok(NoticeDetail.from(aNotice));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<?> getNoticeList(Integer page, Integer size) {
		Pageable pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Notice> pages = noticeService.getNotices(pageRequest);
		List<NoticeDetail> notices = pages.stream().map(NoticeDetail::from).collect(Collectors.toList());
		return ResponseEntity.ok(new Notices(notices, pages.getTotalPages(), page));
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
