package br.com.zupacademy.antonio.proposta.viagem;

import br.com.zupacademy.antonio.proposta.bloqueio.BloqueioForm;
import com.google.gson.Gson;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class InformaViagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    @DisplayName("Falha no Informe de viagem cartão não encontrado")
    public void falhaNoInformeDeViagemCartaoNaoEncontrado() throws Exception {

        InformaViagemForm informaViagemForm = new InformaViagemForm("Joinville", null);

        mockMvc.perform(post("/informa/viagens/cartoes/id")
                        .locale(new Locale("pt", "BR"))
                        .content(gson.toJson(informaViagemForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}