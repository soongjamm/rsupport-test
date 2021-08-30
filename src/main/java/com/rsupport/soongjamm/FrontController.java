package com.rsupport.soongjamm;

import com.rsupport.soongjamm.notice.Notices;
import com.rsupport.soongjamm.notice.interfaces.NoticeDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class FrontController {

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/")
	public String home(@RequestParam(defaultValue = "0") Integer page, Model model) {
		ResponseEntity<Notices> entity = null;
		try {
			entity = restTemplate.getForEntity("http://localhost:8080/api/notices?page={page}", Notices.class, page);
		} catch (RuntimeException ex){
			System.out.println(ex);
		}
		model.addAttribute("notices", entity.getBody());
		return "home";
	}

	@GetMapping("/notices/post")
	public String posting() {
		return "post";
	}

	@GetMapping("/notices/{noticeId}")
	public String getDetail(@PathVariable int noticeId, Model model) {
		ResponseEntity<NoticeDetail> entity = getANoticeDetail(noticeId);
		model.addAttribute("notice", entity.getBody());
		return "detail";
	}

	@GetMapping("/notices/{noticeId}/update")
	public String editNotice(@PathVariable int noticeId, Model model) {
		ResponseEntity<NoticeDetail> entity = getANoticeDetail(noticeId);
		model.addAttribute("notice", entity.getBody());
		return "update";
	}

	private ResponseEntity<NoticeDetail> getANoticeDetail(int noticeId) {
		ResponseEntity<NoticeDetail> entity = null;
		try {
			entity = restTemplate.getForEntity("http://localhost:8080/api/notices/{noticeId}", NoticeDetail.class, noticeId);
		} catch (RuntimeException ex){
			System.out.println(ex);
		}
		return entity;
	}
}
