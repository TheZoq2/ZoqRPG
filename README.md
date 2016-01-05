Zoq RPG
====
Zoq RPG is a minecraft plugin that adds RPG features to spigot or bukkit servers. In its current state, the plugin adds a customizeable spell system with spells ranging from short range teleports to fireballs. The spells can be customized by mixing basic components to create new and interesting spells.

The spell system
----

### Structure
There are 2 distinct kinds of spells supported by the plugin. Self cast and projectiles. Spells consist of one or four
different components. A self spell has one component and that is a *boon*. The boon defines what happens when an 
an entity is affected by it and naturally the *self spell* applies that boon to the caster.

Just like self spells, projectile spells contain a *boon* but they also contain a *mover*, a *visualiser* and a 
*volume*. The mover defines the path that the projectile will move, the visualiser defines the look of the projectile
as it is moving through the air and the volume defines what part of the world will be affected by the spell.

### Configuration
Spell information is stored in the 'spells' file in the plugin folder. The file uses a custom data format to group
data together and there are two distinct data types. Values and structs. Structs contain a chunk of other data
while 'values' contain actual data. 

In order to define a new variable you type the name of the variable followed by an `=` sign.

    variable=someValue

In order to store a data value, the `=` sign should be followed by the desired value followed by a `;` to mark
the end of the value.

    maxRange=1000;

In order to store a struct of data, the = sign should be followed by a `{` and a `}` at the end of the struct.
Inside the struct you can put any number of variables of any type you like.




