package it.dipendentepubico.concorsiparenti.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dipendentepubico.concorsiparenti.usecase.AnagraficaUseCase;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import it.dipendentepubico.concorsiparenti.rest.model.AnagraficaAutocomplete;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "Endpoint per operazioni su Anagrafica" )
@RestController
@RequestMapping("api/anagrafica")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
@Validated
public class AnagraficaController extends AbstractControllerImpl{
    @Autowired
    private AnagraficaUseCase anagraficaUseCase;

    @Operation(summary = "Ritorna l'elenco delle anagrafiche limitato a 20 risultati (codicefiscale) cognome nome ")
    @GetMapping("autocomplete/{autostring}")
    public ResponseEntity<List<AnagraficaAutocomplete>> getAnagraficaAutocomplete(@PathVariable("autostring") String searchLike) { // TODO @NotNull?
        ResponseEntity<List<AnagraficaAutocomplete>> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            List<AnagraficaAutocomplete> d = mapper.map(anagraficaUseCase.searchLikeAutocomplete(searchLike), new TypeToken<List<AnagraficaAutocomplete>>() {}.getType());
            response = new ResponseEntity<>( d , HttpStatus.OK);
        } catch (NoSuchElementException ex){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
