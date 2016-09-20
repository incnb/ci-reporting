Stash API Scripts
=================

Using the groovyconsole
-----------------------
$ cd to <output-destination>
$ groovyconsole

When starting the groovyconsole, the current directory 
will be used as the base folder('.'). This is important
for locating files written out by the scripts. 

To get a report of Stash projects:
----------------------------------
Run "stash-project-report.groovy" within the Groovy Console.
This creates "stash-project-report.txt".

To get a report of Stash repos within Stash projects:
-----------------------------------------------------
Run "stash-project-repo-report.groovy" within the Groovy Console.
This creates "stash-project-repo-report.txt".

To display the Present Working Directory of the current Groovy Console:
-----------------------------------------------------------------------
Run "pwd.groovy" within the Groovy Console.
The result is displayed in the output window.

To create a single (.sh) script to clone all Stash repos:
---------------------------------------------------------
Run "clone-all.groovy" within the Groovy Console.
This creates "clone-all.sh". This script can be run from a destination where you wish to have all
Stash repos cloned. The Architect Team Desktop (KAHJAX76857) has been used for this purpose.

To create a multiple (.sh) scripts to clone Stash repos by Stash project:
-------------------------------------------------------------------------
Run "clone-scripts.groovy" within the Groovy Console.
This creates multiple scripts under the cloneScripts directory.
Specific scripts can be run from a destination where you wish to have the Stash repos cloned.
The Architect Team Desktop (KAHJAX76857) has been used for this purpose.
