package it.dipendentepubico.concorsiparenti.domain;

import it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData;

public class EnteDomain implements DomainInterface {
   private Integer id;
   private String descrizione;
   private String codiceFiscale;
   private String codiceIPA;

   private EStatoOpenData statoOpenData;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDescrizione() {
      return descrizione;
   }

   public void setDescrizione(String descrizione) {
      this.descrizione = descrizione;
   }

   public String getCodiceFiscale() {
      return codiceFiscale;
   }

   public void setCodiceFiscale(String codiceFiscale) {
      this.codiceFiscale = codiceFiscale;
   }

   public String getCodiceIPA() {
      return codiceIPA;
   }

   public void setCodiceIPA(String codiceIPA) {
      this.codiceIPA = codiceIPA;
   }

   public EStatoOpenData getStatoOpenData() {
      return statoOpenData;
   }

   public void setStatoOpenData(EStatoOpenData statoOpenData) {
      this.statoOpenData = statoOpenData;
   }
}
