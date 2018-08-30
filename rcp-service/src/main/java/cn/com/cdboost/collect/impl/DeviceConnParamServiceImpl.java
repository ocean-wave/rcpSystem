package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceConnParamMapper;
import cn.com.cdboost.collect.model.DeviceConnParam;
import cn.com.cdboost.collect.service.DeviceConnParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 设备连接参数接口服务实现类
 */
@Service("deviceConnParamService")
public class DeviceConnParamServiceImpl extends BaseServiceImpl<DeviceConnParam> implements DeviceConnParamService {

	@Autowired
	private DeviceConnParamMapper deviceConnParamMapper;

	@Override
	@Transactional
	public int updateSelectiveByCno(DeviceConnParam deviceConnParam) {
		Condition cond = new Condition(DeviceConnParam.class);
		Example.Criteria criteria = cond.createCriteria();
		criteria.andEqualTo("cno", deviceConnParam.getCno());
		return deviceConnParamMapper.updateByConditionSelective(deviceConnParam,cond);

	}

	@Override
	public DeviceConnParam selectByCno(String cno) {
		DeviceConnParam param = new DeviceConnParam();
		param.setCno(cno);
		return deviceConnParamMapper.selectOne(param);
	}

	@Override
	@Transactional
	public void batchDeleteByCnos(List<String> cnos) {
		Condition condition = new Condition(DeviceConnParam.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andIn("cno",cnos);
		deviceConnParamMapper.deleteByCondition(condition);
	}

	@Override
	public void deleteByCno(String cno) {
		DeviceConnParam param = new DeviceConnParam();
		param.setCno(cno);
		deviceConnParamMapper.delete(param);
	}
}
