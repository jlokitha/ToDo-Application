DROP DATABASE IF EXISTS toDo;
CREATE DATABASE IF NOT EXISTS toDo;

USE toDo;

create table users(
                         email varchar(35) primary key ,
                         name varchar(155) not null,
                         password text not null
);

create table tasks(
                         task_id INT primary key ,
                         email varchar(35),
                         description text not null,
                         dueDate DATE NOT NULL,
                         isCompleted tinyint not null,
                         constraint foreign key (email) references users(email) on delete cascade on update cascade
);