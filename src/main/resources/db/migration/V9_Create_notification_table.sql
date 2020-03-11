create table notification
(
	id BIGINT auto_increment,
	notifier BIGINT not null,
	receiver BIGINT not null,
	outerId int not null,
	type int not null,
	gmt_create BIGINT not null,
	status int default 0 not null,
	constraint notification_pk
		primary key (id)
);


alter table NOTIFICATION alter column OUTERID BIGINT not null;
