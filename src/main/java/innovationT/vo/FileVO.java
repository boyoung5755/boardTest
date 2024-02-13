package innovationT.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="fileCode")
public class FileVO implements Serializable{
	
	private MultipartFile boardFile;
	
	private String fileCode       ;    //게시판과 파일의 공통코드
	private int boNo           ;
	private String fileName       ;
	private String fileSn         ;		//파일의 저장이름
	private String fileMime       ;
	private long fileSize       ;
	private String fileFancysize  ;
	private int fileNo; 				//파일의순번 
	
	
	//파일을 지정된 폴더에 저장
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(boardFile != null) {
			boardFile.transferTo(new File(saveFolder, fileSn));
		}
	}
	
	public FileVO() {};
	
	
	public FileVO(MultipartFile boardFile) {
		super();
		this.boardFile = boardFile;
		this.fileName = boardFile.getOriginalFilename();
		this.fileSn = UUID.randomUUID().toString();
		this.fileMime = boardFile.getContentType();
		this.fileSize = boardFile.getSize();
		this.fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
	}
	

}
