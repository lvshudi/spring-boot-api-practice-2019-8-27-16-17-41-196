package com.tw.apistackbase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.entity.Employees;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiStackBaseApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should_return_list_when_query() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/male")).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void should_return_204_when_delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
				.andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void should_return_200_status_when_update_user() throws Exception {
		Employees employee = new Employees(3, "zhangxx", 15, "female");
		ObjectMapper objectMapper = new ObjectMapper();
		String example = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(example))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shoud_return_201_when_pust() throws Exception {

		// Given
		MockHttpServletRequestBuilder input = MockMvcRequestBuilders
				.post("/employees").content("{\r\n" + "    \"id\": \"2\",\r\n" + "    \"name\": \"li\",\r\n"
						+ "    \"age\": 19,\r\n" + "    \"gender\": \"nu\"\r\n" + "}")
				.contentType(MediaType.APPLICATION_JSON);
		// When
		ResultActions result = mockMvc.perform(input);
		// Then
		result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated());

	}
}
