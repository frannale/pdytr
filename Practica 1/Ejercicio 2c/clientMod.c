#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n,i, datosRestantes, checksum, tamMensaje, buffSize;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char *buffer;
    checksum = 0;

    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
     else{
     	if (argc < 4) {
     	    fprintf(stderr,"ERROR, tamaño del mensaje no indicado\n");
     	    exit(1);
        }
     }

    //Alocar memoria para el mensaje
    buffSize = atoi(argv[3]);
    buffer = (char *) malloc(sizeof(char)*buffSize);
    datosRestantes = buffSize - 1;

	//TOMA EL NUMERO DE PUERTO DE LOS ARGUMENTOS
    portno = atoi(argv[2]);
	
	//CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
	//AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
	//SOCK_STREAM - TIPO DE SOCKET 
	
    if (sockfd < 0) 
        error("ERROR opening socket");
	
	//TOMA LA DIRECCION DEL SERVER DE LOS ARGUMENTOS
    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
	
	//COPIA LA DIRECCION IP Y EL PUERTO DEL SERVIDOR A LA ESTRUCTURA DEL SOCKET
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
     serv_addr.sin_port = htons(portno);
	
	//DESCRIPTOR - DIRECCION - TAMAÑO DIRECCION
    if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");

    //Enviar tamaño del buffer
    tamMensaje = buffSize - 1;
    n = write(sockfd,&tamMensaje,sizeof(tamMensaje));

    //Crear los datos para el envio
    bzero(buffer,buffSize);	
    for(i = 0; i < buffSize - 1; i++){
	buffer[i] = 'a';
    }

    //Calcular checksum
    for(i = 0; i < buffSize - 1; i++){
	checksum += buffer[i];
    }    

   printf("\nCantidad de datos a enviar: %d \n", buffSize - 1);
    
   printf("Checksum calculado: %d \n\n", checksum);

   //Enviar checksum
   n = write(sockfd,&checksum,sizeof(checksum));


    //Escribo datos hasta que no queden mas
    while(datosRestantes > 0){ 
	    //ENVIA UN MENSAJE AL SOCKET
	    n = write(sockfd,buffer,strlen(buffer));
	    if (n < 0) 
		 error("ERROR writing to socket");
	    //Sacar esto????????
	    printf("Cantidad de datos escritos en esta iteración %d \n", n);
            *buffer += n;
	    datosRestantes -= n;
    }

    bzero(buffer,buffSize);
    //ESPERA RECIBIR UNA RESPUESTA
	n = read(sockfd,buffer,255);
    if (n < 0) 
         error("ERROR reading from socket");
    
    printf("\n%s\n",buffer);


    free(buffer);
    return 0;
}
