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
import javax.sound.midi.Track;

public class Test {
	static Robot rb;

	public static void main(String[] args) throws InvalidMidiDataException, IOException, MidiUnavailableException, InterruptedException, AWTException {
		rb = new Robot();// 键盘操作
		// 启动延迟
		for (int i = 5; i > 0; i--) {
			System.out.println(i);
			rb.delay(1000);
		}

		int[] key = { KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M };
		byte[] val = { 0x48, 0x4A, 0x4C, 0x4D, 0x4F, 0x51, 0x53, 0x3C, 0x3E, 0x40, 0x41, 0x43, 0x45, 0x47, 0x30, 0x32, 0x34, 0x35, 0x37, 0x39, 0x3B };

		File sound = new File("./midi/111111111.mid");
		Sequence seq = MidiSystem.getSequence(sound);
		long t = seq.getResolution();// 获得一个4分音符的ticks数

		Track[] tracks = seq.getTracks();
		for (int i = 0; i < tracks.length; i++) {
			int iii = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					Track track = tracks[iii];
					byte[] mess = new byte[4];
					System.arraycopy(tracks[1].get(0).getMessage().getMessage(), 3, mess, 1, 3);
					int tt = bytesToInt2(mess, 0);// 从全局音轨得到播放速度

					long ttt = tt / t;// 计算1tick等于多少微妙

					for (int i = 0; i < track.size(); i++) {
						int ii = i;
						new Thread(new Runnable() {
							@Override
							public void run() {
								MidiEvent midievent = track.get(ii);
								MidiMessage midimessage = midievent.getMessage();

								try {
									Thread.sleep(track.get(ii).getTick() * ttt / 1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								byte[] message = midimessage.getMessage();

								// String hex = Integer.toHexString(message[0] & 0xFF);
								String hex = bytesToHexString(message).split(" ")[0];

								if (hex.startsWith("A")) {
									System.out.println("音轨" + iii + " " + "触后音符" + bytesToHexString(message));
								} else if (hex.startsWith("B")) {
									System.out.println("音轨" + iii + " " + "控制器变化" + bytesToHexString(message));
								} else if (hex.startsWith("C")) {
									System.out.println("音轨" + iii + " " + "改变乐器" + bytesToHexString(message));
								} else if (hex.startsWith("D")) {
									System.out.println("音轨" + iii + " " + "通道触动压力" + bytesToHexString(message));
								} else if (hex.startsWith("E")) {
									System.out.println("音轨" + iii + " " + "弯音轮变换" + bytesToHexString(message));
								} else if (hex.startsWith("9") || hex.startsWith("8")) {
									boolean flag = true;
//
									for (int j = 0; j < val.length; j++) {
										if (message[1] == val[j]) {
											// 按下按键
											if (hex.startsWith("9")) {
												rb.keyPress(key[j]);
												rb.keyRelease(key[j]);
												System.out.println("音轨" + iii + " " + "触发音符" + bytesToHexString(message));
											}
											// 松开按键
//											if (hex.startsWith("8")) {
//												rb.keyRelease(key[j]);
//											}
											flag = false;
											break;
										}
									}

									if (flag) {
										if (hex.startsWith("9"))
											System.err.println("音轨" + iii + " " + "无法适配音符" + Integer.toHexString(message[1] & 0xFF));
									}
								} else {
									System.out.println("音轨" + iii + " " + bytesToHexString(message));
								}
							}
						}).start();
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
//	Sequencer midi = MidiSystem.getSequencer();
//	midi.open();
//	midi.setSequence(seq);
//	midi.start();
//	Thread.sleep(midi.getMicrosecondLength() / 1000);
//	midi.stop();
//	midi.close();
}
