package it.dipendentepubico.concorsiparenti;

import it.dipendentepubico.concorsiparenti.domain.AnagraficaConDipendentiParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.DipendenteConParentiDomain;
import it.dipendentepubico.concorsiparenti.domain.ParenteDomain;
import it.dipendentepubico.concorsiparenti.jpa.entity.*;
import it.dipendentepubico.concorsiparenti.jpa.repository.*;
import it.dipendentepubico.concorsiparenti.jpa.searchspec.EntitySpecification;
import it.dipendentepubico.concorsiparenti.jpa.searchspec.SearchCriteria;
import it.dipendentepubico.concorsiparenti.jpa.searchspec.SearchOperation;
import it.dipendentepubico.concorsiparenti.usecase.EnteUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Test sui Repository
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:trxServiceStatus"})
class BackendCPDApplicationTests {

	@Autowired
	private AnagraficaRepository anagraficaRepository;
	@Autowired
	private GradoParentelaRepository gradoParentelaRepository;
	@Autowired
	private ParentelaRepository parentelaRepository;
	@Autowired
	private EnteRepository enteRepository;
	@Autowired
	private DipendenteRepository dipendenteRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ConcorsoRepository concorsoRepository;
	@Autowired
	private GraduatoriaRepository graduatoriaRepository;
	@Autowired
	private GraduatoriaAnagraficaRepository graduatoriaAnagraficaRepository;
	@Autowired
	private SettingsRepository settingsRepository;

	@Autowired
	private EnteUseCase enteUseCase;

	@LocalServerPort
	private int port;


	private String generateCF() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 16;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

	@BeforeAll
	public static void startup(@Autowired GradoParentelaRepository gradoParentelaRepository, @Autowired SettingsRepository settingsRepository, @Autowired CategoriaRepository categoriaRepository){
		// popolo database con valori che non variano. in alternativa usare script sql
		SettingsEntity settingsEntity = new SettingsEntity();
		settingsEntity.setCodice("RUNNING_ALIGN");
		settingsEntity.setValore("N");
		settingsEntity.setDescrizione("Y se è in corso l'allineamento IPA");
		settingsRepository.save(settingsEntity);

		CategoriaEntity cat = new CategoriaEntity();
		cat.setDescrizione("Cat. D");
		categoriaRepository.save(cat);

		GradoParentelaEntity gradoParentela = new GradoParentelaEntity();
		gradoParentela.setDescrizione("Padre");
		gradoParentelaRepository.save(gradoParentela);
		gradoParentela = new GradoParentelaEntity();
		gradoParentela.setDescrizione("Fratello");
		gradoParentelaRepository.save(gradoParentela);
	}


	@Test //dato ente, data: elenco dipendenti con parentele
	@Transactional
	public void findDilpendentiParentiEnte()
	{
		GradoParentelaEntity gpSaved = gradoParentelaRepository.findGradoParentelaEntityByDescrizione("Padre");

		String cognome = "Rossi";
		AnagraficaEntity padre = new AnagraficaEntity();
		padre.setCognome(cognome);
		padre.setNome("Mario");
		padre.setCodiceFiscale(generateCF());
		AnagraficaEntity padreSaved = anagraficaRepository.save(padre);

		AnagraficaEntity figlio = new AnagraficaEntity();
		figlio.setCognome(cognome);
		figlio.setNome("Luigi");
		figlio.setCodiceFiscale(generateCF());
		AnagraficaEntity figlioSaved = anagraficaRepository.save(figlio);

		ParentelaEntity parentela = new ParentelaEntity();
		parentela.setAnagraficaFrom(padreSaved);
		parentela.setAnagraficaTo(figlioSaved);
		parentela.setParentela(gpSaved);
		parentelaRepository.save(parentela);

		EnteEntity ente = new EnteEntity();
		ente.setDescrizione("Ente 01");
		EnteEntity enteSaved = enteRepository.save(ente);

		DipendenteEntity dipPadre = new DipendenteEntity();
		dipPadre.setAnagrafica(padreSaved);
		dipPadre.setEnte(enteSaved);
		dipPadre.setLink("http://ente/concorso");
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE, -10);
		dipPadre.setDataInizio(instance.getTime());
		dipendenteRepository.save(dipPadre);

		DipendenteEntity dipFiglio = new DipendenteEntity();
		dipFiglio.setAnagrafica(figlioSaved);
		dipFiglio.setEnte(enteSaved);
		dipFiglio.setLink("http://ente/concorso");
		instance.add(Calendar.DATE, -2);
		dipFiglio.setDataInizio(instance.getTime());
		dipendenteRepository.save(dipFiglio);


		List<AnagraficaEntity> dipendentiParentiEnte = anagraficaRepository.findDipendentiParentiEnte(1, new Date());
		Assertions.assertEquals(figlio.getNome(), dipendentiParentiEnte.get(0).getNome());
		Assertions.assertEquals(figlio.getCodiceFiscale(), dipendentiParentiEnte.get(0).getCodiceFiscale());
		Assertions.assertEquals(padre.getCodiceFiscale(), dipendentiParentiEnte.get(1).getCodiceFiscale());
	}



	@Test //dato dipendente, data: elenco parenti in stesso ente
	@Transactional
	public void findParentiDelDipendente()
	{
		GradoParentelaEntity gpSaved = gradoParentelaRepository.findGradoParentelaEntityByDescrizione("Fratello");

		String cognome = "Bianchi";
		AnagraficaEntity anagrafica = new AnagraficaEntity();
		anagrafica.setCognome(cognome);
		anagrafica.setNome("Mario");
		anagrafica.setCodiceFiscale(generateCF());
		AnagraficaEntity padreSaved = anagraficaRepository.save(anagrafica);

		AnagraficaEntity fratello = new AnagraficaEntity();
		fratello.setCognome(cognome);
		fratello.setNome("Luigi");
		fratello.setCodiceFiscale(generateCF());
		AnagraficaEntity figlioSaved = anagraficaRepository.save(fratello);

		ParentelaEntity parentela = new ParentelaEntity();
		parentela.setAnagraficaFrom(padreSaved);
		parentela.setAnagraficaTo(figlioSaved);
		parentela.setParentela(gpSaved);
		parentelaRepository.save(parentela);

		EnteEntity ente = new EnteEntity();
		ente.setDescrizione("Ente 03");
		EnteEntity enteSaved = enteRepository.save(ente);

		DipendenteEntity dipAna = new DipendenteEntity();
		dipAna.setAnagrafica(padreSaved);
		dipAna.setEnte(enteSaved);
		dipAna.setLink("http://ente/concorso");
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE, -10);
		dipAna.setDataInizio(instance.getTime());
		dipendenteRepository.save(dipAna);

		DipendenteEntity dipFrate = new DipendenteEntity();
		dipFrate.setAnagrafica(figlioSaved);
		dipFrate.setEnte(enteSaved);
		dipFrate.setLink("http://ente/concorso");
		instance.add(Calendar.DATE, -2);
		dipFrate.setDataInizio(instance.getTime());
		dipendenteRepository.save(dipFrate);


		DipendenteConParentiDomain dipendenteConParentiDomain = dipendenteRepository.findDipendenteConParenti(anagrafica.getCodiceFiscale(), new Date());
		Assertions.assertEquals(fratello.getCodiceFiscale(), dipendenteConParentiDomain.getParenti().get(0).getAnagrafica().getCodiceFiscale());
		Assertions.assertEquals(gpSaved.getDescrizione(), dipendenteConParentiDomain.getParenti().get(0).getGradoParentela().getDescrizione());
		Assertions.assertEquals(anagrafica.getCodiceFiscale(), dipendenteConParentiDomain.getAnagrafica().getCodiceFiscale());
	}


	/**
	 * dato concorso: elenco parenti tra dipendenti e idonei
	 */
	@Test
	@Transactional
	public void findIdoneiDipendentiParentiEnte()
	{
		GradoParentelaEntity gpSaved = gradoParentelaRepository.findGradoParentelaEntityByDescrizione("Padre");

		String cognome = "Rossi";
		AnagraficaEntity padre = new AnagraficaEntity();
		padre.setCognome(cognome);
		padre.setNome("Mario");
		padre.setCodiceFiscale(generateCF());
		AnagraficaEntity padreSaved = anagraficaRepository.save(padre);

		AnagraficaEntity figlioIdoneo = new AnagraficaEntity();
		figlioIdoneo.setCognome(cognome);
		figlioIdoneo.setNome("Luigi");
		figlioIdoneo.setCodiceFiscale(generateCF());
		AnagraficaEntity figlioSaved = anagraficaRepository.save(figlioIdoneo);

		ParentelaEntity parentela = new ParentelaEntity();
		parentela.setAnagraficaFrom(padreSaved);
		parentela.setAnagraficaTo(figlioSaved);
		parentela.setParentela(gpSaved);
		parentelaRepository.save(parentela);

		EnteEntity ente = new EnteEntity();
		ente.setDescrizione("Ente 01");
		EnteEntity enteSaved = enteRepository.save(ente);

		DipendenteEntity dipPadre = new DipendenteEntity();
		dipPadre.setAnagrafica(padreSaved);
		dipPadre.setEnte(enteSaved);
		dipPadre.setLink("http://ente/concorso");
		Calendar instance =instance = Calendar.getInstance();
		instance.add(Calendar.DATE, -10);
		dipPadre.setDataInizio(instance.getTime());
		dipendenteRepository.save(dipPadre);


		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setDescrizione("Cat D");
		CategoriaEntity catSaved = categoriaRepository.save(categoria);

		ConcorsoEntity concorso = new ConcorsoEntity();
		concorso.setEnte(enteSaved);
		concorso.setCategoria(catSaved);
		concorso.setGuAnno(2020);
		concorso.setGuNumero(10);
		ConcorsoEntity concSaved = concorsoRepository.save(concorso);

		GraduatoriaFinaleEntity graduatoria = new GraduatoriaFinaleEntity();
		graduatoria.setLink("link graduatoria");
		graduatoria.setConcorso(concSaved);
		instance = Calendar.getInstance();
		instance.add(Calendar.DATE, -5);
		graduatoria.setData(instance.getTime());
		GraduatoriaFinaleEntity gradSaved = graduatoriaRepository.save(graduatoria);

		GraduatoriaAnagraficaEntity gae = new GraduatoriaAnagraficaEntity();
		gae.setAnagrafica(figlioSaved);
		gae.setGraduatoria(gradSaved);
		gae.setPosizione(2);
		gae.setVincitore(false);
		graduatoriaAnagraficaRepository.save(gae);


		List<AnagraficaConDipendentiParentiDomain> idoneoConDipendentiParenti = anagraficaRepository.findIdoneiConDipendentiParenti(concSaved.getId());
		Assertions.assertEquals(figlioIdoneo.getCodiceFiscale(), idoneoConDipendentiParenti.get(0).getAnagrafica().getCodiceFiscale() );
		Assertions.assertEquals(padre.getCodiceFiscale(), idoneoConDipendentiParenti.get(0).getParenteList().get(0).getAnagrafica().getCodiceFiscale());
		Assertions.assertEquals(gpSaved.getDescrizione(), idoneoConDipendentiParenti.get(0).getParenteList().get(0).getGradoParentela().getDescrizione());
	}

	@Test
	@Transactional
	public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {

		EnteEntity ente = new EnteEntity();
		String descrizione = "Ente 01";
		ente.setDescrizione(descrizione);
		enteRepository.save(ente);

		EntitySpecification spec1 =
				new EntitySpecification(new SearchCriteria("descrizione", SearchOperation.CONTAINS, descrizione));
		List<EnteEntity> results = enteRepository.findAll(Specification.where(spec1));

		Assertions.assertEquals(descrizione, results.get(0).getDescrizione());


	}

	@Test
	@Transactional
	public void retriveConcorsoConGraduatoria(){
		CategoriaEntity cat = new CategoriaEntity();
		cat.setDescrizione("Cat. D");
		CategoriaEntity savedCat = categoriaRepository.save(cat);
		EnteEntity e = new EnteEntity();
		e.setDescrizione("Ente 05");
		EnteEntity savedE = enteRepository.save(e);
		ConcorsoEntity c = new ConcorsoEntity();
		c.setGuAnno(2022);
		c.setGuNumero(5);
		c.setCategoria(savedCat);
		c.setEnte(savedE);
		ConcorsoEntity savedC = concorsoRepository.save(c);
		GraduatoriaFinaleEntity gf = new GraduatoriaFinaleEntity();
		gf.setConcorso(savedC);
		gf.setData(new Date());
		gf.setLink("http://the.link");
		GraduatoriaFinaleEntity savedGf = graduatoriaRepository.save(gf);
		AnagraficaEntity a1 = new AnagraficaEntity();
		a1.setNome("Nome1");
		a1.setCognome("Cognome2");
		a1.setCodiceFiscale("CF1");
		AnagraficaEntity savedA1 = anagraficaRepository.save(a1);
		GraduatoriaAnagraficaEntity gfa1 = new GraduatoriaAnagraficaEntity();
		gfa1.setGraduatoria(savedGf);
		gfa1.setAnagrafica(savedA1);
		gfa1.setPosizione(0);
		gfa1.setVincitore(true);
		graduatoriaAnagraficaRepository.save(gfa1);
		AnagraficaEntity a2 = new AnagraficaEntity();
		a2.setNome("Nome2");
		a2.setCognome("Cognome2");
		a2.setCodiceFiscale("CF2");
		AnagraficaEntity savedA2 = anagraficaRepository.save(a2);
		GraduatoriaAnagraficaEntity gfa2 = new GraduatoriaAnagraficaEntity();
		gfa2.setGraduatoria(savedGf);
		gfa2.setAnagrafica(savedA2);
		gfa2.setPosizione(1);
		gfa2.setVincitore(true);
		graduatoriaAnagraficaRepository.save(gfa2);

		savedGf.setGraduatoriaAnagraficaEntityList(new ArrayList<>());
		savedGf.getGraduatoriaAnagraficaEntityList().add(gfa1);
		savedGf.getGraduatoriaAnagraficaEntityList().add(gfa2);
		graduatoriaRepository.save(savedGf);

		GraduatoriaFinaleEntity readGf = graduatoriaRepository.findGraduatoriaFinaleEntityByConcorsoId(savedC.getId());
		Assertions.assertEquals(2, readGf.getGraduatoriaAnagraficaEntityList().size());
	}




	@Test
	@Transactional
	public void retriveDipendentiParentiByIdoneo(){
		Date dataConcorso = new Date();
		Calendar instance = Calendar.getInstance();
		instance.setTime(dataConcorso);
		instance.add(Calendar.DATE, -10);
		Date dataInizio = instance.getTime();
		// categoria
		CategoriaEntity cat = new CategoriaEntity();
		cat.setDescrizione("Cat. D");
		// ente
		CategoriaEntity savedCat = categoriaRepository.save(cat);
		EnteEntity e = new EnteEntity();
		e.setDescrizione("Ente 05");
		EnteEntity savedE = enteRepository.save(e);
		// dipendente
		AnagraficaEntity ad = new AnagraficaEntity();
		ad.setCodiceFiscale("CF dip");
		ad.setCognome("cognomedip");
		ad.setNome("nomedip");
		AnagraficaEntity savedAd = anagraficaRepository.save(ad);
		DipendenteEntity dip = new DipendenteEntity();
		dip.setAnagrafica(savedAd);
		dip.setCategoria(savedCat);
		dip.setEnte(savedE);
		dip.setDataInizio(dataInizio);
		dip.setDataFine(null);
		dip.setLink("https://link.dipendente");
		dipendenteRepository.save(dip);
		// concorso
		ConcorsoEntity c = new ConcorsoEntity();
		c.setGuAnno(2022);
		c.setGuNumero(5);
		c.setCategoria(savedCat);
		c.setEnte(savedE);
		ConcorsoEntity savedC = concorsoRepository.save(c);
		// graduatoria
		GraduatoriaFinaleEntity gf = new GraduatoriaFinaleEntity();
		gf.setConcorso(savedC);
		gf.setData(dataConcorso);
		gf.setLink("http://the.link");
		GraduatoriaFinaleEntity savedGf = graduatoriaRepository.save(gf);
		// anagrafica e idoneo 1
		AnagraficaEntity a1 = new AnagraficaEntity();
		a1.setNome("Nome1");
		a1.setCognome("Cognome2");
		a1.setCodiceFiscale("CF1");
		AnagraficaEntity savedA1 = anagraficaRepository.save(a1);
		GraduatoriaAnagraficaEntity gfa1 = new GraduatoriaAnagraficaEntity();
		gfa1.setGraduatoria(savedGf);
		gfa1.setAnagrafica(savedA1);
		gfa1.setPosizione(0);
		gfa1.setVincitore(true);
		graduatoriaAnagraficaRepository.save(gfa1);
		// anagrafica e idoneo 2
		AnagraficaEntity a2 = new AnagraficaEntity();
		a2.setNome("Nome2");
		a2.setCognome("Cognome2");
		a2.setCodiceFiscale("CF2");
		AnagraficaEntity savedA2 = anagraficaRepository.save(a2);
		GraduatoriaAnagraficaEntity gfa2 = new GraduatoriaAnagraficaEntity();
		gfa2.setGraduatoria(savedGf);
		gfa2.setAnagrafica(savedA2);
		gfa2.setPosizione(1);
		gfa2.setVincitore(true);
		graduatoriaAnagraficaRepository.save(gfa2);
		savedGf.setGraduatoriaAnagraficaEntityList(new ArrayList<>());
		savedGf.getGraduatoriaAnagraficaEntityList().add(gfa1);
		savedGf.getGraduatoriaAnagraficaEntityList().add(gfa2);
		graduatoriaRepository.save(savedGf);
		// parentela
		GradoParentelaEntity gpSaved = gradoParentelaRepository.findGradoParentelaEntityByDescrizione("Padre");
		ParentelaEntity parentela = new ParentelaEntity();
		parentela.setParentela(gpSaved);
		parentela.setAnagraficaFrom(savedAd);
		parentela.setAnagraficaTo(a1);
		parentelaRepository.save(parentela);

		List<ParenteDomain> dipendentiParentiByIdoneo = anagraficaRepository.findDipendentiParentiByIdoneo(savedC.getId(), a1.getId());
		Assertions.assertEquals(gpSaved.getDescrizione(), dipendentiParentiByIdoneo.get(0).getGradoParentela().getDescrizione());

		List<ParenteDomain> dipendentiParentiByIdoneo1 = anagraficaRepository.findDipendentiParentiByIdoneo(savedC.getId(), a2.getId());
		Assertions.assertEquals(0, dipendentiParentiByIdoneo1.size());

	}

	@Test
	@Transactional
	public void autocompleteAnagrafica(){
		AnagraficaEntity a1 = new AnagraficaEntity();
		a1.setNome("Nome1");
		String cognomeTest = "Cognome2";
		a1.setCognome(cognomeTest);
		a1.setCodiceFiscale("CF1AAABBBCCC");
		anagraficaRepository.save(a1);
		a1 = new AnagraficaEntity();
		String nomeTest = "Ciccio";
		a1.setNome(nomeTest);
		a1.setCognome("Cappuccio");
		String cfTest = "CCCAPCCIO";
		a1.setCodiceFiscale(cfTest);
		anagraficaRepository.save(a1);
		a1 = new AnagraficaEntity();
		a1.setNome(nomeTest);
		a1.setCognome("Frappuccio");
		a1.setCodiceFiscale("FRACCIO");
		anagraficaRepository.save(a1);
		a1 = new AnagraficaEntity();
		a1.setNome("Francesco");
		a1.setCognome(cognomeTest);
		a1.setCodiceFiscale("FRCOG");
		anagraficaRepository.save(a1);



		String testo = "Cogn";
		List<AnagraficaEntity> anagraficaEntities = anagraficaRepository.searchAutocomplete(testo);
		Assertions.assertEquals(cognomeTest, anagraficaEntities.get(0).getCognome());
		testo = "CCC";
		anagraficaEntities = anagraficaRepository.searchAutocomplete(testo);
		Assertions.assertEquals(cfTest, anagraficaEntities.get(0).getCodiceFiscale());
		testo = "Ciccio ";
		anagraficaEntities = anagraficaRepository.searchAutocomplete(testo);
		Assertions.assertEquals(0, anagraficaEntities.size());
		testo = cognomeTest;
		anagraficaEntities = anagraficaRepository.searchAutocomplete(testo);
		Assertions.assertEquals(2, anagraficaEntities.size());

	}


	@Test
	public void importOpenData() throws InterruptedException, ExecutionException {
		EnteEntity disallineato = new EnteEntity();
		String cf = "codiceFisc";
		String codiceIpa = "058";
		disallineato.setCodiceFiscale(cf);
		disallineato.setDescrizione("prova");
		enteRepository.save(disallineato);
		Future enteCSVImportThread = enteUseCase.allineaOpenData("src/test/resources/opendata/b0aa1f6c-f135-4c8a-b416-396fed4e1a5d.csv");
		enteCSVImportThread.get();
		EnteEntity inserito = enteRepository.findByCodiceFiscale("04733471009");
		Assertions.assertNotNull(inserito);
		Assertions.assertEquals(codiceIpa, inserito.getCodiceIPA());
		Assertions.assertEquals(inserito.getCodiceFiscale(), enteRepository.findByCodiceIPA(codiceIpa).getCodiceFiscale());
		Assertions.assertEquals(EStatoOpenData.NON_ALLINEATO, enteRepository.findByCodiceFiscale(cf).getStatoOpenData()) ;
		EnteEntity c_l425 = enteRepository.findByCodiceIPA("arpa_ve");
		Assertions.assertEquals("92111430283", c_l425.getCodiceFiscale());
		Assertions.assertEquals(EStatoOpenData.ALLINEATO, c_l425.getStatoOpenData());
		Assertions.assertEquals(22266, enteRepository.count());
	}

}
