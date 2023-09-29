drop table if exists Answer;
drop table if exists Question;
drop table if exists Game;
drop table if exists Player;

CREATE TABLE Player(
   idplayer INT AUTO_INCREMENT,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(idplayer)
);

CREATE TABLE Game(
   idgame INT AUTO_INCREMENT,
   score INT,
   idplayer INT NOT NULL,
   PRIMARY KEY(idgame),
   FOREIGN KEY(idplayer) REFERENCES Player(idplayer)
);

CREATE TABLE Question(
   idquestion INT,
   difficultyLevel INT,
   label VARCHAR(255) ,
   idgame INT NOT NULL,
   PRIMARY KEY(idquestion),
   FOREIGN KEY(idgame) REFERENCES Game(idgame)
);

CREATE TABLE Answer(
   idanswer INT AUTO_INCREMENT,
   label VARCHAR(255) ,
   iscorrect BOOLEAN,
   idquestion INT NOT NULL,
   PRIMARY KEY(idanswer),
   FOREIGN KEY(idquestion) REFERENCES Question(idquestion)
);

-- Insérez quelques joueurs
INSERT INTO Player (name) VALUES
   ('Joueur 1'),
   ('Joueur 2'),
   ('Joueur 3');

-- Insérez des jeux avec des scores pour les joueurs
INSERT INTO Game (score, idplayer) VALUES
   (100, 1),
   (150, 2),
   (200, 3);

-- Insérez 20 questions avec différentes difficultés pour les jeux
INSERT INTO Question (idquestion, difficultyLevel, label, idgame) VALUES
   (1, 1, 'Question 1 (Facile) - Jeu 1', 1),
   (2, 2, 'Question 2 (Moyenne) - Jeu 1', 1),
   (3, 3, 'Question 3 (Difficile) - Jeu 1', 1),
   (4, 1, 'Question 1 (Facile) - Jeu 2', 2),
   (5, 2, 'Question 2 (Moyenne) - Jeu 2', 2),
   (6, 3, 'Question 3 (Difficile) - Jeu 2', 2),
   (7, 1, 'Question 1 (Facile) - Jeu 3', 3),
   (8, 2, 'Question 2 (Moyenne) - Jeu 3', 3),
   (9, 3, 'Question 3 (Difficile) - Jeu 3', 3);

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
   ('Réponse 2 à la Question 9', 0, 9);


