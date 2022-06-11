package it.dipendentepubico.concorsiparenti.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.jpa.repository.ConcorsoRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.GraduatoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import it.dipendentepubico.concorsiparenti.jpa.entity.ConcorsoEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.GraduatoriaFinaleEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.AnagraficaRepository;
import it.dipendentepubico.concorsiparenti.jpa.repository.RepositoryUtil;
import it.dipendentepubico.concorsiparenti.domain.AnagraficaConDipendentiParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.ParenteDomain;

import java.util.List;

@Tag(name = "Endpoint per CRUD su Concorso" )
@RestController
@RequestMapping("api/concorso")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
public class ConcorsoController {
    @Autowired
    private ConcorsoRepository concorsoRepository;

    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Autowired
    private GraduatoriaRepository graduatoriaRepository;

    @Operation(summary = "Ritorna la lista dei concorsi")
    @GetMapping
    public Page<ConcorsoEntity> getConcorsoList(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(defaultValue = "id") String sortField,
                                                @RequestParam(defaultValue = "ASC") String sortDirection,
                                                @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        RepositoryUtil repositoryUtil = new RepositoryUtil();
        Page<ConcorsoEntity> all = repositoryUtil.retrieveEntityFilteredPagedList(page, size, sortField, sortDirection, search, concorsoRepository);
        return all;
    }



    @Operation(summary = "Ritorna le informazioni sul concorso")
    @GetMapping("{id}")
    public ConcorsoEntity getConcorso(@PathVariable("id") Integer concorsoId){
        return concorsoRepository.getById(concorsoId);
    }

    @Operation(summary = "Ritorna le anagrafiche degli idonei contenenti l'elenco dei dipendenti a loro parenti")
    @GetMapping("{id}/idonei-parenti-dipendenti")
    public List<AnagraficaConDipendentiParentiDomain> getIdoneiParenteDipendenti(@PathVariable("id") Integer concorsoId){
        return anagraficaRepository.findIdoneiConDipendentiParenti(concorsoId);
    }

    @Operation(summary = "Ritorna la graduatoria del concorso")
    @GetMapping("{id}/graduatoria")
    public GraduatoriaFinaleEntity getGraduatoria(@PathVariable("id") Integer concorsoId){
        return graduatoriaRepository.findGraduatoriaFinaleEntityByConcorsoId(concorsoId);
    }

    @Operation(summary = "Ritorna i parenti dello specifico idoneo del concorso")
    @GetMapping("{idConcorso}/idoneo/{idIdoneo}/parenti")
    public List<ParenteDomain> getIdoneoParenti(@PathVariable("idConcorso") Integer concorsoId, @PathVariable("idIdoneo") Integer anagraficaIdoneoId)
    {
        return anagraficaRepository.findDipendentiParentiByIdoneo(concorsoId, anagraficaIdoneoId);
    }

}
