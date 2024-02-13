package innovationT.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import innovationT.vo.BoardVO;
import innovationT.vo.CommentVO;
import innovationT.vo.PaginationInfo;

@Mapper
public interface BoardDAO {

	public int selectTotalRecord(PaginationInfo<BoardVO> paging);

	public List<BoardVO> selectBoardList(PaginationInfo<BoardVO> paging);

	public int insertBoard(BoardVO board);

	public int selectBoNo();

	public int updateBoard(BoardVO board);

	public int removeBoard(int boNo);

	public BoardVO selectOneBoard(int boNo);

	public void incrementHit(int boNo);

	public int insertComm(CommentVO comment);

	public int selectCommNo(int boNo);

	public String selectPw(int boNo);

	public List<CommentVO> selectCommList(CommentVO comment);

}
