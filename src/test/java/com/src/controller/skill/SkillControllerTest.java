package com.src.controller.skill;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.controllers.skill.SkillController;
import com.src.models.skill.SkillRequest;
import com.src.models.skill.SkillResponse;
import com.src.service.skill.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SkillController.class)
public class SkillControllerTest {

    public static final String SKILL1_SDESC = "Java";
    public static final String SKILL1_LDESC = "Version 8";
    public static final String SKILL2_LDESC = "Version 2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<SkillRequest> addSkillRequest;

    private SkillRequest skillRequestSuccess;
    private SkillRequest skillRequestFailed;
    private SkillResponse skillResponse;

    @BeforeEach
    public void setUp() {
        skillRequestSuccess = new SkillRequest();
        skillRequestSuccess.setSkillSdesc(SKILL1_SDESC);
        skillRequestSuccess.setSkillLdesc(SKILL1_LDESC);

        skillRequestFailed = new SkillRequest();
        skillRequestFailed.setSkillSdesc(null);
        skillRequestFailed.setSkillLdesc(SKILL2_LDESC);

        skillResponse = new SkillResponse();
        skillResponse.setSkillSdesc(SKILL1_SDESC);
        skillResponse.setSkillLdesc(SKILL1_LDESC);
    }
    /*
    When requestbody contain empty skill name application should response bad request.
     */
    @Test
    void whenSkillNameBlankValue_thenReturns400() throws Exception {
        mockMvc.perform(post("/skill/add")
                .content(objectMapper.writeValueAsString(skillRequestFailed))
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")).andExpect(status().isBadRequest());
    }

   /*
   When user provide all the required field for the request body , this test covers happy path for add skill flow.
    */
    @Test
    public void givenskill_whenaddSkill_thenStatus200() throws Exception {

         when(skillService.addSkill(any(SkillRequest.class))).thenReturn(skillResponse);

         MvcResult mvcResult = mockMvc.perform(post("/skill/add")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(skillRequestSuccess)))
                        .andDo(print())
                        .andExpect(jsonPath("$.skillName").value(SKILL1_SDESC) )
                        .andExpect(jsonPath("$.skillDescription").value(SKILL1_LDESC) ).andReturn();

         assertEquals("application/json", mvcResult.getResponse().getContentType());



    }

}
