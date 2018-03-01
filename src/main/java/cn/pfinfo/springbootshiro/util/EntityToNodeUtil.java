package cn.pfinfo.springbootshiro.util;

import java.util.ArrayList;
import java.util.Collection;

import cn.pfinfo.springbootshiro.entity.Organization;
import cn.pfinfo.springbootshiro.util.exception.ConnotChangeException;
import cn.pfinfo.springbootshiro.vo.TreeNode;

/**
 * 此类是一个工具类，用于将我的Entity类转化为TreeNode
 * @author panfei
 *
 */
public class EntityToNodeUtil {
	
	/**
	 * 将Organization转化为TreeNode
	 * @return
	 * @throws ConnotChangeException 
	 */
	public static TreeNode organizationToTreeNode(Organization org) throws ConnotChangeException{
		TreeNode node = new TreeNode();
		if(org!=null){
			node.setHref(org.getAddress());
			node.setId(org.getId());
			node.setName(org.getName());
			node.addNodes(OrganizationsToTreeNodes(org.getOrganizations()));
		}else{
			throw new ConnotChangeException();
		}
		return node;
	}
	/**
	 * 将<code>Collection<Organization></code>转化为<code>Collection<TreeNode></code>
	 * @return
	 */
	public static Collection<TreeNode> OrganizationsToTreeNodes(Collection<Organization> orgs){
		Collection<TreeNode> nodes= new ArrayList<>();
		orgs.forEach(org->{
			try {
				nodes.add(organizationToTreeNode(org));
			} catch (ConnotChangeException e) {
			}
		});
		return nodes;
	}

}
