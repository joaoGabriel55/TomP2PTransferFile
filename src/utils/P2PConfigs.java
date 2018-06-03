package utils;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFutureAdapter;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;

public class P2PConfigs {

	/**
	 * Basic example for storing and retrieving content.
	 * 
	 * @param peers
	 *            The peers in this P2P network
	 * @param nr
	 *            The number where the data is stored
	 * @throws IOException
	 *             e.
	 * @throws ClassNotFoundException
	 *             .
	 */
	public static void examplePutGet(final PeerDHT[] peers, final Number160 nr, int PEER_NR_1, int PEER_NR_2)
			throws IOException, ClassNotFoundException {
		FuturePut futurePut = peers[10].put(nr).data(new Data("hallo")).start();
		futurePut.awaitUninterruptibly();
		System.out.println("peer " + PEER_NR_1 + " stored [key: " + nr + ", value: \"hallo\"]");
		FutureGet futureGet = peers[20].get(nr).start();
		futureGet.awaitUninterruptibly();
		System.out.println("peer " + PEER_NR_2 + " got: \"" + futureGet.data().object() + "\" for the key " + nr);
		// the output should look like this:
		// peer 30 stored [key: 0xba419d350dfe8af7aee7bbe10c45c0284f083ce4, value:
		// "hallo"]
		// peer 77 got: "hallo" for the key 0xba419d350dfe8af7aee7bbe10c45c0284f083ce4
	}

	public static String examplePutGetConfig(PeerDHT[] peers, Number160 nr, int peerNum1, int peerNum2, File file,
			String domain) throws IOException, ClassNotFoundException {
		// Number160 nr = new Number160(RND);
		FuturePut futurePut = peers[peerNum1].put(nr).data(new Number160(11), new Data(file))
				.domainKey(Number160.createHash(domain)).start();
		futurePut.awaitUninterruptibly();
		System.out.println("peer 30 stored [key: " + nr + ", value: \"hallo\"]");
		// this will fail, since we did not specify the domain
		FutureGet futureGet = peers[peerNum2].get(nr).all().start();
		futureGet.awaitUninterruptibly();
		System.out.println("peer 77 got: \"" + futureGet.data() + "\" for the key " + nr);
		// this will succeed, since we specify the domain
		futureGet = peers[peerNum2].get(nr).all().domainKey(Number160.createHash(domain)).start()
				.awaitUninterruptibly();
		System.out.println("peer 77 got: \"" + futureGet.data().object() + "\" for the key " + nr);
		// the output should look like this:
		// peer 30 stored [key: 0x8992a603029824e810fd7416d729ef2eb9ad3cfc, value:
		// "hallo"]
		// peer 77 got: "hallo" for the key 0x8992a603029824e810fd7416d729ef2eb9ad3cfc

		String nameAndContent = FileHandler.getFile(futureGet.data().object().toString());
		//System.out.println(nameAndContent + ";" + nr + ";" + domain + ";" + peerNum1 + ";" + peerNum2);

		return nameAndContent + ";" + nr + ";" + domain + ";" + peerNum1 + ";" + peerNum2;
	}

	public static void exampleAddGet(PeerDHT[] peers, Random RND) throws IOException, ClassNotFoundException {
		Number160 nr = new Number160(RND);
		String toStore1 = "hallo1";
		String toStore2 = "hallo2";
		Data data1 = new Data(toStore1);
		Data data2 = new Data(toStore2);
		FuturePut futurePut = peers[30].add(nr).data(data1).start();
		futurePut.awaitUninterruptibly();
		System.out.println("added: " + toStore1 + " (" + futurePut.isSuccess() + ")");
		futurePut = peers[50].add(nr).data(data2).start();
		futurePut.awaitUninterruptibly();
		System.out.println("added: " + toStore2 + " (" + futurePut.isSuccess() + ")");
		FutureGet futureGet = peers[77].get(nr).all().start();
		futureGet.awaitUninterruptibly();
		System.out.println("size: [" + futureGet.dataMap().size() + "]");
		Iterator<Data> iterator = futureGet.dataMap().values().iterator();
		System.out.println("got: " + iterator.next().object() + " (" + futureGet.isSuccess() + ")");
		System.out.println("got: " + iterator.next().object() + " (" + futureGet.isSuccess() + ")");
	}

	/**
	 * Example of a blocking operation and what happens after.
	 * 
	 * @param peers
	 *            The peers in this P2P network
	 * @param nr
	 *            The number where the data is stored
	 * @throws ClassNotFoundException
	 *             .
	 * @throws IOException
	 *             .
	 */
	public static void exampleGetBlocking(final PeerDHT[] peers, final Number160 nr, int PEER_NR_2)
			throws ClassNotFoundException, IOException {
		FutureGet futureGet = peers[PEER_NR_2].get(nr).start();
		// blocking operation
		futureGet.awaitUninterruptibly();
		System.out.println("result blocking: " + futureGet.data().object());
		System.out.println("this may *not* happen before printing the result");
	}

	/**
	 * Example of a non-blocking operation and what happens after.
	 * 
	 * @param peers
	 *            The peers in this P2P network
	 * @param nr
	 *            The number where the data is stored
	 */
	public static void exampleGetNonBlocking(final PeerDHT[] peers, final Number160 nr, int PEER_NR_2) {
		FutureGet futureGet = peers[PEER_NR_2].get(nr).start();
		// non-blocking operation
		futureGet.addListener(new BaseFutureAdapter<FutureGet>() {
			@Override
			public void operationComplete(FutureGet future) throws Exception {
				System.out.println("result non-blocking: " + future.data().object());
			}

		});
		System.out.println("this may happen before printing the result");
	}

}
