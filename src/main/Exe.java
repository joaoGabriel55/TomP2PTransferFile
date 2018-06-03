package main;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import p2p.TomP2PTransfer;

public class Exe {
	private Display display = new Display();
	private Shell shell = new Shell(display);
	
	private String dataTemp = null;
	private String nameArchive = null;
	private String content = null;
	private String key = null;
	private String domain = null;
	private String peerN1 = null;
	private String peerN2 = null;
	

	public Exe() {
		shell.setText("TomP2P");

		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.verticalSpacing = 16;

		shell.setLayout(gridLayout);

		// Title
		Label label = new Label(shell, SWT.NULL);
		label.setText("Archive: ");

		Text title = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		title.setLayoutData(gridData);
		
		Label archiveNameContent = new Label(shell, SWT.NULL);
		archiveNameContent.setText("Archive/Content: ");
		archiveNameContent.setVisible(false);

		Label archiveNameContentTx = new Label(shell, SWT.SINGLE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		archiveNameContentTx.setLayoutData(gridData);

		Label domainLabel = new Label(shell, SWT.NULL);
		domainLabel.setText("Domain: ");
		domainLabel.setVisible(false);

		Label domainTx = new Label(shell, SWT.SINGLE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		domainTx.setLayoutData(gridData);

		Label keyLabel = new Label(shell, SWT.NULL);
		keyLabel.setText("Key: ");
		keyLabel.setVisible(false);

		Label keyText = new Label(shell, SWT.SINGLE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		keyText.setLayoutData(gridData);

		Label sendLabel = new Label(shell, SWT.NULL);
		sendLabel.setText("Send by: ");
		sendLabel.setVisible(false);

		Label send = new Label(shell, SWT.SINGLE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		send.setLayoutData(gridData);

		Label receiveLabel = new Label(shell, SWT.NULL);
		receiveLabel.setText("Received by: ");
		receiveLabel.setVisible(false);

		Label receive = new Label(shell, SWT.SINGLE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		receive.setLayoutData(gridData);
		
		title.setText("C:/User/somefile.*                                                                                       ");// Gambs

		// Button.
		Button enter = new Button(shell, SWT.PUSH);
		enter.setText("Enter");

		enter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				try {
					dataTemp = TomP2PTransfer.startTransfer(title.getText().trim());
					String[] dataInfo = dataTemp.split(";");

					nameArchive = dataInfo[0];
					content = dataInfo[1];
					key = dataInfo[2];
					domain = dataInfo[3];
					peerN1 = dataInfo[4];
					peerN2 = dataInfo[5];

					archiveNameContent.setVisible(true);
					archiveNameContentTx.setText(nameArchive + " / " + content);
					
					sendLabel.setVisible(true);
					send.setText("PeerN1: ["+peerN1+ "]");
					
					receiveLabel.setVisible(true);
					receive.setText("PeerN2: ["+peerN2+ "]");
					
					keyLabel.setVisible(true);
					keyText.setText(key);
					
					domainLabel.setVisible(true);
					domainTx.setText(domain);
					

				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = GridData.END;
		enter.setLayoutData(gridData);

		shell.pack();
		shell.open();

		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in event queue
				display.sleep();
			}
		}

		display.dispose();
	}

	public static void main(String[] args) {
		new Exe();
	}
}
