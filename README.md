# ⚡ Projet de Simulation de Réseau Électrique

Ceci est un projet Java en ligne de commande qui simule la gestion d'un petit réseau électrique. Il permet aux utilisateurs de configurer manuellement un réseau en ajoutant des générateurs et des maisons, en créant des connexions, puis d'analyser l'efficacité de ce réseau.

Ce projet démontre une bonne application des principes de la Programmation Orientée Objet (POO) pour gérer un état complexe et implémenter une logique métier (le calcul de coût).

## 🚀 Fonctionnalités

* **Ajout dynamique** de générateurs et de maisons.
* **Mise à jour intelligente** des entités (la ré-ajout d'une maison met à jour sa consommation).
* **Connexion** de maisons à des générateurs avec vérification (une maison ne peut être connectée qu'une fois).
* **Calcul de coût** avancé pour évaluer l'efficacité du réseau (équilibrage de charge et surcharge).
* **Modification en direct** des connexions pour optimiser le réseau.
* **Affichage** textuel de l'état complet du réseau.

---

## 🛠️ Prérequis

* [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (version 8 ou supérieure)
* Un terminal ou une console

## ⚙️ Comment Lancer le Projet

1.  **Cloner le dépôt** (ou téléchargez les fichiers `.java`) :
    ```bash
    git clone https://[URL-DE-VOTRE-DEPOT-GIT].git
    ```

2.  **Naviguer dans le dossier** du projet :
    ```bash
    cd [NOM-DU-DOSSIER]
    ```

3.  **Compiler tous les fichiers** `.java` :
    ```bash
    javac *.java
    ```

4.  **Exécuter le programme** (en lançant la classe `Utilisateur`) :
    ```bash
    java Utilisateur
    ```

---

## 📖 Guide d'Utilisation

L'interaction se fait en deux phases, via des menus dans le terminal.

### Phase 1 : Menu Création# ⚡ Projet de Simulation de Réseau Électrique

Ceci est un projet Java en ligne de commande qui simule la gestion d'un petit réseau électrique. Il permet aux utilisateurs de configurer manuellement un réseau en ajoutant des générateurs et des maisons, en créant des connexions, puis d'analyser l'efficacité de ce réseau.

Ce projet démontre une bonne application des principes de la Programmation Orientée Objet (POO) pour gérer un état complexe et implémenter une logique métier (le calcul de coût).

## 🚀 Fonctionnalités

* **Ajout dynamique** de générateurs et de maisons.
* **Mise à jour intelligente** des entités (la ré-ajout d'une maison met à jour sa consommation).
* **Connexion** de maisons à des générateurs avec vérification (une maison ne peut être connectée qu'une fois).
* **Calcul de coût** avancé pour évaluer l'efficacité du réseau (équilibrage de charge et surcharge).
* **Modification en direct** des connexions pour optimiser le réseau.
* **Affichage** textuel de l'état complet du réseau.

---

## 🛠️ Prérequis

* [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (version 8 ou supérieure)
* Un terminal ou une console

## ⚙️ Comment Lancer le Projet

1.  **Cloner le dépôt** (ou téléchargez les fichiers `.java`) :
    ```bash
    git clone https://[URL-DE-VOTRE-DEPOT-GIT].git
    ```

2.  **Naviguer dans le dossier** du projet :
    ```bash
    cd [NOM-DU-DOSSIER]
    ```

3.  **Compiler tous les fichiers** `.java` :
    ```bash
    javac *.java
    ```

4.  **Exécuter le programme** (en lançant la classe `Utilisateur`) :
    ```bash
    java Utilisateur
    ```

---

## 📖 Guide d'Utilisation

L'interaction se fait en deux phases, via des menus dans le terminal.

### Phase 1 : Menu Création
* **Option 1 (Générateur)** : Saisir le nom et la capacité sur une seule ligne.
    * `> Entrez le nom et la capacite max (ex: G1 60) :` **`G1 100`**
* **Option 2 (Maison)** : Saisir le nom et le niveau de consommation (`BASSE`, `NORMAL`, `FORTE`).
    * `> Entrez le nom et la consommation (ex: M1 NORMAL) :` **`MaisonA NORMAL`**
* **Option 3 (Connexion)** : Saisir le nom de la maison et du générateur.
    * `> Entrez le nom de la maison et du generateur (ex: M1 G1 ou G1 M1) :` **`MaisonA G1`**

### Phase 2 : Menu Calcul

--- MENU CALCUL --- 1 - Calculer le coût du réseau électrique actuel 2 - Modifier une connexion. 3 - Afficher le reseau 4 - Fin Votre choix :

* **Option 1 (Calcul)** : Affiche le score d'inefficacité du réseau.
* **Option 2 (Modifier)** : Permet de déplacer une maison d'un générateur à un autre.
* **Option 3 (Afficher)** : Affiche l'état visuel du réseau.
* **Option 4 (Fin)** : Quitte le programme.

---

## 🏗️ Architecture et Détails Techniques

Ce projet est conçu autour du principe de **Séparation des Responsabilités** (Separation of Concerns).

* **`Utilisateur.java` (La Vue / Contrôleur)**
    * **Rôle** : Gérer l'interaction avec l'utilisateur (menus, `Scanner`).
    * **Logique** : Traduire les entrées de l'utilisateur (ex: `G1 60`) en appels de méthode (ex: `rxe.ajoutGenerateur(...)`). C'est le "chef d'orchestre".

* **`ReseauElectrique.java` (Le Modèle / Cœur)**
    * **Rôle** : Représenter l'état complet du réseau.
    * **Logique** : Contient les `ArrayList` qui servent de "base de données" pour les maisons, générateurs et connexions. Il contient la logique métier la plus importante (vérifier les doublons, interdire les connexions multiples, etc.).

* **`Maison.java` / `Generateur.java` / `Connexion.java` (Entités)**
    * **Rôle** : Classes de données pures (parfois appelées POJO). Elles stockent les informations sur un objet précis.
    * **Logique** : Implémentent `equals()` (basé sur le nom) pour permettre à `ReseauElectrique` de les retrouver et de gérer les doublons.

* **`Consomation.java` (Enum)**
    * **Rôle** : Garantir la **cohérence des données**.
    * **Logique** : En utilisant une `enum` au lieu d'un `int` ou `String` libre, on empêche l'utilisateur de créer une maison avec une consommation de `-100` ou `MOYENNE`. C'est une pratique de codage défensif.

* **`CoutRxElct.java` (Le Service / Calculateur)**
    * **Rôle** : Contenir la logique de calcul complexe.
    * **Logique** : Il prend le `ReseauElectrique` en entrée et effectue des calculs dessus. Cela évite de surcharger la classe `ReseauElectrique` avec des méthodes de calcul qui n'ont pas de rapport avec la gestion de l'état.

## 💡 Concepts Clés et Logique Métier

### 1. Le Calcul du Coût (Équilibrage de Charge)

Le "coût" est une **mesure d'inefficacité**. L'objectif est de le minimiser (idéalement à 0).
La formule est : `Coût = Dispersion + (Pénalité * Surcharge)`

* **Dispersion (`disp()`)** :
    * Mesure l'**équilibrage** du réseau.
    * Il calcule la charge moyenne de *tous* les générateurs, puis additionne l'écart de chaque générateur par rapport à cette moyenne.
    * *Exemple* : Si `G1` est à 100% et `G2` à 0%, la dispersion sera très élevée. Si `G1` et `G2` sont tous les deux à 50%, la dispersion est de 0 (idéal).

* **Surcharge (`surcharge()`)** :
    * Mesure la **surchauffe** du réseau.
    * Il calcule le pourcentage de charge *au-dessus* de la capacité maximale pour chaque générateur.
    * *Exemple* : Un générateur de 100 kWh avec une charge de 120 kWh a une surcharge de `0.20`. Cette valeur est multipliée par une pénalité pour la rendre très coûteuse.

### 2. La Gestion des Mises à Jour

La méthode `ajoutMaison(Maison m)` ne fait pas qu'ajouter. Elle parcourt d'abord la liste :
1.  Si elle trouve une maison avec le même nom (`t.equals(m)`), elle ne crée pas de doublon.
2.  À la place, elle met à jour la consommation de la maison existante (`t.setConso(m.getConso())`).
3.  **(Voir Bug 1 ci-dessous)** Elle doit aussi mettre à jour la charge du générateur auquel cette maison est connectée.

## 🐞 Bugs Corrigés et Améliorations

Durant le développement, plusieurs problèmes de logique ont été identifiés et corrigés pour rendre le programme robuste.

### Bug 1 : Désynchronisation de la Charge (`chargeActu`)

* **Problème** : Quand on mettait à jour une maison (ex: `BASSE` -> `FORTE`), l'objet `Maison` était mis à jour, mais le `Generateur` auquel elle était connectée gardait son ancienne valeur de `chargeActu`. Le réseau devenait incohérent.
* **Solution** :
    1.  Ajout de la méthode `Generateur.soustraireCharge(int conso)`.
    2.  Modification de `ReseauElectrique.ajoutMaison()` :
        * Quand une maison existante est détectée, on recherche sa `Connexion`.
        * On récupère l'**ancien** générateur (`ancienGen = c.getGen()`).
        * On met à jour sa charge : `ancienGen.soustraireCharge(ancienneConso)` puis `ancienGen.setChargeActu(nouvelleConso)`.

### Bug 2 : Données Périmées (Stale Data) dans `CoutRxElct`

* **Problème** : L'objet `CoutRxElct` était créé une seule fois au début de la Phase 2. Son constructeur copiait les listes de générateurs et de connexions à cet instant `T`. Si l'utilisateur utilisait "Modifier une connexion", le réseau était modifié, mais `CoutRxElct` continuait de calculer le coût sur son **ancienne copie** des données.
* **Solution** :
    1.  Le constructeur de `CoutRxElct` ne copie plus les listes.
    2.  Il stocke une **référence** à l'objet `ReseauElectrique` principal (`this.rxe = rxe`).
    3.  Toutes les méthodes de calcul (`disp()`, `surcharge()`, etc.) appellent maintenant `rxe.getGens()` ou `rxe.getConnexions()` pour garantir qu'elles travaillent toujours sur les données les plus récentes.

### Implémentation : `modifierConnexion()`

* **Logique** : Cette fonctionnalité, qui manquait à l'origine, a été implémentée.
    1.  Ajout de `Connexion.setGen(Generateur g)` pour permettre de changer le générateur d'une connexion.
    2.  La méthode demande à l'utilisateur le nom de la maison à déplacer et le nom du nouveau générateur.
    3.  Elle trouve l'ancien générateur (via la `Connexion`) et le nouveau (via le `ReseauElectrique`).
    4.  Elle **transfère la charge** : `ancienGen.soustraireCharge(conso)` puis `nouveauGen.setChargeActu(conso)`.
    5.  Elle met à jour la connexion : `connexion.setGen(nouveauGen)`.

---

## 🧑‍💻 Auteurs

* **(Votre Nom / Pseudo)**
