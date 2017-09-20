 --
--clean start
-----
DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS RELATED_PROTEINS
   (
     REL_PROT_INTERNAL_ID NUMBER(30,0) NOT NULL ,
     NAME_PREFIX          VARCHAR2(30 ),
     PROTEIN_NAME VARCHAR2(4000 ), 
     CONSTRAINT PK_REL_PROT_ID PRIMARY KEY (REL_PROT_INTERNAL_ID) 
  );


-----------------------
-- DDL for PROTEIN_GROUPS
-------------------
CREATE TABLE IF NOT EXISTS PROTEIN_GROUPS (
PROTEIN_NAME VARCHAR2(4000), 
PROTEIN_GROUP_ID VARCHAR2(10) NOT NULL, 
ENTRY_TYPE NUMBER,
CONSTRAINT PK_PROTEIN_GROUP_ID PRIMARY KEY (PROTEIN_GROUP_ID)
);

--------------------------
--DDL FOR PRIMARY_PROTEIN
----------------------------
  CREATE TABLE IF NOT EXISTS PRIMARY_PROTEIN
   (	PROTEIN_GROUP_ID VARCHAR2(10), 
	ACCESSION VARCHAR2(15), 
	TAX_ID NUMBER(15,0), 
	COMMON_NAME VARCHAR2(255), 
	SCIENTIFIC_NAME VARCHAR2(255), 
	PRIORITY_CODE VARCHAR2(3),
	PDB_FLAG CHAR(1) DEFAULT 'N', 
	PDB_ID VARCHAR2(10),

 CONSTRAINT PRIMARY_PROTEIN_FK1 FOREIGN KEY (PROTEIN_GROUP_ID) REFERENCES PROTEIN_GROUPS (PROTEIN_GROUP_ID)
   );
  
--------------------------
--DDL FOR UNIPROT_ENTRY
----------------------------
CREATE TABLE IF NOT EXISTS UNIPROT_ENTRY
  (
    DBENTRY_ID      NUMBER(15,0) NOT NULL,
    ACCESSION       VARCHAR2(15) NOT NULL ,
    NAME            VARCHAR2(30),
    TAX_ID          NUMBER(15,0),
    PROTEIN_NAME    VARCHAR2(4000),
    SCIENTIFIC_NAME VARCHAR2(255),
    COMMON_NAME     VARCHAR2(255),
    SYNONYM_NAMES CLOB,
    SEQUENCE_LENGTH     NUMBER(6,0),
    RELATED_PROTEINS_ID NUMBER,
    LAST_UPDATE_TIMESTAMP DATE,
    FUNCTION   VARCHAR2(4000 ),
    ENTRY_TYPE NUMBER(1,0),
    FUNCTION_LENGTH NUMBER, 
    EXP_EVIDENCE_FLAG NUMBER, 
    PROTEIN_GROUP_ID VARCHAR2(10), 
    REFERENCE_PROTEOME NUMBER, 
    CONSTRAINT PK_UNIPROT_ACCESSION PRIMARY KEY (ACCESSION),
    CONSTRAINT FK_ENTRY_REL_PROT_PREFIX FOREIGN KEY (RELATED_PROTEINS_ID) REFERENCES RELATED_PROTEINS (REL_PROT_INTERNAL_ID),
    CONSTRAINT FK_PROTEIN_GROUP_ID FOREIGN KEY (PROTEIN_GROUP_ID) REFERENCES PROTEIN_GROUPS (PROTEIN_GROUP_ID)
  );
 
  
  CREATE TABLE IF NOT EXISTS UNIPROT_XREF
  (
    XREF_ID     NUMBER NOT NULL ,
    ACCESSION   VARCHAR2(15 ) NOT NULL ,
    DBENTRY_ID  NUMBER(15,0) NOT NULL ,
    SOURCE_ID   VARCHAR2(60 ),
    SOURCE      VARCHAR2(8 ),
    SOURCE_NAME VARCHAR2(4000 ),
    CONSTRAINT PK_UNIPROT_XREF_ID PRIMARY KEY (XREF_ID)  ,
    CONSTRAINT FK_XREF_ACCESION FOREIGN KEY (ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  
  CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_SUMMARY
    (
      ENZYME_ID         NUMBER NOT NULL ,
      UNIPROT_ACCESSION VARCHAR2(15 ) NOT NULL ,
      DBENTRY_ID        NUMBER(15,0) NOT NULL ,
      COMMENT_TYPE      VARCHAR2(30 ),
      COMMENT_TEXT      VARCHAR2(4000 ),
      CONSTRAINT PK_ENZYME_ID PRIMARY KEY (ENZYME_ID)  ,
      CONSTRAINT FK_ENZYME_ACCESSION FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );

  CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_REACTION
    (
      REACTION_INTERNAL_ID NUMBER,
      REACTION_ID          VARCHAR2(255 ),
      REACTION_NAME        VARCHAR2(4000 ),
      REACTION_SOURCE      VARCHAR2(30 ),
      RELATIONSHIP         VARCHAR2(30 ),
      UNIPROT_ACCESSION    VARCHAR2(15 ),
      URL                  VARCHAR2(255 ),
      CONSTRAINT REACTION_PK PRIMARY KEY (REACTION_INTERNAL_ID) ,
      CONSTRAINT REACTION_ACCESSION_FK FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  
  CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_EC_NUMBERS
    (
      EC_INTERNAL_ID    NUMBER NOT NULL ,
      UNIPROT_ACCESSION VARCHAR2(15 ),
      EC_NUMBER         VARCHAR2(15 ),
      "EC_FAMILY"       NUMBER(1,0),
      CONSTRAINT PK_EC_ID PRIMARY KEY (EC_INTERNAL_ID)  ,
      CONSTRAINT FK_EC_ACCESSION FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  
 CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_NAMES
  (
    DBENTRY_ID        NUMBER(15,0) NOT NULL ,
    UNIPROT_ACCESSION VARCHAR2(15 ) NOT NULL ,
    DESCRIPTION_TYPE  VARCHAR2(7 ) NOT NULL ,
    CATEGORY_TYPE     VARCHAR2(7 ) NOT NULL ,
    NAME              VARCHAR2(4000 ) NOT NULL ,
    ENZYME_NAME_ID    NUMBER,
    CONSTRAINT FK_ENZYME_NAMES_ACC FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  
  CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_COMPOUND
    (
      COMPOUND_INTERNAL_ID NUMBER NOT NULL ,
      COMPOUND_ID          VARCHAR2(30 ),
      COMPOUND_NAME        VARCHAR2(4000 ),
      COMPOUND_SOURCE      VARCHAR2(30 ),
      RELATIONSHIP         VARCHAR2(30 ),
      UNIPROT_ACCESSION    VARCHAR2(15 ),
      URL                  VARCHAR2(255 ),
      COMPOUND_ROLE        VARCHAR2(30 ),
      CONSTRAINT COMPOUND_PK PRIMARY KEY (COMPOUND_INTERNAL_ID)  ,
      CONSTRAINT ENZYME_COMPOUND_FK FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_DISEASE
    (
      DISEASE_ID        NUMBER NOT NULL ,
      UNIPROT_ACCESSION VARCHAR2(15 ) NOT NULL ,
      OMIM_NUMBER       VARCHAR2(30 ),
      MESH_ID           VARCHAR2(30 ),
      EFO_ID            VARCHAR2(30 ),
      DISEASE_NAME      VARCHAR2(150 ),
      EVIDENCE          VARCHAR2(4000 ),
      DEFINITION        VARCHAR2(4000 ),
      SCORE             VARCHAR2(150 ),
      URL               VARCHAR2(255 ),
      CONSTRAINT PK_DISEASE_ID PRIMARY KEY (DISEASE_ID)  ,
      CONSTRAINT FK_DISEASE_ACCESION FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );


CREATE TABLE IF NOT EXISTS ENZYME_PORTAL_PATHWAYS
  (
    PATHWAY_INTERNAL_ID NUMBER NOT NULL ,
    UNIPROT_ACCESSION   VARCHAR2(15 ),
    PATHWAY_ID          VARCHAR2(15 ),
    PATHWAY_URL         VARCHAR2(255 ),
    PATHWAY_NAME        VARCHAR2(4000 ),
    STATUS              VARCHAR2(5 ),
    SPECIES             VARCHAR2(255 ),
    CONSTRAINT PATHWAY_PK PRIMARY KEY (PATHWAY_INTERNAL_ID)  ,
    CONSTRAINT FK_PATHWAYS_ACCESSION FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION) 
  );
  
  
 
  

CREATE TABLE IF NOT EXISTS COMPOUND_TO_REACTION
  (
    COMPOUND_INTERNAL_ID NUMBER,
    REACTION_INTERNAL_ID NUMBER,
    CONSTRAINT PK_COMPOUND_REACTION PRIMARY KEY (REACTION_INTERNAL_ID, COMPOUND_INTERNAL_ID) ,
    CONSTRAINT FK_COMPOUND_INTERNAL_D FOREIGN KEY (COMPOUND_INTERNAL_ID) REFERENCES ENZYME_PORTAL_COMPOUND (COMPOUND_INTERNAL_ID) ,
    CONSTRAINT FK_REACTION_INTERNAL_ID FOREIGN KEY (REACTION_INTERNAL_ID) REFERENCES ENZYME_PORTAL_REACTION (REACTION_INTERNAL_ID) 
  );


--  DDL for Table ENZYME_CATALYTIC_ACTIVITY
--------------------------------------------------------

  CREATE TABLE IF NOT EXISTS ENZYME_CATALYTIC_ACTIVITY (
ACTIVITY_INTERNAL_ID NUMBER, 
CATALYTIC_ACTIVITY VARCHAR2(4000), 
UNIPROT_ACCESSION VARCHAR2(15),
CONSTRAINT ENZYME_CATALYTIC_ACTIVITY_PK PRIMARY KEY (ACTIVITY_INTERNAL_ID),
CONSTRAINT ENZYME_CATALYTIC_ACTIVITY_FK1 FOREIGN KEY (UNIPROT_ACCESSION) REFERENCES UNIPROT_ENTRY (ACCESSION)
); 

--------------------------------------------------------
--  DDL for Table ENZYMES_TO_TAXONOMY
--------------------------------------------------------
  CREATE TABLE IF NOT EXISTS ENZYMES_TO_TAXONOMY 
   (	INTERNAL_ID NUMBER, 
	EC_NUMBER VARCHAR2(15), 
	TAX_ID NUMBER, 
	SCIENTIFIC_NAME VARCHAR2(255), 
	COMMON_NAME VARCHAR2(255), 
	 CONSTRAINT PK_ENZYME_TX_ID PRIMARY KEY (INTERNAL_ID)
   );

--------------------------------------------------------
--  DDL for Table INTENZ_ENZYMES
--------------------------------------------------------

 CREATE TABLE IF NOT EXISTS INTENZ_ENZYMES 
   (	INTERNAL_ID NUMBER, 
	EC_NUMBER VARCHAR2(15), 
	ENZYME_NAME VARCHAR2(300), 
	CATALYTIC_ACTIVITY VARCHAR2(4000), 
	TRANSFER_FLAG CHAR(1), 
	 CONSTRAINT PK_INTENZ_ENZYMES PRIMARY KEY (EC_NUMBER)
   );
