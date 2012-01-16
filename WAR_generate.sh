###########################
## Begin - Armamos la estructura para el generar el war

# Generamos la carpeta donde vamos a construir la estructura del WAR
mkdir build

# Copiamos el contenido de web 
cp -R web/* build/

# Copiamos las librerias
cp -R lib build/WEB-INF/

# Creamos la carpeta donde vamos a agregar las clases compiladas
mkdir build/WEB-INF/classes

# Copiamos el archivo de configuracion a la carpeta de clases
cp src/Config.properties build/WEB-INF/classes/

# Genero el jar
sh JAR_generate.sh

# Copiamos el jar a las librerias
cp mercadopago-sdk-java-0.0.1.jar build/WEB-INF/lib

## end - Armamos la estructura para el generar el war
###########################

###########################
## Begin - Generamos el WAR
# nos situamos en la carpeta donde estan los archivos para crear el war
cd build

# construimos el war y lo guardamos en el directorio raiz
jar -cfv ../ROOT.war .

# Retornamos al directorio raiz
cd ..

## end - Generamos el WAR
###########################

