package it.dipendentepubico.concorsiparenti.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.domain.Constants;
import it.dipendentepubico.concorsiparenti.domain.EnteDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.EnteEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.AnagraficaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.EnteRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.RepositoryUtil;
import it.dipendentepubico.concorsiparenti.rest.align.interceptor.BlockOnAlign;
import it.dipendentepubico.concorsiparenti.rest.model.Ente;
import it.dipendentepubico.concorsiparenti.spring.SwaggerConfiguration;
import it.dipendentepubico.concorsiparenti.usecase.EnteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Tag(name = "Endpoint per CRUD su Ente" )
@RestController
@RequestMapping("api/ente")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
@Validated
public class EnteController extends AbstractControllerImpl {

    @Autowired
    private EnteUseCase enteUseCase;

    @Autowired
    private RepositoryUtil repositoryUtil;

    @Autowired
    private EnteRepository enteRepository;

    @Autowired
    private AnagraficaRepository anagraficaRepository;


    @Operation(summary = "Ritorna la lista degli enti")
    @GetMapping
    public Page<EnteEntity> getEnteList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size,
                                        @RequestParam(defaultValue = "id") String sortField,
                                        @RequestParam(defaultValue = "ASC") String sortDirection,
                                        @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        Page<EnteEntity> all = repositoryUtil.retrieveEntityFilteredPagedList(page, size, sortField, sortDirection, search, enteRepository);
        return all;
    }

    @Operation(summary = "Ritorna esportazione csv/xlsx degli enti filtrati a video")
    @GetMapping("export/{format}")
    public void exportToCSV(HttpServletResponse servletResponse,
                            @PathVariable(value = "format", required = true) String format,
                            @RequestParam(defaultValue = "id") String sortField,
                            @RequestParam(defaultValue = "ASC") String sortDirection,
                            @RequestParam(value = "search", required = false, defaultValue = "") String search) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        Constants.EXPORT_TYPE exportType = Constants.EXPORT_TYPE.valueOf(format.toUpperCase());
        switch (exportType){
            case CSV:
                servletResponse.setContentType("text/csv");
                break;
            case XLSX:
                servletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                break;
        }
        servletResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"enti_" + currentDateTime + "."+format+"\"");
        servletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        enteUseCase.writeEnteListToFormat(exportType, sortField, sortDirection, search, servletResponse.getOutputStream());
    }


    @Operation(summary = "Ritorna lo specifico ente")
    @GetMapping("{id}")
    public Ente getEnte(@PathVariable("id") Integer enteId) {
        return mapper.map(enteUseCase.retrieveEnte(enteId), Ente.class);
    }


    @Operation(summary = "Ritorna l'elenco delle anagrafiche dei dipendenti con parentela in dato ente")
    @GetMapping("{id}/parenti/{data}")
    public List<AnagraficaEntity> findDipendentiParentiEnte(@PathVariable("id") Integer enteId, @PathVariable("data") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date data){
        return anagraficaRepository.findDipendentiParentiEnte(enteId, data);
    }

    @Operation(summary = "Inserisce nuovo ente", security = { @SecurityRequirement(name = SwaggerConfiguration.SECURITY_SCHEME_NAME)})
    @PostMapping()
    @BlockOnAlign
    public ResponseEntity<Ente> insertEnte(HttpServletRequest request, @Valid @RequestBody Ente ente) {

        if(!checkCaptcha(request, ente.getUserEnteredCaptchaCode())){
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        EnteDomain enteDomain = enteUseCase.insertEnte(mapper.map(ente, EnteDomain.class));

        return new ResponseEntity<>(mapper.map(enteDomain, Ente.class), HttpStatus.OK);
    }

    /* UPDATE */
    @Operation(summary = "Modifica l'ente", security = { @SecurityRequirement(name = SwaggerConfiguration.SECURITY_SCHEME_NAME)})
    @PutMapping()
    @BlockOnAlign
    public ResponseEntity<Ente> updateEnte(HttpServletRequest request, @RequestBody Ente ente) {
        if(!checkCaptcha(request, ente.getUserEnteredCaptchaCode())){
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        EnteDomain enteDomain = enteUseCase.updateEnte(mapper.map(ente, EnteDomain.class));

        return new ResponseEntity<>(mapper.map(enteDomain, Ente.class), HttpStatus.OK);
    }

    /**
     * Carica csv per allineare rispetto ad IPA
     */
    @PostMapping("/import")
    @BlockOnAlign
    public String uploadAndAlign(@RequestParam("filecsvpart") MultipartFile file) {
        enteUseCase.uploadAndAlign(file);
        return "You successfully uploaded " + file.getOriginalFilename() + "!";
    }



}
