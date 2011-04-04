/*
script generated by aqua data studio 9.0.1 on jan-30-2011 10:11:33 pm
database: vars
schema: dbo
objects: table, view, index
*/
alter table videoframe
	drop constraint videoframe_fk1
;
alter table videoarchive
	drop constraint videoarchive_fk1
;
alter table physicaldata
	drop constraint physicaldata_fk1
;
alter table observation
	drop constraint observation_fk1
;
alter table cameraplatformdeployment
	drop constraint cameradeployment_fk1
;
alter table cameradata
	drop constraint cameradata_fk1
;
alter table association
	drop constraint association_fk1
;
alter table videoarchive
	drop constraint uc_videoarchivename
;
alter table physicaldata
	drop constraint uc_physicaldata_fk1
;
alter table cameradata
	drop constraint uc_videoframeid_fk
;
drop index videoframe.idx_videoframe_lut
;
drop index videoframe.idx_videoframe_fk1
;
drop index videoarchive.idx_videoarchive_name
;
drop index videoarchive.idx_videoarchive_id
;
drop index videoarchive.idx_videoarchive_lut
;
drop index videoarchive.idx_videoarchive_fk1
;
drop index videoarchiveset.idx_videoarchiveset_id
;
drop index videoarchiveset.idx_videoarchiveset_lut
;
drop index physicaldata.idx_physicaldata_lut
;
drop index physicaldata.idx_physicaldata_fk1
;
drop index observation.idx_observation_lut
;
drop index observation.idx_observation_fk1
;
drop index observation.idx_observation_conceptname
;
drop index cameraplatformdeployment.idx_cameraplatformdeployment_lut
;
drop index cameraplatformdeployment.idx_cameradeployment_fk1
;
drop index cameradata.idx_cameradata_lut
;
drop index cameradata.idx_cameradata_fk1
;
drop index association.idx_association_toconcept
;
drop index association.idx_association_lut
;
drop index association.idx_association_fk1
;
drop view annotations
;
drop table videoframe
;
drop table videoarchiveset
;
drop table videoarchive
;
drop table uniqueid
;
drop table physicaldata
;
drop table observation
;
drop table expdmergestatus
;
drop table cameraplatformdeployment
;
drop table cameradata
;
drop table association
;

create table association  ( 
	id               	int8 not null,
	observationid_fk 	int8 null,
	linkname         	varchar(50) null,
	toconcept        	varchar(50) null,
	linkvalue        	varchar(100) null,
	last_updated_time	timestamp null,
	constraint association_pk primary key (id)
)
;
create table cameradata  ( 
	id               	int8 not null,
	videoframeid_fk  	int8 null,
	name             	varchar(50) null,
	direction        	varchar(50) null,
	zoom             	int null,
	focus            	int null,
	iris             	int null,
	fieldwidth       	float null,
	stillimageurl    	varchar(1024) null,
	logdtg           	timestamp null,
	last_updated_time	timestamp null,
	x                	float null,
	y                	float null,
	z                	float null,
	pitch            	float null,
	roll             	float null,
	xyunits          	varchar(50) null,
	zunits           	varchar(50) null,
	heading          	float null,
	viewheight       	float null,
	viewwidth        	float null,
	viewunits        	varchar(50) null,
	constraint cameradata_pk primary key(id)

	)
;
create table cameraplatformdeployment  ( 
	id                  	int8 not null,
	videoarchivesetid_fk	int8 null,
	seqnumber           	int null,
	chiefscientist      	varchar(50) null,
	usagestartdtg       	timestamp null,
	usageenddtg         	timestamp null,
	last_updated_time   	timestamp null,
	constraint cameradeployment_pk primary key (id)
)
;
create table expdmergestatus  ( 
	videoarchivesetid_fk	int8 not null,
	mergedate           	timestamp not null,
	isnavigationedited  	smallint not null,
	statusmessage       	varchar(512) not null,
	videoframecount     	int not null,
	ismerged            	smallint not null,
	datesource          	varchar(4) not null,
	constraint expdmergestatus_pk primary key(videoarchivesetid_fk)
)
;
create table observation  ( 
	id               	int8 not null,
	videoframeid_fk  	int8 null,
	observationdtg   	timestamp null,
	observer         	varchar(50) null,
	conceptname      	varchar(50) null,
	notes            	varchar(200) null,
	last_updated_time	timestamp null,
	x                	float null,
	y                	float null,
	constraint observation_pk primary key(id)
)
;
create table physicaldata  ( 
	id               	int8 not null,
	videoframeid_fk  	int8 null,
	depth            	real null,
	temperature      	real null,
	salinity         	real null,
	oxygen           	real null,
	light            	real null,
	latitude         	float8 null,
	longitude        	float8 null,
	logdtg           	timestamp null,
	last_updated_time	timestamp null,
	constraint physicaldata_pk primary key (id)
)
;
create table uniqueid  ( 
	tablename	varchar(200) not null,
	nextid   	int8 null 
	)
;
create table videoarchive  ( 
	id                  	int8 not null,
	videoarchivesetid_fk	int8 null,
	tapenumber          	smallint null,
	starttimecode       	varchar(11) null,
	videoarchivename    	varchar(1024) null,
	last_updated_time   	timestamp null,
	constraint videoarchive_pk primary key(id)
)
;
create table videoarchiveset  ( 
	id               	int8 not null,
	trackingnumber   	varchar(7) null,
	shipname         	varchar(32) null,
	platformname     	varchar(32) null,
	formatcode       	varchar(2) null,
	startdtg         	timestamp null,
	enddtg           	timestamp null,
	last_updated_time	timestamp null,
	constraint videoarchiveset_pk primary key(id)
)
;
create table videoframe  ( 
	id               	int8 not null,
	recordeddtg      	timestamp null,
	tapetimecode     	varchar(11) null,
	hdtimecode       	varchar(11) null,
	insequence       	bit null,
	displacer        	varchar(50) null,
	displacedtg      	timestamp null,
	videoarchiveid_fk	int8 null,
	last_updated_time	timestamp null,
	constraint videoframe_pk primary key(id)
)
;
create view annotations
as 
select  
    obs.observationdtg as observationdate,
    obs.observer,
    obs.conceptname,
    obs.notes,
    obs.x as xpixelinimage,
    obs.y as ypixelinimage,
    vf.tapetimecode,
    vf.recordeddtg as recordeddate ,
    va.videoarchivename,
    vas.trackingnumber,
    vas.shipname,
    vas.platformname as rovname,
    vas.formatcode as annotationmode,
    cpd.seqnumber as divenumber,
    cpd.chiefscientist,
    cd.name as cameraname,
    cd.direction as cameradirection,
    cd.zoom,
    cd.focus,
    cd.iris,
    cd.fieldwidth,
    cd.stillimageurl as image,
    cd.x as camerax,
    cd.y as cameray,
    cd.z as cameraz,
    cd.pitch as camerapitchradians,
    cd.roll as camerarollradians,
    cd.heading as cameraheadingradians,
    cd.xyunits as cameraxyunits,
    cd.zunits as camerazunits,
    cd.viewwidth as cameraviewwidth,
    cd.viewheight as cameraviewheight,
    cd.viewunits as cameraviewunits,
    pd.depth,
    pd.temperature,
    pd.salinity,
    pd.oxygen,
    pd.light,
    pd.latitude,
    pd.longitude,
    obs.id as observationid_fk,
    ass.id as associationid_fk,
    ass.linkname,
    ass.toconcept,
    ass.linkvalue,
    ass.linkname  || ' | ' || ass.toconcept || ' | ' || ass.linkvalue  as associations,
    vf.hdtimecode as highdeftimecode,
    expd.isnavigationedited,
    vf.id as videoframeid_fk,
    pd.id as physicaldataid_fk,
    cd.id as cameradataid_fk,
    va.id as videoarchiveid_fk,
    vas.id as videoarchivesetid_fk 
from 
    videoarchiveset vas 
        left outer join cameraplatformdeployment cpd 
        on cpd.videoarchivesetid_fk = vas.id 
        left outer join videoarchive va 
        on vas.id = va.videoarchivesetid_fk 
        left outer join videoframe vf 
        on va.id = vf.videoarchiveid_fk 
        left outer join cameradata cd 
        on cd.videoframeid_fk = vf.id 
        left outer join physicaldata pd 
        on pd.videoframeid_fk = vf.id 
        left outer join observation obs 
        on obs.videoframeid_fk = vf.id 
        left outer join association ass 
        on ass.observationid_fk = obs.id 
        left outer join expdmergestatus expd 
        on expd.videoarchivesetid_fk = vas.id
;
create index idx_association_fk1
	on association(observationid_fk)
;
create index idx_association_lut
	on association(last_updated_time)
;
create index idx_association_toconcept
	on association(toconcept)
;
create index idx_cameradata_fk1
	on cameradata(videoframeid_fk)
;
create index idx_cameradata_lut
	on cameradata(last_updated_time)
;
create index idx_cameradeployment_fk1
	on cameraplatformdeployment(videoarchivesetid_fk)
;
create index idx_cameraplatformdeployment_lut
	on cameraplatformdeployment(last_updated_time)
;
create index idx_observation_conceptname
	on observation(conceptname)
;
create index idx_observation_fk1
	on observation(videoframeid_fk)
;
create index idx_observation_lut
	on observation(last_updated_time)
;
create index idx_physicaldata_fk1
	on physicaldata(videoframeid_fk)
;
create index idx_physicaldata_lut
	on physicaldata(last_updated_time)
;
create index idx_videoarchiveset_lut
	on videoarchiveset(last_updated_time)
;
create unique index idx_videoarchiveset_id
	on videoarchiveset(id)
;
create index idx_videoarchive_fk1
	on videoarchive(videoarchivesetid_fk)
;
create index idx_videoarchive_lut
	on videoarchive(last_updated_time)
;
create unique index idx_videoarchive_id
	on videoarchive(id)
;
create index idx_videoarchive_name
	on videoarchive(videoarchivename)
;
create index idx_videoframe_fk1
	on videoframe(videoarchiveid_fk)
;
create index idx_videoframe_lut
	on videoframe(last_updated_time)
;

alter table cameradata
	add constraint uc_videoframeid_fk
	unique (videoframeid_fk)
;
alter table physicaldata
	add constraint uc_physicaldata_fk1
	unique (videoframeid_fk)
;
cluster uc_physicaldata_fk1 on physicaldata;

alter table videoarchive
	add constraint uc_videoarchivename
	unique (videoarchivename)
;
alter table association
	add constraint association_fk1
	foreign key(observationid_fk)
	references observation(id)
	on delete no action 
	on update no action 
--        disable replica rule	
;
alter table cameradata
	add constraint cameradata_fk1
	foreign key(videoframeid_fk)
	references videoframe(id)
	on delete no action 
	on update no action 
--	not for replication 
;
alter table cameraplatformdeployment
	add constraint cameradeployment_fk1
	foreign key(videoarchivesetid_fk)
	references videoarchiveset(id)
	on delete no action 
	on update no action 
;
alter table observation
	add constraint observation_fk1
	foreign key(videoframeid_fk)
	references videoframe(id)
	on delete no action 
	on update no action 
--	not for replication 
;
alter table physicaldata
	add constraint physicaldata_fk1
	foreign key(videoframeid_fk)
	references videoframe(id)
	on delete no action 
	on update no action 
--	not for replication 
;
alter table videoarchive
	add constraint videoarchive_fk1
	foreign key(videoarchivesetid_fk)
	references videoarchiveset(id)
	on delete no action 
	on update no action 
--	not for replication 
;
alter table videoframe
	add constraint videoframe_fk1
	foreign key(videoarchiveid_fk)
	references videoarchive(id)
	on delete no action 
	on update no action 
--	not for replication 
;

