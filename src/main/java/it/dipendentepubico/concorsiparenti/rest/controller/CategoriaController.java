package it.dipendentepubico.concorsiparenti.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.jpa.entity.CategoriaEntity;
import it.dipendentepubico.concorsiparenti.jpa.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Endpoint per CRUD su Categoria" )
@RestController
@RequestMapping("api/categoria")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
public class CategoriaController {


    @Autowired
    private CategoriaRepository categoriaRepository;


    /* GET */
    @Operation(summary = "Ritorna tutte le categorie")
    @GetMapping()
    public Collection<CategoriaEntity> getCategoriaList() {
        return categoriaRepository.findAll();
    }
}
