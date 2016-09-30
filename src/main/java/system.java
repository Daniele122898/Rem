import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Daniele on 30.09.2016.
 */
public class system {
    public static Runtime runtime = Runtime.getRuntime();

    public static void sysInfo(MessageReceivedEvent event){
        float totalRam = Math.round(runtime.totalMemory()/ 1048576F); // all it is using rn as a proccess
        float allocatedRamUse = Math.round((runtime.totalMemory() - runtime.freeMemory()) / 1048576F); //java programm itself is using
        float allocatedRam = Math.round(runtime.freeMemory() / 1048576F);
        float maxRam = Math.round(runtime.maxMemory() / 1048576F); //Max ram for JAVA
        /*
        Regarding your question: {ram usage} / 1048576F is a shortcut for {ram usage in bytes} / 1024 / 1024 => usage in MB
        Max Ram => Max amount of ram the JVM can access
        Total ram => Total amount of memory the JVM has pre-allocated
        Allocated ram => Amount of ram where the JVM has stored objects and other stuff

        ________________________

        While interpreting your code the JVM needs to store objects/variables/etc somewhere.
That "storage" is your ram (obviously).
To organize things the ram is split into two areas: The Stack and The Heap.
The Stack is a neatly organized region but only capable to store small amounts of data efficiently and fast.
The second region is called the Heap and it's basically a large unorganized pile of things.

Now if you create an object in java (like MyCar x = new MyCar()) the JVM will create a pointer "x" on the stack that points to a "MyCar" on your heap.
If you reassign x (e.g x = new MyCar("blue")) the JVM will put a new MyCar object onto the heap and change where the pointer "x" is pointing to.
(The old MyCar will be deleted by the GC later)

So the total allocated ram is the amount of space the JVM "reserved" on your Heap to throw objects to at any point in the future.
The used allocated ram is the amount of stuff the JVM has already thrown onto that pile.
The free allocated ram is basically "how many stuff the JVM can throw onto that pile until it needs to be resized"
And lastly the max usable ram is the maximum amount of ram your JVM may EVER use.(edited)
         */
        RequestBuffer.request(()->{
        try {
            event.getMessage().getChannel().sendMessage(
                    ":information_source:System Info ```" +
                            "Total Allocated Ram: " + totalRam+ " mb\n" +
                            "Used allocated Ram:  " + allocatedRamUse+ " mb\n" +
                            "Free allocated Ram:  " + allocatedRam+ " mb\n" +
                            "Max. usable Ram:     " + maxRam+ " mb\n" +
                            "```"
            );
        } catch (MissingPermissionsException|DiscordException  e) {
            e.printStackTrace();
        }
        });
    }
}
