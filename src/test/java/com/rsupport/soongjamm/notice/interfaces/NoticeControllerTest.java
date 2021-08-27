package com.rsupport.soongjamm.notice.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.interfaces.impl.NoticeControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.rsupport.soongjamm.notice.TestData.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NoticeControllerTest {

	private NoticeController noticeController;
	private MockMvc mvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private String baseUrl = "/api/notices";

	@BeforeEach
	void setup(@Mock NoticeService noticeService) {
		noticeController = new NoticeControllerImpl(noticeService);
		CreateNoticeTarget createRequest = createNoticeTarget().build();
		given(noticeService.createNotice(createRequest)).willReturn(createRequest.toEntity());
		objectMapper.registerModule(new JavaTimeModule());objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mvc = MockMvcBuilders.standaloneSetup(noticeController)
				.alwaysDo(print())
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	void create_notice() throws Exception {
	    //given
		CreateNoticeRequest noticeRequest = createNoticeRequest().build();

		//when
		ResultActions perform = mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(noticeRequest)));

		//then
		perform
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("제목"))
				.andExpect(jsonPath("$.author").value("작성자"))
				.andExpect(jsonPath("$.content").value("내용"));
	}


}