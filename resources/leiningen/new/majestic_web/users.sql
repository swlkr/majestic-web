-- name: insert<!
insert into users (id, email, password)
values (:id, :email, :password)

-- name: find-by-id
select *
from users
where id = :id

-- name: find-by-email
select *
from users
where email = :email

-- name: update<!
update users
set email = :email, password = :password
where id = :id

-- name: delete<!
delete from users
where id = :id
