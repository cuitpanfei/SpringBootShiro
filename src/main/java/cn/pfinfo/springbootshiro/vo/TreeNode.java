package cn.pfinfo.springbootshiro.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点类
 * 
 * @author panfei
 *
 */
@Setter
@Getter
public class TreeNode {
	private Long id;
	private String name;
	private String href;
	private String enname;
	private List<TreeNode> child;

	/**
	 * 批量添加子节点
	 */
	public void addNodes(Collection<TreeNode> nodes) {
		if (this.child == null) {
			this.child = new ArrayList<>();
		}
		this.child.addAll(nodes);
	}

	/**
	 * 添加子节点
	 */
	public void addNode(TreeNode node) {
		if (this.child == null) {
			this.child = new ArrayList<>();
		}
		this.child.add(node);
	}

	/**
	 * 通过节点id获取节点，如果传入的id 为<code>null</code>,该方法将会抛出异常:{@link NullPointerException}。 如果在本节点以及子节点中都找不到与此id一致的节点，将会返回<code>null</code>。
	 * 
	 * @param id
	 * @return TreeNode Or null
	 * @throws NullPointerException
	 */
	public TreeNode getNodeById(Long id) throws NullPointerException {
		if (id == null) {
			throw new NullPointerException();
		} else if (this.id == id) {
			return this;
		} else if (this.child != null) {
			for (TreeNode node : this.child) {
				return node.getNodeById(id);
			}
		}
		return null;
	}
	public boolean isEmpty(){
		boolean flag = false;
		if(this.id ==null){
			flag=true;
		}
		return flag;
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

}