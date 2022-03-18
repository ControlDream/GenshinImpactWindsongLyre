package kongmeng.windsonglyre;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public abstract class MIDI {
	
	public MIDI(String str) throws InvalidMidiDataException, IOException{
		Player(str);
	}
	
	static long ttt = 0;

	public abstract void a(int iii, byte[] message, String hex);

	public void Player(String str) throws InvalidMidiDataException, IOException {
		File sound = new File(str);
		Sequence seq = MidiSystem.getSequence(sound);

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
						MidiEvent midievent = track.get(i);
						MidiMessage midimessage = midievent.getMessage();
						byte[] message = midimessage.getMessage();
						String hex = bytesToHexString(message);
						try {
							Thread.sleep((midievent.getTick() - lastsleep) * ttt / 1000);
							lastsleep = midievent.getTick();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (hex.startsWith("FF 51")) {
							byte[] a = { 0x00, message[3], message[4], message[5] };
							ttt = bytesToInt2(a, 0) / t;
							System.out.println("音轨" + iii + " " + "指定速度" + hex + "__" + bytesToInt2(a, 0));
						} else if (hex.startsWith("A")) {
//							System.out.println("音轨" + iii + " " + "触后音符" + bytesToHexString(message));
						} else if (hex.startsWith("B")) {
//							System.out.println("音轨" + iii + " " + "控制器变化" + bytesToHexString(message));
						} else if (hex.startsWith("C")) {
							int type = (int) (message[1] & 0xFF);
							System.out.println("音轨" + iii + " " + "改变乐器" + bytesToHexString(message) + "____" + type);
						} else if (hex.startsWith("D")) {
//							System.out.println("音轨" + iii + " " + "通道触动压力" + bytesToHexString(message));
						} else if (hex.startsWith("E")) {
//							System.out.println("音轨" + iii + " " + "弯音轮变换" + bytesToHexString(message));
						} else if (hex.startsWith("FF 03")) {
							System.out.println("音轨" + iii + "=>" + new String(hexStringToByteArray(hex.substring(9).replace(" ", ""))));
						} else if (hex.startsWith("FF 04")) {
							System.out.println("音轨" + iii + " " + "指定乐器=>" + hex);
						} else {
//							System.out.println("音轨" + iii + " " + bytesToHexString(message));
						}
						a(iii, message, hex);
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
}
