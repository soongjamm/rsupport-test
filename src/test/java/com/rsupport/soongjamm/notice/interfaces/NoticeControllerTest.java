package com.rsupport.soongjamm.notice.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.DeleteNoticeTarget;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
import com.rsupport.soongjamm.notice.application.UnauthorizedTaskException;
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
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NoticeControllerTest {
	@Mock NoticeService noticeService;
	private NoticeController noticeController;
	private MockMvc mvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private String baseUrl = "/api/notices";

	@BeforeEach
	void setup() {
		noticeController = new NoticeControllerImpl(noticeService);
		objectMapper.registerModule(new JavaTimeModule());objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mvc = MockMvcBuilders.standaloneSetup(noticeController)
				.alwaysDo(print())
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	void create_notice_return_details_with_201_status() throws Exception {
	    //given
		CreateNoticeRequest requestBody = createNoticeRequest().build();
		CreateNoticeTarget serviceTarget = createNoticeTarget().build();
		given(noticeService.createNotice(serviceTarget)).willReturn(serviceTarget.toEntity());

		//when
		ResultActions perform = mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody)));

		//then
		perform
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value(requestBody.getTitle()))
				.andExpect(jsonPath("$.author").value(requestBody.getAuthor()))
				.andExpect(jsonPath("$.content").value(requestBody.getContent()));
	}

	@Test
	void update_notice_return_details_with_200_status() throws Exception {
		//given
		UpdateNoticeRequest requestBody = updateNoticeRequest().build();
		UpdateNoticeTarget serviceTarget = requestBody.toTarget(1L);
		given(noticeService.updateNotice(serviceTarget)).willReturn(notice().title(serviceTarget.getTitle()).content(serviceTarget.getContent()).build());

		//when
		ResultActions perform = mvc.perform(put(baseUrl + "/{noticeId}", serviceTarget.getNoticeId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody)));

		//then
		perform
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(requestBody.getTitle()))
				.andExpect(jsonPath("$.author").value(requestBody.getAuthor()))
				.andExpect(jsonPath("$.content").value(requestBody.getContent()));
	}

	@Test
	void update_notice_validation_fail_return_409_status() throws Exception {
		//given
		UpdateNoticeRequest requestBody = updateNoticeRequest().build();
		UpdateNoticeTarget serviceTarget = requestBody.toTarget(1L);
		given(noticeService.updateNotice(serviceTarget)).willThrow(IllegalArgumentException.class);

		//when
		ResultActions perform = mvc.perform(put(baseUrl + "/{noticeId}", serviceTarget.getNoticeId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody)));

		//then
		perform
				.andExpect(status().isConflict());
	}

	@Test
	void delete_notice_always_return_204_no_content() throws Exception {
		//given
		DeleteNoticeRequest requestBody = deleteNoticeRequest().build();
		DeleteNoticeTarget serviceTarget = requestBody.toTarget(1L);

		//when
		ResultActions perform = mvc.perform(delete(baseUrl + "/{noticeId}", serviceTarget.getNoticeId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody)));

		//then
		perform
				.andExpect(status().isNoContent());
	}


	@Test
	void delete_notice_from_not_authorized_return_401_unauthorized() throws Exception {
		//given
		DeleteNoticeRequest requestBody = deleteNoticeRequest().author("불량유저").build();
		DeleteNoticeTarget serviceTarget = requestBody.toTarget(1L);
		doThrow(new UnauthorizedTaskException("")).when(noticeService).deleteNotice(serviceTarget);

		//when
		ResultActions perform = mvc.perform(delete(baseUrl + "/{noticeId}", serviceTarget.getNoticeId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestBody)));

		//then
		perform
				.andExpect(status().isUnauthorized());
	}

}