package innovationT.vo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of="boNo")
@ToString
public class BoardVO {
	
	private int rnum;

	private int boNo      ;
	private String boSj      ;
	private String boRdate   ;
	private String boRdcnt   ;
	private String boPw      ;
	private String boPwCheck ;
	private String boNick    ;
	private String boCn      ;
	private String fileCode  ;    //게시판과 파일에 공통으로 추가되는 코드
	
	private String option;   //리스트형 앨범형 옵션 구분
	
	private String boImage;  //ck에디터로 저장한 이미지 
	
	//1:N관계형성
	private List<FileVO> fileList;
	
	private List<CommentVO> commList;
	
	private MultipartFile[] boardFile;
	
	
	public void setBoardFile(MultipartFile[] boardFile) {
		if(boardFile != null) {
			this.boardFile = Arrays.stream(boardFile)
								. filter((f)->!f.isEmpty())
								.toArray(MultipartFile[]::new);
		}
		if(this.boardFile!=null) {
			this.fileList = Arrays.stream(this.boardFile)
								.map((f)->new FileVO(f))
								.collect(Collectors.toList());
		}
		
	}
	
	
}
