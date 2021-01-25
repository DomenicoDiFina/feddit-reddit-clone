INSERT INTO `users` (`userid`, `birth`, `email`, `name`, `password`, `surname`, `username`) VALUES
(1, '2021-01-20', 'test@test.it', 'test', '$2a$10$QckEBpvejE7LVMyOZjO29.itpzqXHff3p2GDvn65eAutcVYl7NU56', 'test', 'test'),
(2, '2021-01-11', 'admin@admin.it', 'admin', '$2a$10$ANPR9MXIkQqppTz2Ps.BpuiqPQixwJ3FYCM54LThfN69tuTs7lq3S', 'admin', 'admin');

INSERT INTO `roles` (`roleid`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN');

INSERT INTO `users_roles` (`userid`, `roleid`) VALUES
(1, 1),
(2, 1),
(2, 2);
