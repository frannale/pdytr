Los códigos en ambas carpetas son compilados y ejecutados de la misma manera que server.c y client.c. Solo tener en cuenta que si se quiere ejecutar los scripts que se encuentran dentro de cada carpeta se deben seguir los siguientes nombres al compilar:

	gcc -o clientTiempo clientTiempo.c
	gcc -o serverTiempo serverTiempo.c
	gcc -o clientTiempoExacto clientTiempoExacto.c
	gcc -o serverTiempoExacto serverTiempoExacto.c

Lo unico que difieren estos pares de codigos es la forma en la que toman el tiempo, explicados en el informe. Los resultados de ejecutarlos seran numero que indican el tiempo de una comunicación medida en microsegundos. En el caso de la carpeta aprox, el cliente es quien imprime este valor y en el otro par el server se encarga de imprimir este valor.
En cuanto a los scripts, ver que en cada carpeta hay uno que corresponde al cliente y otro al server, el orden de ejecucion de los mismo no importa siempre y cuando cada uno se ejecute en una terminal diferente. La ejecución de los scripts va a crear 1000 conexiones cada uno e ira imprimiendo en la pantalla correspondiente los valores de tiempo. La salida de estos valores puede dirigirse a un archivo para luego importarlos en una hoja de calculo y realizar los calculos que se crean necesarios.
	
