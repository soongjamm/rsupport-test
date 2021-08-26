package com.rsupport.soongjamm.notice.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/notices")
public interface NoticeController {

	@PostMapping
	ResponseEntity<?> createNotice(@RequestBody CreateNoticeRequest dto);
}
