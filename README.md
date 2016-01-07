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

    struct=
    {
        data1=1
        data2={
            data3=yolo
        }
    }

The *spells* file is divided into 5 structs: `spells, boons, visualisers, volumes and movers`. These structs are where
you will add your own configurations for spells.

### Defining new spells

Now that we know how a spell is structured and stored in in the configuration we can define a new spell. In this 
example we will create a simple fireball. Let's start with creating the boon that will affect entities that are 
affected by the spell.

    awesomeBurningBoon
    {
        base=burning;
        minBurnTime=6000;
        maxBurnTime=12000;
    }

As you can see, this boon contains three variables which describe what it does. The first being `base` which tells
the plugin what internal boon type it should use. In this case burning which makes entities burn for a semi random
amount of time. 

Then there are two variables which are specific to the burning boon, minBurnTime and maxBurnTime. As you might expect
they define the time an entity will burn for when affected by the boon. Each boon base has its own variables like this
and you can set the ones you care about, they all have default values.

Let's create our first spell. 

    superAwesomeSpell=
    {
        base=modularSelf;
        boon=awesomeBurningBoon;
    }

As you can see, spells also have a base variable which in this case tells the plugin that you want to create a self 
cast spell. The modular part means that the spell behaviour depends on the modules assigned to it. Self spells only
have one attribute and that is the boon. When the spell is cast, the boon is applied to the entity that casted it.
In this case it will set the caster on fire which is probably a bad idea.

Let's make a more usefull spell. We start by defining a visualiser, a volume and a mover to go along with the boon
we created earlier.

visualisers = 
{
    fire=
    {
        base=fire;
        speed=0.1;
        range=150;
        particleAmount = 50;
    }
}

volumes = 
{
    sphere=
    {
        base=sphere;

        radius=3;
    }
}

movers = 
{
    linear= //MOves in a straight line
    {
        base=linear;
        
        speed=5;
    }
}

As you can see, all attributes in the plugin have a base variable along with a set of behaviour variables which you
can set. You can probably figure out what all of these do by yourself.

Now it's time to combine our attributes into a working spell. This time we want the spell to use our new attributes
so instead of chosing a `modularSelf` as base, we chose a `modular` base. The modular base takes one of each of the
attributes we have created and makes a spell from it.


    fireball= 
    {
        base=modular;
        mover=linear; //Change to a new mover to test something else
        boon=awesomeBurningBoon; 
        visualiser=fire;
        volume=sphere;
    }

And that's it, you have created a working fireball spell.
