
package com.mermer.cm.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.Reply;
import com.mermer.cm.entity.UpLoadFile;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.entity.dto.ReplyDto;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.entity.type.UseYn;
import com.mermer.cm.repository.NoticeReplyRepository;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.service.NoticeService;
import com.mermer.common.BaseTest;

/**
 * @packageName : com.mermer.cm.controller
 * @fileName : NoticeContollerTest.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : NoticeControler 테스트
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public class NoticeContollerTest extends BaseTest {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeReplyRepository noticeReplyRepository;
	
	@Autowired
	NoticeService noticeService;
	
	@BeforeEach
	public void init() {
		//repo 초기화는 의존성 역순으로 제거
		noticeReplyRepository.deleteAll();
		noticeRepository.deleteAll();
		accountRepository.deleteAll();
	}
	
	@After
	public void after() {
		noticeRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Notice 생성")
	public void createNotice() throws Exception {
		
		//밖에서 account 안 꺼낼때는 parameter에 true
		String token = getBearerToken(getAccessToken(true));
		
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		StringBuilder sb = new StringBuilder();
		File f = new File("src/test/resource/test.html");
		BufferedReader br = new BufferedReader(
				new FileReader(f, Charset.forName("UTF-8")),
				16 * 1024
				);
		String str = null;
		while((str = br.readLine())!= null) {
			sb.append(str);
		}
		
		br.close();
		
		NoticeDto notice = NoticeDto.builder()
				.title(title)
				//.content(sb.toString())
				.content("sestet")
				.build();
		
		mockMvc.perform(post("/api/notice")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(notice))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
						)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("title").value(title))
			//.andExpect(jsonPath("content").value(sb.toString()))
			.andExpect(jsonPath("content").value("sestet"))
			.andDo(document("create-notice", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile"),
				    linkWithRel("query-notice").description("link for query notices"),
					linkWithRel("update-notice").description("link for updating the notice"),
					linkWithRel("notice-reply-create").description("link for reply to the notice")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("title").description("title of new notice"),
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("readCnt").description("read count of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
						
					)
				
			));
	}
	
	@Test
	@DisplayName("공지사항에 내용이 없는 경우")
	public void createNotice400_BadRequest() throws Exception {
		
		//밖에서 account 안 꺼낼때는 parameter에 true
		String token = getBearerToken(getAccessToken(true));
		
		
		//Given
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String str = "";
		NoticeDto notice = NoticeDto.builder()
				.title(title)
				.content(str)
				//.content("sestet")
				.build();
		mockMvc.perform(post("/api/notice")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(notice))
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		
		;
				
	}
	
	@Test
	@DisplayName("Notice 전체 조회")
	public void queryNotice() throws Exception {
		
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));//토큰은 필요없음..
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = Notice.builder()
				.title(title)
				.content(testDoc)
				.inster(account)
				.mdfer(account)
				.writerIp("127.0.0.1")
				.build();
		
		noticeRepository.save(notice);
		
		//IntStream.range(0, 30).forEach(this::generateNotice);
		
		mockMvc.perform(get("/api/notice")
				.accept(MediaTypes.HAL_JSON)
				//.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(account))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].insterId").exists())
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].title").value(title))
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].content").value(testDoc))
		.andDo(document("query-notice", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile"),
			    linkWithRel("first").description("link to first page of this list"),
			    linkWithRel("next").description("link to next page of this list"),
			    linkWithRel("last").description("link to last page of this list")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("_embedded.tupleBackedMapList[0].id").description("Id of new notice"),
											
					//request +
					fieldWithPath("_embedded.tupleBackedMapList[0].title").description("title of new notice"),
					//fieldWithPath("_embedded.noticeList[0].content").description("content of new notice"), //목록조회에서는 content 안나오게함
					fieldWithPath("_embedded.tupleBackedMapList[0].readCnt").description("read count of new notice"),
					//fieldWithPath("_embedded.noticeList[0].writerIp").description("writer IP Port of new notice"),
					//fieldWithPath("_embedded.tupleBackedMapList[0].instDtm").description("insert DateTime of new notice"),
					//fieldWithPath("_embedded.tupleBackedMapList[0].mdfDtm").description("modified DateTime of new notice"),
					fieldWithPath("_embedded.tupleBackedMapList[0].insterId").description("insert account ID of new notice")
					//fieldWithPath("_embedded.tupleBackedMapList[0].mdfer").description("modified account ID of new notice")
					
				)
			
		));
	}
	
	@Test
	@DisplayName("Notice 단건 상세 조회")
	public void getNoticeDetail() throws Exception {
		
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));//토큰은 필요없음..
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = Notice.builder()
				.title(title)
				.content(testDoc)
				.inster(account)
				.mdfer(account)
				.writerIp("127.0.0.1")
				.build();
		
		notice = noticeRepository.save(notice);
		
		mockMvc.perform(get("/api/notice/{id}", notice.getId())
				.accept(MediaTypes.HAL_JSON)
				//.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].insterId").exists())
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].title").value(title))
		//.andExpect(jsonPath("_embedded.tupleBackedMapList[0].content").value(testDoc))
		.andDo(document("get-notice", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile"),
			    linkWithRel("update-notice").description("link to update notice")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("id").description("Id of new notice"),
											
					//request +
					fieldWithPath("title").description("title of new notice"),
					fieldWithPath("content").description("content of new notice"), //목록조회에서는 content 안나오게함
					fieldWithPath("readCnt").description("read count of new notice"),
					fieldWithPath("writerIp").description("writer IP Port of new notice"),
					fieldWithPath("instDtm").description("insert DateTime of new notice"),
					fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
					fieldWithPath("inster").description("insert account ID of new notice"),
					fieldWithPath("mdfer").description("modified account ID of new notice"),
					fieldWithPath("useYn").description("use status of the data")
					
				)
			
		));
	}
	
	
	@Test
	@DisplayName("Notice 수정")
	public void updasteNotice() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = generateNotice(account);
		
		//When
		//해당 계정으로 공지사항 글 작성
		
		StringBuilder sb = new StringBuilder();
		File f = new File("src/test/resource/test2.html");
		BufferedReader br = new BufferedReader(
				new FileReader(f, Charset.forName("UTF-8")),
				16 * 1024
				);
		String str = null;
		while((str = br.readLine())!= null) {
			sb.append(str);
		}
		br.close();
		String newtitle = "Notice Test modified";
		String changed = "changed";
		NoticeDto noticeDto = NoticeDto.builder()
				.title(newtitle)
				.content(changed)
				.build();
		
		//Then
		mockMvc.perform(put("/api/notice/{id}", notice.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(noticeDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
						)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("title").value(newtitle))
			.andExpect(jsonPath("content").value(changed))
			.andDo(document("update-notice", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("title").description("title of new notice"),
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("readCnt").description("read count of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
						
					)
			));
	}
	
	@Test
	@DisplayName("Notice 권한없는 사람 update")
	public void updateNotice_UnAuth_401() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		Account unauth = generateAccount_arg("unauth", "pass");
		//공지사항 글작성
		Notice notice = generateNotice(unauth);
		
		String newtitle = "Notice Test modified";
		String changed = "changed";
		NoticeDto noticeDto = NoticeDto.builder()
				.title(newtitle)
				.content(changed)
				.build();
				
		mockMvc.perform(put("/api/notice/{id}", notice.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(noticeDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().is(401))
		;
	}
	
	@Test
	@DisplayName("Notice 삭제")
	public void deleteNotice() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//해당 계정으로 공지사항 글 작성
		Notice notice = generateNotice(account);
			
		//Then
		mockMvc.perform(delete("/api/notice/{id}", notice.getId())
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
						)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("useYn").value(UseYn.N.toString()))
			.andDo(document("delete-notice", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header")
				),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("title").description("title of new notice"),
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("readCnt").description("read count of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
					)
			));
		;
	}
	
	@Test
	@DisplayName("Notice 권한없는 사람 delete")
	public void deleteNotice_UnAuth_401() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		Account unauth = generateAccount_arg("unauth", "pass");
		//공지사항 글작성
		Notice notice = generateNotice(unauth);
		
		String newtitle = "Notice Test modified";
		String changed = "changed";
		NoticeDto noticeDto = NoticeDto.builder()
				.title(newtitle)
				.content(changed)
				.build();
				
		mockMvc.perform(delete("/api/notice/{id}", notice.getId())
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().is(401))
		;
	}
	
	
	@Test
	@DisplayName("Notice 댓글 작성")
	public void noticeReplyCreate() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		String replyText = "reply test";
		//reply Entity - notice, bulletin의 댓글 entity
		ReplyDto replyDto= ReplyDto.builder()
					.content(replyText)
					.build();
		
		mockMvc.perform(post("/api/notice/{id}/reply", notice.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(replyDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("content").value(replyText))
			.andDo(document("create-notice-reply", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile"),
				    linkWithRel("query-notice-reply").description("link for query notices"),
					linkWithRel("update-notice-reply").description("link for updating the notice"),
					linkWithRel("create-notice-reply-re").description("link for create the re-reply to notice")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
						
					)
				
			))
			;
	}
	
	@Test
	@DisplayName("Notice 대-댓글 작성")
	public void noticeReplyCreateRE() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		String replyText = "reply test";
		//reply Entity - notice, bulletin의 댓글 entity
		Reply reply= Reply.builder()
					.content(replyText)
					.build();
		reply = noticeReplyRepository.save(reply);
		
		//댓글에 대한 댓글
		String re_replyText = replyText + "_re";
		ReplyDto replyDto = ReplyDto.builder()
					.content(re_replyText)
					.build();
		
		
		mockMvc.perform(post("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(replyDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("content").value(re_replyText))
			.andDo(document("create-notice-reply-re", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile"),
				    linkWithRel("query-notice-reply").description("link for query notices"),
					linkWithRel("update-notice-reply").description("link for updating the notice")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
						
					)
				
			))
			;
	}
	
	@Test
	@DisplayName("Notice 댓글 전체 조회")
	public void queryNoticeReply() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		
		
		IntStream.range(0, 30).forEach(index -> generateReply(index, notice, account));
		
		mockMvc.perform(get("/api/notice/{id}/reply", notice.getId())
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("query-notice-reply", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile"),
			    linkWithRel("first").description("link to first page of this list"),
			    linkWithRel("next").description("link to next page of this list"),
			    linkWithRel("last").description("link to last page of this list")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("_embedded.replyList[0].id").description("Id of new notice-reply"),
											
					//request +
					fieldWithPath("_embedded.replyList[0].content").description("content of new notice-reply"),
					fieldWithPath("_embedded.replyList[0].parent").description("parent reply of this reply"),
					fieldWithPath("_embedded.replyList[0].writerIp").description("writer IP Port of new notice-reply"),
					fieldWithPath("_embedded.replyList[0].instDtm").description("insert DateTime of new notice-reply"),
					fieldWithPath("_embedded.replyList[0].mdfDtm").description("modified DateTime of new notice-reply"),
					fieldWithPath("_embedded.replyList[0].inster").description("insert account ID of new notice-reply"),
					fieldWithPath("_embedded.replyList[0].mdfer").description("modified account ID of new notice-reply")
				)
			
		))
		;
	}
	
	@Test
	@DisplayName("Notice 댓글 단건 조회")
	public void getNoticeReply() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		Reply reply = generateReply(1, notice, account);
		
		mockMvc.perform(get("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("content").value("test1"))
		.andDo(document("get-notice-reply", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile"),
				linkWithRel("update-notice-reply").description("link for updating the notice")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("id").description("Id of new notice"),
											
					//request +
					fieldWithPath("content").description("content of new notice"),
					fieldWithPath("parent").description("parent reply of this reply"),
					fieldWithPath("writerIp").description("writer IP Port of new notice"),
					fieldWithPath("instDtm").description("insert DateTime of new notice"),
					fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
					fieldWithPath("inster").description("insert account ID of new notice"),
					fieldWithPath("mdfer").description("modified account ID of new notice")
					
				)
		))
		;
	}
	
	@Test
	@DisplayName("Notice 댓글 단건 수정")
	public void updateNoticeReply() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		Reply reply = generateReply(1, notice, account);
		
		String context = "modified-complete";
		ReplyDto replyDto = ReplyDto.builder()
							.content(context)
							.build();
				
		mockMvc.perform(put("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(replyDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("content").value(context))
		.andDo(document("update-notice-reply", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("id").description("Id of new notice"),
											
					//request +
					fieldWithPath("content").description("content of new notice"),
					fieldWithPath("parent").description("parent reply of this reply"),
					fieldWithPath("writerIp").description("writer IP Port of new notice"),
					fieldWithPath("instDtm").description("insert DateTime of new notice"),
					fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
					fieldWithPath("inster").description("insert account ID of new notice"),
					fieldWithPath("mdfer").description("modified account ID of new notice")
					
				)
		))
		;
	}
	
	@Test
	@DisplayName("Notice 댓글 권한없는 사람 update")
	public void updateNoticeReply_UnAuth_401() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		Account unauth = generateAccount_arg("unauth", "pass");
		//공지사항 글작성
		Notice notice = generateNotice(unauth);
		
		Reply reply = generateReply(1, notice, unauth);
		
		String context = "modified-complete";
		ReplyDto replyDto = ReplyDto.builder()
							.content(context)
							.build();
				
		mockMvc.perform(put("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(replyDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().is(401))
		;
	}
	
	
	@Test
	@DisplayName("Notice 댓글 단건 삭제")
	public void deleteNoticeReply() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		//공지사항 글작성
		Notice notice = generateNotice(account);
		
		Reply reply = generateReply(1, notice, account);
		
		mockMvc.perform(delete("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("delete-notice-reply", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			)
		))
		;
	}
	
	
	@Test
	@DisplayName("Notice 댓글 권한없는 사람 delete")
	public void deleteNoticeReply_UnAuth_401() throws Exception {
		//Given
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));
		
		Account unauth = generateAccount_arg("unauth", "pass");
		//공지사항 글작성
		Notice notice = generateNotice(unauth);
		
		Reply reply = generateReply(1, notice, unauth);
		
		String context = "modified-complete";
		ReplyDto replyDto = ReplyDto.builder()
							.content(context)
							.build();
				
		mockMvc.perform(delete("/api/notice/{id}/reply/{replyId}", notice.getId(), reply.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(replyDto))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
				)
		.andDo(print())
		.andExpect(status().is(401))
		;
	}
	
	

	
	
	/**
	 * @method generateReply
	 * @param index
	 * @return
	 * Reply
	 * @description 댓글생성 
	 */
	private Reply generateReply(int index, Notice notice, Account account) {
		Reply reply= Reply.builder()
				.content("test" + index)
				.writerIp("127.0.0.1")
				.inster(account)
				.mdfer(account)
				.notice(notice)
				.build();
		return noticeReplyRepository.save(reply);
	}

	/**
	 * @method getTestDoc
	 * @return
	 * String
	 * @throws IOException 
	 * @description 
	 */
	private String getTestDoc() throws IOException {
		StringBuilder sb = new StringBuilder();
		File f = new File("src/test/resource/test.html");
		BufferedReader br = new BufferedReader(
				new FileReader(f, Charset.forName("UTF-8")),
				16 * 1024
				);
		String str = null;
		while((str = br.readLine())!= null) {
			sb.append(str);
		}
		
		br.close();
		return sb.toString();
				
		
	}

	/**
	 * @method getAccessToken
	 * @param account
	 * @param b
	 * @return
	 * String
	 * @throws Exception 
	 * @description 
	 */
	public String getAccessToken(boolean isAccount) throws Exception {
		// Given
		if(isAccount) {
			generateAccount();
		}
		

		// com.mermer.config.AppConfig.applicationRunner() 에서 app 통해 최초 생성되는 계정과 겹치면 중복
		// 에러 발생
		if(appProperties == null)return "";
		String username = appProperties.getAdminName(); 
		String password = appProperties.getAdminPass();
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();

		ResultActions perform = mockMvc.perform(post("/oauth/token").with(httpBasic(clientId, clientSecret))
				.param("username", username).param("password", password).param("grant_type", "password"));
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();

		return parser.parseMap(responseBody).get("access_token").toString();
	}

	/**Token(getAccessToken(true))) //포스트 픽스로 "Bear
	 * @method generateAccount
	 * void
	 * @description 
	 */
	private Account generateAccount() {
		String username = appProperties.getAdminName(); 
		String password = appProperties.getAdminPass();
		Account account = Account.builder()
				.login(username)
				.username(username)
				.hpNum("01012345678")
				.email("admin@naver.com")
				.pass(password)
				.role(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
	
		account = accountService.saveAccount(account);
		return account;
		
	}
	
	//임의의 이름,비번으로 계정 만들기 
	private Account generateAccount_arg(String username, String password) {

		Account account = Account.builder()
				.login(username)
				.username(username)
				.hpNum("01012345678")
				.email("admin@naver.com")
				.pass(password)
				.role(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
	
		account = accountService.saveAccount(account);
		return account;
		
	}
	
	
	/**
	 * @method generateNotice
	 * @return Notice
	 * @description 테스트 공지사항 작성
	 */
	private Notice generateNotice(Account account) {
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = Notice.builder()
				.title(title)
				.content(testDoc)
				.inster(account)
				.mdfer(account)
				.writerIp("127.0.0.1")
				.build();
		
		return noticeRepository.save(notice);
	}
	
	/**
	 * @method generateNotice
	 * @return Notice
	 * @description 테스트 공지사항 작성
	 */
	private Notice generateNotice(Integer index) {
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = Notice.builder()
				.title(title + index)
				.content(testDoc)
				.writerIp("127.0.0.1")
				.build();
		
		return noticeRepository.save(notice);
	}
	

	/**
	 * @method getBearerToken
	 * @param accessToken
	 * @return
	 * Object
	 * @description 
	 */
	public String getBearerToken(String accessToken) {
		return "Bearer " + accessToken;
	}
}
