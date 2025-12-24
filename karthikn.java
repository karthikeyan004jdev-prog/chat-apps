
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class karthikn extends Frame implements Runnable, ActionListener{

    TextField textField;
    TextArea textArea;
    Button send;

    ServerSocket severskt;//Waits for client connection.
    Socket skt;//Used by both client and server to communicate.
    
    //Used to send and receive data
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    
    //Allow both sides to chat at the same time
    Thread chat;

    public karthikn() {
        textField=new TextField();
        textArea=new TextArea();
        send=new Button("Send");

        send.addActionListener(this);

        try {
            severskt=new ServerSocket(12000);
            System.out.println("Server started ,waiting for client");
            skt=severskt.accept();

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
        setTitle("KARHTIK");
        setLayout(new FlowLayout());
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
            String mgs=textField.getText();
            textArea.append("Karthik:"+mgs+"\n");
            textField.setText("");

        try { 
            dataOutputStream.writeUTF(mgs);
            dataOutputStream.flush();
        } catch (IOException ex) {
        }
        
    }

    public static void main(String[] args) {
        new karthikn();
        
    }
    public void run(){
        while(true){
            
            try {
                String mgs=dataInputStream.readUTF();
                textArea.append("Vimal:"+mgs+"\n");
            } catch (IOException ex) {
            }
            

        }
    }

}