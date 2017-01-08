package game_interface;

public class InterfaceLaucher {
	public static void main(String[] Args) {
		Thread t=new Thread(new MainWindow());
		t.start();
	}
}
