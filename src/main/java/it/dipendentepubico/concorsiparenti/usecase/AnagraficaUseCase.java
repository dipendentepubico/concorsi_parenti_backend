package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.domain.AnagraficaDomain;

import java.util.List;

public interface AnagraficaUseCase extends AbstractUseCase{
    List<AnagraficaDomain> searchLikeAutocomplete(String searchLike);
}
