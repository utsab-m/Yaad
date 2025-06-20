@echo off
javac yaad\*.java -cp ".;.\lib\jackson-annotations-2.18.1.jar;.\lib\jackson-core-2.18.1.jar;.\lib\jackson-databind-2.18.1.jar"
java -cp ".;.\lib\jackson-annotations-2.18.1.jar;.\lib\jackson-core-2.18.1.jar;.\lib\jackson-databind-2.18.1.jar" yaad.Yaad