package kongmeng.windsonglyre;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public class Test1 extends MIDI {
	public static Robot rb;

	public Test1(String str) throws InvalidMidiDataException, IOException {
		super(str);
	}

	@Override
	public void a(int iii, byte[] message, String hex) {
		// C C# D D# E F F# G G# A A# B
		// Ô­Éñ
//		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_Y, KeyEvent.VK_U };
		// vrc3
//		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_COMMA, KeyEvent.VK_X, KeyEvent.VK_PERIOD, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_SLASH, KeyEvent.VK_B, KeyEvent.VK_NUMPAD0, KeyEvent.VK_N, KeyEvent.VK_DECIMAL, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_K, KeyEvent.VK_S, KeyEvent.VK_L, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_SEMICOLON, KeyEvent.VK_G, KeyEvent.VK_NUMPAD2, KeyEvent.VK_H, KeyEvent.VK_NUMPAD3, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_O, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_P, KeyEvent.VK_T, KeyEvent.VK_NUMPAD5, KeyEvent.VK_Y, KeyEvent.VK_NUMPAD6, KeyEvent.VK_U, KeyEvent.VK_1, KeyEvent.VK_8, KeyEvent.VK_2, KeyEvent.VK_9, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_0, KeyEvent.VK_5, KeyEvent.VK_NUMPAD8, KeyEvent.VK_6, KeyEvent.VK_NUMPAD9, KeyEvent.VK_7, KeyEvent.VK_F1, KeyEvent.VK_F8, KeyEvent.VK_F2, KeyEvent.VK_F9, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F10, KeyEvent.VK_F5, 111, KeyEvent.VK_F6, 106, KeyEvent.VK_F7 };
		// Ä£ÄâÍøÕ¾4
		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_COMMA, KeyEvent.VK_X, KeyEvent.VK_PERIOD, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_SLASH, KeyEvent.VK_B, KeyEvent.VK_NUMPAD0, KeyEvent.VK_N, KeyEvent.VK_DECIMAL, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_K, KeyEvent.VK_S, KeyEvent.VK_L, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_SEMICOLON, KeyEvent.VK_G, KeyEvent.VK_NUMPAD2, KeyEvent.VK_H, KeyEvent.VK_NUMPAD3, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_O, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_P, KeyEvent.VK_T, KeyEvent.VK_NUMPAD5, KeyEvent.VK_Y, KeyEvent.VK_NUMPAD6, KeyEvent.VK_U, KeyEvent.VK_1, KeyEvent.VK_8, KeyEvent.VK_2, KeyEvent.VK_9, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_0, KeyEvent.VK_5, KeyEvent.VK_NUMPAD8, KeyEvent.VK_6, KeyEvent.VK_NUMPAD9,
				KeyEvent.VK_7 };

		if (hex.startsWith("9") || hex.startsWith("8")) {
			int k = message[1];
			k -= (12 * 4);// VRCÎª3
//			System.out.println(k);
//			System.out.println(key.length);
			if (k < 0 || k >= key.length) {
				//Integer.toHexString(message[1] & 0xFF)
				System.err.println("Òô¹ì" + iii + " ÎÞ·¨ÊÊÅäÒô·û:" + k + " Á¦¶È:" + (int) message[2] +" [" +bytesToHexString(message)+"]");
			} else {
				if (hex.startsWith("9")) {
					if ((int) message[2] >20) {
						rb.keyRelease(key[k]);
						rb.keyPress(key[k]);
						System.out.println("Òô¹ì" + iii + " ´¥·¢Òô·û:" + k + " Á¦¶È:" + (int) message[2] +" [" +bytesToHexString(message)+"]");
					}else {
//						System.out.println("Òô¹ì" + iii + " " + "·ÅÆúÒô·û" + bytesToHexString(message) + " Á¦¶È£º" + (int) message[2]);
					}
				} else if (hex.startsWith("8")) {
//					System.out.println("Òô¹ì" + iii + " " + "ËÉ¿ªÒô·û" + bytesToHexString(message) + " Á¦¶È£º" + (int) message[2]);
					rb.keyRelease(key[k]);
				}
			}
		}
	}

	public static void main(String[] args) throws InvalidMidiDataException, IOException, AWTException {
		rb = new Robot();// ¼üÅÌ²Ù×÷
		// Æô¶¯ÑÓ³Ù
		for (int i = 5; i > 0; i--) {
			System.out.println(i);
			rb.delay(1000);
		}
		new Test1("./midi/ÄñÖ®Ê« - Air.mid");
	}

}
