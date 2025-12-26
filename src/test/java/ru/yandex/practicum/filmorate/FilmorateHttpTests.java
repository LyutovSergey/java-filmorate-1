package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FilmorateHttpTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testPostFilm() throws Exception {
		postMatrixFilm();
		getMatrixFilm();
	}

	@Test
	void testPostUser() throws Exception {
		createFailLogin();
		createUser();
	}

	void postMatrixFilm() throws Exception {
		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
						         "  \"name\": \"Матрица\",\n" +
						         "  \"description\": \"Добро пожаловать в реальный мир\",\n" +
						         "  \"releaseDate\": \"1999-03-24\",\n" +
						         "  \"duration\": 136,\n" +
						         "  \"likes\": null\n" +
						         "}"
						))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Матрица"))
				.andExpect(jsonPath("$.description").value("Добро пожаловать в реальный мир"))
				.andExpect(jsonPath("$.releaseDate").value("1999-03-24"))
				.andExpect(jsonPath("$.duration").value("136"))
				.andExpect(jsonPath("$.likes").isArray())
				.andExpect(jsonPath("$.rate").value(0));
	}

	void getMatrixFilm() throws Exception {
		mockMvc.perform(get("/films/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Матрица"))
				.andExpect(jsonPath("$.description").value("Добро пожаловать в реальный мир"))
				.andExpect(jsonPath("$.releaseDate").value("1999-03-24"))
				.andExpect(jsonPath("$.duration").value("136"))
				.andExpect(jsonPath("$.likes").isArray())
				.andExpect(jsonPath("$.rate").value(0));
	}

	void createFailLogin() throws Exception {
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
						         "  \"login\": \"dolore ullamco\",\n" +
						         "  \"email\": \"yandex@mail.ru\",\n" +
						         "  \"birthday\": \"2446-08-20\"\n" +
						         "}"
						))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value(
						"Некорректное значение параметра dolore ullamco: " +
								"Логин не должен содержать пробелов."
				));
	}

	void createUser() throws Exception {
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
						         "     \"name\": \"Cay Horstmann\",\n" +
						         "     \"login\": \"horstman444\",\n" +
						         "     \"email\": \"cay@horstmann.com\",\n" +
						         "     \"birthday\": \"1959-06-16\"\n" +
						         " }"
						))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.name").value("Cay Horstmann"))
				.andExpect(jsonPath("$.login").value("horstman444"))
				.andExpect(jsonPath("$.email").value("cay@horstmann.com"))
				.andExpect(jsonPath("$.birthday").value("1959-06-16"))
				.andExpect(jsonPath("$.friends").isArray());
	}
}
