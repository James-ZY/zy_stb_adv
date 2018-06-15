CREATE DATABASE IF NOT EXISTS adv DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

use mysql;

insert into mysql.user (Host,User,authentication_string,ssl_cipher,x509_issuer,x509_subject) values("%","adv",password("adv"),'','','');

update user set authentication_string=password('48STX2X') where user='root';

flush privileges;

grant all privileges on *.* to 'root'@'%' identified by '48STX2X';
grant all privileges on *.* to 'root'@'localhost' identified by '48STX2X';
grant all privileges on *.* to 'root'@'127.0.0.1' identified by '48STX2X';

grant all privileges on adv.* to 'adv'@'%' identified by 'adv';
grant all privileges on adv.* to 'adv'@'localhost' identified by 'adv';
grant all privileges on adv.* to 'adv'@'127.0.0.1' identified by 'adv';


flush privileges;
