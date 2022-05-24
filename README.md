# Jamstack - générateur de site statique

Ce projet est réalisé dans le cadre du cours "Processus de développement en ingénierie logicielle" de la HEIG-VD.

Il sert avant tout de prétexte pour l'application et l'expérimentation des outils de processus de développement logiciel vus dans ce cours.

## Utiliser statique en tant que commande
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
$ statique –version
```

Affiche la version du site statique dams le terminal
```
$ statique init <path to the folder>
```
La commande init pourrait créer ou enrichir le dossier nommé /mon/site avec un fichier de configuration config.json contenant des informations générales liées au site (titre, description, domaine, etc.) et un fichier Markdown index.md contenant une page d’accueil avec des métadonnées et du contenu.

```
$ statique build <path to the folder> [-- watch]
```

La commande build crée un dossier /mon/site/build sous le répertoire dont le chemin est passé en argument  contenant des fichiers HTML correspondant au contenu de chaque page du site statique.



L'option --watch : permet de régénérer le site statique à la volée, c'est-à-dire de manière automatique lorsque des changements sont effectués sur le système de fichier
```
$ statique clean
```

La commande clean  supprime le dossier /mon/site/build.

```
$ statique serve <path to the folder> [-- watch]
```
L’exécution de  la commande sreve permet de  visualiser le résultat de la compilation du site Internet dans un navigateur Web.



L'option --watch : permet de régénérer le site statique à la volée, c'est-à-dire de manière automatique lorsque des changements sont effectués sur le système de fichier





