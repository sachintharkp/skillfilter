package com.src.service.skill;

import com.src.models.skill.SkillRequest;
import com.src.models.skill.SkillResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unused")
@SpringBootTest()
@AutoConfigureMockMvc
public class SkillServiceTest {

    public static final String SKILL1_SDESC = "Java";
    public static final String SKILL1_LDESC = "Version 8";
    @Autowired
    @InjectMocks
    private SkillService skillService;

    private SkillRequest skillRequest;
    private SkillResponse skillResponse;

    @BeforeEach
    void setUp(){
        skillRequest = new SkillRequest();
        skillRequest.setSkillSdesc(SKILL1_SDESC);
        skillRequest.setSkillLdesc(SKILL1_LDESC);
        skillResponse = new SkillResponse();
    }

    @Test
    void testAddSkill(){
        skillResponse = skillService.addSkill(skillRequest);
        assertEquals(SKILL1_SDESC,skillResponse.getSkillSdesc() );
        assertEquals(SKILL1_LDESC,skillResponse.getSkillLdesc());

    }


}
