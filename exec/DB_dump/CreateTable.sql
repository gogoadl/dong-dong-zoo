create table if not exists lyrics_keyword
(
    lyrics_keyword_id bigint auto_increment
        primary key,
    keyword           varchar(255) not null,
    lyrics_id         bigint       not null
);

create table if not exists song
(
    song_id        bigint auto_increment
        primary key,
    song_name      varchar(255) not null,
    song_image_url varchar(255) null,
    song_file_url  varchar(255) not null,
    beat           bigint       not null,
    views          bigint       not null
);

create table if not exists lyrics
(
    lyrics_id  bigint auto_increment
        primary key,
    content    varchar(255) not null,
    song_id    bigint       not null,
    start_time varchar(255) null,
    constraint lyrics_ibfk_1
        foreign key (song_id) references song (song_id)
);

create index song_id
    on lyrics (song_id);

create table if not exists song_keyword
(
    song_keyword_id     bigint       not null
        primary key,
    song_keyword        varchar(255) not null,
    song_id             bigint       not null,
    song_keyword_korean varchar(255) null,
    constraint FK_song_TO_song_keyword_1
        foreign key (song_id) references song (song_id)
);

create table if not exists user
(
    user_id        bigint auto_increment
        primary key,
    user_name      varchar(255) not null,
    email          varchar(255) not null,
    password       varchar(255) not null,
    user_image_url varchar(255) null
);

create table if not exists song_likes
(
    song_likes_id bigint auto_increment
        primary key,
    user_id       bigint not null,
    song_id       bigint not null,
    constraint song_likes_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint song_likes_ibfk_2
        foreign key (song_id) references song (song_id)
);

create index song_id
    on song_likes (song_id);

create index user_id
    on song_likes (user_id);

create table if not exists story
(
    story_id   bigint auto_increment
        primary key,
    story_name varchar(255) not null,
    user_id    bigint       not null,
    song_id    bigint       not null,
    constraint story_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint story_ibfk_2
        foreign key (song_id) references song (song_id)
);

create index song_id
    on story (song_id);

create index user_id
    on story (user_id);

create table if not exists story_image
(
    story_image_id  bigint auto_increment
        primary key,
    story_image_url varchar(255) not null,
    story_id        bigint       not null,
    constraint story_image_ibfk_1
        foreign key (story_id) references story (story_id)
);

create index story_id
    on story_image (story_id);

create table if not exists story_keyword
(
    story_keyword_id bigint auto_increment
        primary key,
    keyword          varchar(255) not null,
    story_id         bigint       not null,
    constraint story_keyword_ibfk_1
        foreign key (story_id) references story (story_id)
);

create index story_id
    on story_keyword (story_id);

create table if not exists story_likes
(
    story_likes_id bigint auto_increment
        primary key,
    user_id        bigint not null,
    story_id       bigint not null,
    constraint fk_story_id
        foreign key (story_id) references story (story_id)
            on delete cascade,
    constraint story_likes_ibfk_1
        foreign key (user_id) references user (user_id)
);

create index user_id
    on story_likes (user_id);

