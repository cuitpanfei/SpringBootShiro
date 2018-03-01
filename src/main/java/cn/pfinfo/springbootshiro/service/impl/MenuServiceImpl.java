package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IMenuDao;
import cn.pfinfo.springbootshiro.dao.interf.IOrganizationDao;
import cn.pfinfo.springbootshiro.entity.Menu;
import cn.pfinfo.springbootshiro.entity.Organization;
import cn.pfinfo.springbootshiro.service.site.IMenuService;
import cn.pfinfo.springbootshiro.util.EntityToNodeUtil;
import cn.pfinfo.springbootshiro.util.exception.ConnotChangeException;
import cn.pfinfo.springbootshiro.vo.TreeNode;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements IMenuService {
	
	@Resource
    private IOrganizationDao OrganizationDao;
	@Resource
	private IMenuDao menuDao;

	public List<TreeNode> findAll() {
		List<Organization> list = OrganizationDao.findAll(new Specification<Organization>(){  
            @Override  
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {  
            	 Predicate p1 = criteriaBuilder.isNull(root.get("pOrganization"));
            	 query.where(criteriaBuilder.and(p1));  
                return query.getRestriction();  
            }  
        });
		TreeNode treeNode = new TreeNode();
		list.forEach(organization->{
			if (organization.getPOrganization() == null) {
				TreeNode node = null;
				try {
					node = EntityToNodeUtil.organizationToTreeNode(organization);
				} catch (ConnotChangeException e) {
					e.printStackTrace();
				}
				if(node!=null&&node.isNotEmpty()){
					treeNode.addNode(node);
				}
			}
		});
		return treeNode.getChild();
	}

	@Override
	public Menu getOne(Long id) {
		return menuDao.findOne(id);
	}


}
