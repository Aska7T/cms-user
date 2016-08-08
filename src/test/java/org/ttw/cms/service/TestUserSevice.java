package org.ttw.cms.service;

import javax.inject.Inject;
import static org.easymock.EasyMock.*;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ttw.basic.model.Pager;
import org.ttw.cms.dao.IGroupDao;
import org.ttw.cms.dao.IRoleDao;
import org.ttw.cms.dao.IUserDao;
import org.ttw.cms.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserSevice {
	@Inject
	private IUserService userService;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IUserDao userDao;
	@Inject
	private IGroupDao groupDao;
	private User baseUser = new User(1,"admin1","123","admin1","admin1@","123",0);
	
	@Test
	public void testDelete(){
		reset(userDao);
		int uid=2;
		userDao.deleteUserGroups(uid);
		expectLastCall();
		userDao.deleteUserRoles(uid);
		expectLastCall();
		userDao.delete(uid);
		expectLastCall();
		replay(userDao);
		userService.delete(uid);
		verify(userDao);
	}
	
	@Test
	public void testUpdateStatus(){
		reset(userDao);
		int uid = 2;
		expect(userDao.load(uid)).andReturn(baseUser);
		userDao.update(baseUser);
		expectLastCall();
		replay(userDao);
		userService.updateStatus(uid);
		Assert.assertEquals(baseUser.getStatus(),1);
		verify(userDao);
	}
	
	@Test
	public void testFindUser(){
		reset(userDao);
		expect(userDao.findUser()).andReturn(new Pager<User>());
		replay(userDao);
		userService.findUser();
		verify(userDao);
	}
}
