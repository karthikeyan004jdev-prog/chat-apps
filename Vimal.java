
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Vimal extends Frame implements Runnable, ActionListener{

    TextField textField;
    TextArea textArea;
    Button send;

    Socket skt;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Thread chat;

    public Vimal() {
        textField=new TextField();
        textArea=new TextArea();
        send=new Button("Send");

        send.addActionListener(this);

        try {
           skt=new Socket("localhost",12000);

            dataInputStream =new DataInputStream(skt.getInputStream());
            dataOutputStream=new DataOutputStream(skt.getOutputStream());

        } catch (IOException ex) {
        }

        add(textField);
        add(textArea);
        add(send);
        
        chat=new Thread(this);
        chat.setDaemon(true);
        chat.start();

        

        setSize(500,500);
        setTitle("VIMAL");
        setLayout(new FlowLayout());
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
            String mgs=textField.getText();
            textArea.append("Vimal:"+mgs+"\n");
            textField.setText("");

        try { 
            dataOutputStream.writeUTF(mgs);
            dataOutputStream.flush();
        } catch (IOException ex) {
        }
        
    }

    public static void main(String[] args) {
        new Vimal();
        
    }
    public void run(){
        while(true){
            
            try {
                String mgs=dataInputStream.readUTF();
                textArea.append("karthik:"+mgs+"\n");
            } catch (IOException ex) {
            }
            

        }
    }

}