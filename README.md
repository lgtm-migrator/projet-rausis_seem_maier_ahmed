[![Total alerts](https://img.shields.io/lgtm/alerts/g/dil-classroom/projet-rausis_seem_maier_ahmed.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/dil-classroom/projet-rausis_seem_maier_ahmed/alerts/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/dil-classroom/projet-rausis_seem_maier_ahmed.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/dil-classroom/projet-rausis_seem_maier_ahmed/context:java)
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