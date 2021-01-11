package cn.xdaoy.common.bean;

import java.util.List;

public class TreeBean extends BaseBean{

	private static final long serialVersionUID = 1L;
	
	private List<? extends TreeBean> children;

	public List<? extends TreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<? extends TreeBean> children) {
		this.children = children;
	}

	public TreeBean() {
		super();
	}
	
	public TreeBean(String id) {
		super(id);
	}

	
}
