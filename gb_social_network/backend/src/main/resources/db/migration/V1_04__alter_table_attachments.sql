alter table attachments
    drop column body;

alter table attachments
    add column storage_file_name varchar(255) not null unique;

alter table attachments
    add column extension varchar(10) not null;



