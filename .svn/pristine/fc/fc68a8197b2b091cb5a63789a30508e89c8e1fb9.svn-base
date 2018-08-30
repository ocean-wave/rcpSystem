package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.ChangeMeterOthMapper;
import cn.com.cdboost.collect.model.ChangeMeterOth;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.model.UserRole;
import cn.com.cdboost.collect.service.ChangeMeterOthService;
import cn.com.cdboost.collect.service.UserRoleService;
import cn.com.cdboost.collect.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 电表更换的其他参数项目服务接口实现类
 */
@Service
public class ChangeMeterOthServiceImpl extends BaseServiceImpl<ChangeMeterOth> implements ChangeMeterOthService {

    @Resource
    private ChangeMeterOthMapper changeMeterOthMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public void addUpdateTransaction(boolean flag) {
        ChangeMeterOth oth = new ChangeMeterOth();
        oth.setDataValue("1111");
        oth.setItemCode("1234");
        oth.setChangeFlag("1");
        changeMeterOthMapper.insertSelective(oth);

        ChangeMeterOth oth1 = changeMeterOthMapper.selectByPrimaryKey(1);

        User user = new User();
        user.setLoginName("fanlvyu");
        user.setUserPassword("654321");
        user.setOrgNo(1000L);
        user.setCreateUserId(1L);
        user.setUserMobile("13510362548");
        user.setCreateTime(new Date());
        user.setRemark("哈哈哈");
        user.setUserName("翻翻");
        userService.insertSelective(user);
        System.out.println("chenggong11111");

        UserRole userRole = new UserRole();
        userRole.setUserId(20L);
        userRole.setRoleId(21L);
        userRoleService.insertSelective(userRole);
        System.out.println("chenggong22222");

        if (!flag) {
            int i = 10;
            int j = 0;
            int result = i/j;
            System.out.println(result);
        }

        oth1.setChangeFlag("fly");
        oth1.setItemCode("fly001");
        oth1.setDataValue("fly1002");
        changeMeterOthMapper.updateByPrimaryKey(oth1);
        System.out.println("执行成功");
    }

    @Override
    @Transactional
    public void updateSingle() {
        ChangeMeterOth oth = new ChangeMeterOth();
        oth.setDataValue("1234");
        oth.setItemCode("6789");
        oth.setId(11);
        int i = changeMeterOthMapper.updateByPrimaryKeySelective(oth);
        System.out.println(i);

        int total = 0;
        for (i = 0; i < 10; i++) {
            total+=i;
        }
        System.out.println(total);

        int num=0;
        int result = total/num;
        System.out.println(result);
    }
}
