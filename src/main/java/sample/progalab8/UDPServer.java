package sample.progalab8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPServer {
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT=21346;
    static LinkedList<Ticket> list = new LinkedList<>();
    static Date creationDate = new Date();
    //SocketAddress socketAddress1;
    SendThing sendThing;
    ByteBuffer bytebufferToSend = ByteBuffer.allocate(32768);
    ExecutorService service = Executors.newFixedThreadPool(5);
    private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);

    public static void main(String[] args) {
        logger.info("Server started");
        new UDPServer();
    }

    public UDPServer() {
        Receiver2 receiver2 = new Receiver2(list,creationDate);
        receiver2.connection();
        Parser parser = new Parser(list,receiver2.c);
        parser.parse();
        SerializationHelper serializationHelper = new SerializationHelper();
        DeserializationHelper deserializationHelper = new DeserializationHelper();
        Scanner sc = new Scanner(System.in);
        try {
            //ByteBuffer bytebufferToSend = ByteBuffer.allocate(2048);
            //ByteBuffer bytebufferToReceive = ByteBuffer.allocate(32768);
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            SocketAddress socketAddress = new InetSocketAddress("localhost", SERVICE_PORT);
            channel.bind(socketAddress);
            SocketAddress socketAddress1 = null;
            while (true) {
                if (System.in.available() > 0) {
                    String s = sc.next();
                    if (s.equals("exit")){
                        logger.info("Server is down");
                        System.exit(0);
                    }
                }
//                socketAddress1 = channel.receive(bytebufferToReceive);
//                if (socketAddress1 != null){
//                    logger.info("The server received a request");
//                    sendThing = (SendThing) deserializationHelper.deSerialization(bytebufferToReceive);
//                    String k = receiver2.receive(sendThing.getCommand(),sendThing.getTicket(),sendThing.getArgument(),sendThing.getLogin());
//                    Answer answer = new Answer(k);
//                    bytebufferToSend = serializationHelper.serialize(answer);
//                    channel.send(bytebufferToSend, socketAddress1);
//                    logger.info("Reply sent");
//                    bytebufferToSend.clear();
//                    bytebufferToReceive.clear();
//                }
                serializationHelper = new SerializationHelper();
                deserializationHelper = new DeserializationHelper();
                ByteBuffer bytebufferToSend = ByteBuffer.allocate(32768);
                ByteBuffer bytebufferToReceive = ByteBuffer.allocate(32768);

                SerializationHelper finalSerializationHelper = serializationHelper;
                DeserializationHelper finalDeserializationHelper = deserializationHelper;
                Runnable task = () -> listen(socketAddress1,channel,bytebufferToReceive, finalSerializationHelper, finalDeserializationHelper, receiver2, list);
                service.submit(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listen(SocketAddress socketAddress1, DatagramChannel channel, ByteBuffer bytebufferToReceive, SerializationHelper serializationHelper, DeserializationHelper deserializationHelper, Receiver2 receiver2, LinkedList list){
        try {
            socketAddress1 = channel.receive(bytebufferToReceive);
            if (socketAddress1 != null) {
                logger.info("The server received a request");
                SocketAddress finalSocketAddress = socketAddress1;
                Runnable task = () -> execution(finalSocketAddress,channel,bytebufferToReceive, serializationHelper, deserializationHelper, receiver2, list);
                service.submit(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void execution(SocketAddress socketAddress1, DatagramChannel channel, ByteBuffer bytebufferToReceive, SerializationHelper serializationHelper, DeserializationHelper deserializationHelper, Receiver2 receiver2, LinkedList list){
        sendThing = (SendThing) deserializationHelper.deSerialization(bytebufferToReceive);
        String k = receiver2.receive(sendThing.getCommand(),sendThing.getTicket(),sendThing.getArgument(),sendThing.getLogin());
        bytebufferToReceive.clear();
        Runnable task = () -> sending(socketAddress1,k,serializationHelper,channel, list);
        Thread thread = new Thread(task);
        thread.start();
    }
    public void sending(SocketAddress socketAddress1,String k, SerializationHelper serializationHelper, DatagramChannel channel, LinkedList list){
        Answer answer = new Answer(k, list);
        //System.out.println(answer.getList());
        //System.out.println(answer.toString());
        bytebufferToSend = serializationHelper.serialize(answer);
        try {
            channel.send(bytebufferToSend, socketAddress1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Reply sent");
        bytebufferToSend.clear();
    }
}