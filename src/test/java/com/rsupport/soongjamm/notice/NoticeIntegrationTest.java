package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.interfaces.CreateNoticeRequest;
import com.rsupport.soongjamm.notice.interfaces.NoticeDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static com.rsupport.soongjamm.notice.TestData.createNoticeRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoticeIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	void createNoticeTest() {
		//given
		TestRestTemplate template = new TestRestTemplate();
		template.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port + "/api/notices"));
		CreateNoticeRequest requestDto = createNoticeRequest().build();

		//when
		ResponseEntity<NoticeDetail> result = template.postForEntity("", requestDto, NoticeDetail.class);

		//then
		result.getStatusCode().is2xxSuccessful();
		assertThat(result.getBody().getTitle()).isEqualTo(requestDto.getTitle());
		assertThat(result.getBody().getAuthor()).isEqualTo(requestDto.getAuthor());
		assertThat(result.getBody().getContent()).isEqualTo(requestDto.getContent());
	}
}
