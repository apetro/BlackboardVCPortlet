package org.jasig.portlet.blackboardvcportlet.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;
import java.util.concurrent.Callable;

import org.jasig.portlet.blackboardvcportlet.dao.ConferenceUserDao;
import org.jasig.portlet.blackboardvcportlet.dao.MultimediaDao;
import org.jasig.portlet.blackboardvcportlet.data.ConferenceUser;
import org.jasig.portlet.blackboardvcportlet.data.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jpaTestContext.xml")
public class ConferenceUserDaoImplTest extends BaseJpaDaoTest {
    @Autowired
    private ConferenceUserDao conferenceUserDao;
    
    @Autowired
    private MultimediaDao multimediaDao;
    
    @Test
    public void testEmptyQueries() throws Exception {
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user = conferenceUserDao.getUser(1);
                assertNull(user);
                
                return null;
            }
        });
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user = conferenceUserDao.getUserByUniqueId("012332412");
                assertNull(user);
                
                return null;
            }
        });
        
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user = conferenceUserDao.getExternalUserByEmail("user@example.com");
                assertNull(user);
                
                return null;
            }
        });
        
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user = conferenceUserDao.getUser(1);
                final Set<Session> chairedSessions = conferenceUserDao.getChairedSessionsForUser(user);
                assertNull(chairedSessions);
                
                return null;
            }
        });
        
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user = conferenceUserDao.getUser(1);
                final Set<Session> participatingSessions = conferenceUserDao.getNonChairedSessionsForUser(user);
                assertNull(participatingSessions);
                
                return null;
            }
        });
    }
    
    @Test
    public void testCreateAndSearch() throws Exception {
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final ConferenceUser user1 = conferenceUserDao.createInternalUser("user@example.com");
                user1.setEmail("user@dept.example.com");
                conferenceUserDao.updateUser(user1);
                
                final ConferenceUser user2 = conferenceUserDao.createInternalUser("admin@example.com");
                user2.setEmail("admin@dept.example.com");
                user2.getAdditionalEmails().add("admin@external.example.com");
                conferenceUserDao.updateUser(user2);
                
                conferenceUserDao.createExternalUser("New User", "admin@external.example.com");
                
                return null;
            }
        });
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                Set<ConferenceUser> users = conferenceUserDao.getUsersByPrimaryEmail("user@dept.example.com");
                assertNotNull(users);
                assertEquals(1, users.size());
                
                users = conferenceUserDao.getUsersByAnyEmail("admin@external.example.com");
                assertNotNull(users);
                assertEquals(2, users.size());
                
                return null;
            }
        });
    }
}
