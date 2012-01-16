@echo off
rem ##########################################################
rem # Generar el WAR con el JAR mercadopago-sdk-java-0.0.1.jar
rem ###########################
rem ## Begin - Generar el jar
rem # Creamos la carpeta para almacenar los archivos .class para generar el war
mkdir mercadopago-sdk-java-0.0.1

rem # Compilamos las clases y guardamos los .class en mercadopago-sdk-java-0.0.1
javac -cp lib/commons-codec-1.5.jar;lib/commons-httpclient-3.0.1.jar;lib/commons-logging-1.1.1.jar;lib/javax.servlet.jar;lib/jettison-1.0.1.jar;lib/joda-time.jar src/classes/* src/utils/* src/services/* src/samples/accessToken/create/CreateAccessTokenSample.java src/samples/accessToken/refresh/RefreshAccessTokenSample.java src/samples/checkoutPreference/create/CreateCheckoutPreferenceSample.java src/samples/checkoutPreference/get/GetCheckoutPreferenceSample.java src/samples/checkoutPreference/update/UpdateCheckoutPreferenceSample.java src/samples/collection/search/SearchCollectionSample.java -d mercadopago-sdk-java-0.0.1

rem # Nos situamos en la carpeta donde estan los archivos para crear el jar
cd mercadopago-sdk-java-0.0.1

rem # Generamos el jar
jar -cf ../mercadopago-sdk-java-0.0.1.jar .

rem # Retornamos al directorio raiz
cd ..
rem ## end  - Generar el jar
rem ###########################

