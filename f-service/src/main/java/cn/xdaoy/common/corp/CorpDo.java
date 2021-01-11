package cn.xdaoy.common.corp;


import java.util.ArrayList;
import java.util.List;

import cn.xdaoy.common.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CorpDo extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private String sysid;
	
	private String corpcode;
	
	private String corpname;

	private String corphost;
	
	private int corpport;
	
	private String corpproc;
	
	private String corpcharset;
	
	List<CorpCodeDo> corpCodeList = new ArrayList<>();
	
	public CorpDo(String sysid, String corpcode) {
		super();
		this.sysid = sysid;
		this.corpcode = corpcode;
	}
	
}
