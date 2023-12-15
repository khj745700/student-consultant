# student-consultant
학생 상담 관리 시스템 API

# dependency
### MySQL 8.0
### Spring Boot 3.2.0
### JAVA corretoJDK 17.0.4
### gradle v8.5


# installation and run
``` bash
./gradlew build -x test
java -jar ./build/libs/application-0.0.1-SNAPSHOT.jar
```

# DB DDL
``` SQL
create table student (
  id varchar(20) not null,
  name varchar(20) not null,
  student_status enum ('ACTIVE','RETIRED') not null,
  primary key (id)
);

create table consulting (
  is_reading bit not null,
  created_at datetime(6),
  id bigint not null auto_increment,
  modified_at datetime(6),
  consultant_id varchar(20) not null,
  description text not null,
  feedback text,
  manager_id varchar(20),
  student_id varchar(20) not null,
  primary key (id)
);

create table staff (
    sort enum ('CONSULTANT','MANAGER') not null,
    created_at datetime(6),
    modified_at datetime(6),
    id varchar(20) not null,
    name varchar(20) not null,
    staff_status enum ('ACTIVE','RETIRED'),
    primary key (id)
);

alter table consulting
  add constraint FK_consultant_id
  foreign key (consultant_id)
  references staff (id);

alter table consulting
  add constraint FK_manager_id
  foreign key (manager_id)
  references staff (id);

alter table consulting
  add constraint FK_student_id
  foreign key (student_id)
  references student (id);
```

