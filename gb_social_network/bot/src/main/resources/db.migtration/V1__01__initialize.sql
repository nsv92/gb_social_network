
create table if not exists bot_db.bot_users
(
    chat_id      bigint       not null
        constraint pk_bot_users
            primary key,
    tg_username  varchar(255) not null
        unique,
    app_nickname varchar(255) not null
        unique,
    subscription boolean          not null
);
