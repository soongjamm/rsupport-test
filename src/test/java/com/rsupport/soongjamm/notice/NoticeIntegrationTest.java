package com.rsupport.soongjamm.notice;

import static com.rsupport.soongjamm.notice.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.rsupport.soongjamm.notice.domain.Notice;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.DeleteNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.NoticeDetail;
import com.rsupport.soongjamm.notice.interfaces.UpdateNoticeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoticeIntegrationTest {

	@LocalServerPort
	private int port;
	private TestRestTemplate template;

	@Autowired
	private NoticeRepository noticeRepository;

	@BeforeEach
	void setup() {
		template = new TestRestTemplate();
		template.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port + "/api/notices"));
	}

	@Test
	void get_notice_list() {
		//given
		List<Notice> init = new ArrayList<>();
		for (long i = 1; i <= 30; i++) {
			init.add(notice().id(i).title("title : " + i).build());
		}
		noticeRepository.saveAllAndFlush(init);
		System.out.println(noticeRepository.findAll());
		int page = 1;
		int size = 10;

		//when
		ResponseEntity<Notices> result = template.getForEntity("/?page={page}&size={size}", Notices.class, page, size);
		List<Notice> notices = result.getBody().getNotices();

		//then
		result.getStatusCode().is2xxSuccessful();
		assertThat(notices.size()).isEqualTo(10);
	}

	@Test
	void create_notice_test() {
		//given
		CreateNoticeRequest requestDto = createNoticeRequest().build();

		//when
		ResponseEntity<NoticeDetail> result = template.postForEntity("", requestDto, NoticeDetail.class);

		//then
		result.getStatusCode().is2xxSuccessful();
		assertThat(result.getBody().getTitle()).isEqualTo(requestDto.getTitle());
		assertThat(result.getBody().getAuthor()).isEqualTo(requestDto.getAuthor());
		assertThat(result.getBody().getContent()).isEqualTo(requestDto.getContent());
	}

	@Test
	void update_notice_test() {
		//given
		noticeRepository.save(notice().build());
		UpdateNoticeRequest requestDto = updateNoticeRequest().build();
		HttpEntity<UpdateNoticeRequest> requestEntity = new HttpEntity<>(requestDto);

		//when
		ResponseEntity<NoticeDetail> result = template.exchange("/{noticeId}", HttpMethod.PUT, requestEntity, NoticeDetail.class, 1L);

		//then
		result.getStatusCode().is2xxSuccessful();
		assertThat(result.getBody().getTitle()).isEqualTo(requestDto.getTitle());
		assertThat(result.getBody().getAuthor()).isEqualTo(requestDto.getAuthor());
		assertThat(result.getBody().getContent()).isEqualTo(requestDto.getContent());
	}

	@Test
	@DirtiesContext
	void delete_notice_test() {
		//given
		DeleteNoticeRequest requestDto = deleteNoticeRequest().build();
		HttpEntity<DeleteNoticeRequest> requestEntity = new HttpEntity<>(requestDto);

		//when
		ResponseEntity<Void> result = template.exchange("/{noticeId}", HttpMethod.DELETE, requestEntity, Void.class, 1L);

		//then
		result.getStatusCode().is2xxSuccessful();
	}

	@Test
	void get_a_notice() {
		//given
		Notice saved = noticeRepository.save(notice().build());
		System.out.println(noticeRepository.findAll());

		//when
		ResponseEntity<Notice> result = template.getForEntity("/{id}", Notice.class, saved.getId());

		//then
		result.getStatusCode().is2xxSuccessful();
	}

}
