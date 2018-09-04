create database db_serralheria;

use db_serralheria;

create table tbl_cliente(
cliente_id int(10) auto_increment,
cliente_nome varchar (70) not null,
cliente_sobrenome varchar (70) not null,
cliente_cpf varchar (14),
cliente_rg varchar(15) ,
cliente_numero varchar(10) not null,
cliente_cep varchar (70) ,
cliente_endereco varchar (65) not null,
cliente_bairro varchar (65) not null,
cliente_complemento varchar (65),
cliente_telefone1 varchar (30) ,
cliente_telefone2 varchar (30),
cliente_datanasc date ,
cliente_email varchar (30) ,
primary key(cliente_id)
);

create table tbl_usuario(
usuario_id int (10) not null auto_increment,
usuario_nome varchar (65) not null,
usuario_rg varchar (30) not null,
usuario_cpf varchar (30) not null,
usuario_endereco varchar (65) not null,
usuario_bairro varchar (65) not null,
usuario_complemento varchar (65),
usuario_numero varchar(70),
usuario_telefone1 varchar (30) not null,
usuario_telefone2 varchar (30),
usuario_email varchar (30) not null,
usuario_salario int (10) not null,
usuario_dataAdmissao date,
primary key (usuario_id)
);

create table tbl_login(
login_id int(10) not null,
login_usuario varchar(30) not null,
login_senha varchar(30) not null,
login_email varchar(50) not null,
primary key(login_id)
);

 -- não exclua, o programa nescessita desse registro pra funcionar
insert into tbl_login(login_id, login_usuario, login_senha, login_email) values (2, '1', '1', '');

create table tbl_categoriamaterial(
categoriamaterial_id int(10) auto_increment,
categoriamaterial_nome varchar(60),
primary key(categoriamaterial_id)
);

create table tbl_material(
material_id int(10) not null auto_increment,
material_custo double not null,
material_nome varchar(60),
material_descricao varchar(60),
categoriamaterial_id int references tbl_categoriamaterial(categoriamaterial_id),
material_observacao varchar(100),
primary key(material_id)
);

insert into tbl_material(material_custo, material_nome, material_descricao, material_observacao) values ('0,50', 'Parafuso', 'Parafuso para portão', 'null');

create table tbl_categoriamodelo(
categoriamodelo_id int(10) auto_increment,
categoriamodelo_nome varchar(60),
primary key(categoriamodelo_id)
);

 -- não exclua, o programa nescessita desse registro pra funcionar
insert into tbl_categoriamaterial set categoriamaterial_nome ='Nenhuma';
 -- não exclua, o programa nescessita desse registro pra funcionar
insert into tbl_categoriamodelo set categoriamodelo_nome ='Nenhuma';

create table tbl_modelo(
modelo_id int(10) not null auto_increment,
modelo_nome varchar(30) not null,
modelo_listaMaterial text not null,
modelo_listaqtd text not null,
modelo_listaCusto text not null,
modelo_custo double not null,
modelo_descricao varchar(100),
categoriamodelo_id int references tbl_categoriamodelo(categoriamodelo_id),
primary key(modelo_id)
);


select * from tbl_modelo;

create table tbl_visita (
visita_id int(10) not null auto_increment,
visita_data date not null,
visita_status int(5) not null,
visita_horario varchar(30),
visita_bairro varchar (60) not null,
visita_numero varchar (10) not null,
visita_nome varchar (70) not null,
visita_endereco varchar (65) not null,
visita_telefone varchar (30) not null,
visita_observacao varchar(300),
primary key(visita_id)
);

create table tbl_orcamento(
orcamento_id int(10) not null auto_increment,
orcamento_valorTotal double not null,
orcamento_datagerado date,
orcamento_metodoPagamento varchar(30) not null,	
orcamento_valorMaoDeObra double not null,
orcamento_totalPrazo double not null,
orcamento_juros double not null,
orcamento_parcelamento double not null,
orcamento_largura double not null,
orcamento_altura double not null,
orcamento_area double not null,
orcamento_precopormetroquadrado double not null,
orcamento_descricaoproduto text,
orcamento_detalesadicionais text,
orcamento_listaqtd text not null,
orcamento_listaMaterial text not null,
orcamento_descricaopedido text,
modelo_id int(10),
visita_id int(10),
cliente_id int(10),
primary key(orcamento_id),
foreign key(cliente_id) references tbl_cliente(cliente_id),
foreign key(modelo_id) references tbl_modelo(modelo_id),
foreign key(visita_id) references tbl_visita(visita_id)
);

select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal 
							from tbl_orcamento where orcamento_id =  7 and 	orcamento_valorTotal >= 1 and orcamento_descricaoproduto like '%';

select * from tbl_orcamento where orcamento_id >= orcamento_id;

create table tbl_pedido(
pedido_id int(10) not null auto_increment,
pedido_dataentrega date,
pedido_gerado date,
pedido_nome text,
cliente_id int(10) not null,
orcamento_id int(10),
visita_id int (10) not null,
pedido_nivel int (10) not null,
pedido_historico text not null,
pedido_materiaisencomendados varchar(10),
pedido_diaencomendamateriais date,
primary key (pedido_id),
foreign key(cliente_id) references tbl_cliente(cliente_id),
foreign key(visita_id) references tbl_visita(visita_id),
foreign key(orcamento_id) references tbl_orcamento(orcamento_id)
);

create table tbl_amostra(
amostra_id int(10) not null auto_increment,
amostra_descricao varchar (60) not null,
amostra_preco int(10),
amostra_imagem varchar(150) not null,
modelo_id int (10) not null,
primary key(amostra_id),
foreign key(modelo_id) references tbl_modelo(modelo_id)
);

create table tbl_pagamento(
pagamento_id int(10) not null auto_increment,
pagamento_forma varchar(60) not null,
pagamento_valor double not null,
pagamento_parcelamento double not null,
pagamento_parcelasRestantes int,
pagamento_status varchar (10) not null,
pagamento_valorPago double not null,
pagamento_valorPendente double not null,
pagamento_valorPrazo double,
pagamento_valorJuros double,
pedido_id int(10) not null,
primary key(pagamento_id),
foreign key(pedido_id) references tbl_pedido(pedido_id)
);

create table tbl_instalacao(
instalacao_id int(10) not null auto_increment,
instalacao_dia date not null,
instalacao_horario varchar (30) not null, 
instalacao_observacao varchar (100) not null,
cliente_id int(10),
pedido_id int (10),
primary key(instalacao_id),
foreign key(cliente_id) references tbl_cliente(cliente_id),
foreign key(pedido_id) references tbl_pedido(pedido_id)
);




