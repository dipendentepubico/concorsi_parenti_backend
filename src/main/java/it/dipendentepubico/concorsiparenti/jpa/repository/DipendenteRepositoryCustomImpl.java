package it.dipendentepubico.concorsiparenti.jpa.repository;

import it.dipendentepubico.concorsiparenti.domain.AnagraficaDomain;
import it.dipendentepubico.concorsiparenti.domain.DipendenteConParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.GradoParentelaDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.DipendenteEntity;
import it.dipendentepubico.concorsiparenti.rest.model.Anagrafica;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import it.dipendentepubico.concorsiparenti.jpa.entity.GradoParentelaEntity;
import it.dipendentepubico.concorsiparenti.domain.ParenteDomain;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.*;

public class DipendenteRepositoryCustomImpl implements DipendenteRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public DipendenteConParentiDomain findDipendenteConParenti(String codiceFiscale, Date data) {
        TypedQuery<Tuple> query = entityManager.createQuery("select d as dip, d2 as parente, p.parentela as grado " +
                        " from DipendenteEntity d, DipendenteEntity d2, ParentelaEntity p " +
                        " where d.anagrafica.codiceFiscale = :codiceFiscale " +
                        " and (d.dataFine is null or d.dataFine < :data) and d.dataInizio <= :data " +
                        " and (d2.dataFine is null or d2.dataFine < :data) and d2.dataInizio <= :data " +
                        " and d.ente = d2.ente " +
                        " and ( (p.anagraficaFrom = d.anagrafica and p.anagraficaTo = d2.anagrafica) or (p.anagraficaFrom = d2.anagrafica and p.anagraficaTo = d.anagrafica) ) " +
                        " and d.anagrafica <> d2.anagrafica", Tuple.class)
                .setParameter("codiceFiscale", codiceFiscale)
                .setParameter("data", data);

        List<DipendenteConParentiDomain> resultList = query.unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    private Map<String, DipendenteConParentiDomain> dipendenteDTOMap = new LinkedHashMap<>();

                    @Override
                    public Object transformTuple(Object[] tuple, String[] aliases) {
                        DipendenteEntity dip = (DipendenteEntity) tuple[0];
                        DipendenteEntity parente = (DipendenteEntity) tuple[1];
                        GradoParentelaEntity grado = (GradoParentelaEntity) tuple[2];

                        DipendenteConParentiDomain adp = dipendenteDTOMap.computeIfAbsent(dip.getAnagrafica().getCodiceFiscale(),
                                k -> {
                                    AnagraficaDomain a = new AnagraficaDomain(dip.getAnagrafica().getNome(), dip.getAnagrafica().getCognome(), dip.getAnagrafica().getCodiceFiscale());
                                    return new DipendenteConParentiDomain(a, new ArrayList<>());
                                }
                        );
                        ParenteDomain p = new ParenteDomain(new AnagraficaDomain(parente.getAnagrafica().getNome(), parente.getAnagrafica().getCognome(), parente.getAnagrafica().getCodiceFiscale()),
                                new GradoParentelaDomain(grado.getDescrizione()));
                        adp.getParenti().add(p);
                        return adp;
                    }

                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>(dipendenteDTOMap.values());
                    }
                })
                .getResultList();

        return resultList.get(0);
    }
}
