# Fire Forest Simulator 2000

Ce simulateur permet de simuler des feux de forêts. Il fonctionne avec un automate cellulaire.

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

4 exemples de simulations sont disponibles dans le fichier `SimulatorExpress.scala`


Pour faire une simulation avec visualisation, il suffit de mettre la simulation dans le fichier `Simulator.scala` et de lancer le fichier `Main.java`
Une simulation d'exemple avec variation de paramètre y est déjà placée.







