drop table if exists Possess;
drop table if exists Answer;
drop table if exists Question;
drop table if exists Game;
drop table if exists Player;

CREATE TABLE Player(
   idplayer INT AUTO_INCREMENT,
   name VARCHAR(50)  NOT NULL,
   password VARCHAR(255) NOT NULL,
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
   label VARCHAR(255),
   PRIMARY KEY(idquestion),
);

CREATE TABLE Answer(
   idanswer INT AUTO_INCREMENT,
   label VARCHAR(255) ,
   iscorrect BOOLEAN,
   idquestion INT NOT NULL,
   PRIMARY KEY(idanswer),
   FOREIGN KEY(idquestion) REFERENCES Question(idquestion)
);

CREATE TABLE Possess(
   idgame INT NOT NULL,
   idquestion INT NOT NULL,
   FOREIGN KEY(idgame) REFERENCES Game(idgame),
   FOREIGN KEY(idquestion) REFERENCES Question(idquestion),
   PRIMARY KEY(idgame, idquestion)
);

-- Insérez quelques joueurs
INSERT INTO Player (name, password) VALUES
   ('Joueur 1', '$2a$10$o2UumzT7fDeIQiFZfv.uJOR7U7jP6uj62PSrLjvjh4tR.b3Th4wIa');

-- Insérez des jeux avec des scores pour les joueurs
INSERT INTO Game (score, idplayer) VALUES
   (100, 1);

-- Insérez 20 questions avec différentes difficultés pour les jeux
INSERT INTO Question (idquestion, difficultyLevel, label, idgame) VALUES
   (1, 1, 'Question 1 (Facile) - Jeu 1', 1),
   (2, 2, 'Question 2 (Moyenne) - Jeu 1', 1),
   (3, 3, 'Question 3 (Difficile) - Jeu 1', 1);


INSERT INTO Possess (idgame, idquestion) VALUES
   (1, 1),
   (1, 2),
   (1, 3);

-- Insérez des réponses pour chaque question
INSERT INTO Answer (label, iscorrect, idquestion) VALUES
   ('Réponse 1 à la Question 1', 1, 1),
   ('Réponse 2 à la Question 1', 0, 1),
   ('Réponse 1 à la Question 2', 1, 2),
   ('Réponse 2 à la Question 2', 0, 2),
   ('Réponse 1 à la Question 3', 1, 3),
   ('Réponse 2 à la Question 3', 0, 3);
