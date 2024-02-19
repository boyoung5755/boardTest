package innovationT.board.service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import innovationT.board.dao.BoardDAO;
import innovationT.board.dao.BoardFileDAO;
import innovationT.common.ServiceResult;
import innovationT.util.Sha256EncryptUtil;
import innovationT.vo.BoardVO;
import innovationT.vo.CommentVO;
import innovationT.vo.FileVO;
import innovationT.vo.PaginationInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	
	private final BoardDAO dao;
	private final BoardFileDAO fDao;
	
	
	
	@Value("#{appInfo.boardFiles}")
	private Resource boardFiles;
	
	@Override
	public List<BoardVO> retrieveBoardList(PaginationInfo<BoardVO> paging) {
		
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<BoardVO> dataList = dao.selectBoardList(paging);
		paging.setDataList(dataList);
		return dataList;
		
	}

	@Override
	public ServiceResult createBoard(BoardVO board) {
		
		int newBoNo = getBoNo();
		board.setBoNo(newBoNo);
		
		//비밀번호암호화
		board.setBoPw(Sha256EncryptUtil.ShaEncoder(board.getBoPw()));
		
		//ck에디터 이미지 
		board.setBoImage(makeImageSrc(board.getBoCn()));
		
		int rowcnt = dao.insertBoard(board);
		
//		//파일 등록
//		try {
//			processBoardFiles(board);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		return DAOResult(rowcnt);
	}
	
	private String makeImageSrc(String makeImageSrc) {
		
		String imageUrl = "";
		String regex = "src=\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(makeImageSrc);
        
        // 매칭된 부분이 있다면
        if (matcher.find()) {
            // src 속성의 값 가져오기
            imageUrl = matcher.group(1);
            System.out.println("-----------------이미지URL----------------------->>"+imageUrl);
        } else {
            System.out.println("No match found.");
        }
		return imageUrl;
	}
	
	//등록될 게시판번호가져오기
	@Override
	public int getBoNo() {
		return dao.selectBoNo();
	}
	
	//결과
	private ServiceResult DAOResult(int rowcnt) {
		
		ServiceResult result = null;
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	
	//파일등록
	private void processBoardFiles(BoardVO board) {
		
		List<FileVO> fileList = board.getFileList();
		if(fileList!=null) {
			fileList.forEach((atch)->{
				try {
					atch.setBoNo(board.getBoNo());
					atch.setFileCode(board.getFileCode());
					fDao.insertBoardFile(atch);
					atch.saveTo(boardFiles.getFile());
					
				}catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		
		//ck에디터 이미지 
		board.setBoImage(makeImageSrc(board.getBoCn()));
		int rowcnt = dao.updateBoard(board);
		return DAOResult(rowcnt);
	}

	@Override
	public ServiceResult removeBoard(int boNo) {
		int rowcnt = dao.removeBoard(boNo);
		return DAOResult(rowcnt);
	}

	@Override
	public BoardVO retrieveOneBoard(int boNo) {
		BoardVO OneBoard = new BoardVO();
		//조회수증가
		dao.incrementHit(boNo);
		
		OneBoard = dao.selectOneBoard(boNo);
		
		//하나의 게시물의 파일리스트
		OneBoard.setFileList(fDao.selectFileList(OneBoard.getFileCode()));
		return OneBoard;
	}

	@Override
	public ServiceResult createComm(CommentVO comment) {
		
		int commMax = dao.selectCommNo(comment.getBoNo());
		String newCommNo = comment.getBoNo()+"_"+String.valueOf(commMax);
		comment.setCommNo(newCommNo);
		int rowcnt = dao.insertComm(comment);
		return DAOResult(rowcnt);
	}

	@Override
	public ServiceResult retrievePw(BoardVO board) {
		ServiceResult result= null;
		String savePw = dao.selectPw(board.getBoNo());
		String checkPw = Sha256EncryptUtil.ShaEncoder(board.getBoPwCheck());
		
		if(savePw.equals(checkPw)) {
			result = ServiceResult.OK;
		}else{
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<CommentVO> retrieveCommList(CommentVO comment) {
		List<CommentVO> commList = dao.selectCommList(comment);
		return commList;
	}

	@Override
	public ServiceResult createFileUpload(MultipartFile[] boardFile, String fileCode) {
		
		BoardVO board = new BoardVO();
		board.setBoardFile(boardFile);
		board.setFileCode(fileCode);
		
		ServiceResult result= null;
		//파일 등록
		try {
			processBoardFiles(board);
			result = ServiceResult.OK;
		} catch (Exception e) {
			// TODO: handle exception
			result = ServiceResult.FAIL;
		}
		
		return result;
	}

	@Override
	public FileVO retrieveFile(FileVO file) {
		return fDao.selectOneFile(file);
	}

}
