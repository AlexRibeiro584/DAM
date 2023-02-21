drop table jokes_flags cascade;
drop table jokes cascade;
drop table categories cascade;
drop table flags cascade;
drop table joketypes cascade;
drop table languages cascade;
CREATE TABLE LANGUAGES(
	language_code numeric primary key
);
CREATE TABLE CATEGORIES(
	category_name varchar(15) primary key
);
CREATE TABLE FLAGS(
	id_flag varchar(15) primary key
);
CREATE TABLE JOKETYPES(
	typename varchar(7) primary key
);
CREATE TABLE JOKES(
	id_joke numeric,
	language_joke numeric,
	category varchar(15), 
	type_joke varchar(7), 
	joke varchar(999),
	setup varchar(999), 
	delivery varchar(999),
	constraint pk_jokes primary key(id_joke, language_joke),
	constraint fk_lg foreign key(language_joke) references LANGUAGES(language_code),
	constraint fk_ct foreign key(category) references CATEGORIES(category_name),
	constraint fk_ty foreign key(type_joke) references JOKETYPES(typename)
);
CREATE TABLE JOKES_FLAGS(
	jf_id numeric,
	jf_lg numeric,
	jf_fl varchar(15),
	constraint pk_jf primary key (jf_id,jf_lg,jf_fl),
	constraint fk_jfid foreign key(jf_id, jf_lg) references JOKES(id_joke, language_joke),
	constraint fk_jffl foreign key(jf_fl) references FLAGS(id_flag)
);

CREATE OR REPLACE FUNCTION BuscarChiste (texto VARCHAR)
RETURNS SETOF jokes AS
$$
DECLARE
    fila jokes%rowtype;
BEGIN
    FOR fila IN SELECT * FROM jokes WHERE 
	joke LIKE LOWER('%'||texto||'%') OR 
	setup LIKE LOWER('%'||texto||'%') OR 
	delivery LIKE LOWER('%'||texto||'%') LOOP
        RETURN NEXT fila;
    END LOOP;
END;
$$
LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION ChistesLimpios ()
RETURNS SETOF jokes AS
$$
DECLARE
    fila jokes%rowtype;
BEGIN
    FOR fila IN SELECT * FROM jokes j WHERE 
	id_joke not in (select jf_id from jokes_flags jf
					where j.id_joke = jf.jf_id 
				   and j.language_joke = jf.jf_lg) LOOP
        RETURN NEXT fila;
    END LOOP;
END;
$$
LANGUAGE PLPGSQL;