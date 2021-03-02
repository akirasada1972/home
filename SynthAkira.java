import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;

public class SynthAkira extends JFrame implements ChangeListener{

  JSlider slider1;
  JSlider slider2;
  JPanel p1;
  JPanel p2;
  JButton b;
  JLabel label1;
  JLabel label2;

  public static void main(String[] args){
    SynthAkira frame = new SynthAkira();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 600, 200);
    frame.setTitle("SYNTH AKIRA");
    frame.setVisible(true);
  }

  SynthAkira(){

    slider1 = new JSlider(0, 1000);
    slider1.setValue(100);
    slider1.addChangeListener(this);
    slider2 = new JSlider(0, 1000);
    slider2.setValue(100);
    slider2.addChangeListener(this);

    p1 = new JPanel();
    p1.add(slider1);
    p1.add(slider2);

    b = new JButton("PUSH");

    label1 = new JLabel();
    label2 = new JLabel();
    label1.setText("値：" + slider1.getValue());
    label2.setText("値：" + slider2.getValue());

    p2 = new JPanel();
    p2.add(label1);
    p2.add(label2);

    getContentPane().add(p1, BorderLayout.PAGE_START);
    getContentPane().add(b, BorderLayout.CENTER);
    getContentPane().add(p2, BorderLayout.PAGE_END);
    
    b.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      			setSound();
		  }
		});
	}

   void setSound(){
      try{
         byte[] wave_data= new byte[44100*2];
         double l1      = 44100.0/slider1.getValue();
         double l2      = 44100.0/slider2.getValue();
         for(int i=0;i<wave_data.length;i++){
            wave_data[i]= (byte)(110*Math.sin((i/l1)*Math.PI*2
            +110*Math.sin((i/l2)*Math.PI*2)));
            }
         AudioFormat   frmt= new AudioFormat(44100,8,1,true,false);
         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
         Clip          clip= (Clip)AudioSystem.getLine(info);
         clip.open(frmt,wave_data,0,wave_data.length);
         clip.start();

         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
         }
      catch(Exception e){e.printStackTrace(System.err);}
      }

	public void stateChanged(ChangeEvent e) {
    label1.setText("値：" + slider1.getValue());
    label2.setText("値：" + slider2.getValue());
 }
}