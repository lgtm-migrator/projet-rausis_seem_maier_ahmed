[![Total alerts](https://img.shields.io/lgtm/alerts/g/dil-classroom/projet-rausis_seem_maier_ahmed.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/dil-classroom/projet-rausis_seem_maier_ahmed/alerts/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/dil-classroom/projet-rausis_seem_maier_ahmed.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/dil-classroom/projet-rausis_seem_maier_ahmed/context:java)
# Jamstack - générateur de site statique

Ce projet est réalisé dans le cadre du cours "Processus de développement en ingénierie logicielle" de la HEIG-VD.

Il s'agit d'un générateur de site statique.

## Guide utilisateur

Premièrement, téléchargez le fichier .jar ne contenant pas "original" dans son nom. 
Durant toutes les explications suivante, seul les commandes seront citée. Il faut 
cependant noter que la commande sera précédée à chaque fois de:
```
java -jar <nom du fichier>.jar
```
De plus, toute les commandes sont à effectuer dans une invite de commande 
au même niveau que là où a été stocké le fichier .jar. Enfin, comme il s'agit 
d'un fichier java, il faut évidemment avoir java d'installer. La version utilisée
ici est la version 11.0.10 de java.

### Connaître la version du générateur
Une fois le fichier .jar télécharger, rendez-vous au même niveau que lui dans 
une invite de commande, et entrez une des trois commandes ci-dessous
```
-v
-V
-version
```
La version du projet que vous utilisé s'affichera dans la console.

### Commande Init et fichiers de configuration

Créez, au même niveau que le fichier .jar, un dossier où sera stocker le site. 
Pour cet exemple, le dossier s'appelera monSite.

Le répertoire où vous vous trouvez devrait actuellement ressembler à celà :

    monSite/
    Projet.jar

Une fois cela fait, vous pouvez utliser la commande `init` afin de générer 
les éléments de base pour la création de votre site.

```
init /monSite
```

Une fois cela fait, le répertoire devrait ressembler à ceci:

```
monSite/
        config.json
        index.md
Projet.jar
```

Il est également possible de rajouter des fichier de configuration supplémentaire, 
selon l'arborescence suivante:

```
monSite/
        content/
                | - index.md
                | - image.png (facultatif, mais si image il y a, la/les mettre ici)
        templates/
                | - layout.html
                | - menu.html
        config.json
        index.md
Projet.jar
```

### Commande build
On peut maintenant construire le site grâce à la commande 
```
build <chemin absolu ver le dossier monSite>
```
La commande build permet de créer un dossier build, dans le dossier monSite, 
qui contiendra les différents fichier HTML du site statique. Cependant, contrairement  
à init, ici il faut utiliser le chemin absolu vers monSite. Elle va construire 
le site statique à partir des informations présentent dans les différents 
fichiers markdown et les transposer aux emplacement correspondant dans les templates
présents (pour les fichiers dans content). Pour le fichier index.md, ce dernier
est directement convertit HTML.

Le répertoire devrait maintenant ressembler à celà:
```
monSite/
        build/
                | - content/
                |           | - page.html
                |           | - image.png
                | - templates/
                |           | - layout.html
                |           | - menu.html
                | - index.html
        content/
                | - page.md
                | - image.png (facultatif, mais si image il y a, la/les mettre ici)
        templates/
                | - layout.html
                | - menu.html
        config.json
        index.md
Projet.jar
```

L'option --watch permet de monitorer le dossier monSite en continu afin de rebuild 
automatiquement le site si un changement est effectué dans monSite.

### Commande clean
la commande clean permet simplement de supprimmer le site statique, c'est à dire
le dossier build et tout son contenu. Pour se faire, utilisez
```
clean /monSite
```
Pour cette commande, comme pour init, il n'y a pas besoin de mettre le chemin absolu.

Si tout se passe bien, on ne devrait recevoir aucun message, et le dossier build 
devrait avoir disparu, nous ramenant à l'arborescence suivante:

```
monSite/
        content/
                | - index.md
                | - image.png (facultatif, mais si image il y a, la/les mettre ici)
        templates/
                | - layout.html
                | - menu.html
        config.json
        index.md
Projet.jar
```

### Remarque
Vous pouvez utilisez le dossier nommé monSite fournis dans la release du projet
sous format zip, il suffit de le télécharger et de le dézipper et vous aurez tous
les éléments qui peuvent être utilisé par build directement.


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

## Descriptif des commandes :

```
$ statique –version, -V, -v
```

Affiche la version du projet.
```
$ statique init <path to the folder>
```
La commande init permet de créer ou d'enrichir le dossier `<path to the folder>`
avec un fichier de configuration config.json contenant des informations générales 
liées au site (titre, description, domaine, etc.) et un fichier Markdown index.md 
contenant les informations nécessaire à la création d'une page d'accueil.

```
$ statique build <absolute path to the folder> [-- watch]
```

La commande build crée un dossier build sous le répertoire `<absolute path to the folder>`.
Il contient des fichiers HTML générés à partir des fichiers de configurations déjà
présents dans le répertoir `<absolute path to the folder>`.

Les fichiers HTML sont construit à partir des fichiers Markdown et des template HTML
présent dans le `<absolute path to the folder>`.


L'option --watch : permet de régénérer le site statique à la volée, c'est-à-dire de manière automatique lorsque des changements sont effectués sur le système de fichier

```
$ statique clean <path to the folder>
```

La commande clean  supprime le dossier build créé par la commande build dans le `<path to the folder>`

