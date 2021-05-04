create table hibernate_sequence (next_val bigint) engine=MyISAM
insert into hibernate_sequence values ( 1 )
create table plasma_donor (id bigint not null, age integer not null, blood_group varchar(255), city varchar(255), covid_negative datetime, covid_postive datetime, email varchar(255), first_name varchar(255), gender varchar(255), hospital_address varchar(255), hospital_name varchar(255), last_name varchar(255), phone_number varchar(255), state varchar(255), primary key (id)) engine=MyISAM
