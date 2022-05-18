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

##Descriptif des commandes :

```
$ statique –version
```

Cette commande  initialise un site statique dans le dossier /mon/site

```
$ statique init /mon/site
```
la commande init pourrait créer ou enrichir le dossier nommé /mon/site avec un fichier de configuration config.yaml contenant des informations générales liées au site (titre, description, domaine, etc.) et un fichier Markdown index.md contenant une page d’accueil avec des métadonnées et du contenu.

```
$ statique build /mon/site
```

La commande build crée un dossier /mon/site/build contenant des fichiers HTML correspondant au contenu de chaque page du site statique. Certains fichiers comme les fichiers de configuration ne doivent pas être ajoutés au dossier build.

```
$ statique clean
```

la commande statique clean /mon/site de manière à nettoyer le site statique. En d’autres termes, la sous-commande clean  supprime le dossier /mon/site/build.

```
$ statique statique serve /mon/site
```
l’exécution de  la commande sreve permet de  visualiser le résultat de la compilation du site Internet dans un navigateur Web.

```
-- watch 
```

Les commandes build et serve peuvent être appelées avec l’option “--watch” ce qui leur permet de régénérer le site statique à la volée, c'est-à-dire de manière automatique lorsque des changements sont effectués sur le système de fichier.
