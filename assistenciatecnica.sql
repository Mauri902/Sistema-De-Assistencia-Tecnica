create database assistencia
default character set utf8
default collate utf8_general_ci;
 
use assistencia; 
 
create table assistencia(
id int not null auto_increment primary key,
nome_cliente varchar(30) not null,
telefone varchar(20),
equipamento varchar(100) not null,
defeito text not null,
descricao_servico text,
valor decimal (7,2),
foto_equipamento longblob,

status enum ('Aguardando atendimento', 'Em andamento', 'Concluído'),
data_entrada datetime default current_timestamp
)default charset = utf8;



