-- :name create-records-table :! :raw
create table records
(
    id         integer auto_increment primary key,
    first_name varchar(40),
    last_name  varchar(40),
    email varchar (120),
    favorite_color varchar(20),
    date_of_birth date
)

-- :name insert-record :! :n
insert into records (first_name, last_name, email, favorite_color, date_of_birth)
values (:first-name, :last-name, :email, :favorite-color, :date-of-birth)

-- :name get-records :? :*
select * from records