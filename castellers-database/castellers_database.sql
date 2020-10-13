-- Alumnes Marc Elias i Luis Alvarez

drop database castells;
create database castells; 
use castells;

create table paisos(
	nom varchar(30),
	primary key (nom)
) engine = innodb;

create table poblacions(
	nom varchar(30),
	pais varchar(30),
	primary key (nom, pais),
	foreign key (pais) references paisos(nom)
) engine = innodb;

create table castellers(
	codi int(10),
	nom varchar(30) not null,
	cognom1 varchar(30) not null,
	cognom2 varchar(30), -- suposem que hi pot haber gent sense un segon cognom
	pes smallint(3) not null,
	alcada smallint(3) not null, 
	primary key(codi)
) engine = innodb;

create table colles(
	nom varchar(30),
	any_fund smallint(4),
	color_camisa varchar(20) not null,
	adreca_local varchar(60), -- no hem posat una ce trencada per la codificacio ascii i no ho posem com a not nul perque podria haver hi una colla que no te un local
	poblacio varchar(30) not null,
	pais varchar(30) not null,
	cap_de_colla int(10) not null,
	primary key (nom),
	foreign key (poblacio,pais) references poblacions(nom,pais),
	foreign key (cap_de_colla) references castellers(codi)
) engine = innodb;

create table motius(
	motiu varchar(100), -- es un varchar(100) perque pot anar desde un motiu simple fins a una petita descripcio del mateix
	primary key (motiu)
) engine = innodb;

create table pertanyenca(
	casteller int(10),
	data_alta date,
	colla varchar(30) not null,
	data_baixa date,
	motiu_baixa varchar(100),
	primary key (casteller,data_alta),
	foreign key (casteller) references castellers(codi),
	foreign key (colla) references colles(nom),
	foreign key (motiu_baixa) references motius(motiu)
) engine = innodb;

create table substituts(
	casteller int(10),
	substituible_per int(10),
	primary key (casteller,substituible_per),
	foreign key (casteller) references castellers(codi),
	foreign key (substituible_per) references castellers(codi),
	constraint un_subst unique (casteller, substituible_per)
) engine = innodb;

create table actuacions(    -- considerem que s'introdueix una actuacio quan ja esta planificada
	nom varchar(30),
	data_actuacio date not null,
	hora time not null, 
	lloc varchar(30) not null,
	poblacio varchar(30) not null,
	pais varchar(30) not null,
	primary key (nom),
	foreign key (poblacio,pais) references poblacions(nom,pais),
	constraint un_actuacions unique (data_actuacio, poblacio, pais)
) engine = innodb;

create table actua(
	nom_colla varchar(30),
	nom_actuacio varchar(30),
	primary key (nom_colla,nom_actuacio),
	foreign key (nom_colla) references colles(nom),
	foreign key (nom_actuacio) references actuacions(nom)
) engine = innodb;

create table tipus_lesio(
	nom varchar(30),
	primary key (nom)
) engine = innodb;

create table lesions(
	codi_casteller int(10),
	nom_actuacio varchar(30),
	tipus_lesio varchar(30),
	grau int(2) not null,
	primary key (codi_casteller,nom_actuacio,tipus_lesio),
	foreign key (codi_casteller) references castellers(codi),
	foreign key (nom_actuacio) references actuacions(nom),
	foreign key (tipus_lesio) references tipus_lesio(nom)
) engine = innodb;

create table concursos(
	nom_actuacio varchar(30),
	primary key (nom_actuacio),
	foreign key (nom_actuacio) references actuacions(nom)
) engine = innodb;

create table inscrita(
	colla varchar(30),
	concurs varchar(30),
	primary key (colla, concurs),
	foreign key (colla) references colles(nom),
	foreign key (concurs) references concursos(nom_actuacio)
) engine = innodb;

create table castells(
	amplada int(1), 
	alcada int(2),
	n_reforcos int(1),
	per_sota char(1),
	primary key (amplada,alcada,n_reforcos,per_sota)
) engine = innodb;

create table intenta(
	colla varchar(30),
	concurs varchar(30),
	amplada int(1),
	alcada int(2),
	n_reforcos int(1),
	per_sota char(1),
	carregat char(1) not null,
	descarregat char(1) not null,
	primary key (colla,concurs,amplada,alcada,n_reforcos,per_sota),
	foreign key (colla, concurs) references inscrita(colla,concurs),
	foreign key (amplada,alcada,n_reforcos,per_sota) references castells(amplada,alcada,n_reforcos,per_sota)
) engine = innodb;

create table puntuacions(
	amplada varchar(30),
	alcada int(2),
	n_reforcos int(1),
	per_sota char(1),
	data_p date,
	data_fi date,
	punts_carregat int(3) not null, 
	punts_descarregat int(3) not null,
	primary key (amplada,alcada,n_reforcos,per_sota,data_p)
	-- foreign key (amplada,alcada,n_reforcos,per_sota) references castells(amplada,alcada,n_reforcos,per_sota)
) engine = innodb;

-- ***********************************************************************************

insert into paisos(nom) values ('espanya');

insert into poblacions (nom, pais) values ('reus', 'espanya');

insert into castellers (codi, nom,cognom1, cognom2, pes, alcada)
values (0000000001, 'marc', 'sanchez', 'serret', 070, 185);

insert into castellers (codi, nom, cognom1, cognom2, pes, alcada)
values (0000000002, 'pepito', 'matkovic', 'del pozzo', 100, 170);

insert into castellers (codi, nom, cognom1, cognom2, pes, alcada)
values (0000000003, 'juanito', 'blackbourne', null, 058, 210);

insert into castellers (codi, nom, cognom1, cognom2, pes, alcada)
values (0000000004, 'luisito', 'martinez', null, 081, 187);

-- ***********************************************************************************

insert into colles (nom, any_fund, color_camisa, adreca_local, poblacio, pais, cap_de_colla) 
values ('colla1', 1990, 'groc', 'asdad', 'reus', 'espanya', 0000000001);

insert into colles (nom, any_fund, color_camisa, adreca_local, poblacio, pais, cap_de_colla) 
values ('colla2', 1980, 'verd', 'carrer 1', 'reus', 'espanya', 0000000002);

insert into colles (nom, any_fund, color_camisa, adreca_local, poblacio, pais, cap_de_colla) 
values ('colla3', 1993, 'vermell', 'carrero de la dreta', 'reus', 'espanya', 0000000003);

-- ***********************************************************************************

insert into motius (motiu)
values ("Cansanci");

-- ***********************************************************************************

insert into pertanyenca (casteller, data_alta, colla, data_baixa, motiu_baixa)
values (0000000001, '1980-08-07', 'colla1', null, null);

insert into pertanyenca (casteller, data_alta, colla, data_baixa, motiu_baixa)
values (0000000002, '2000-05-18', 'colla2', null, null);

insert into pertanyenca (casteller, data_alta, colla, data_baixa, motiu_baixa)
values (0000000003, '1993-12-25', 'colla3', null, null);

insert into pertanyenca (casteller, data_alta, colla, data_baixa, motiu_baixa)
values (0000000004, '1993-12-25', 'colla3', "1994-11-21", "Cansanci");

-- ***********************************************************************************

insert into actuacions (nom, data_actuacio, hora, lloc, poblacio, pais)
values ('actuacio1', '2005-08-05', '12:00', 'placa1', 'reus', 'espanya');

insert into actuacions (nom, data_actuacio, hora, lloc, poblacio, pais)
values ('actuacio2', '2003-10-04', '16:00', 'placa2', 'reus', 'espanya');

insert into actuacions (nom, data_actuacio, hora, lloc, poblacio, pais)
values ('actuacio3', '1990-08-12', '09:00', 'placa3', 'reus', 'espanya');

insert into actuacions (nom, data_actuacio, hora, lloc, poblacio, pais)
values ('actuacio4', '1995-01-20', '10:00', 'placa4', 'reus', 'espanya');

insert into actuacions (nom, data_actuacio, hora, lloc, poblacio, pais)
values ('actuacio5', '1880-9-15', '20:00', 'placa5', 'reus', 'espanya');

-- ***********************************************************************************

insert into concursos (nom_actuacio)
values ('actuacio1');

insert into concursos (nom_actuacio)
values ('actuacio2');

insert into concursos (nom_actuacio)
values ('actuacio3');

-- ***********************************************************************************

insert into inscrita (colla, concurs)
values ('colla1', 'actuacio1');

insert into inscrita (colla, concurs)
values ('colla1', 'actuacio2');

insert into inscrita (colla, concurs)
values ('colla1', 'actuacio3');

insert into inscrita (colla, concurs)
values ('colla2', 'actuacio1');

insert into inscrita (colla, concurs)
values ('colla2', 'actuacio2');

insert into inscrita (colla, concurs)
values ('colla2', 'actuacio3');

-- ***********************************************************************************

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(3, 9, 0, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(4, 9, 2, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(4, 9, 0, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(5, 9, 3, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(3, 8, 0, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(4, 8, 1, 'n');

insert into castells (amplada, alcada, n_reforcos, per_sota)
values(5, 8, 2, 'n');

-- ***********************************************************************************

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla1', 'actuacio1', 4, 9, 0, 'n', 's', 'n');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla2', 'actuacio1', 3, 9, 0, 'n', 's', 's');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla1', 'actuacio3', 4, 9, 0, 'n', 's', 's');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla2', 'actuacio1', 4, 9, 0, 'n', 's', 's');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla2', 'actuacio2', 4, 9, 0, 'n', 's', 's');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla2', 'actuacio2', 3, 9, 0, 'n', 'n', 'n');

insert into intenta (colla, concurs , amplada, alcada, n_reforcos, per_sota, carregat, descarregat)
values ('colla1', 'actuacio3', 3, 9, 0, 'n', 'n', 'n');

-- ***********************************************************************************

insert into actua (nom_colla, nom_actuacio)
values ('colla1', 'actuacio4');

insert into actua (nom_colla, nom_actuacio)
values ('colla2', 'actuacio4');

insert into actua (nom_colla, nom_actuacio)
values ('colla3', 'actuacio4');

insert into actua (nom_colla, nom_actuacio)
values ('colla1', 'actuacio5');

insert into actua (nom_colla, nom_actuacio)
values ('colla2', 'actuacio5');

-- ***********************************************************************************

insert into tipus_lesio (nom)
values ('cop al cap');

insert into tipus_lesio (nom)
values ('mal d-esquena');

-- ***********************************************************************************

insert into lesions (codi_casteller, nom_actuacio, tipus_lesio, grau)
values (0000000001, 'actuacio4', 'mal d-esquena', 1);

insert into lesions (codi_casteller, nom_actuacio, tipus_lesio, grau)
values (0000000001, 'actuacio4', 'cop al cap', 8);

insert into lesions (codi_casteller, nom_actuacio, tipus_lesio, grau)
values (0000000002, 'actuacio4', 'cop al cap', 9);

insert into lesions (codi_casteller, nom_actuacio, tipus_lesio, grau)
values (0000000002, 'actuacio5', 'mal d-esquena', 8);

insert into lesions (codi_casteller, nom_actuacio, tipus_lesio, grau)
values (0000000003, 'actuacio4', 'cop al cap', 10);

-- ***********************************************************************************

insert into puntuacions(amplada,alcada,n_reforcos,per_sota,data_p,data_fi,punts_carregat,punts_descarregat)
values(4,9,0,"n","1993-12-25", null,10,15);

insert into puntuacions(amplada,alcada,n_reforcos,per_sota,data_p,data_fi,punts_carregat,punts_descarregat)
values(4,8,0,"n","1993-12-25", null,10,15);

insert into puntuacions(amplada,alcada,n_reforcos,per_sota,data_p,data_fi,punts_carregat,punts_descarregat)
values(3,9,0,"n","1993-12-25", null,10,15);

insert into puntuacions(amplada,alcada,n_reforcos,per_sota,data_p,data_fi,punts_carregat,punts_descarregat)
values(4,7,0,"n","1993-12-25", null,10,15);

-- ***********************************************************************************

-- Consulta 2
select distinct nom, any_fund 
from intenta join colles 
on colla = nom 
where amplada=4 
and alcada=9 
and carregat='s'
order by any_fund asc;

-- Consulta 3
select c.nom, cas.nom, cas.cognom1, cas.cognom2
from colles c , castellers cas
where c.cap_de_colla = cas.codi
and c.nom not in (select colla
					from inscrita);
					
-- Consulta 4			
select c.nom 
from colles c, inscrita i
where c.nom = i.colla
group by c.nom
having count(*) = (select count(*) from concursos)
order by c.nom asc;

-- consulta 5
select codi, nom
from castellers c, lesions l
where l.codi_casteller = c.codi
group by c.codi, c.nom
having count(*) > 1;

-- Consulta 6
select i.colla, count(*) "vegades"
from intenta i
where i.alcada = 9
and i.amplada = 4
and i.descarregat = 's'
group by i.colla
order by vegades desc
limit 1;

-- Accio 9
START TRANSACTION;
-- select alcada,punts_carregat,punts_descarregat,data_p
-- from puntuacions;

update puntuacions
set punts_carregat=punts_carregat+7, punts_descarregat=punts_descarregat+7, data_p=current_date()
where alcada=8
and data_fi is null;

-- select alcada,punts_carregat,punts_descarregat,data_p
-- from puntuacions;

update puntuacions
set punts_carregat=punts_carregat+10, punts_descarregat=punts_descarregat+10, data_p=current_date()
where alcada=9
and data_fi is null;

-- select alcada,punts_carregat,punts_descarregat,data_p
-- from puntuacions;
COMMIT;

-- Accio 10
-- select colla,concurs , amplada, alcada, n_reforcos, per_sota,carregat
-- from intenta;

delete from intenta 
where carregat = 'n';

-- select colla,concurs , amplada, alcada, n_reforcos, per_sota,carregat
-- from intenta;

-- Vista 11
create view castellers_actius as 
select c.nom "Nom casteller", c.pes, c.alcada, co.nom, co.color_camisa
from castellers c, pertanyenca p , colles co
where p.data_baixa is null
and p.casteller=c.codi
and p.colla=co.nom;

select * from castellers_actius;