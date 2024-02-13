package innovationT.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import innovationT.vo.FileVO;

@Mapper
public interface BoardFileDAO {

	public void insertBoardFile(FileVO atch);

	public List<FileVO> selectFileList(String fileCode);

	public FileVO selectOneFile(FileVO file);

}
