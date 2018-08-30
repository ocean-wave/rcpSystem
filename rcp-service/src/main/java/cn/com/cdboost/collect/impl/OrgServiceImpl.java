package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.RedisKeyConstant;
import cn.com.cdboost.collect.dao.OrgMapper;
import cn.com.cdboost.collect.dto.OrgTreeInfo;
import cn.com.cdboost.collect.dto.param.OrgInforVo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.Org;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.util.RedisUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 组织服务接口实现类
 */
@Service
public class OrgServiceImpl extends BaseServiceImpl<Org> implements OrgService {

	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public List<OrgTreeInfo> queryByUserId(Long userId,String orgNo) {
		List<OrgTreeInfo> treeInfos = Lists.newArrayList();
		List<Org> list;
		if(!StringUtils.isEmpty(orgNo)){
			list = queryChildren(Long.valueOf(orgNo));
		}else{
			list = orgMapper.selectByUserId(userId);
		}
		if (CollectionUtils.isEmpty(list)) {
			return treeInfos;
		}

		// 复制
		for (Org org : list) {
			OrgTreeInfo info = new OrgTreeInfo();
			BeanUtils.copyProperties(org,info);
			treeInfos.add(info);
		}

		return refactorOrgList(treeInfos,null);
	}

	@Override
	public Org queryByOrgNo(Long orgNo) {
		Org param = new Org();
		param.setOrgNo(orgNo);
		return orgMapper.selectOne(param);
	}

	@Override
	public List<Org> batchQueryByOrgNos(List<Long> orgNos) {
		Condition condition = new Condition(Org.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("orgNo",orgNos);
		return orgMapper.selectByCondition(condition);
	}

	@Override
	public List<Org> queryChildren(Long orgNo) {
		Condition condition = new Condition(Org.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andLike("levelCode","%/"+orgNo+"%");
		criteria.andEqualTo("isEnabled","1");
		return orgMapper.selectByCondition(condition);
	}

	@Override
	public List<Org> queryTreeByOrgNo(Long orgNo) {
		// 查询当前组织以及其孩子组织
		List<Org> children = this.queryChildren(orgNo);

		// 查询当前组织的父组织
		List<Org> parentList = this.queryParent(orgNo);

		// 合并
		children.addAll(parentList);
		return children;
	}

	@Override
	public List<Org> queryParent(Long orgNo) {
		List<Org> dataList = Lists.newArrayList();
		// 查询当前组织
		Org org = this.queryByOrgNo(orgNo);
		List<Long> parentOrgNo = this.getParentOrgNo(org);
		if (CollectionUtils.isEmpty(parentOrgNo)) {
			return dataList;
		}

		// 当前节点的父级节点
		dataList = this.batchQueryByOrgNos(parentOrgNo);
		return dataList;
	}

	/**
	 * 查询当前组织的所有父组织orgNo列表
	 * @param org
	 * @return
	 */
	private List<Long> getParentOrgNo(Org org) {
		List<Long> dataList = Lists.newArrayList();
		String levelCode = org.getLevelCode();
		// 去掉自身orgNo
		String tempStr = levelCode.substring(0, levelCode.lastIndexOf("/"));
		if (!StringUtils.isEmpty(tempStr)) {
			// 去掉第一个斜杠
			String substring = tempStr.substring(1);
			String[] split = substring.split("/");
			for (String s : split) {
				dataList.add(Long.valueOf(s));
			}
		}
		return dataList;
	}

	@Override
	public Map<Long, List<Org>> queryParentListMap(Set<Long> orgNoSet) {
		Map<Long,List<Org>> dataMap = Maps.newHashMap();
		for (Long orgNo : orgNoSet) {
			List<Org> orgs = this.queryParent(orgNo);
			dataMap.put(orgNo,orgs);
		}
		return dataMap;
	}

	@Override
	public List<Org> queryNextChild(Set<Long> orgNoSet) {
		Condition condition = new Condition(Org.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("pOrgNo",orgNoSet);
		List<Org> orgs = orgMapper.selectByCondition(condition);
		return orgs;
	}

	@Override
	public List<Org> queryParentAndCurrent(Set<Long> orgNoSet) {
		// 批量查询当前节点
		List<Long> orgNoList = Lists.newArrayList(orgNoSet);
		List<Org> orgs = this.batchQueryByOrgNos(orgNoList);

		// 找出所有父节点组织编号
		Set<Long> all = Sets.newHashSet();
		for (Org org : orgs) {
			String levelCode = org.getLevelCode();
			String tempStr = levelCode.substring(1);
			String[] split = tempStr.split("/");
			for (String s : split) {
				all.add(Long.valueOf(s));
			}
		}

		// 查询所有的组织
		List<Long> paramList = Lists.newArrayList(all);
		List<Org> dataList = this.batchQueryByOrgNos(paramList);
		return dataList;
	}

	@Override
	public List<Org> queryAll() {
		Org param = new Org();
		param.setIsEnabled(1);
		return orgMapper.select(param);
	}

	@Override
	public int updateByorgNo(String orgNo,String orgName,Integer id,String pOrgNo) {
		if(orgNo.equals(pOrgNo)){
			throw new BusinessException("不允许修改自己为父级");
		}
		Condition condition=new Condition(Org.class);
		Example.Criteria criteria=condition.createCriteria();
		criteria.andEqualTo("orgNo",orgNo);
		Org org4 = queryByOrgNo(Long.valueOf(orgNo));
		if(org4==null){
			throw new BusinessException("修改的组织不存在");
		}
		Condition condition1=new Condition(Org.class);
		Example.Criteria criteria1=condition1.createCriteria();
		criteria1.andLike("levelCode",org4.getLevelCode()+"%");
		criteria1.andEqualTo("level",org4.getLevel()+1);
		List<Org> orgs = orgMapper.selectByCondition(condition1);
		Condition condition2=new Condition(Org.class);
		Example.Criteria criteria2=condition2.createCriteria();
		criteria2.andLike("levelCode",org4.getLevelCode()+"%");
		List<Org> orgs2 = orgMapper.selectByCondition(condition2);
		for (Org org : orgs2) {
			if(pOrgNo.equals(String.valueOf(org.getOrgNo()))){
				throw new BusinessException("父级不能放在子级下");
			}
		}
		Org org=new Org();
		org.setOrgName(orgName);
		org.setIsEnabled(1);
		Org select = orgMapper.selectOne(org);
		if(select!=null){
			if(!orgNo.equals(String.valueOf(select.getOrgNo()))){
				throw new BusinessException("不允许修改重复机构");
			}
		}
		if("0".equals(pOrgNo)){
			Org org1=new Org();
			org1.setOrgName(orgName);
			org1.setCreateUserId(Long.valueOf(id));
			int i = orgMapper.updateByConditionSelective(org1, condition);
            if(i==1){
            	this.deleteOrgCache();
			}
			return i;
		}else{
			Org org2 = queryByOrgNo(Long.valueOf(pOrgNo));
			if(org2!=null){
				Org org3=new Org();
				org3.setOrgName(orgName);
				org3.setCreateUserId(Long.valueOf(id));
				StringBuffer levelcode=new StringBuffer(org2.getLevelCode());
				org3.setLevelCode(String.valueOf(levelcode.append("/").append(orgNo)));
				org3.setLevel(org2.getLevel()+1);
				org3.setpOrgNo(org2.getOrgNo());
				List<Org> list= Lists.newArrayList();
				for (Org org1 : orgs) {
					if(!orgNo.equals(String.valueOf(org1.getOrgNo()))&&!pOrgNo.equals(String.valueOf(org1.getOrgNo()))){
						list.add(org1);
					}
				}
				orgMapper.updateByConditionSelective(org3,condition);
				for (Org org1 : list) {
					generatechilden(id, org1,org3);
				}

				// redis 操作
				this.deleteOrgCache();
				return 1;
			}
			return 0;
		}
	}
	private void generatechilden( Integer id,  Org org,Org pOrg) {
		Condition condition=new Condition(Org.class);
		Example.Criteria criteria=condition.createCriteria();
		criteria.andLike("levelCode",org.getLevelCode()+"%");
		criteria.andNotEqualTo("orgNo",org.getOrgNo());
		criteria.andEqualTo("level",org.getLevel()+1);
		List<Org> orgs = orgMapper.selectByCondition(condition);
		org.setCreateUserId(Long.valueOf(id));
		StringBuffer levelcode=new StringBuffer(pOrg.getLevelCode());
		org.setLevelCode(String.valueOf(levelcode.append("/").append(org.getOrgNo())));
		org.setLevel(pOrg.getLevel()+1);
		orgMapper.updateByPrimaryKeySelective(org);
		if(orgs.size()>0){
			for (Org org1 : orgs) {
				generatechilden(id,org1,org);
			}
		}
	}

	@Override
	public int deletebyorgNo(Long orgNo) {
		List<Org> children = this.queryChildren(orgNo);
		List<Long> orgNoList = Lists.newArrayList(orgNo);
		for (Org child : children) {
			orgNoList.add(child.getOrgNo());
		}
		int result = orgMapper.batchUpdate(orgNoList);
		return result;
	}

	@Override
	public int addByorgNo(String porgNo, String orgName, Integer id) {
		Org org2=new Org();
		org2.setOrgName(orgName);
		org2.setIsEnabled(1);
		Org select = orgMapper.selectOne(org2);
		if(select!=null){
			throw new BusinessException("不允许添加重复机构");
		}
		OrgInforVo orgInforVo=new OrgInforVo();
		orgInforVo.setId(String.valueOf(id));
		orgInforVo.setPorgNo(porgNo);
		orgInforVo.setOrgName(orgName);
		orgMapper.insertOrg(orgInforVo);

		Integer result = orgInforVo.getResult();
		if (result == 1) {
			this.deleteOrgCache();
		}
		return result;
	}

	@Override
	public List<Long> queryDataOrg(Collection<Long> orgNos) {
		Set<Long> orgNoSet = Sets.newHashSet();
		for (Long orgNo : orgNos) {
			List<Org> children = this.queryChildren(orgNo);
			for (Org child : children) {
				orgNoSet.add(child.getOrgNo());
			}
		}
		List<Long> list = Lists.newArrayList(orgNoSet);
		return list;
	}

	/**
	 * 删除组织相关缓存
	 */
	private void deleteOrgCache() {
		Set<String> keys = Sets.newHashSet();
		// 删除用户组织权限缓存
		String pattern = RedisKeyConstant.USER_ORGS_PREFIX + "*";
		Set<String> set1 = redisUtil.keys(pattern);
		keys.addAll(set1);

		// 删除组织以及他的孩子节点列表
		String pattern2 = RedisKeyConstant.ORG_CHILD_PREFIX + "*";
		Set<String> set2 = redisUtil.keys(pattern2);
		keys.addAll(set2);

		// 删除某个组织所在的整个组织树列表
		String pattern3 = RedisKeyConstant.ORG_TREE_PREFIX + "*";
		Set<String> set3 = redisUtil.keys(pattern3);
		keys.addAll(set3);

		// 删除组织是否存在子节点缓存
		String pattern4 = RedisKeyConstant.ORG_EXIST_CHILD_PREFIX + "*";
		Set<String> set4 = redisUtil.keys(pattern4);
		keys.addAll(set4);

		// 删除当前组织的父组织
		String pattern5 = RedisKeyConstant.ORG_PARENT_PREFIX + "*";
		Set<String> set5 = redisUtil.keys(pattern5);
		keys.addAll(set5);

		// 删除组织信息
		String pattern6 = RedisKeyConstant.ORG_PREFIX + "*";
		Set<String> set6 = redisUtil.keys(pattern6);
		keys.addAll(set6);

		redisUtil.batchDeleteKeys(keys);
	}

	private List<OrgTreeInfo> refactorOrgList(List<OrgTreeInfo> srclist, Org org) {
		// 存储新的组织机构
		List<OrgTreeInfo> newList = null;
		// 遍历查询的组织结构
		for (OrgTreeInfo organization : srclist) {

			if (!organization.getUse() && (org == null || organization.getpOrgNo().equals(org.getOrgNo()))) {
				if (newList == null) {
					newList = new LinkedList<>();
				}

				OrgTreeInfo newOrg = new OrgTreeInfo();
				newOrg.setOrgNo(organization.getOrgNo());
				newOrg.setpOrgNo(organization.getpOrgNo());
				newOrg.setOrgName(organization.getOrgName());
				
				// 查询子节点
				List<OrgTreeInfo> childNode = refactorOrgList(srclist, newOrg);
				if (childNode != null) {
					newOrg.setChildren(childNode);
				}
				
				newList.add(newOrg);
				// 配置当前节点已经操作
				organization.setUse(true);
			}
		}

		return newList;
	}
}
