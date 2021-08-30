package com.rsupport.soongjamm.notice.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/notices")
public interface NoticeController {

	@GetMapping("/{noticeId}")
	ResponseEntity<?> getANotice(@PathVariable Long noticeId);

	@GetMapping
	ResponseEntity<?> getNoticeList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size);

	@PostMapping
	ResponseEntity<?> createNotice(@RequestBody CreateNoticeRequest dto);

	@PutMapping("/{noticeId}")
	ResponseEntity<?> updateNotice(@RequestBody UpdateNoticeRequest dto, @PathVariable Long noticeId);

	@DeleteMapping("/{noticeId}")
	ResponseEntity<?> deleteNotice(@RequestBody DeleteNoticeRequest dto, @PathVariable Long noticeId);
}
