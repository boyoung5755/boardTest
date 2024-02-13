package innovationT.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import innovationT.common.ServiceResult;
import innovationT.vo.BoardVO;
import innovationT.vo.CommentVO;
import innovationT.vo.FileVO;
import innovationT.vo.PaginationInfo;

public interface BoardService {

	/**
	 * 게시판 목록 페이징
	 * @param paging
	 * @return 게시판리스트
	 */
	public List<BoardVO> retrieveBoardList(PaginationInfo<BoardVO> paging);

	/**
	 * 게시글 등록
	 * @param board
	 * @return 실패,성공
	 */
	public ServiceResult createBoard(BoardVO board);

	/**
	 * 새로운 게시판의 번호(시퀀스)
	 * @return
	 */
	public int getBoNo();

	/**
	 * 게시글 수정
	 * @param board
	 * @return 실패,성공
	 */
	public ServiceResult modifyBoard(BoardVO board);

	/**
	 * 게시글 삭제
	 * @param boNo
	 * @return 실패,성공
	 */
	public ServiceResult removeBoard(int boNo);

	/**
	 * 하나의 게시글
	 * @param boNo
	 * @return 하나의 게시글 상세
	 */
	public BoardVO retrieveOneBoard(int boNo);

	/**
	 * 댓글 등록하기
	 * @param comment
	 * @return 성공,실패
	 */
	public ServiceResult createComm(CommentVO comment);

	/**
	 * 비밀번호 확인
	 * @param board
	 * @return
	 */
	public ServiceResult retrievePw(BoardVO board);

	/**
	 * 댓글목록
	 * @param comment
	 * @return 댓글리스트
	 */
	public List<CommentVO> retrieveCommList(CommentVO comment);

	/**
	 * 파일등록
	 * @param boardFile
	 * @param fileCode
	 * @return 성공 , 실패
	 */
	public ServiceResult createFileUpload(MultipartFile[] boardFile, String fileCode);

	/**
	 * 파일다운로드
	 * @param files
	 * @return
	 */
	public FileVO retrieveFile(FileVO files);



}
