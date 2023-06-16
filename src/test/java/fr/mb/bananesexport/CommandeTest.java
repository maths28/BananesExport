package fr.mb.bananesexport;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.mb.bananesexport.model.Commande;
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

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ClassOrder(2)
public class CommandeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(14)
    void createCommande() throws Exception{

        Commande commande = new Commande("01-01-2024", 25);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(commande.getDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantite").value(commande.getQuantite()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prix").value(62.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(15)
    void oneEmptyFieldCreationCommande() throws Exception {
        Commande commande = new Commande( "", 25);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(16)
    void unknownDestinataireCommande() throws Exception {
        Commande commande = new Commande( "01-01-2024", 25);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/100/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(17)
    void wrongDateCommande() throws Exception {
        Date date = new Date();
        Commande commande = new Commande(new SimpleDateFormat("dd-MM-yyyy").format(date), 25);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(18)
    void wrongQuantiteMin() throws Exception {
        Commande commande = new Commande( "01-01-2024", -1);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(19)
    void wrongQuantiteMax() throws Exception {
        Commande commande = new Commande( "01-01-2024", 250000);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(20)
    void wrongQuantiteIntervalle() throws Exception {
        Commande commande = new Commande( "01-01-2024", 35);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/destinataires/1/commandes")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(21)
    void listCommandes () throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/destinataires/1/commandes")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantite").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prix").value(62.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("01-01-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
    }

    @Test
    @Order(22)
    void updateCommande() throws Exception{

        Commande commande = new Commande("01-02-2024", 50);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(commande.getDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantite").value(commande.getQuantite()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prix").value(125))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(23)
    void updateCommandeWithEmptyField() throws Exception{

        Commande commande = new Commande("", 50);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(24)
    void updateCommandeUnknownDestinataire() throws Exception{

        Commande commande = new Commande("01-02-2024", 50);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/100/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(25)
    void updateCommandeWrongDateCommande() throws Exception{

        Date date = new Date();
        Commande commande = new Commande(new SimpleDateFormat("dd-MM-yyyy").format(date), 25);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(26)
    void updateCommandeQuantiteMin() throws Exception{
        Commande commande = new Commande("01-01-2024", -1);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(27)
    void updateCommandeQuantiteMax() throws Exception{
        Commande commande = new Commande("01-01-2024", 25000);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(28)
    void updateCommandeQuantiteIntervalle() throws Exception{
        Commande commande = new Commande("01-01-2024", 40);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/3")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(29)
    void listCommandesWithUpdatedCommand () throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/destinataires/1/commandes")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantite").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prix").value(125))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("01-02-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
    }

    @Test
    @Order(30)
    void updateCommandeUnknownId() throws Exception{

        Commande commande = new Commande("01-05-2024", 75);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(commande.getDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantite").value(commande.getQuantite()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prix").value(187.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(31)
    void updateCommandeWithEmptyFieldUnknownId() throws Exception{

        Commande commande = new Commande("", 50);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(32)
    void updateCommandeUnknownDestinataireUnknwownId() throws Exception{

        Commande commande = new Commande("01-05-2024", 75);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/100/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(33)
    void updateCommandeWrongDateCommandeUnknownId() throws Exception{

        Date date = new Date();
        Commande commande = new Commande(new SimpleDateFormat("dd-MM-yyyy").format(date), 75);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(34)
    void updateCommandeQuantiteMinUnknownId() throws Exception{
        Commande commande = new Commande("01-07-2024", -1);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(35)
    void updateCommandeQuantiteMaxUnknownId() throws Exception{
        Commande commande = new Commande("01-07-2024", 25000);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(36)
    void updateCommandeQuantiteIntervalleUnknownId() throws Exception{
        Commande commande = new Commande("01-07-2024", 40);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/destinataires/1/commandes/100")
                                .content(objectMapper.writeValueAsString(commande)).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(37)
    void listCommandesWithCreatedCommandOnUpdate () throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/destinataires/1/commandes")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantite").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prix").value(125))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value("01-02-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantite").value(75))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].prix").value(187.50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value("01-05-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists());
    }

    @Test
    @Order(38)
    void deleteCommande() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/destinataires/1/commandes/4")
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/destinataires/1/commandes")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").doesNotExist());

    }

    @Test
    @Order(39)
    void deleteUnknownCommande() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/destinataires/1/commandes/100")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(40)
    void deleteUnknownDestinataireCommande() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/destinataires/100/commandes/3")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
