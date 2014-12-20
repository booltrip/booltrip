Projet Booltrip

== Intro ==
Ce r�pertoire contient les sources et les medias cr��s pour le Hackathlon 22.

== Applications utilis�es ==
Android Studio v1.0
mongodb-win32-x86_64-2008plus-2.6.6-signed
node-v0.10.25-x64
notepad++ 6.5.2

== Contenu ==
- booltrip: appli Android
- data: donn�es GPS pour import.js
- docs: documents et pr�sentation
- media: images et sources graphiques pour l'appli Android
- mongo: base de test et les donn�es de demo
- node: serveur web, source backend, import.js, job mapreduce pour corr�ler les trajets
- booltrip-presentation.mp4: vid�o de backup

== Licences ==
Le code source est fourni en "GPL v3", voir License_GPL3.txt
http://www.gnu.org/copyleft/gpl.html

Les medias sont en "Creative Commons Attribution 4.0 International", voir License_CCA4.txt
http://creativecommons.org/

== TODO ==
- Authentication
- Cr�ation users
- Enregistrement GPS
- Envoi du trip GPS au serveur
- Ex�cution p�riodique du mapreduce.txt sur mongo
- mapreduce devrait tenir compte du sens de trajet
- D�tecter les m�mes trajets d�un utilisateur et les fusionner
- mapreduce ne fonctionnera bien que sur des trajets � haute r�solution, pas ceux de Google Maps

