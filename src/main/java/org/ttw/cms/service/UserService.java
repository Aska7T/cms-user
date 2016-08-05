package org.ttw.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.ttw.cms.dao.IGroupDao;
import org.ttw.cms.dao.IRoleDao;
import org.ttw.cms.dao.IUserDao;
import org.ttw.cms.dao.RoleDao;
import org.ttw.cms.dao.UserDao;
import org.ttw.cms.model.CmsException;
import org.ttw.cms.model.Group;
import org.ttw.cms.model.Role;
import org.ttw.cms.model.User;

@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	

	public IUserDao getUserDao() {
		return userDao;
	}

	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		User tu = userDao.loadByUsername(user.getUsername());
		if(tu!=null)throw new CmsException("添加的对象已经存在，不能添加");
		userDao.add(user);
		//添加角色对象
		for(Integer rid:rids){
			//1.检查角色对象是否存在，如果不存在，就抛出异常
			Role role=roleDao.load(rid);
			if(role==null) throw new CmsException("要添加的用户角色不存在");
			//2.检查用户角色对象是否存在，如果存在不添加
			userDao.addUserRole(user,role);
		}
		//添加用户组对象
		for(Integer gid:gids){
			Group group=groupDao.load(gid);
			if(group==null) throw new CmsException("要添加的组不存在");
			userDao.addUserGroup(user,group);
		}
	}

	@Override
	public void delete(int id) {
		//TODO 需要进行用户是否有文章的判断
		
		//1.删除用户管理的角色对象
		userDao.deleteUserGroups(id);
		//2.删除用户管理的组对象
		userDao.deleteUserRoles(id);
		userDao.delete(id);
	}

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatus(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> listUserRoles(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserGroups(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
