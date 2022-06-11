package it.dipendentepubico.concorsiparenti.spring;

import it.dipendentepubico.concorsiparenti.domain.EnteDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData;
import it.dipendentepubico.concorsiparenti.rest.model.Ente;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import it.dipendentepubico.concorsiparenti.domain.AnagraficaDomain;
import it.dipendentepubico.concorsiparenti.domain.DipendenteDomain;
import it.dipendentepubico.concorsiparenti.rest.model.AnagraficaAutocomplete;
import it.dipendentepubico.concorsiparenti.rest.model.DipendenteUpdate;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(DipendenteDomain.class, DipendenteUpdate.class).addMappings(mapper -> {
            // concateno il nominativo da mostrare a video
            mapper.using(context -> {
                return getNominativoFromAnagraficaDomain(((DipendenteDomain) context.getSource()).getAnagrafica());
            }).<String>map(source -> source, (destination, value) -> destination.setNominativo(value));
            // appiattisco la categoria
            mapper.map(src -> src.getCategoria().getId(),
                    DipendenteUpdate::setIdCategoria);
        });

        modelMapper.typeMap(AnagraficaDomain.class, AnagraficaAutocomplete.class).addMappings(
                mapper -> mapper.using(context -> getNominativoFromAnagraficaDomain((AnagraficaDomain) context.getSource()))
                        .<String>map(source -> source, (destination, value) -> destination.setDescrizione(value))
        );

        modelMapper.typeMap(EnteDomain.class, Ente.class).addMappings(
          mapping -> mapping.using(context ->  statoOpenData2AllineatoBool((EnteDomain) context.getSource()))
                      .<Boolean>map(source -> source, (destination, value) -> destination.setAllineato(value))
        );

        return modelMapper;
    }

    private boolean statoOpenData2AllineatoBool(EnteDomain ed) {
        return  ed.getStatoOpenData() != null && ed.getStatoOpenData() == EStatoOpenData.ALLINEATO;
    }

    private String getNominativoFromAnagraficaDomain(AnagraficaDomain anagrafica) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(anagrafica.getCodiceFiscale()).append(") ")
                .append(anagrafica.getCognome()).append(" ").append(anagrafica.getNome());
        return sb.toString();
    }

}
