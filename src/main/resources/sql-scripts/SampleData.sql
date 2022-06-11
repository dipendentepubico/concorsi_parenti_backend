INSERT INTO settings(codice, valore, descrizione) VALUES
    ('RUNNING_ALIGN', 'N', 'Y se è in corso l''allineamento IPA');

INSERT INTO ente (id,descrizione, codice_fiscale, CODICE_IPA, STATO_OPENDATA) VALUES
                                      (nextval('ente_seq'),'Citta'' Metrop. di Roma Capitale', '80034390585', 'cmrc', 'N'),
                                      (nextval('ente_seq'), 'Regione Autonoma Trentino-Alto Adige/Suedtirol', '80003690221', 'r_trenti', 'A'),
                                      (nextval('ente_seq'), 'Ministero dell''Istruzione', '80185250588', 'm_pi', 'A'),
                                        (nextval('ente_seq'), 'Ministero dell''Economia e delle Finanze', '80415740580', 'm_ef', 'A'),
                                      (nextval('ente_seq'), 'Regione Lazio', 'cf_5', 'r_lazio', 'N'),
                                        (nextval('ente_seq'), 'Paginazione_010','codFis_10','054','N'),
                                        (nextval('ente_seq'), 'Paginazione_011','codFis_11','055','N'),
                                        (nextval('ente_seq'), 'Paginazione_012','codFis_12','056','N'),
                                        (nextval('ente_seq'), 'Paginazione_013','codFis_13','058','N'),
                                        (nextval('ente_seq'), 'Paginazione_014','codFis_14','080','N'),
                                        (nextval('ente_seq'), 'Paginazione_015','codFis_15','092','N'),
                                        (nextval('ente_seq'), 'Paginazione_016','codFis_16','093','N'),
                                        (nextval('ente_seq'), 'Paginazione_017','codFis_17','0SGNV76C','N'),
                                        (nextval('ente_seq'), 'Paginazione_018','codFis_18','102','N'),
                                        (nextval('ente_seq'), 'Paginazione_019','codFis_19','1CMVL','N'),
                                        (nextval('ente_seq'), 'Paginazione_020','codFis_20','1I4R6HFH','N'),
                                        (nextval('ente_seq'), 'Paginazione_021','codFis_21','1MF1PZD3','N'),
                                        (nextval('ente_seq'), 'Paginazione_022','codFis_22','1MPXMKGE','N'),
                                        (nextval('ente_seq'), 'Paginazione_023','codFis_23','1VO9PWVQ','N'),
                                        (nextval('ente_seq'), 'Paginazione_024','codFis_24','21W263TE','N'),
                                        (nextval('ente_seq'), 'Paginazione_025','codFis_25','2QR9KMNO','N'),
                                        (nextval('ente_seq'), 'Paginazione_026','codFis_26','38HU6HCT','N'),
                                        (nextval('ente_seq'), 'Paginazione_027','codFis_27','3EYJH0BU','N'),
                                        (nextval('ente_seq'), 'Paginazione_028','codFis_28','3JN4ZMWH','N'),
                                        (nextval('ente_seq'), 'Paginazione_029','codFis_29','3LM92QZX','N'),
                                        (nextval('ente_seq'), 'Paginazione_030','codFis_30','3MR8D7NT','N'),
                                        (nextval('ente_seq'), 'Paginazione_031','codFis_31','3S94F5QD','N'),
                                        (nextval('ente_seq'), 'Paginazione_032','codFis_32','3T9KJCT0','N'),
                                        (nextval('ente_seq'), 'Paginazione_033','codFis_33','3TWJAXOP','N'),
                                        (nextval('ente_seq'), 'Paginazione_034','codFis_34','437MRY30','N'),
                                        (nextval('ente_seq'), 'Paginazione_035','codFis_35','4FGHPC8A','N'),
                                        (nextval('ente_seq'), 'Paginazione_036','codFis_36','4I5ECKIA','N'),
                                        (nextval('ente_seq'), 'Paginazione_037','codFis_37','4ICNTMCY','N'),
                                        (nextval('ente_seq'), 'Paginazione_038','codFis_38','4SK10CDN','N'),
                                        (nextval('ente_seq'), 'Paginazione_039','codFis_39','53029QD1','N'),
                                        (nextval('ente_seq'), 'Paginazione_040','codFis_40','5DNNOS0T','N'),
                                        (nextval('ente_seq'), 'Paginazione_041','codFis_41','5DVYPGTK','N'),
                                        (nextval('ente_seq'), 'Paginazione_042','codFis_42','5GOPE0AP','N'),
                                        (nextval('ente_seq'), 'Paginazione_043','codFis_43','68PDM2YH','N'),
                                        (nextval('ente_seq'), 'Paginazione_044','codFis_44','6YJWRKS1','N'),
                                        (nextval('ente_seq'), 'Paginazione_045','codFis_45','710VMN1T','N'),
                                        (nextval('ente_seq'), 'Paginazione_046','codFis_46','7HP3V1V6','N'),
                                        (nextval('ente_seq'), 'Paginazione_047','codFis_47','7S3QTPXP','N'),
                                        (nextval('ente_seq'), 'Paginazione_048','codFis_48','7T093CG4','N'),
                                        (nextval('ente_seq'), 'Paginazione_049','codFis_49','7UG4CTPM','N'),
                                        (nextval('ente_seq'), 'Paginazione_050','codFis_50','7ZHXNWHL','N'),
                                        (nextval('ente_seq'), 'Paginazione_051','codFis_51','8FL5MZFG','N'),
                                        (nextval('ente_seq'), 'Paginazione_052','codFis_52','8HFG1ZY6','N'),
                                        (nextval('ente_seq'), 'Paginazione_053','codFis_53','8KA77F34','N'),
                                        (nextval('ente_seq'), 'Paginazione_054','codFis_54','8O2ZYX7O','N'),
                                        (nextval('ente_seq'), 'Paginazione_055','codFis_55','8U5DDGY9','N'),
                                        (nextval('ente_seq'), 'Paginazione_056','codFis_56','8UYS28RD','N'),
                                        (nextval('ente_seq'), 'Paginazione_057','codFis_57','8Y6XD6TV','N'),
                                        (nextval('ente_seq'), 'Paginazione_058','codFis_58','9AXJSAEX','N'),
                                        (nextval('ente_seq'), 'Paginazione_059','codFis_59','9IDG0APG','N'),
                                        (nextval('ente_seq'), 'Paginazione_060','codFis_60','9RNBB1Z2','N'),
                                        (nextval('ente_seq'), 'Paginazione_061','codFis_61','a13_f952','N'),
                                        (nextval('ente_seq'), 'Paginazione_062','codFis_62','a1_025','N'),
                                        (nextval('ente_seq'), 'Paginazione_063','codFis_63','a1_na','N'),
                                        (nextval('ente_seq'), 'Paginazione_064','codFis_64','a1_ss','N'),
                                        (nextval('ente_seq'), 'Paginazione_065','codFis_65','a4_te','N'),
                                        (nextval('ente_seq'), 'Paginazione_066','codFis_66','A690_bpe','N'),
                                        (nextval('ente_seq'), 'Paginazione_067','codFis_67','A86RQ42W','N'),
                                        (nextval('ente_seq'), 'Paginazione_068','codFis_68','a9_tv','N'),
                                        (nextval('ente_seq'), 'Paginazione_069','codFis_69','aaba_001','N')
;
INSERT INTO categoria (id,descrizione) VALUES
                                                  (nextval('categoria_seq'),'Cat D'),
                                                  (nextval('categoria_seq'),'Cat C');
INSERT INTO grado_parentela (id,descrizione) VALUES
                                                        (nextval('parentela_seq'),'genitore'),
                                                        (nextval('parentela_seq'),'suocero'),
                                                        (nextval('parentela_seq'),'nonno'),
                                                        (nextval('parentela_seq'),'sposo'),
                                                        (nextval('parentela_seq'), 'fratello');

INSERT INTO anagrafica (id,codice_fiscale,cognome,nome) VALUES
                                                                   (nextval('anagrafica_seq'),'cppp','CognomeParente','ParentePadre'),
                                                                   (nextval('anagrafica_seq'),'cppfi','CognomeParente','ParenteFiglio'),
                                                                   (nextval('anagrafica_seq'),'fh','hood','frank'),
                                                                   (nextval('anagrafica_seq'),'cppfr','CognomeParente','ParenteFratello');

INSERT INTO parentela (id,anagrafica_from_id,anagrafica_to_id,parentela_id) VALUES
    (1,1,2,1),
                                                                                   (2,1,4,5);

INSERT INTO concorso (id,gazzetta_ufficiale_anno,gazzetta_ufficiale_numero,categoria_id,ente_id) VALUES
    (1,2020,9,1,1);

INSERT INTO graduatoria_finale (id,data,link,concorso_id) VALUES
    (nextval('graduatoria_seq'),'2022-01-05 00:00:00','LINK',1);

INSERT INTO graduatoria_anagrafica (id,posizione,vincitore,anagrafica_id,graduatoria_id) VALUES
    (nextval('gradanag_seq'),1,true,3,1),
    (nextval('gradanag_seq'),2,true,2,1);

INSERT INTO dipendente (id,data_fine,data_inizio,link,anagrafica_id,categoria_id,ente_id) VALUES
                         (nextval('dipendente_seq'),NULL,'2001-01-02 00:00:00','link',1,1,1), -- Roma, CognomeParente ParentePadre
                         (nextval('dipendente_seq'),NULL,'2011-05-03 00:00:00','lin',4,2,1), -- Roma, CognomeParente ParenteFratello
                         (nextval('dipendente_seq'),NULL,'2011-05-03 00:00:00','lin',3,2,6); -- Arpal, hood frank

INSERT INTO ROLES(id, name) VALUES (nextval('ROLE_SEQ'), 'ROLE_ADMIN');
INSERT INTO ROLES(id, name) VALUES (nextval('ROLE_SEQ'), 'ROLE_USER');
INSERT INTO ROLES(id, name) VALUES (nextval('ROLE_SEQ'), 'ROLE_MOD');
-- admin/admin2admin
INSERT INTO USERS(id, username, password, email) VALUES (nextval('USER_SEQ'), 'admin', '$2a$10$QJJiFF/vc/W4avV2S1a09u6kfr0TFnKvXmcBxJreYRhnVYxmG/ode', 'admin@cpd.invalid');
-- string/string
INSERT INTO USERS(id, username, password, email) VALUES (nextval('USER_SEQ'), 'string', '$2a$10$uqv8C7IWIBSclTLHZDN0DeFBYd7hthunx8JLu/gKkke0oZ664V916', 'string@cpd.invalid');
INSERT INTO USERS(id, username, password, email) VALUES (nextval('USER_SEQ'), 'simpleuser', '$2a$10$LB8rbmGTOj4p7zO/wjujou.6l6VXaMvemzg3bMeVXRiz4jLutfkwC');

INSERT INTO user_roles(user_id, role_id) VALUES ((select id from USERS where username = 'admin'), (select id from ROLES where name = 'ROLE_ADMIN'));
INSERT INTO user_roles(user_id, role_id) VALUES ((select id from USERS where username = 'string'), (select id from ROLES where name = 'ROLE_ADMIN'));
INSERT INTO user_roles(user_id, role_id) VALUES ((select id from USERS where username = 'simpleuser'), (select id from ROLES where name = 'ROLE_USER'));