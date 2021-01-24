-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Gen 24, 2021 alle 17:02
-- Versione del server: 10.4.17-MariaDB
-- Versione PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hibernate`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `comments`
--

CREATE TABLE `comments` (
  `commentid` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `downvotes` int(11) DEFAULT NULL,
  `upvotes` int(11) DEFAULT NULL,
  `postid` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `comments_comments`
--

CREATE TABLE `comments_comments` (
  `comment_commentid` bigint(20) NOT NULL,
  `comments_commentid` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `posts`
--

CREATE TABLE `posts` (
  `postid` bigint(20) NOT NULL,
  `creation_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `downvotes` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `upvotes` int(11) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `posts_comments`
--

CREATE TABLE `posts_comments` (
  `post_postid` bigint(20) NOT NULL,
  `comments_commentid` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `roleid` int(11) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `roles`
--

INSERT INTO `roles` (`roleid`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `userid` bigint(20) NOT NULL,
  `birth` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`userid`, `birth`, `email`, `name`, `password`, `surname`, `username`) VALUES
(1, '2021-01-20', 'test@test.it', 'test', '$2a$10$QckEBpvejE7LVMyOZjO29.itpzqXHff3p2GDvn65eAutcVYl7NU56', 'test', 'test'),
(2, '2021-01-14', 'admin@admin.it', 'admin', '$2a$10$IEeTc9CXWqFjDNiiYrIUFeNc6f/06Vd7KXuXZf4s8PMqGicnhKMCa', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Struttura della tabella `users_roles`
--

CREATE TABLE `users_roles` (
  `userid` bigint(20) NOT NULL,
  `roleid` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `users_roles`
--

INSERT INTO `users_roles` (`userid`, `roleid`) VALUES
(1, 1),
(2, 1),
(2, 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`commentid`),
  ADD KEY `FKqt8anaen7vlhry2a766wkvv41` (`postid`),
  ADD KEY `FKjxggc60wwwlf4xl065fjrx68y` (`userid`);

--
-- Indici per le tabelle `comments_comments`
--
ALTER TABLE `comments_comments`
  ADD UNIQUE KEY `UK_pqj6lujrlwstetic75q101823` (`comments_commentid`),
  ADD KEY `FK1nalgk4h2i7fauus1ksss57tq` (`comment_commentid`);

--
-- Indici per le tabelle `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`postid`),
  ADD KEY `FKtc10cvjiaj3p7ldl526coc36a` (`userid`);

--
-- Indici per le tabelle `posts_comments`
--
ALTER TABLE `posts_comments`
  ADD UNIQUE KEY `UK_s66w0xu7vuol1qam7tl20afds` (`comments_commentid`),
  ADD KEY `FK5o89oop1k7qdx6e41m3fn970q` (`post_postid`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roleid`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`) USING HASH;

--
-- Indici per le tabelle `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`userid`,`roleid`),
  ADD KEY `FKalw2yaqhsip58rjm076ngh5v7` (`roleid`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `comments`
--
ALTER TABLE `comments`
  MODIFY `commentid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `posts`
--
ALTER TABLE `posts`
  MODIFY `postid` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `roles`
--
ALTER TABLE `roles`
  MODIFY `roleid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `userid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
