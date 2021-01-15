create table ordem_servico
(
	id serial,
	descricao text not null,
	preco decimal(10,2) not null,
	status varchar(20) not null,
	data_abertura timestamp not null,
	data_finalizacao timestamp,
	cliente_id bigint not null,
	
	primary key (id)
);

alter table ordem_servico add constraint fk_ordem_servico_cliente
foreign key (cliente_id) references cliente (id);