# mbowlscore
bowlgamescore
mvn exec:java -Dexec.mainClass="com.mt.Main"
Please download the zip file on your local machine
the zip file is https://github.com/miteash/mbowlscore/archive/repo/heads/master.zip

Please have maven installed on your local machine either windows machine or a linux machine
unzip the file
cd to the unzip folder in command line tool if using windows
cd mbowlscore-master
in this folder or directory you will see folling directories
.github
.idea
src
.gitignore
pom.xml
README.xml

at the command line prompt
type
>mvn claean package
this is build the package and you will see more directories
like 
target

please run the test
>mvn tests
This will run 8 tests to validate the edge cases

to run the commandline program
please type the following 
> mvn exec:java -Dexec.mainClass="com.mt.Main"

This will start the program and ask a user to input pin fallen per throw per frame basis and it will show the score of the game
enjoy try this out
Thanks a lot.


