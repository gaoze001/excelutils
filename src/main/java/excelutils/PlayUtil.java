package excelutils;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PlayUtil {
    File file = null;
    AudioInputStream audio = null;
    AudioInputStream stream = null;
    SourceDataLine line = null;
    SourceDataLine data = null;

    /**
     * Java Music ���� flac
     *
     * @param path flac�ļ�·��
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: play_flac
     * @Description: ���� flac
     * @date 2019��10��25�� ����12:28:41
     */
    public void playFlac(String path) throws UnsupportedAudioFileException, IOException {
        file = new File(path);
        if (!file.exists() || !path.toLowerCase().endsWith(".flac")) {
            return;
        }
        audio = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audio.getFormat();
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);
            audio = AudioSystem.getAudioInputStream(format, audio);
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, AudioSystem.NOT_SPECIFIED);
        data = null;
        try {
            data = (SourceDataLine) AudioSystem.getLine(info);
            data.open(format);
            data.start();
            byte[] bt = new byte[1024];
            while (audio.read(bt) != -1) {
                data.write(bt, 0, bt.length);
            }
            data.drain();
            data.stop();
            data.close();
            audio.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music ���� wav
     *
     * @param path wav �ļ�·��
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: play_wav
     * @Description: ���� wav
     * @date 2019��10��25�� ����12:28:41
     */
    public void playWav(String path) throws UnsupportedAudioFileException, IOException {
        file = new File(path);
        if (!file.exists() || !path.toLowerCase().endsWith(".wav")) {
            throw new RuntimeException("�ļ�������");
        }
        audio = AudioSystem.getAudioInputStream(file);
        AudioFormat target = audio.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target);
        data = null;
        int len = -1;
        try {
            data = (SourceDataLine) AudioSystem.getLine(dinfo);
            data.open(target);
            data.start();
            byte[] buffer = new byte[1024];
            while ((len = audio.read(buffer)) > 0) {
                data.write(buffer, 0, len);
            }
            data.drain();
            data.stop();
            data.close();
            audio.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music ���� pcm
     *
     * @param path pcm�ļ�·��
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: play_pcm
     * @Description: ���� pcm
     * @date 2019��10��25�� ����12:28:41
     */
    public void playPcm(String path) throws UnsupportedAudioFileException, IOException {
        file = new File(path);
        if (!file.exists() || !path.toLowerCase().endsWith(".pcm")) {
            throw new RuntimeException("�ļ�������");
        }
        stream = AudioSystem.getAudioInputStream(file);
        AudioFormat target = stream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target, AudioSystem.NOT_SPECIFIED);
        line = null;
        int len = -1;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(target);
            line.start();
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                line.write(buffer, 0, len);
            }
            line.drain();
            line.stop();
            line.close();
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music ���� mp3
     *
     * @param url mp3�ļ�·��
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: play_mp3
     * @Description: ���� mp3
     * @date 2019��10��25�� ����12:28:41
     */
    public void playMp3(URL url) throws UnsupportedAudioFileException, IOException {
//        file = new File(path);
//        if (!file.exists() || !path.toLowerCase().endsWith(".mp3")) {
//            throw new RuntimeException("�ļ�������");
//        }
        stream = null;
        //ʹ�� mp3spi ���� mp3 ��Ƶ�ļ�
        MpegAudioFileReader mp = new MpegAudioFileReader();
        stream = mp.getAudioInputStream(url);
        AudioFormat baseFormat = stream.getFormat();
        //�趨�����ʽΪpcm��ʽ����Ƶ�ļ�
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
        // �������Ƶ
        stream = AudioSystem.getAudioInputStream(format, stream);
        AudioFormat target = stream.getFormat();
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target, AudioSystem.NOT_SPECIFIED);
        line = null;
        int len = -1;
        try {
            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(target);
            line.start();
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                line.write(buffer, 0, len);
            }
            line.drain();
            line.stop();
            line.close();
        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
        } finally {
            if(null!=stream)
                stream.close();
        }
    }

    /**
     * Java Music ��ȡpcm�ļ�
     *
     * @param path pcm�ļ�·��
     * @return AudioInputStream
     * @Title: read_pcm
     * @Description: ��ȡpcm�ļ�
     * @date 2019��10��25�� ����12:28:41
     */
    public AudioInputStream readPcm(String path) throws UnsupportedAudioFileException, IOException {
        file = new File(path);
        if (!file.exists()) {
            return null;
        }
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = stream.getFormat();
        System.out.println(format.toString());
        return stream;
    }

    /**
     * Java Music ��ȡ mp3 ����������
     *
     * @param rpath mp3�ļ�·��
     * @param spath pcm�ļ�����·��
     * @return AudioInputStream
     * @Title: get_pcm_from_mp3
     * @Description: ��ȡ mp3 ����������
     * @date 2019��10��25�� ����12:28:41
     */
    public void getPcmFromMp3(String rpath, String spath) {
        file = new File(rpath);
        if (!file.exists()) {
            return;
        }
        stream = null;
        AudioFormat format = null;
        try {
            AudioInputStream in = null;
            //��ȡ��Ƶ�ļ�����
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(file);
            AudioFormat baseFormat = in.getFormat();
            //�趨�����ʽΪpcm��ʽ����Ƶ�ļ�
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            //�������Ƶ
            stream = AudioSystem.getAudioInputStream(format, in);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(spath));
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music mp3 ת pcm
     *
     * @param rpath MP3�ļ�·��
     * @param spath PCM�ļ�����·��
     * @return AudioInputStream
     * @Title: mp3_to_pcm
     * @Description: MP3 PCM
     * @date 2019��10��25�� ����12:28:41
     */
    public void mp3ToPcm(String rpath, String spath) {
        file = new File(rpath);
        if (!file.exists()) {
            return;
        }
        stream = null;
        AudioFormat format = null;
        try {
            AudioInputStream in = null;
            //��ȡ��Ƶ�ļ�����
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(file);
            AudioFormat baseFormat = in.getFormat();
            //�趨�����ʽΪpcm��ʽ����Ƶ�ļ�
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            //�������Ƶ
            stream = AudioSystem.getAudioInputStream(format, in);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(spath));
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music wavתpcm
     *
     * @param wpath wav�ļ�·��
     * @param ppath pcm�ļ�����·��
     * @return AudioInputStream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: wav_to_pcm
     * @Description: wavתpcm
     * @date 2019��10��25�� ����12:28:41
     */
    public void wavToPcm(String wpath, String ppath) {
        file = new File(wpath);
        if (!file.exists()) {
            throw new RuntimeException("�ļ�������");
        }
        AudioInputStream stream1;
        try {
            stream1 = AudioSystem.getAudioInputStream(file);
            // ����ʵ������޸� AudioFormat.Encoding.PCM_SIGNED
            AudioInputStream stream2 = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, stream1);
            AudioSystem.write(stream2, AudioFileFormat.Type.WAVE, new File(ppath));
            stream2.close();
            stream1.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java Music PCMתWAV
     *
     * @param wpath WAV�ļ�·��
     * @param ppath PCM�ļ�����·��
     * @return AudioInputStream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @Title: pcm_to_wav
     * @Description: PCMתWAV
     * @date 2019��10��25�� ����12:28:41
     */
    public void pcmToWav(String ppath, String wpath) {

    }

    /**
     * Java Music ��ȡwav����pcm�ļ��ı�����Ϣ
     *
     * @param path wav����pcm�ļ�·��
     * @return wav����pcm�ļ��ı�����Ϣ
     * @Title: get_info
     * @Description: ��ȡwav����pcm�ļ��ı�����Ϣ
     * @date 2019��10��25�� ����12:28:41
     */
    public String getInfo(String path) {
        File file = new File(path);
        AudioInputStream ais;
        String result = "";
        try {
            ais = AudioSystem.getAudioInputStream(file);
            AudioFormat af = ais.getFormat();
            result = af.toString();
            System.out.println(result);
            System.out.println("��Ƶ��֡����" + ais.getFrameLength());
            System.out.println("ÿ�벥��֡����" + af.getSampleRate());
            float len = ais.getFrameLength() / af.getSampleRate();
            System.out.println("��Ƶʱ�����룩��" + len);
            System.out.println("��Ƶʱ����" + (int) len / 60 + "��" + len % 60 + "��");
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public void closeAll() {
        if (null != data) {
            try {
                data.close();
            } catch (Exception e) {
                data.close();
                data = null;
            } finally {
                data = null;
            }
        }
        if (null != line) {
            try {
                line.close();
            } catch (Exception e) {
                line.close();
                line = null;
            } finally {
                line = null;
            }
        }
        if (null != audio) {
            try {
                audio.close();
            } catch (Exception e) {
                audio = null;
            } finally {
                audio = null;
            }
        }
        if (null != stream) {
            try {
                stream.close();
            } catch (Exception e) {
                stream = null;
            } finally {
                stream = null;
            }
        }
    }
//    /**
//     * Java Music ��ȡmp3�ļ���ͼƬ
//     * @Title: get_image_from_mp3
//     * @Description: ��ȡmp3�ļ���ͼƬ
//     * @param mpath mp3flac�ļ�·��
//     * @date 2019��10��25�� ����12:28:41
//     *
//     */
//    public void get_image_from_mp3(String mpath) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
//        File sourceFile = new File(mpath);
//        MP3File mp3file = new MP3File(sourceFile);
//        AbstractID3v2Tag tag = mp3file.getID3v2Tag();
//        AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
//        FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
//        byte[] image = body.getImageData();
//        Image img=Toolkit.getDefaultToolkit().createImage(image, 0,image.length);
//        ImageIcon icon = new ImageIcon(img);
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\�κ� - �Ӽ��˳�.jpg");
//        fos.write(image);
//        fos.close();
//        System.out.println("width:"+icon.getIconWidth());
//        System.out.println("height:"+icon.getIconHeight());
//    }
}
