package hr.corvus.tictactoe;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import hr.corvus.tictactoe.game.NewGame;
import hr.corvus.tictactoe.game.data.GameData;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
	}

	@Test
	public void startNewGame() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(
				"/game/new/").param("first", "Nives");
		mockMvc.perform(request).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(1))).andExpect(status().isOk());
	}

	@Test
	public void startNewGameNoParams() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/game/new/");
		mockMvc.perform(request).andExpect(status().is4xxClientError());
	}

	@Test
	public void getStatus() throws Exception {
		new NewGame(100, "Nives", "computer");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(
				"/game/status/").param("gameId", "100");
		mockMvc.perform(request).andExpect(jsonPath("$.gameId", is(100)))
				.andExpect(jsonPath("$.status", is("inProgress")))
				.andExpect(status().isOk());
	}

	@Test
	public void getStatusWrong() throws Exception {
		new NewGame(100, "Nives", "computer");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/game/status/");
		mockMvc.perform(request).andExpect(status().is4xxClientError());
	}

	@Test
	public void getStatistics() throws Exception {
		new NewGame(100, "Nives", "computer");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(
				"/game/stats/");
		mockMvc.perform(request)
				.andExpect(jsonPath("$.stat[0].name", is("Nives")))
				.andExpect(jsonPath("$.stat[0].wins", is(0)))
				.andExpect(jsonPath("$.stat[0].losses", is(0)))
				.andExpect(jsonPath("$.stat[0].draws", is(0)))
				.andExpect(status().isOk());
	}

	@Test
	public void gamePlay() throws Exception {
		GameData.addGame(100, new NewGame(100, "Nives", ""));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/game/play").param("gameId", "100").param("row", "2")
				.param("column", "2");
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void gamePlayInvalidMove() throws Exception {
		GameData.addGame(100, new NewGame(100, "Nives", ""));
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/game/play").param("gameId", "100").param("row", "2")
				.param("column", "2");
		mockMvc.perform(request);
		request = MockMvcRequestBuilders.get("/game/play")
				.param("gameId", "100").param("row", "2").param("column", "2");
		mockMvc.perform(request).andExpect(status().is4xxClientError());
	}

}
