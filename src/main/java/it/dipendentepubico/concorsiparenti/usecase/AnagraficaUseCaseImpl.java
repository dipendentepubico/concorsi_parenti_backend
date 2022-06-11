package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.jpa.repository.AnagraficaRepository;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import it.dipendentepubico.concorsiparenti.domain.AnagraficaDomain;

import java.util.List;

@Service
public class AnagraficaUseCaseImpl extends AbstractUseCaseImpl implements AnagraficaUseCase{
    @Autowired
    private AnagraficaRepository anagraficaRepository;

    @Override
    public JpaSpecificationExecutor getRepository() {
        return anagraficaRepository;
    }

    @Override
    public List<AnagraficaDomain> searchLikeAutocomplete(String searchLike) {
       return mapper.map(anagraficaRepository.searchAutocomplete(searchLike), new TypeToken<List<AnagraficaDomain>>() {}.getType());
    }


}
