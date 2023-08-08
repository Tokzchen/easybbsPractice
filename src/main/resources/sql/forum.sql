use youfa;
drop table article_likes;
drop table article_comment_likes;
drop table  article_second_comment_likes;
drop table forum_article_second_comment;
drop table forum_article_comment;
drop table forum_article;
create table forum_article
(
    id          bigint primary key,
    uid         bigint,
    title       varchar(200),
    content     text,
    category    varchar(200),
    visited     bigint default 0,
    `like`      bigint default 0,
    top         bigint,
    good        bigint,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_fa_uid_user foreign key (uid) references user_info (user_id)
);


create index idx_article_like on forum_article (`like`);

create table forum_article_comment
(
    id          bigint primary key,
    uid         bigint,
    article_id         bigint,
    content     text,
    `like`      bigint default 0,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_fac_uid_user foreign key (uid) references user_info (user_id),
    constraint fk_fac_article_id_user foreign key (article_id) references forum_article (id)

);
create index idx_cmt_like on forum_article (`like`);
create table forum_article_second_comment
(
    id          bigint primary key,
    uid         bigint,
    cmtId         bigint,
    content     text,
    `like`      bigint default 0,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_fasc_uid_user foreign key (uid) references user_info (user_id),
    constraint fk_fasc_cmtId_user foreign key (cmtId) references forum_article_comment (id)
);
create index idx_scmt_like on forum_article (`like`);






create table article_likes
(
    id          bigint primary key,
    uid         bigint,
    article_id  bigint,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_al_uid_user foreign key (uid) references user_info (user_id),
    constraint fk_al_articleID_user foreign key (article_id) references forum_article_comment (id)
);

create table article_comment_likes
(
    id          bigint primary key,
    uid         bigint,
    cmt_id      bigint,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_acl_uid_user foreign key (uid) references user_info (user_id),
    constraint fk_acl_cmtID_user foreign key (cmt_id) references forum_article_comment (id)
);
create table article_second_comment_likes
(
    id          bigint primary key,
    uid         bigint,
    scmt_id     bigint,
    create_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp on update current_timestamp,
    constraint fk_ascl_uid_user foreign key (uid) references user_info (user_id),
    constraint fk_acl_scmtID_user foreign key (scmt_id) references forum_article_second_comment (id)
);

