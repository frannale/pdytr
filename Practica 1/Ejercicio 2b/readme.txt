Para compilar estos codigos se hace con:

gcc -o server server.c
gcc -o client client.c
gcc -o serverCheck serverCheck.c
gcc -o clientCheck clientCheck.c


Para ejecutar estos ejercicios se hacen de a pares. Primero se debe ejecutar el servidor y luego el cliente correspondiente. EL servidor y el cliente deben ejecutarse en terminales diferentes.

		server.c: este codigo es simplemente para mostrar como no siempre se leen todos los datos. Adentro del codigo hay una constante 		llamada buffSize la cual se tiene que cambiar el valor para ver los diferentes ejemplos. Ejecutar usando:
			
			./server 4000 (si el puerto esta en uso elegir otro)

		client.c: este codigo debe ser ejecutado en conjunto con server.c. AL igual que server.c tiene una constante llamada buffSize que 			debe modificarse. Si bien los buffsize pueden ser diferentes para el experimento usar el mismo tamaño tanto para el buffer del 			server como el del cliente. Para ejecutarlo:

			./client localhost 4000 (deberia ser el mismo que el puerto usado al ejecutar el server)

		serverCheck.c: este codigo es para mostrar como capturar todos los datos que el cliente quiera enviar. Junto con el puerto se 			debe especificar el tamaño del buffer que se usara para leer, este no tiene que ver con el tamaño de mensaje a recibir ya que eso 			sera determinado al ejecutar el cliente, sino que tiene que ver de a cuantos bytes irá leyendo del socket. Ejecutar:

			./serverCheck 4000 1000 (si el puerto esta en uso elegir otro, el segundo parametro es el tamaño del buffer de lectura)

		clientCheck.c: es el codigo análogo a serverCheck. El segundo argumento de este comando corresponde al tamaño del mensaje que se 			quiere enviar. Al igual que en el primer ejemplo ambos buffers, aunque no necesarios deberian ser del mismo tamaño. Ejecutar:

			./clientCheck 4000 1000 (Tanto el buffer como el socket deberian ser iguales a los usados al ejecutar el server)

Por defecto los valores de buffSize en server.c y client.c es de 1000000.
Para ambos pares de codigos tener en cuenta que los datos enviados seran el tamaño indicado menos 1. Por lo que si se quiere enviar 10⁵ datos en la constante de client.c se debera colocar el valor 100001 y para el caso de clientCheck.c la ejecución deberia ser ./serverCheck 4000 100001.
