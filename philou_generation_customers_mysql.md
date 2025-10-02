# 📌 Génération de 50 000 clients dans MySQL 8.0.31

## 1️⃣ Création de la table `customer`

```sql
CREATE TABLE customer (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    gender VARCHAR(20),
    region VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## 2️⃣ Génération de 50 000 lignes

Nous voulons remplir la table `customer` avec 50 000 lignes synthétiques contenant :

- `id` : de 1 à 50 000  
- `name` : `Name_1 … Name_50000`  
- `email` : `user1@example.com … user50000@example.com`  
- `gender` : distribution **non uniforme**  
  - 56 % Male  
  - 43 % Female  
  - 1 % Non-binary  
- `region` : distribution **non uniforme**  
  - 35 % Europe  
  - 30 % Asia  
  - 20 % America  
  - 10 % Africa  
  - 5 % Oceania  

---

## 3️⃣ Option A — Insertion standard (⚡ utilisée)

👉 Cette méthode utilise une variable utilisateur `@row` et des `CROSS JOIN` pour générer les lignes.  
Elle **échoue** si la table contient déjà des doublons (clé primaire ou email).

```sql
-- Facultatif : vider la table avant
-- TRUNCATE TABLE customer;

-- Initialiser la variable
SET @row := 0;

-- Insérer 50 000 lignes
INSERT INTO customer (id, name, email, gender, region)
SELECT
  n AS id,
  CONCAT('Name_', n) AS name,
  CONCAT('user', n, '@example.com') AS email,
  CASE
    WHEN RAND(n * 17) < 0.56 THEN 'Male'
    WHEN RAND(n * 17) < 0.99 THEN 'Female'
    ELSE 'Non-binary'
  END AS gender,
  CASE
    WHEN RAND(n * 97) < 0.35 THEN 'Europe'
    WHEN RAND(n * 97) < 0.65 THEN 'Asia'
    WHEN RAND(n * 97) < 0.85 THEN 'America'
    WHEN RAND(n * 97) < 0.95 THEN 'Africa'
    ELSE 'Oceania'
  END AS region
FROM (
  SELECT @row := @row + 1 AS n
  FROM
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d0
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d1
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d2
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d3
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d4
) gen
LIMIT 50000;
```

---

## 4️⃣ Option B — Insertion avec `INSERT IGNORE`

👉 Identique à l’option A, mais utilise `INSERT IGNORE`.  
Utile si la table contient déjà des données : les doublons (`id` ou `email`) sont ignorés.

```sql
SET @row := 0;

INSERT IGNORE INTO customer (id, name, email, gender, region)
SELECT
  n AS id,
  CONCAT('Name_', n) AS name,
  CONCAT('user', n, '@example.com') AS email,
  CASE
    WHEN RAND(n * 17) < 0.56 THEN 'Male'
    WHEN RAND(n * 17) < 0.99 THEN 'Female'
    ELSE 'Non-binary'
  END AS gender,
  CASE
    WHEN RAND(n * 97) < 0.35 THEN 'Europe'
    WHEN RAND(n * 97) < 0.65 THEN 'Asia'
    WHEN RAND(n * 97) < 0.85 THEN 'America'
    WHEN RAND(n * 97) < 0.95 THEN 'Africa'
    ELSE 'Oceania'
  END AS region
FROM (
  SELECT @row := @row + 1 AS n
  FROM
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d0
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d1
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d2
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d3
  CROSS JOIN
    (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
           UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d4
) gen
LIMIT 50000;
```

---

## 5️⃣ Vérification de la répartition

```sql
SELECT gender, COUNT(*) AS nb, ROUND(100*COUNT(*)/50000,2) AS pct
FROM customer GROUP BY gender;

SELECT region, COUNT(*) AS nb, ROUND(100*COUNT(*)/50000,2) AS pct
FROM customer GROUP BY region;
```

---

📌 **Résumé** :  
- **Option A** : rapide, simple, mais échoue si la table contient déjà des doublons.  
- **Option B** : même logique, mais ignore les doublons → utile si la table est déjà partiellement remplie.  
