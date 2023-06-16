package fr.mb.bananesexport;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.mb.bananesexport.model.Destinataire;
import fr.mb.bananesexport.order.ClassOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ClassOrder(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DestinataireTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	@Order(1)
	void createDestinataire() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Mathieu", "9 Rue Emile Goeury",
				"94140", "Alfortville", "France");
		mockMvc.perform(
				MockMvcRequestBuilders.post("/destinataires")
				.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(destinataire.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.adresse").value(destinataire.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.codePostal").value(destinataire.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ville").value(destinataire.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.pays").value(destinataire.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

	}

	@Test
	@Order(2)
	void duplicateDestinataire() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Mathieu", "9 Rue Emile Goeury",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.post("/destinataires")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@Order(3)
	void oneEmptyFieldCreationDestinataire() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Mathieu", "9 Rue Emile Goeury",
				"94140", "", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.post("/destinataires")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@Order(4)
	void listDestinataire () throws Exception{
		Destinataire destinataire = new Destinataire(1, "Boyé Mathieu", "9 Rue Emile Goeury",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.get("/destinataires")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value(destinataire.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].adresse").value(destinataire.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].codePostal").value(destinataire.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].ville").value(destinataire.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].pays").value(destinataire.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(destinataire.getId()));
	}

	@Test
	@Order(5)
	void updateDestinataire() throws Exception {
		Destinataire destinataire = new Destinataire(1, "Boyé Mathieu", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.put("/destinataires/1")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(destinataire.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.adresse").value(destinataire.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.codePostal").value(destinataire.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ville").value(destinataire.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.pays").value(destinataire.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(destinataire.getId()));

	}

	@Test
	@Order(6)
	void updateDestinataireAnyEmptyField() throws Exception {
		Destinataire destinataire = new Destinataire(1, "Boyé Mathieu", "15 Rue Du Paradis",
				"", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.put("/destinataires/1")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@Order(7)
	void updateDestinataireAlreadyExists() throws Exception {
		Destinataire destinataire = new Destinataire(1, "Boyé Mathieu", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.put("/destinataires/1")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());


	}

	@Test
	@Order(8)
	void updateDestinataireUnknowId() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Francois", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.put("/destinataires/100")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(destinataire.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.adresse").value(destinataire.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.codePostal").value(destinataire.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ville").value(destinataire.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.pays").value(destinataire.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

	}

	@Test
	@Order(9)
	void updateDestinataireUnknownIdAlreadyExists() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Francois", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		mockMvc.perform(
				MockMvcRequestBuilders.put("/destinataires/100")
						.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@Order(10)
	void updateDestinataireUnknownIdAnyFieldEmpty() throws Exception {
		Destinataire destinataire = new Destinataire("Boyé Francois", "15 Rue Du Paradis",
				"94140", "", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.put("/destinataires/100")
								.content(objectMapper.writeValueAsString(destinataire)).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@Order(11)
	void listDestinataireWithUpdated () throws Exception{
		Destinataire destinataire = new Destinataire(1, "Boyé Mathieu", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		Destinataire destinataire2 = new Destinataire(2,"Boyé Francois", "15 Rue Du Paradis",
				"94140", "Alfortville", "France");
		mockMvc.perform(
						MockMvcRequestBuilders.get("/destinataires")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value(destinataire.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].adresse").value(destinataire.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].codePostal").value(destinataire.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].ville").value(destinataire.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].pays").value(destinataire.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(destinataire2.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value(destinataire2.getNom()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].adresse").value(destinataire2.getAdresse()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].codePostal").value(destinataire2.getCodePostal()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].ville").value(destinataire2.getVille()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].pays").value(destinataire2.getPays()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(destinataire2.getId()));
	}

	@Test
	@Order(12)
	void deleteDestinataire() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.delete("/destinataires/2")
				).andExpect(MockMvcResultMatchers.status().isNoContent());
		mockMvc.perform(
						MockMvcRequestBuilders.get("/destinataires")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1]").doesNotExist());

	}

	@Test
	@Order(13)
	void deleteUnknownDestinataire() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/destinataires/100")
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
