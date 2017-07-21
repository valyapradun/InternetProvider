package com.epam.training.provider;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.impl.UserServiceImpl;



@RunWith(AllTestsRunner.class)
public class UserServiceImplTest {
	private UserServiceImpl serviceImpl = null;
	private static User user;

	@BeforeClass
	public static void initUser() {
		user = new User(1, "testUser", "testTest1", "test", "user");
	}

	@AfterClass
	public static void clearUser() {
		user = null;
	}

	@Before
	public void setupService() {
		serviceImpl = new UserServiceImpl();
	}

	@After
	public void clearService() {
		serviceImpl = null;
	}

	@Test(expected = ValidateException.class)
	public void testAuthorizeValidateException() throws ValidateException, ServiceException {
		User expectedResult = user;
		User actualResult = serviceImpl.authorize("testUser*", "testTest1");
		assertEquals("For login 'testUser*' wasn't exception: ", expectedResult, actualResult);
	}

	@Ignore("this test is not ready yet")
	@Test(expected = ServiceException.class)
	public void testAuthorizeServiceException() throws ServiceException, ValidateException {
		User expectedResult = user;
		User actualResult = serviceImpl.authorize("testUser*", "testTest1");
		assertEquals("For login 'testUser*' wasn't exception: ", expectedResult, actualResult);
	}

}
