/*
* AskRemote.java
* a) Looks up for the remote object
* b) "Makes" the RMI
*/
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
public class AskRemote
{
public static void main(String[] args)
{
/* Look for hostname and msg length in the command line */
if (args.length != 1)
{
System.out.println("1 argument needed: (remote) hostname");
System.exit(1);
}
try {
    String rname = "//" + args[0] + ":" + Registry.REGISTRY_PORT + "/remote";
    ITimeRemoteClass remote = (ITimeRemoteClass) Naming.lookup(rname);

    /* Take the time before the comunication */
    long intialTime = System.nanoTime();

    remote.sendTrue();

    /* Take the time after the comunication */
    long finalTime = System.nanoTime();

    /* The aproximated time of each individual comunication */
    long aproximatedTime = ((finalTime - intialTime)/2);

    /* Print the time of an individual comunicattion */
    System.out.println(aproximatedTime);

} catch (Exception e) {
    e.printStackTrace();
}
}
}