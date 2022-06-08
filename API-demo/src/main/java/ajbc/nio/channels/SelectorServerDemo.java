package ajbc.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SelectorServerDemo {

	private static final int PORT = 9090, BUFFER_SIZE = 256;
	private static List<SelectionKey> allConecting = new ArrayList<SelectionKey>();

	public static void main(String[] args) throws IOException, InterruptedException {
		Selector selector = Selector.open();

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress(PORT));
		serverChannel.configureBlocking(false);

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		while(true) {
//			selector.select(); //blocking
			while(selector.selectNow()==0);//non-blocking
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while(iter.hasNext()) {
				
				SelectionKey key = iter.next();
				
				System.out.println("blu blu blu");
				allConecting.add(key);
				
				System.out.println("rrrr");
				
				if(key.isAcceptable()) {
					registerServer(selector, serverChannel);
				}
				else
					if(key.isReadable()) {
						echo(buffer, key);
					}
				iter.remove();
			}
		}

	}

	private static void echo(ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {
		System.out.println("hi");
		
		for (SelectionKey socketChannel : allConecting) {
			if(!socketChannel.equals((SocketChannel) key.channel())) {
				
				System.out.println("hi");
				SocketChannel client = (SocketChannel) key.channel();
				
				client.read(buffer);
				process();
				buffer.flip();
				client.write(buffer);
				buffer.clear();
			}
		}
		
		
		
		
//		Set<SelectionKey> selectedKeys = selector.selectedKeys();
//		Iterator<SelectionKey> iter = selectedKeys.iterator();
//		SelectionKey key;
//		while(iter.hasNext()) {
//			System.out.println("hi");
//			key = iter.next();
//			SocketChannel client = (SocketChannel) key.channel();
//			
//			client.read(buffer);
//			process();
//			buffer.flip();
//			client.write(buffer);
//			buffer.clear();
//		}
		
		
		
	}

	private static void registerServer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
	}

	private static void process() throws InterruptedException {
//		System.out.println(" ... PROCESSING ...");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println("----- FINISHED -----");
		
	}
}
