### Rapport de Projet Détaillé : Développement d'une API RESTful pour la Manipulation de Données XML

<img width="1067" height="260" alt="image" src="https://github.com/user-attachments/assets/579eca2b-9353-44b0-824c-f73113620170" />


#### 1. Introduction et Vision du Projet
Ce projet a été conçu et développé comme une API RESTful robuste, tirant parti du framework Spring Boot pour une manipulation avancée de documents XML. L'objectif principal est de fournir une solution complète, permettant de traiter des documents XML via des requêtes standard, et de démontrer la puissance de la transformation et de la gestion de données structurées. L'accent a été mis sur la modularité, la fiabilité et une documentation de qualité.

#### 2. Architecture Technique et Séparation des Responsabilités
L'architecture du projet, visible dans la capture d'écran, est organisée en deux couches principales :

<img width="542" height="903" alt="image" src="https://github.com/user-attachments/assets/59939a65-5fb6-4de0-beb9-9256250f7f30" />


* **Couche `controller`** : Elle sert de point d'entrée pour toutes les requêtes HTTP et délègue la logique métier à la couche service, assurant ainsi une API propre.
* **Couche `service`** : Elle contient le cœur de la logique métier, responsable de toutes les opérations de manipulation des fichiers XML.

#### 3. Fonctionnalités de l'API
L'API implémente une gamme complète de fonctionnalités, chacune validée par un point de terminaison spécifique. Le tableau ci-dessous résume les endpoints exposés par l'API pour interagir avec les fichiers XML :

| Méthode | URL | Description | Consomme | Produit |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/xml/upload` | Uploade un fichier XML sur le serveur. | `multipart/form-data` | `Texte` (confirmation) |
| `GET` | `/api/xml/transform` | Transforme le contenu XML actuel en HTML via XSLT. | `N/A` | `HTML` |
| `GET` | `/api/xml/content` | Récupère le contenu brut du fichier XML actuel. | `N/A` | `XML` |
| `POST` | `/api/xml/add` | Ajoute un nouvel élément/fragment XML au document. | `application/xml` | `Texte` (confirmation) |
| `PUT` | `/api/xml/update` | Met à jour un élément XML existant par son `id`. | `application/xml` | `Texte` (confirmation) |
| `DELETE` | `/api/xml/delete/{id}` | Supprime un élément XML par son attribut `id`. | `N/A` | `Texte` (confirmation) |

#### 4. Validation et Testabilité de l'API
Pour garantir la qualité et la fiabilité de l'API, le projet a été soumis à des tests unitaires rigoureux. La validation s'articule autour des deux points suivants.

##### 4.1. Documentation Interactive avec Swagger UI
La documentation interactive de l'API est accessible via **Swagger UI** à l'URL `http://localhost:8080/swagger-ui/index.html`. Cette interface permet de visualiser tous les points de terminaison de l'API et de les tester directement depuis le navigateur.

<img width="1868" height="954" alt="image" src="https://github.com/user-attachments/assets/6a570593-bc0a-4d33-938b-c370d0b80404" />


##### 4.2. Couverture de Tests Unitaires et Preuves
Le projet est couvert par des tests unitaires robustes pour garantir la fiabilité des contrôleurs et du service. Les tests du `XmlControllerTest`, utilisant JUnit et Mockito, vérifient la bonne gestion des requêtes pour chaque endpoint.

Ci-dessous, un tableau démontrant la couverture des tests, avec des renvois aux noms des tests et à la capture d'écran des résultats pour en attester :

| Endpoint | Cas de Test Réalisés | Statut dans la Capture d'écran |
| :--- | :--- | :--- |
| `/upload` | `testUploadXmlSuccess()` et `testUploadXmlFails()` | ✅ |
| `/transform` | `testTransformXmlSuccess()` et `testTransformXmlFails()` | ✅ |
| `/content` | `testGetContentSuccess()` et `testGetContentFails()` | ✅ |
| `/add` | (Testé dans le `XmlServiceTest` et indirectement par le `XmlControllerTest`) | ✅ |
| `/update` | `testUpdateElementSuccess()` et `testUpdateElementFails()` | ✅ |
| `/delete` | `testDeleteElementSuccess()` et `testDeleteElementFails()` | ✅ |

### Tests unitaires du `XmlController` avec JUnit et Mockito

Ce fichier de tests (`XmlControllerTest`) garantit la fiabilité des endpoints de l'API REST de gestion XML. Il utilise le framework **Mockito** pour simuler le comportement du `XmlService`, ce qui permet de tester la couche contrôleur de manière isolée.


Chaque fonctionnalité du CRUD (Create, Read, Update, Delete) est couverte, avec des tests pour les scénarios de succès et d'échec :
* **`upload`** : Envoi d'un fichier XML.
* **`transform`** : Transformation du XML en HTML via XSLT.
* **`add`** : Ajout d'un nouvel élément XML.
* **`content`** : Récupération du contenu XML.
* **`update`** : Modification d'un élément XML existant.
* **`delete`** : Suppression d'un élément par ID.

 -MOCKITO

<img width="744" height="499" alt="image" src="https://github.com/user-attachments/assets/f0b070ca-ba8a-475a-9e33-caf69e604c3d" />

-JUNIT
<img width="743" height="371" alt="image" src="https://github.com/user-attachments/assets/a2910d8f-c6aa-4db2-b8cd-6c40a9a21e97" />



**1. Capture des tests**
La capture d'écran des tests unitaires du contrôleur (figure 1) atteste que les 12 tests, couvrant les cas de succès et d'échec de tous les endpoints, ont été exécutés avec succès.
<img width="1891" height="990" alt="image" src="https://github.com/user-attachments/assets/8d8a3810-4e73-4cfd-b466-c7477c6653fb" />
-Uploader un fichier nommé & afficher son contenu
<img width="1038" height="913" alt="image" src="https://github.com/user-attachments/assets/3790bcbd-7f64-47b4-8202-19d05d579d3b" />
-Transformation 
<img width="989" height="787" alt="image" src="https://github.com/user-attachments/assets/dfd6e579-9055-4d98-86f0-823635416237" />
-Ajout 
<img width="1033" height="459" alt="image" src="https://github.com/user-attachments/assets/35312955-4469-45fa-a95c-edb456c724c7" />
-MIS A JOUR 
<img width="1012" height="422" alt="image" src="https://github.com/user-attachments/assets/c9fc3bf3-5fd1-4fc4-b986-b8f83ca630eb" />
-Supression 
<img width="1092" height="353" alt="image" src="https://github.com/user-attachments/assets/c6ac8165-b24b-407a-aca8-b23878ca392b" />











