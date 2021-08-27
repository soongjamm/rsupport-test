package com.rsupport.soongjamm.notice;

import static com.rsupport.soongjamm.notice.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;
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
import org.springframework.web.util.DefaultUriBuilderFactory;

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
		noticeRepository.save(notice().build());
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
}
