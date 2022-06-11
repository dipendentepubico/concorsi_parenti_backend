package it.dipendentepubico.concorsiparenti.usecase;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AbstractUseCase {
    JpaSpecificationExecutor getRepository();
}
