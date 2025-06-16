# Fire Forest Simulator 2000

Ce simulateur permet de simuler des feux de forêts. Il fonctionne avec un automate cellulaire.

## Faire une simulation

Pour créer un simulation, il faut créer une instance de la classe Simulation :

```
// Simulation simple 40% d'arbre dans une grille de 50x50 avec 0 rocher
val sim: Simulation = new Simulation(50, 50, 40, 0)
```

Les paramètres disponibles :
- La taille de la grille
- Le pourcentage de case d’arbre
- Le nombre de rocher
- La direction du vent
- L’intensité du vent
- L’humidité
- La repousse des arbres
- Les éclaires
- Un monde déjà généré

Les types de cellules disponibles :
- Vide
- Arbre
- Feu
- Cendre
- Eau
- Pierre


Pour éxécuter une seul itération de la simulation :

```
sim.update()
```

Pour éxécuter X itérations :

```
sim.update(X)
```

4 exemples de simulations sont disponibles dans le fichier `SimulatorExpress.scala`.


Pour faire une simulation avec visualisation, il suffit de mettre la simulation dans le fichier `Simulator.scala` et de lancer le fichier `Main.java`.
Une simulation d'exemple avec variation de paramètre y est déjà placée.
(Pour modifier la vitesse de calcule pour ralentir ou accélérer la visualisation, il suffit de modifier la ligne `updateSync.Sync(...);` dans le fichier `Main.java`)


## Récuperer, Comprendre et Visualiser les résultats

Dans les 4 exemples du fichier `SimulatorExpress.scala`, les résultats enregistré sont retourné avec le format ci-dessous :

```
*********************************************************************
Simulation : Densité de forêt
X=[0, 1, 2, 3, 4, 5, ... 99, ]
Y=[0, 4, 2, 1, 1, 1, ... 100, ]
*********************************************************************
```

On peut les visualiser très facilement avec un petit programme python :

```
X=[0, 1, 2, 3, ...
Y=[0, 1, 0, 0, ...
plt.plot(X, Y)
plt.title("Simulation : Pourcentage de forêt")
plt.xlabel("% d'arbre généré")
plt.ylabel("% d'arbre brûlé")
plt.ylim((0, 100))
plt.grid()
plt.show()
```


## Screenshots
![image](https://github.com/user-attachments/assets/c7dd225d-342c-4d46-be03-b79339798506)




