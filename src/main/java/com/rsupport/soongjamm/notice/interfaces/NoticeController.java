package com.rsupport.soongjamm.notice.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/notices")
public interface NoticeController {

	@GetMapping
	ResponseEntity<?> getNoticeList(@RequestParam Integer limit, @RequestParam Integer offset);

	@PostMapping
	ResponseEntity<?> createNotice(@RequestBody CreateNoticeRequest dto);

	@PutMapping("/{noticeId}")
	ResponseEntity<?> updateNotice(@RequestBody UpdateNoticeRequest dto, @PathVariable Long noticeId);

	@DeleteMapping("/{noticeId}")
	ResponseEntity<?> deleteNotice(@RequestBody DeleteNoticeRequest dto, @PathVariable Long noticeId);
}
