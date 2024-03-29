
This installer will install LaTeXDraw in the selected directory and create a directory for shared templates. 
This last action can require administrator privileges.

*** Windows Vista ***

For Windows Vista you must use the install_vista.vbs to install LaTeXDraw; 
this script activates the "run as administrator" feature in order to install 
files in protected directories such as "Program Files". It will ask you your password.

However, it seems to have some problems while using the installer on Vista Ultimate. For
the moment, a workaround is to install latexdraw manually (see at the bottom of this page).


*** Debian/Ubuntu ***

The script install_debian_ubuntu will ask administrator privileges before launching the installer.


*** Running the installer ***

If you want to launch the installer in a console, the command is "java -jar installer.jar".

The shared templates are in the following folder is:
 - for Unix, /usr/share/latexdraw
 - for Mac OS X, /Users/Shared/latexdraw
 - for Vista, ProgramData\latexdraw
 - for other Windows, All Users\Application Data\latexdraw 
 
For Linux, a script will be created in /usr/bin to launch LaTeXDraw (if the installer is launched as root).
No shortcut is created for Windows.

The first execution of LaTeXDraw will create the profile of the current user in:
 - for Unix, ~/.latexdraw
 - for Max OS X, <user>/Library/Preferences/latexdraw
 - for Vista, <user>\AppData\Local\latexdraw
 - for other Windows, <user>\Application Data\latexdraw

This profile contains the preferences of the user and its templates.


*** HOW TO INSTALL LATEXDRAW MANUALLY ***

If you want to install LaTeXDraw without using the installer you must place "LaTeXDraw.jar", "release_notes.txt", "licence.txt", "help/" and "lib/" in the same directory.
