package org.jasig.portlet.blackboardvcportlet.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;
import java.util.concurrent.Callable;

import org.jasig.portlet.blackboardvcportlet.dao.MultimediaDao;
import org.jasig.portlet.blackboardvcportlet.data.Multimedia;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elluminate.sas.BlackboardMultimediaResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jpaTestContext.xml")
public class MultimediaDaoImplTest extends BaseJpaDaoTest {
	@Autowired
	private MultimediaDao dao;
	
	@Test
    public void testEmptyQueries() throws Exception {
        this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final Set<Multimedia> multimedias = dao.getAllMultimedia();
                assertEquals(0, multimedias.size());
                
                return null;
            }
        });
    }
	
	@Test
	public void testCreateMultimedia() {
		this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final BlackboardMultimediaResponse response = new BlackboardMultimediaResponse();
                response.setCreatorId("test@example.com");
                response.setDescription("super sweet media");
                response.setMultimediaId(183838);
                response.setSize(1024);
                
                final Multimedia mm = dao.createMultimedia(response, "aliens_exist.pdf");
                assertNotNull(mm);
                
                assertEquals(1024, mm.getSize());
                assertEquals(183838, mm.getBbMultimediaId());
                
                Multimedia pullFromDB = dao.getMultimediaByBlackboardId(mm.getBbMultimediaId());
                assertNotNull(pullFromDB);
                
                return null;
            }
        });
	}
	
	@Test
	public void testDeleteMultimedia() {
		this.execute(new Callable<Object>() {
            @Override
            public Object call() {
                final BlackboardMultimediaResponse response = new BlackboardMultimediaResponse();
                response.setCreatorId("test@example.com");
                response.setDescription("super sweet media");
                response.setMultimediaId(183838);
                response.setSize(1024);
                
                final Multimedia mm = dao.createMultimedia(response, "aliens_exist.pdf");
                
                dao.deleteMultimedia(mm);
                
                Multimedia shouldBeNull = dao.getMultimediaByBlackboardId(mm.getBbMultimediaId());
                assertNull(shouldBeNull);
                return null;
            }
        });
	}
	
	
}
