package it.dipendentepubico.concorsiparenti;

import it.dipendentepubico.concorsiparenti.jpa.entity.CategoriaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.GradoParentelaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.SettingsEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.CategoriaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.EnteRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.GradoParentelaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.SettingsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test che chiamano Controller via restTemplate
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:trxServiceStatus2"})
public class ControllerTests {

    @Autowired
    private EnteRepository enteRepository;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeAll
    public static void startup(@Autowired GradoParentelaRepository gradoParentelaRepository, @Autowired SettingsRepository settingsRepository, @Autowired CategoriaRepository categoriaRepository){
        SettingsEntity settingsEntity = new SettingsEntity();
        settingsEntity.setCodice("RUNNING_ALIGN");
        settingsEntity.setValore("N");
        settingsEntity.setDescrizione("Y se è in corso l'allineamento IPA");
        settingsRepository.save(settingsEntity);

        CategoriaEntity cat = new CategoriaEntity();
        cat.setDescrizione("Cat. D");
        categoriaRepository.save(cat);

        GradoParentelaEntity gradoParentela = new GradoParentelaEntity();
        gradoParentela.setDescrizione("Padre");
        gradoParentelaRepository.save(gradoParentela);
        gradoParentela = new GradoParentelaEntity();
        gradoParentela.setDescrizione("Fratello");
        gradoParentelaRepository.save(gradoParentela);
    }

    @Test
    public void shouldUploadFile() throws Exception {
        ClassPathResource resource = new ClassPathResource(
                "/opendata/b0aa1f6c-f135-4c8a-b416-396fed4e1a5d_upload.csv", getClass());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("filecsvpart", resource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/api/ente/import", map,String.class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

}
