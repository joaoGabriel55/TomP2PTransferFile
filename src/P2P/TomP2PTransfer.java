package p2p;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.tomp2p.dht.PeerDHT;
import net.tomp2p.peers.Number160;
import utils.ExampleUtils;
import utils.P2PConfigs;

public class TomP2PTransfer {

	//private static final String FILENAME = "F:\\GDrive\\Faculdade\\TADS 2018.1\\Sistemas Distribuídos\\workspace\\P2PTask\\src\\archive\\file.txt";

	private static final Random RND = new Random(42L);
	private static final int PEER_NR_1 = 30;
	private static final int PEER_NR_2 = 77;

	private TomP2PTransfer() {
	}
	
	/**
	 * <b>return</b> nameArchiveAndContent <b>;</b> nr(key) <b>;</b> domain <b>;</b> peerNum1 <b>;</b> peerNum2;
	 * */
	public static String startTransfer(String FILENAME) throws IOException, ClassNotFoundException, InterruptedException {
		PeerDHT master = null;
		final int nrPeers = 100;
		final int port = 4001;
		final int waitingTime = 250;
		String dataInfo = null;

		try {
			PeerDHT[] peers = ExampleUtils.createAndAttachPeersDHT(nrPeers, port);
			ExampleUtils.bootstrap(peers);// Mapeando todos peers
			master = peers[0];
			Number160 nr = new Number160(RND);

			File file = new File(FILENAME);

			dataInfo = P2PConfigs.examplePutGetConfig(peers, nr, PEER_NR_1, PEER_NR_2, file, "quaresmaDomain");
			Thread.sleep(waitingTime);
		} finally {
			if (master != null) {
				master.shutdown();
			}
		}

		return dataInfo;
	}

}
