package it.dipendentepubico.concorsiparenti.jpa.repository;


import it.dipendentepubico.concorsiparenti.domain.AnagraficaDomain;
import it.dipendentepubico.concorsiparenti.domain.GradoParentelaDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.AnagraficaEntity;
import it.dipendentepubico.concorsiparenti.jpa.entity.DipendenteEntity;
import it.dipendentepubico.concorsiparenti.rest.model.Anagrafica;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import it.dipendentepubico.concorsiparenti.jpa.entity.GradoParentelaEntity;
import it.dipendentepubico.concorsiparenti.domain.AnagraficaConDipendentiParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.ParenteDomain;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.*;

public class AnagraficaRepositoryCustomImpl implements AnagraficaRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<AnagraficaConDipendentiParentiDomain> findIdoneiConDipendentiParenti(Integer concorsoId) {
        TypedQuery<Tuple> query = entityManager.createQuery("select dip as dipendenteEnte, gae.anagrafica as anagraficaIdoneo, p.parentela as gradoParentela " +
                " from DipendenteEntity dip, GraduatoriaAnagraficaEntity gae, ParentelaEntity p " +
                " where (dip.dataFine is null or dip.dataFine < gae.graduatoria.data) and dip.dataInizio <= gae.graduatoria.data " +
                " and ( (p.anagraficaFrom = dip.anagrafica and p.anagraficaTo = gae.anagrafica) or (p.anagraficaFrom = gae.anagrafica and p.anagraficaTo = dip.anagrafica) ) " +
                " and gae.graduatoria.concorso.ente = dip.ente" +
                " and gae.graduatoria.concorso.id = :concorsoId", Tuple.class)
                .setParameter("concorsoId", concorsoId);


        List resultList = query.unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    private transient Map<String, AnagraficaConDipendentiParentiDomain> anagraficaDTOMap = new LinkedHashMap<>();

                    @Override
                    public Object transformTuple(Object[] tuple, String[] aliases) {
                        DipendenteEntity dip = (DipendenteEntity) tuple[0];
                        AnagraficaEntity ana = (AnagraficaEntity) tuple[1];
                        GradoParentelaEntity grado = (GradoParentelaEntity) tuple[2];

                        AnagraficaConDipendentiParentiDomain adp = anagraficaDTOMap.computeIfAbsent(ana.getCodiceFiscale(),
                                k -> {
                                    AnagraficaDomain a = new AnagraficaDomain(ana.getNome(), ana.getCognome(), ana.getCodiceFiscale());
                                    return new AnagraficaConDipendentiParentiDomain(a, new ArrayList<>());
                                }
                        );
                        ParenteDomain p = new ParenteDomain(new AnagraficaDomain(dip.getAnagrafica().getNome(), dip.getAnagrafica().getCognome(), dip.getAnagrafica().getCodiceFiscale()),
                                new GradoParentelaDomain(grado.getDescrizione()));
                        adp.getParenteList().add(p);
                        return adp;
                    }

                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>(anagraficaDTOMap.values());
                    }
                })
                .getResultList();

        return resultList;


    }

    @Override
    public List<ParenteDomain> findDipendentiParentiByIdoneo(Integer concorsoId, Integer anagraficaIdoneoId) {

        TypedQuery<ParenteDomain> typedQuery = entityManager.createQuery("select new it.dipendentepubico.concorsiparenti.domain.ParenteDomain(dip.anagrafica, p.parentela) " +
                        "                 from DipendenteEntity dip, GraduatoriaAnagraficaEntity gae, ParentelaEntity p " +
                        "                 where (dip.dataFine is null or dip.dataFine < gae.graduatoria.data) and dip.dataInizio <= gae.graduatoria.data " +
                        "                 and ( (p.anagraficaFrom = dip.anagrafica and p.anagraficaTo = gae.anagrafica) or (p.anagraficaFrom = gae.anagrafica and p.anagraficaTo = dip.anagrafica) ) " +
                        "                 and gae.graduatoria.concorso.ente = dip.ente " +
                        "                 and gae.graduatoria.concorso.id = :concorsoId " +
                        " and gae.anagrafica.id = :anagraficaIdoneoId",
                ParenteDomain.class)
                .setParameter("concorsoId", concorsoId)
                .setParameter("anagraficaIdoneoId", anagraficaIdoneoId);

        return typedQuery.getResultList();

    }

    @Override
    public List<AnagraficaEntity> searchAutocomplete(String searchLike) {
        TypedQuery<AnagraficaEntity> typedQuery = entityManager.createQuery(
                "select a from AnagraficaEntity a " +
                        " where a.cognome || ' ' || a.nome like :searchLike || '%' " +
                        " or  a.codiceFiscale like  :searchLike || '%' " ,
                AnagraficaEntity.class
        ).setParameter("searchLike", searchLike)
                .setMaxResults(10);

        return typedQuery.getResultList();

    }

}
