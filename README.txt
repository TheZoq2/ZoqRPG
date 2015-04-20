Usage instructions.
Notes

The code in com.darkblade12.... is not written by me and is used for creating particle effects
please ignore it. 


Because this is a minecraft server plugin, you need to have minecraft installed
and a server version capable of running the plugin. I have included a zip file called 
server.zip which contains a working server with the plugin added. 

Navigate to that folder and run start.sh to run the server.

Now, open minecraft, click multiplayer, click on direct connect and use localhost as the IP.
You should now be on the server and be able to test the plugin.

If you want to make changes and recompile the plugin, you will need to export a .jar file
of the project. Make sure to include plugin.yml in that file, otherwise minecraft won't
load it propperly. The project contains a build artifact which exports a working .jar file
to the appropriate location on my computer. change the export path to server/plugins to 
make it work on your system. Once it is recompiled, type reload in the console to start
using the new version.

Current features:
Right now the plugin is really lacking in the feature department. The only two features 
implemented are two spells which you can activate using right click. If useFire is set to 
true at line 116 in RPGMain.java, a fireball spell will be used, right click to fire. If
you set it to false, a teleportation spell will be used when you right click. Right click once
to start teleporting and once more to teleport to where you are looking. 

Almost all the code is infrastructure for adding more spells in the future which means both
those spells use the same system for creation.

I have spent a lot of time working on reading datafiles for configuring the plugin. The
code for that can be found in the fileio package.


