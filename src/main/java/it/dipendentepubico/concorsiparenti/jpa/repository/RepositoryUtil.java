package it.dipendentepubico.concorsiparenti.jpa.repository;

import com.google.common.base.Joiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import it.dipendentepubico.concorsiparenti.jpa.searchspec.EntitySpecificationsBuilder;
import it.dipendentepubico.concorsiparenti.jpa.searchspec.SearchOperation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RepositoryUtil {

    public <E> Page<E> retrieveEntityFilteredPagedList(int page, int size, String sortField, String sortDirection, String search, JpaSpecificationExecutor<E> repository) {
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Specification<E> spec = null;
        if (!search.isEmpty()) {
            EntitySpecificationsBuilder<E> builder = new EntitySpecificationsBuilder();
            String operationSetExper = Joiner.on("|")
                    .join(SearchOperation.SIMPLE_OPERATION_SET);
            Pattern pattern = Pattern.compile("([a-zA-Z0-9_\\.]+?)(" + operationSetExper + ")(\\p{Punct}?)([a-zA-Z0-9_-]+?)(\\p{Punct}?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
            }
            spec = builder.build();
        }
        return repository.findAll(spec, paging);
    }

    /**
     * Ritorna tutte le entity filtrate senza paginazione.
     * Utilizzato per esportare tabelle mostrate a video con
     * {@link RepositoryUtil#retrieveEntityFilteredPagedList(int, int, String, String, String, JpaSpecificationExecutor)}
     * @param sortField
     * @param sortDirection
     * @param search
     * @param repository
     * @param <E>
     * @return
     */
    public <E> List<E> retrieveEntityFilteredList(String sortField, String sortDirection, String search, JpaSpecificationExecutor<E> repository) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Specification<E> spec = null;
        if (!search.isEmpty()) {
            EntitySpecificationsBuilder<E> builder = new EntitySpecificationsBuilder();
            String operationSetExper = Joiner.on("|")
                    .join(SearchOperation.SIMPLE_OPERATION_SET);
            Pattern pattern = Pattern.compile("([a-zA-Z0-9_\\.]+?)(" + operationSetExper + ")(\\p{Punct}?)([a-zA-Z0-9_-]+?)(\\p{Punct}?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
            }
            spec = builder.build();
        }
        return repository.findAll(spec, sort);
    }
}
