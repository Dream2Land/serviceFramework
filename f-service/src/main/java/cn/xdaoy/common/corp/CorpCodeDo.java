package cn.xdaoy.common.corp;

import cn.xdaoy.common.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CorpCodeDo extends BaseBean{

	private static final long serialVersionUID = 1L;

	private String corpid;
	
	private String tradecode;
	
	private String tradecodetext;
	
	private String url;
	
	private String begintime;
	
	private String endtime;
	
	private String enable;


}
