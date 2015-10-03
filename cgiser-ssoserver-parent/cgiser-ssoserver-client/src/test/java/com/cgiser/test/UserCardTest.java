package com.cgiser.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.cgiser.sso.client.UserGameFacade;

@ContextConfiguration(locations = {"classpath:/bean/sso-client-bean.xml" })
public class UserCardTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private UserGameFacade userGameFacade;
	
	
	@Test
	public void testUserCard() {
		int a = userGameFacade.updateRoleNum("qfHEAAAgEA2F6enF8", "wUEYACAMERUdFXBBQ", 1000000000L, 1);
		System.out.println(a);
	}
}
