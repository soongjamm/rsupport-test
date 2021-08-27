package com.rsupport.soongjamm.notice.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/notices")
public interface NoticeController {

	@PostMapping
	ResponseEntity<?> createNotice(@RequestBody CreateNoticeRequest dto);

	@PutMapping("/{noticeId}")
	ResponseEntity<?> updateNotice(@RequestBody UpdateNoticeRequest dto, @PathVariable Long noticeId);
}
