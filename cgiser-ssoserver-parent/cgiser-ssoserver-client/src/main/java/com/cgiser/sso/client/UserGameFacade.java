package com.cgiser.sso.client;

import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserLoginResult;

/**
 * 保存用户创建角色数量的接口
 * @author yangh
 * 2013-12-24
 */
public interface UserGameFacade {
	/**
	 * 保存用户每个服务器的角色信息
	 * @param gameIden
	 * @param userIden
	 * @param serverId
	 * @param roleNum
	 * @return
	 */
    public int updateRoleNum(String gameIden,String userIden,Long serverId,int roleNum);
    /**
     * 根据用户IDEN获取用户账户信息
     * @param userIden
     * @return
     */
    public User getUserByUserIden(String userIden);
    /**
     * 用户登录
     * @param userName
     * @param pwd
     * @param ip
     * @return
     */
    public UserLoginResult login(String userName,byte[] pwd,String ip);
}
