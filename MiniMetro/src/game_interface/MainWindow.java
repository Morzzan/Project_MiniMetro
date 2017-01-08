package game_interface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game_engine.GameMap;
import game_engine.Train;

public class MainWindow extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	private GameMap gm = new GameMap();
	private JButton play,pause,rush;
	private JPanel btns=new JPanel();
	private MainPanel mPan=new MainPanel(gm);

	public MainWindow() {
		this.setTitle("Mini Metro");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
		Container pan = getContentPane();
		pan.add(mPan,BorderLayout.CENTER);
		pan.add(btns,BorderLayout.EAST);
		configureTimeGestion();
		for(int i=0;i<8;i++){
			int j=i;
			JButton b = new JButton(""+i);
			btns.add(b);
			b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(mPan.getChoice()!=null){
						gm.getLane(j).extendTail(mPan.getChoice());
					}
				}
			});
		}
		gm.getLane(0).extendTail(gm.getStations().get(0));
		gm.getLane(0).extendTail(gm.getStations().get(1));

		new Train(gm.getLane(0));

	}

	private void configureTimeGestion() {
		pause=new JButton("pause");
		play=new JButton("play");
		play.setEnabled(false);
		rush=new JButton("rush");
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.getCl().stop();
				pause.setEnabled(false);
				rush.setEnabled(true);
				play.setEnabled(true);
			}
		});
		rush.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.getCl().rush();
				pause.setEnabled(true);
				rush.setEnabled(false);
				play.setEnabled(true);
			}
		});
		play.addActionListener(new ActionListener() {
	
	@Override
			public void actionPerformed(ActionEvent e) {
				gm.getCl().play();
				pause.setEnabled(true);
				rush.setEnabled(true);
				play.setEnabled(false);
			}
		});
		btns.add(pause);
		btns.add(play);
		btns.add(rush);
	}

	public void run() {
		this.setVisible(true);
		for(;;){
			repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
