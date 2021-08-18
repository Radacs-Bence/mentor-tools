alter table registrations
    add constraint FKcmo9lk1tap4hpbawuxhb8qf94
        foreign key (student_id)
            references students (id);


alter table registrations
    add constraint FKsp4qk9wh7f3vd2sw8ne7mup20
        foreign key (training_class_id)
            references classes (id)