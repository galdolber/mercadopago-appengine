@echo off
rem ###########################
rem ## Begin - Armamos la estructura para el generar el war

rem # Generamos la carpeta donde vamos a construir la estructura del WAR
mkdir build

rem # Copiamos el contenido de web 
xcopy /S web\* build\*

rem # Copiamos las librerias
xcopy lib\* build\WEB-INF\lib\*

rem # Creamos la carpeta donde vamos a agregar las clases compiladas
mkdir build\WEB-INF\classes

rem # Copiamos el archivo de configuracion a la carpeta de clases
copy src\Config.properties build\WEB-INF\classes\

rem # Ejecutamos la creacion del JAR
call JAR_generate.bat
pausa

rem # Copiamos el jar a las librerias
copy mercadopago-sdk-java-0.0.1.jar build\WEB-INF\lib

rem ## end - Armamos la estructura para el generar el war
rem ###########################
rem ## Begin - Generamos el WAR

rem # nos situamos en la carpeta donde estan los archivos para crear el war
cd build

rem # construimos el war y lo guardamos en el directorio raiz
jar -cfv ..\ROOT.war .

rem # Retornamos al directorio raiz
cd ..

rem ## end - Generamos el WAR
rem ###########################
