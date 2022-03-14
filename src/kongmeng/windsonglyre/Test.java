package kongmeng.windsonglyre;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class Test {
	static Robot rb;
	static long ttt = 0;

	public static void main(String[] args) throws InvalidMidiDataException, IOException, MidiUnavailableException, InterruptedException, AWTException {
		rb = new Robot();// 键盘操作
		// 启动延迟
		for (int i = 5; i > 0; i--) {
			System.out.println(i);
			rb.delay(1000);
		}

		// C C# D D# E F F# G G# A A# B
		// 原神
		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_Y, KeyEvent.VK_U };
		// vrc
//		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_COMMA, KeyEvent.VK_X, KeyEvent.VK_PERIOD, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_SLASH, KeyEvent.VK_B, KeyEvent.VK_NUMPAD0, KeyEvent.VK_N, KeyEvent.VK_DECIMAL, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_K, KeyEvent.VK_S, KeyEvent.VK_L, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_SEMICOLON, KeyEvent.VK_G, KeyEvent.VK_NUMPAD2, KeyEvent.VK_H, KeyEvent.VK_NUMPAD3, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_O, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_P, KeyEvent.VK_T, KeyEvent.VK_NUMPAD5, KeyEvent.VK_Y, KeyEvent.VK_NUMPAD6, KeyEvent.VK_U, KeyEvent.VK_1, KeyEvent.VK_8, KeyEvent.VK_2, KeyEvent.VK_9, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_0, KeyEvent.VK_5, KeyEvent.VK_NUMPAD8, KeyEvent.VK_6, KeyEvent.VK_NUMPAD9, KeyEvent.VK_7, KeyEvent.VK_F1, KeyEvent.VK_F8, KeyEvent.VK_F2, KeyEvent.VK_F9, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F10, KeyEvent.VK_F5, 111, KeyEvent.VK_F6, 106, KeyEvent.VK_F7 };
		// 模拟网站
//		int[] key = { KeyEvent.VK_Z, KeyEvent.VK_COMMA, KeyEvent.VK_X, KeyEvent.VK_PERIOD, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_SLASH, KeyEvent.VK_B, KeyEvent.VK_NUMPAD0, KeyEvent.VK_N, KeyEvent.VK_DECIMAL, KeyEvent.VK_M, KeyEvent.VK_A, KeyEvent.VK_K, KeyEvent.VK_S, KeyEvent.VK_L, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_SEMICOLON, KeyEvent.VK_G, KeyEvent.VK_NUMPAD2, KeyEvent.VK_H, KeyEvent.VK_NUMPAD3, KeyEvent.VK_J, KeyEvent.VK_Q, KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_O, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_P, KeyEvent.VK_T, KeyEvent.VK_NUMPAD5, KeyEvent.VK_Y, KeyEvent.VK_NUMPAD6, KeyEvent.VK_U, KeyEvent.VK_1, KeyEvent.VK_8, KeyEvent.VK_2, KeyEvent.VK_9, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_0, KeyEvent.VK_5, KeyEvent.VK_NUMPAD8, KeyEvent.VK_6, KeyEvent.VK_NUMPAD9, KeyEvent.VK_7 };

		File sound = new File("./midi/白色相簿2 - After All 缀る想い.mid");
		Sequence seq = MidiSystem.getSequence(sound);
//		play(seq);

		long t = seq.getResolution();// 获得一个4分音符的ticks数

		Track[] tracks = seq.getTracks();
		for (int ia = 0; ia < tracks.length; ia++) {

			int iii = ia;
			new Thread(new Runnable() {

				@Override
				public void run() {

					Track track = tracks[iii];
					long lastsleep = 0;
					for (int i = 0; i < track.size(); i++) {
						int ii = i;
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
						MidiEvent midievent = track.get(ii);
						MidiMessage midimessage = midievent.getMessage();
						byte[] message = midimessage.getMessage();
						String hex = bytesToHexString(message);
						try {
							Thread.sleep((midievent.getTick() - lastsleep) * ttt / 1000);
							lastsleep = midievent.getTick();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						int type = 0;
						if (hex.startsWith("9") || hex.startsWith("8")) {
							if(type<7) {

							int k = message[1];
							k -= (12 * 4);// VRC为3
							if (k < 0 || k >= key.length) {
								System.err.println("音轨" + iii + " " + "无法适配音符k=" +k+"    "+ Integer.toHexString(message[1] & 0xFF));
							} else {
								if (hex.startsWith("9")) {
									rb.keyPress(key[k]);
									rb.keyRelease(key[k]);
								} else if (hex.startsWith("8")) {
									rb.keyRelease(key[k]);
								}
//								System.out.println("音轨" + iii + " " + "触发音符" + bytesToHexString(message));
							}
							}
						} else if (hex.startsWith("A")) {
//							System.out.println("音轨" + iii + " " + "触后音符" + bytesToHexString(message));
						} else if (hex.startsWith("B")) {
//							System.out.println("音轨" + iii + " " + "控制器变化" + bytesToHexString(message));
						} else if (hex.startsWith("C")) {
							type = (int)(message[1] & 0xFF);
							System.out.println("音轨" + iii + " " + "改变乐器" + bytesToHexString(message) + "____"+type);
						} else if (hex.startsWith("D")) {
//							System.out.println("音轨" + iii + " " + "通道触动压力" + bytesToHexString(message));
						} else if (hex.startsWith("E")) {
//							System.out.println("音轨" + iii + " " + "弯音轮变换" + bytesToHexString(message));
						} else if (hex.startsWith("FF 03")) {
							System.out.println("音轨" + iii + "=>" + new String(hexStringToByteArray(hex.substring(9).replace(" ", ""))));
						} else if (hex.startsWith("FF 04")) {
							System.out.println("音轨" + iii + " " + "指定乐器=>" + hex);
						} else if (hex.startsWith("FF 51")) {
							byte[] a = { 0x00, message[3], message[4], message[5] };
							ttt = bytesToInt2(a, 0) / t;
							System.out.println("音轨" + iii + " " + "指定速度" + hex + "__" + bytesToInt2(a, 0));
						} else {
							System.out.println("音轨" + iii + " " + bytesToHexString(message));
						}
//					}
//				}).start();
					}
				}
			}).start();
		}
	}

	// byte数组转int
	public static int bytesToInt2(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
		return value;
	}

	// byte数组转16进制文本
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
			sb.append(" ");
		}
		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] b = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
			b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return b;
	}

	public static void play(Sequence seq) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		Sequencer midi = MidiSystem.getSequencer();
		midi.open();
		midi.setSequence(seq);
		midi.start();
		Thread.sleep(midi.getMicrosecondLength() / 1000);
		midi.stop();
		midi.close();
	}
}
