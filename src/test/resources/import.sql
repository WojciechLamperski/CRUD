
-- Insert into voivodeships table
INSERT INTO voivodeships (voivodeship_id, voivodeship) VALUES (1, 'Mazowieckie');
INSERT INTO voivodeships (voivodeship_id, voivodeship) VALUES (2, 'Śląskie');

-- Insert into years table
INSERT INTO years (year_id, year) VALUES (1, 2020);
INSERT INTO years (year_id, year) VALUES (2, 2021);


-- Insert into districts table
INSERT INTO districts (district_id, district, voivodeship_id) VALUES (1, 'Warszawa', 1);
INSERT INTO districts (district_id, district, voivodeship_id) VALUES (2, 'Katowice', 2);

-- Insert into populations table
INSERT INTO populations (population_id, year_id, district_id, women, men) VALUES (1, 1, 1, 500, 600);
INSERT INTO populations (population_id, year_id, district_id, women, men) VALUES (2, 2, 2, 400, 500);