Projet Booltrip

== Intro ==
Ce répertoire contient les sources et les medias créés pour le Hackathlon 22.

== Applications utilisées ==
Android Studio v1.0
mongodb-win32-x86_64-2008plus-2.6.6-signed
node-v0.10.25-x64
notepad++ 6.5.2

== Contenu ==
- booltrip: appli Android
- data: données GPS pour import.js
- docs: documents et présentation
- media: images et sources graphiques pour l'appli Android
- mongo: base de test et les données de demo
- node: serveur web, source backend, import.js, job mapreduce pour corréler les trajets
- booltrip-presentation.mp4: vidéo de backup

== Licences ==
Le code source est fourni en "GPL v3", voir License_GPL3.txt
http://www.gnu.org/copyleft/gpl.html

Les medias sont en "Creative Commons Attribution 4.0 International", voir License_CCA4.txt
http://creativecommons.org/

== TODO ==
- Authentication
- Création users
- Enregistrement GPS
- Envoi du trip GPS au serveur
- Exécution périodique du mapreduce.txt sur mongo
- mapreduce devrait tenir compte du sens de trajet
- Détecter les mêmes trajets d’un utilisateur et les fusionner
- mapreduce ne fonctionnera bien que sur des trajets à haute résolution, pas ceux de Google Maps

