package innovationT.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(of="commNo")
public class CommentVO implements Serializable {
	
	private String commNo    ;
	private String commCn    ;
	private int boNo      ;
	private String commRdate ;
	private int maxNo;

}
