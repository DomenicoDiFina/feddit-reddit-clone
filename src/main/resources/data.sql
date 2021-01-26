INSERT INTO `users` VALUES
(1, '2021-01-20', '1990-01-20', 'test@test.it', 'test', 'test', '$2a$10$QckEBpvejE7LVMyOZjO29.itpzqXHff3p2GDvn65eAutcVYl7NU56', 'test'),
(2, '2021-01-11', '1990-01-20', 'admin@admin.it', 'admin', 'admin', '$2a$10$ANPR9MXIkQqppTz2Ps.BpuiqPQixwJ3FYCM54LThfN69tuTs7lq3S', 'admin');

INSERT INTO `roles` VALUES
(1, '2020-01-11', 'USER'),
(2, '2020-01-11', 'ADMIN');

INSERT INTO `users_roles` VALUES
(1, 1),
(2, 1),
(2, 2);
