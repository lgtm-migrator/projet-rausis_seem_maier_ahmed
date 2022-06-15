# Jamstack - générateur de site statique

Ce projet est réalisé dans le cadre du cours "Processus de développement en ingénierie logicielle" de la HEIG-VD.

Il sert avant tout de prétexte pour l'application et l'expérimentation des outils de processus de développement logiciel vus dans ce cours.



## Descriptif des commandes :

```
$ –version, -V, -v
```

Affiche la version du projet.
```
$ init <path to the folder>
```
La commande init permet de créer ou d'enrichir le dossier `<path to the folder>`
avec un fichier de configuration config.json contenant des informations générales 
liées au site (titre, description, domaine, etc.) et un fichier Markdown index.md 
contenant les informations nécessaire à la création d'une page d'accueil.

```
$ build <path to the folder> [-- watch]
```

La commande build crée un dossier build sous le répertoire `<path to the folder>`.
Il contient des fichiers HTML générés à partir des fichiers de configurations déjà
présents dans le répertoir `<path to the folder>`.

Les fichiers HTML sont construit à partir des fichiers Markdown et des template HTML
présent dans le `<path to the folder>`.


L'option --watch : permet de régénérer le site statique à la volée, c'est-à-dire de manière automatique lorsque des changements sont effectués sur le système de fichier

```
$ clean <path to the folder>
```

La commande clean  supprime le dossier build créé par la commande build dans le `<path to the folder>`


## Utiliser statique en tant que commande (développement)
<I> Pour les utilisateurs Windows, utiliser git bash ou un bash unix </I>

Se positionner à la racine du projet (au niveau du pom.xml).  
Utiliser la commande :
```
mvn clean install \
    && unzip -o target/statique.zip
```

ensuite, on ajoute le chemin du bin au path:
```
export PATH=$PATH:`pwd`/statique/bin
```

On peut maintenant utiliser 'statique' suivit d'une commande définie avec picocli en ligne de commande, mais uniquement
à la racine du projet (le chemin du bin n'a pas été ajouté définitivement au path de l'ordinateur).

<I>-- le fichier src/bin/statique contient deux script. Le script non commenté est censé fonctionner
pour tout le monde. Cependant, il se peut que des paramètre de votre ordinateur bloque fasse que vous obteniez
une erreur indiquand que le Main est introuvable. Dans ce cas, commenter le script actuel et décommenter le script
en commentaire puis refaire les étapes devrait résoudre le problème.</I>