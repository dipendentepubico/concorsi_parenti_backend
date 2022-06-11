package it.dipendentepubico.concorsiparenti.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.domain.*;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertDomainRequest;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteInsertUpdateDomainResponse;
import it.dipendentepubico.concorsiparenti.domain.reqsponse.DipendenteUpdateDomainRequest;
import it.dipendentepubico.concorsiparenti.jpa.entity.DipendenteEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.RepositoryUtil;
import it.dipendentepubico.concorsiparenti.rest.align.interceptor.BlockOnAlign;
import it.dipendentepubico.concorsiparenti.spring.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import it.dipendentepubico.concorsiparenti.jpa.repository.DipendenteRepository;
import it.dipendentepubico.concorsiparenti.rest.model.Dipendente;
import it.dipendentepubico.concorsiparenti.rest.model.DipendenteInsert;
import it.dipendentepubico.concorsiparenti.rest.model.DipendenteUpdate;
import it.dipendentepubico.concorsiparenti.usecase.DipendenteUseCase;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

@Tag(name = "Endpoint per operazioni su Dipendente")
@RestController
@RequestMapping("api/dipendente")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
@Validated
public class DipendenteController extends AbstractControllerImpl {
    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private DipendenteUseCase dipendenteUseCase;

    @Operation(summary = "Ritorna i parenti di questo dipendente in questa data nell'ente del dipendente")
    @GetMapping("{cf}/{data}")
    public DipendenteConParentiDomain findDipendenteParentiEnte(@PathVariable("cf") String codiceFiscale, @PathVariable("data") Date data ) {
        return dipendenteUseCase.findDipendenteConParenti(codiceFiscale, data);
    }

    @Operation(summary = "Ritorna l'elenco delle anagrafiche dei dipendenti in dato ente")
    @GetMapping("ente/{id}/{data}")
    public Page<DipendenteEntity> findDipendentiByEnte(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size,
                                                       @RequestParam(defaultValue = "id") String sortField,
                                                       @RequestParam(defaultValue = "ASC") String sortDirection,
                                                       @RequestParam(value = "search", required = false, defaultValue = "")  String search,
                                                       @PathVariable("id") Integer enteId,
                                                       @PathVariable("data") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date data){
        RepositoryUtil repositoryUtil = new RepositoryUtil();
        search = "ente.id:"+enteId+","+search;
        Page<DipendenteEntity> all = repositoryUtil.retrieveEntityFilteredPagedList(page, size, sortField, sortDirection, search, dipendenteRepository);
        return all;
    }

    @Operation(summary = "Ritorna lo specifico dipendente")
    @GetMapping("{id}")
    public ResponseEntity<Dipendente> getDipendente(@PathVariable("id") Integer dipendenteId) {
        ResponseEntity<Dipendente> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            Dipendente d = mapper.map(dipendenteUseCase.getDipendenteById(dipendenteId), Dipendente.class);
            response = new ResponseEntity<>( d , HttpStatus.OK);
        } catch (NoSuchElementException ex){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Operation(summary = "Ritorna lo specifico dipendente con i soli dati necessari per la modifica")
    @GetMapping("edit/{id}")
    public ResponseEntity<DipendenteUpdate> getDipendente4Edit(@PathVariable("id") Integer dipendenteId) {
        ResponseEntity<DipendenteUpdate> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            DipendenteUpdate d = mapper.map(dipendenteUseCase.getDipendenteById(dipendenteId), DipendenteUpdate.class);
            response = new ResponseEntity<>( d , HttpStatus.OK);
        } catch (NoSuchElementException ex){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @Operation(summary = "Inserisce nuovo dipendente per l'ente", security = { @SecurityRequirement(name = SwaggerConfiguration.SECURITY_SCHEME_NAME)})
    @PostMapping("ente/{id}/dipendente")
    @CrossOrigin(allowCredentials = "true")
    @BlockOnAlign
    public ResponseEntity<Integer> insertDipendente(@PathVariable("id") Integer enteId, @Valid @RequestBody DipendenteInsert dipinsert) {
        ResponseEntity<Integer> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            DipendenteInsertDomainRequest did = mapper.map(dipinsert, DipendenteInsertDomainRequest.class);
            did.setIdEnte(enteId);
            DipendenteInsertUpdateDomainResponse dSaved = dipendenteUseCase.insert(did);
            response = new ResponseEntity<>( dSaved.getId() , HttpStatus.OK);
        } catch (NoSuchElementException ex){
            response = new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return response;

    }

    @Operation(summary = "Aggiorna dipendente per l'ente", security = { @SecurityRequirement(name = SwaggerConfiguration.SECURITY_SCHEME_NAME)})
    @PutMapping("ente/{id}/dipendente")
    @CrossOrigin(allowCredentials = "true")
    @BlockOnAlign
    public ResponseEntity<Integer> updateDipendente(@PathVariable("id") Integer enteId, @Valid @RequestBody DipendenteUpdate dipupdate) {
        ResponseEntity<Integer> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            DipendenteUpdateDomainRequest did = mapper.map(dipupdate, DipendenteUpdateDomainRequest.class);
            did.setIdEnte(enteId);
            DipendenteInsertUpdateDomainResponse dSaved = dipendenteUseCase.update(did);
            response = new ResponseEntity<>( dSaved.getId() , HttpStatus.OK);
        } catch (EntityNotFoundException ex){
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return response;

    }

    @Operation(summary = "Rimuove dipendente da ente", security = { @SecurityRequirement(name = SwaggerConfiguration.SECURITY_SCHEME_NAME)})
    @DeleteMapping("{id}")
    @CrossOrigin(allowCredentials = "true")
    @BlockOnAlign
    public ResponseEntity<Dipendente> deleteDipendente(@PathVariable("id") Integer dipendenteId ){
        ResponseEntity<Dipendente> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            dipendenteUseCase.delete(dipendenteId);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException ex){
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return response;
    }



    @Operation(summary = "Ritorna esportazione csv/xlsx dei dipendenti filtrati a video")
    @GetMapping("export/{format}")
    @BlockOnAlign
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
        servletResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"dipendenti_" + currentDateTime + "."+format+"\"");
        servletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        dipendenteUseCase.writeDipendenteListToFormat(exportType, sortField, sortDirection, search, servletResponse.getOutputStream());
    }

}
