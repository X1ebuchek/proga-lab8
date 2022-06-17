package sample.progalab8;

//import com.sun.xml.internal.ws.server.ServerRtException;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UDPClient {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    public static String str = "";
    public static boolean script = false;
    public static int lineCounter = 0;
//    public static Scanner sc1;
//    public static Scanner sc = new Scanner(System.in);
    public final static int SERVICE_PORT = 21346;
    public static String login = "";


    public SendThing send(Ticket ticket, String command, String argument, String login){
        return new SendThing(ticket, command, argument, login);
    }
    public Answer toServer(SendThing sendThing){
        login = sendThing.getLogin();
        SerializationHelper serializationHelper = new SerializationHelper();
        DeserializationHelper deserializationHelper = new DeserializationHelper();
        Answer answer = null;
        ByteBuffer bytebufferToReceive = ByteBuffer.allocate(32768);
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            SocketAddress socketAddress = new InetSocketAddress("localhost", SERVICE_PORT);

            ByteBuffer bytebufferToSend = serializationHelper.serialize(sendThing);
            channel.send(bytebufferToSend, socketAddress);
            bytebufferToSend.clear();

            SocketAddress bebra = null;
            Long time1 = System.currentTimeMillis();
            while (bebra==null){
                bebra = channel.receive(bytebufferToReceive);
                Long time2 = System.currentTimeMillis();
                if (time2-time1>5000){
                    return new Answer(res.getString("server_error"), null);
                }
            }
            answer = (Answer) deserializationHelper.deSerialization(bytebufferToReceive);
            char q[] = answer.getS().toCharArray();
            if (q[0]=='>'){
                String s = "";
                for (int i = 1;i<answer.getS().length();i++){
                    s += q[i];
                }
                MainController.date = s;
                answer.setS(s);
                return answer;
            }
            answer.setS(res.getString(answer.getS()));
            //String k = answer.getS();
            return answer;
        }catch (Exception e){

        }
        return answer;
    }

}
