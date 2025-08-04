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

**1. Capture des tests**
La capture d'écran des tests unitaires du contrôleur (figure 1) atteste que les 12 tests, couvrant les cas de succès et d'échec de tous les endpoints, ont été exécutés avec succès.
<img width="1791" height="721" alt="image" src="https://github.com/user-attachments/assets/7215c361-ea15-415a-a596-e3bbda7d012e" />

