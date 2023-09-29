drop table if exists Posseder;
drop table if exists Game;
drop table if exists Answer;
drop table if exists Question;
drop table if exists Player;

CREATE TABLE Player(
   idplayer INT AUTO_INCREMENT,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idplayer)
);

CREATE TABLE Question(
   idquestion INT,
   difficultyLevel INT,
   label VARCHAR(255) ,
   PRIMARY KEY(idquestion)
);

CREATE TABLE Answer(
   idanswer INT AUTO_INCREMENT,
   label VARCHAR(255) ,
   iscorrect BOOLEAN,
   idquestion INT NOT NULL,
   PRIMARY KEY(idanswer),
   FOREIGN KEY(idquestion) REFERENCES Question(idquestion)
);

CREATE TABLE Game(
   idgame INT AUTO_INCREMENT,
   score INT,
   idplayer INT NOT NULL,
   PRIMARY KEY(idgame),
   FOREIGN KEY(idplayer) REFERENCES Player(idplayer)
);

CREATE TABLE Posseder(
   idgame INT,
   idquestion INT,
   PRIMARY KEY(idgame, idquestion),
   FOREIGN KEY(idgame) REFERENCES Game(idgame),
   FOREIGN KEY(idquestion) REFERENCES Question(idquestion)
);

-- Insérez quelques joueurs
INSERT INTO Player (name) VALUES
   ('Joueur 1'),
   ('Joueur 2'),
   ('Joueur 3');

-- Insérez 20 questions avec différentes difficultés
INSERT INTO Question (idquestion, difficultyLevel, label) VALUES
   (1, 1, 'Question 1 (Facile)'),
   (2, 2, 'Question 2 (Moyenne)'),
   (3, 3, 'Question 3 (Difficile)'),
   (4, 1, 'Question 4 (Facile)'),
   (5, 2, 'Question 5 (Moyenne)'),
   (6, 3, 'Question 6 (Difficile)'),
   (7, 1, 'Question 7 (Facile)'),
   (8, 2, 'Question 8 (Moyenne)'),
   (9, 3, 'Question 9 (Difficile)'),
   (10, 1, 'Question 10 (Facile)'),
   (11, 2, 'Question 11 (Moyenne)'),
   (12, 3, 'Question 12 (Difficile)'),
   (13, 1, 'Question 13 (Facile)'),
   (14, 2, 'Question 14 (Moyenne)'),
   (15, 3, 'Question 15 (Difficile)'),
   (16, 1, 'Question 16 (Facile)'),
   (17, 2, 'Question 17 (Moyenne)'),
   (18, 3, 'Question 18 (Difficile)'),
   (19, 1, 'Question 19 (Facile)'),
   (20, 2, 'Question 20 (Moyenne)');

-- Insérez des réponses pour chaque question
INSERT INTO Answer (label, iscorrect, idquestion) VALUES
   ('Réponse 1 à la Question 1', 1, 1),
   ('Réponse 2 à la Question 1', 0, 1),
   ('Réponse 1 à la Question 2', 1, 2),
   ('Réponse 2 à la Question 2', 0, 2),
   ('Réponse 1 à la Question 3', 1, 3),
   ('Réponse 2 à la Question 3', 0, 3),
   ('Réponse 1 à la Question 4', 1, 4),
   ('Réponse 2 à la Question 4', 0, 4),
   ('Réponse 1 à la Question 5', 1, 5),
   ('Réponse 2 à la Question 5', 0, 5),
   ('Réponse 1 à la Question 6', 1, 6),
   ('Réponse 2 à la Question 6', 0, 6),
   ('Réponse 1 à la Question 7', 1, 7),
   ('Réponse 2 à la Question 7', 0, 7),
   ('Réponse 1 à la Question 8', 1, 8),
   ('Réponse 2 à la Question 8', 0, 8),
   ('Réponse 1 à la Question 9', 1, 9),
   ('Réponse 2 à la Question 9', 0, 9),
   ('Réponse 1 à la Question 10', 1, 10),
   ('Réponse 2 à la Question 10', 0, 10),
   ('Réponse 1 à la Question 11', 1, 11),
   ('Réponse 2 à la Question 11', 0, 11),
   ('Réponse 1 à la Question 12', 1, 12),
   ('Réponse 2 à la Question 12', 0, 12),
   ('Réponse 1 à la Question 13', 1, 13),
   ('Réponse 2 à la Question 13', 0, 13),
   ('Réponse 1 à la Question 14', 1, 14),
   ('Réponse 2 à la Question 14', 0, 14),
   ('Réponse 1 à la Question 15', 1, 15),
   ('Réponse 2 à la Question 15', 0, 15),
   ('Réponse 1 à la Question 16', 1, 16),
   ('Réponse 2 à la Question 16', 0, 16),
   ('Réponse 1 à la Question 17', 1, 17),
   ('Réponse 2 à la Question 17', 0, 17),
   ('Réponse 1 à la Question 18', 1, 18),
   ('Réponse 2 à la Question 18', 0, 18),
   ('Réponse 1 à la Question 19', 1, 19),
   ('Réponse 2 à la Question 19', 0, 19),
   ('Réponse 1 à la Question 20', 1, 20),
   ('Réponse 2 à la Question 20', 0, 20);

-- Insérez des jeux avec des scores pour les joueurs
INSERT INTO Game (score, idplayer) VALUES
   (100, 1),
   (150, 2),
   (200, 3),
   (120, 1),
   (180, 2),
   (220, 3),
   (90, 1),
   (160, 2),
   (190, 3),
   (110, 1),
   (140, 2),
   (210, 3),
   (130, 1),
   (170, 2),
   (230, 3),
   (105, 1),
   (155, 2),
   (205, 3),
   (125, 1),
   (165, 2);

-- Associez les questions aux jeux (Posseder)
INSERT INTO Posseder (idgame, idquestion) VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (2, 4),
   (2, 5),
   (2, 6),
   (3, 7),
   (3, 8),
   (3, 9),
   (4, 10),
   (4, 11),
   (4, 12),
   (5, 13),
   (5, 14),
   (5, 15),
   (6, 16),
   (6, 17),
   (6, 18),
   (7, 19),
   (7, 20);




