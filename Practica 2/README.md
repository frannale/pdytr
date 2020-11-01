INTRUCCIONES DE EJECUCION:

    Para compilar todo en caso de ser necesario: 

        javac -d . AskRemote.java FileManager.java IFaceFileManager.java StartRemoteObject.java

    Para correr el server:

        rmiregistry &
        ./runServer.sh

    Para correr cliente:

        java  -classpath . AskRemote

    El cliente puede configurarse de forma interactiva sin enviar parametros o mediante el envio de los mismos:

        Primer parametro el numero identificador de operacion: 
            1 - Subir archivo 
            2 - Descargar archivo 
            3 - Descargar verificado
        Segundo parametro:
            Nombre del archivo a manipular

INTRUCCIONES PARA EJECUCION CON TOMA DE TIEMPOS:

    Correr el server: ./timeRMIServer.sh
    Correr el cliente guardando tiempos en fle: ./timeRMIServer.sh > tiempos.txt

INTRUCCIONES PARA EJECUCION CONCURRENTE:

    Correr el script ./Clientes/runClientes.sh $1= action $2=filename
    Ejecuta de form concurrente 3 clientes en directorios diferentes con los parametros enviados, ofrenciendo total independencia entre los clientes.

CONSIDERACIONES A TENER EN CUENTA:

    Cabe aclarar que para el correcto del funcionamiento el filename indicado debe existir, en el directorio del cliente
    si se quiere cargar en el servidor, tanto como en el servidor se se quiere realizar una descarga.


