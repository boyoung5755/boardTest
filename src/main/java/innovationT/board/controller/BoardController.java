package innovationT.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import innovationT.board.service.BoardService;
import innovationT.common.ServiceResult;
import innovationT.vo.BoardVO;
import innovationT.vo.CommentVO;
import innovationT.vo.FileVO;
import innovationT.vo.PaginationInfo;
import innovationT.vo.SearchVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Inject
	private BoardService service; 
	
	@Value("#{appInfo.boardFiles}")
	private Resource boardFiles;
	
	@Value("#{appInfo.boardImagesUrl}")
	private String boardImagesUrl;
	
	@Value("#{appInfo.boardImagesUrl}")
	private Resource boardImages;	//폴더는 리소스로 받아와야함
	
	
	//게시판 리스트 비동기 불러오기
	@GetMapping("list/{option}")
	@ResponseBody
	public Map<String,PaginationInfo<BoardVO>> boardList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, @RequestParam(value = "listNum", required = false, defaultValue = "5") int listNum
			, @PathVariable String option
			, @ModelAttribute("simpleCondition") SearchVO simpleCondition	
		){
		
		Map<String, PaginationInfo<BoardVO>> map = new HashMap<String, PaginationInfo<BoardVO>>();
		
		BoardVO board = new BoardVO();
		
		//페이지
		PaginationInfo<BoardVO> paging = new PaginationInfo<BoardVO>(listNum,3);
		paging.setDetailCondition(board);	//키워드 검색 조건
		paging.setSimpleCondition(simpleCondition);
		paging.setCurrentPage(currentPage);
		
		paging.setListNum(listNum);
		paging.setOption(option);
		
		service.retrieveBoardList(paging);
		map.put("paging", paging);
		

		return map;
	}
	
	//댓글리스트
	@GetMapping("commList")
	@ResponseBody
	public Map<String, List<CommentVO>> retrieveCommList(
		@ModelAttribute("commVO") CommentVO comment
		){
		Map<String,List<CommentVO>> map = new HashMap<String, List<CommentVO>>();
		List<CommentVO> commList = service.retrieveCommList(comment);
		map.put("commList" , commList);
		return map;
	}
	
	//파일업로드
	@PostMapping("fileUpload/{fileCode}")
	public void fileUpload(
		@PathVariable String fileCode
		, MultipartHttpServletRequest req
		) {
		MultiValueMap<String, MultipartFile> mvMap = req.getMultiFileMap();
		MultipartFile[] multipartFiles = new MultipartFile[mvMap.size()]; //업로드된 파일을 배열에 담아주기
		int i = 0;
		for(String key : mvMap.keySet()) {
			multipartFiles[i] = ((List<MultipartFile>) mvMap.get(key)).get(0);
			i++;
		}
		service.createFileUpload(multipartFiles,fileCode);
	}
	
	
	/*
	 * 스프링제공인터페이스
	 * MultipartHttpServletRequest :  List 형태로 다중파일데이터를 받을수있다.
	 * MultiValueMap : value의 값을 list로 
	 * 
	 */
	
	//게시글 입력폼 
	@GetMapping("form")
	public String boardForm(Model model) {
		
		//랜덤문자보내기
		model.addAttribute("rndStr", createRandomStrUsingUtilsRand());
		
		return "/board/boardForm";
	}
	
	//수정시 비밀번호 확인
	@PostMapping("pwCheck")
	@ResponseBody
	public Map<String, String> pwCheck(
		@ModelAttribute("boardVO") BoardVO board	
		){
		Map<String, String> map = new HashMap<String, String>();
		ServiceResult result = service.retrievePw(board);
		map =resultMap(result);
		return map;
	}
	
	//게시글 수정폼
	@PostMapping("edit/{boNo}")
	public String boardEdit(
			@PathVariable int boNo
			, @ModelAttribute("boardVO") BoardVO board	
			, Model model
		) {
		//비밀번호확인  
		ServiceResult result = service.retrievePw(board);
		String viewName="";
		
		if(result.equals(ServiceResult.OK)) {
			BoardVO boardDetail = new BoardVO();
			boardDetail = service.retrieveOneBoard(boNo);
			model.addAttribute("detail", boardDetail);
			viewName="/board/boardEdit";
		}else {
			throw new RuntimeException();
		}
		return viewName;
	}	
	
	//댓글 등록하기
	@PostMapping("insertComm")
	@ResponseBody
	public Map<String, String> insertComm(
		@ModelAttribute("commVO") CommentVO comment	
		){
		Map<String, String> map = new HashMap<String, String>();
		ServiceResult result = service.createComm(comment);
		map =resultMap(result);
		map.put("boNo", String.valueOf(comment.getBoNo()));
		return map;
	}
	
	
	//게시글 수정하기
	@PutMapping("update/{boNo}")
	@ResponseBody
	public Map<String, String> updateBoard(
			@ModelAttribute("boardVO") BoardVO board
		){
		Map<String, String> map = new HashMap<String, String>();
		ServiceResult result = service.modifyBoard(board);
		map =resultMap(result);
		map.put("boNo", String.valueOf(board.getBoNo()));
		return map;
	}
	
	//게시글 삭제하기
	@PostMapping("delete")
	@ResponseBody
	public Map<String, String> deleteBoard(
		@RequestParam(value="boNo" , required = true) int boNo	
		){
		Map<String, String> map = new HashMap<String, String>();
		ServiceResult result = service.removeBoard(boNo);
		map =resultMap(result);
		return map;
	}
	
	//게시글 상세보기
	@GetMapping("detail/{boNo}")
	public String selectOneBoard(
			@PathVariable int boNo
			, Model model
		){
		BoardVO boardDetail = new BoardVO();

		boardDetail = service.retrieveOneBoard(boNo);
		model.addAttribute("detail", boardDetail);
		
		return "/board/boardDetail";
	}

	
	//게시글 등록하기
	@PostMapping("insert")
	@ResponseBody
	public Map<String, String> insertBoard(
			@ModelAttribute("boardVO") BoardVO board
		){
		Map<String, String> map = new HashMap<String, String>();
		ServiceResult result = service.createBoard(board);
		String boNo = String.valueOf(service.getBoNo()-1);
		map =resultMap(result);
		map.put("boNo", boNo);
		return map;
	}
	

	//성공실패확인
	public Map<String, String> resultMap(ServiceResult result){
		Map<String, String> resMap = new HashMap<String, String>();
		switch (result) {
		case OK:
			resMap.put("success", "Y");
			break;
		default:
			resMap.put("success", "N");
			break;
		}
		return resMap;
	}
	
	
	//ck에디터 이미지 업로드
	@PostMapping("image")	
	public String imageUpload(MultipartFile upload, Model model, HttpServletRequest req) throws IOException {
	
		if(!upload.isEmpty()) {	//파일이 정상적으로 업로드 되고 있다.
			// 로컬의 파일의 url이 필요하므로 webResource형태로 저장이 되야함
			String saveName = UUID.randomUUID().toString(); //중복되지 않도록 uuid사용
			File saveFolder =  boardImages.getFile(); //파일로 만들어줘야 저장이 가능
			File saveFile = new File(saveFolder, saveName);
			upload.transferTo(saveFile);	// upload 완료
			
			String url = req.getContextPath()+boardImagesUrl + "/" + saveName;	// 이거 비동기의 응답데이터로 나가야함
			model.addAttribute("uploaded",1);
			model.addAttribute("fileName",upload.getOriginalFilename());	//원본파일명
			model.addAttribute("url",url);
		
		} else {	// 업로드가 되지 않았을때.
			model.addAttribute("uploaded",0);
			model.addAttribute("error", Collections.singletonMap("message", "업로드 된 파일 없음"));	// map의 엔트리가 1개밖에 없을 때 사용
		}
		return "jsonView";
		
	}
	
	//파일과 게시판의 저장될 랜덤번호
	public String createRandomStrUsingUtilsRand() {
		boolean useLetters = true; //랜덤 문자열에 영문자 (알파벳)가 포함
		boolean useNumbers = true; //랜덤 문자열에 숫자가 포함
		//랜덤문자자릿수
		int randomStrLen = 15;
		String randomStr = RandomStringUtils.random(randomStrLen, useLetters, useNumbers);
			
		System.out.println("생성된 랜덤문자열 : " + randomStr);
		return randomStr;
	}
	
	
	//파일 다운로드
	@RequestMapping("download")
	public ResponseEntity<InputStreamResource> downloadBoardFile(
			HttpServletResponse response
			, FileVO file
			) throws IOException {
		
		//파일 조회
		FileVO boardFile = service.retrieveFile(file);
		File tmpFile = new File(boardFiles.getFile().getAbsolutePath()+"/"+boardFile.getFileSn());
		
		InputStream res = new FileInputStream(tmpFile) {
			@Override
			public void close() throws IOException {
				super.close();
			}
		};

		// 응답 생성
		return ResponseEntity.ok().contentLength(tmpFile.length())
				.contentType(MediaType.parseMediaType(boardFile.getFileMime())) // 파일에 맞는 컨텐츠 타입 지정
				.header("Content-Disposition", "attachment;filename="+URLEncoder.encode(boardFile.getFileName(),"UTF-8")) // 파일 이름 설정
				.body(new InputStreamResource(res));
	}
	
}


