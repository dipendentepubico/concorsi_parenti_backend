package it.dipendentepubico.concorsiparenti.usecase;

import it.dipendentepubico.concorsiparenti.jpa.entity.EntityInterface;
import it.dipendentepubico.concorsiparenti.jpa.repository.RepositoryUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractUseCaseImpl implements AbstractUseCase {
    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected RepositoryUtil repositoryUtil;

    protected <E extends EntityInterface> List<E> retrieveEntityFilteredList(String sortField, String sortDirection, String search) {
        return repositoryUtil.retrieveEntityFilteredList(sortField, sortDirection, search, getRepository());
    }

}
