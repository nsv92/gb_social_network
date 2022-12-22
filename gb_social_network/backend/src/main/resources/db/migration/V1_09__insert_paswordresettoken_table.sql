create table passwordresettoken (
    id bigint generated by default as identity primary key not null,
    token character varying not null,
    user_id bigint,
    expiry_date timestamp,
    foreign key (user_id) references public.users (id)
    match simple on update no action on delete no action
);

