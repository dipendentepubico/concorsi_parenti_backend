package it.dipendentepubico.concorsiparenti.jpa.searchspec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EntitySpecification<E> implements Specification<E> {
    private static final Logger logger = LoggerFactory.getLogger(EntitySpecification.class);

    private SearchCriteria criteria;

    public EntitySpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }


    @Override
    public Predicate toPredicate
            (Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Path finalPath = processPath(root, criteria.getKey());
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(finalPath, criteria.getValue());
            case NEGATION:
                return builder.notEqual(finalPath, criteria.getValue());
            case GREATER_THAN:
                if(finalPath.getJavaType() == Date.class ){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dateStr = formatter.parse(criteria.getValue().toString());
                        return builder.greaterThan(finalPath, dateStr);
                    } catch (ParseException e) {
                        logger.error("Errore nel parse del criteria", e);
                        return null;
                    }
                }
                return builder.greaterThan(finalPath, criteria.getValue().toString());
            case LESS_THAN:
                if(finalPath.getJavaType() == Date.class ){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dateStr = formatter.parse(criteria.getValue().toString());
                        return builder.lessThan(finalPath, dateStr);
                    } catch (ParseException e) {
                        logger.error("Errore nel parse del criteria", e);
                        return null;
                    }
                }
                return builder.lessThan(finalPath, criteria.getValue().toString());
            case LIKE:
                return builder.like(finalPath, criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(finalPath, criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(finalPath, "%" + criteria.getValue());
            case CONTAINS:
                return builder.like( finalPath , "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

    /**
     * Ottiene il Path sulla base di una filterKey.
     * Gestisce anche il caso complesso di chiave del tipo operator.description che diventa site.<String>get("operator").<String>get("description").
     * Funziona in modo lineare se si tratta di un semplice filtro es "description"
     * Funziona in modo ricorsivo se in aggiunta ci sono oggetti più interni es. "operator.description"
     * Ritorna Path senza generics perchè il tipo è deciso dal secondo parametro es.  cb.lessThan(stringPath,  (BigDecimal) filter.getValue() )
     * @param site
     * @param filterKey
     * @param <E>
     * @return
     */
    protected <E> Path processPath(Root<E> site, String filterKey){
        List<String> keys = new ArrayList<>(Arrays.asList(filterKey.split("\\.")));
        String first = keys.remove(0);
        Path thePath = site.get(first);
        Path result = processPathR(thePath, keys);
        return result;
    }

    private Path processPathR(Path thePath, List<String> keys){
        if(keys.size()>0) {
            String first = keys.remove(0);
            Path theNewPath = thePath.get(first);
            return processPathR(theNewPath, keys);
        }else {
            return thePath;
        }
    }




}