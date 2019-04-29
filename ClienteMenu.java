import java.util.*;
import java.net.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class ClienteMenu implements Runnable {
	
	public void run() {
      
		try{
			String input="";
			Scanner scanIn = new Scanner(System.in);

			InetAddress ip = InetAddress.getByName("localhost");
			Socket menu = new Socket(ip, 1234);

      		DataOutputStream dos = new DataOutputStream(menu.getOutputStream());
			
			while (true){
			
				System.out.println("C - Create");
				System.out.println("R - Read");
				System.out.println("U - Update");
				System.out.println("D - Delete");
				System.out.println("X - eXit");
				System.out.println();
				
				
				input = scanIn.nextLine();  
			
				try{
					switch (input.toUpperCase()){
						case "C":

									System.out.println("Create\n");
									Scanner create = new Scanner(System.in);

									System.out.print("Chave: ");
									int chaveCreate = create.nextInt();
									System.out.print("Valor: ");
									String valorCreate = create.next();

									if(valorCreate.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}

									dos.writeInt(1); // 1 = "create"
									dos.writeInt(chaveCreate);
									dos.writeUTF(valorCreate);
									break;
						case "R":	
									System.out.println("Read\n");
									Scanner read = new Scanner(System.in);

									System.out.print("Chave: ");
									int chaveRead = read.nextInt();

									dos.writeInt(2); // 2 = "read"
									dos.writeInt(chaveRead);
									break;
						case "U":
									System.out.println("Update\n");
									Scanner update = new Scanner(System.in);

									System.out.print("Chave: ");
									int chaveUpdate= update.nextInt();
									
									System.out.print("Novo valor: ");
									String valorUpdate = update.next();

									if(valorUpdate.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}

									dos.writeInt(3); // 3 = "update"
									dos.writeInt(chaveUpdate);
									dos.writeUTF(valorUpdate);
									break;
						case "D":
									System.out.println("Delete\n");
									Scanner delete = new Scanner(System.in);

									System.out.print("Chave: ");
									int chaveDeleite = delete.nextInt();

									dos.writeInt(4); // 4 = "delete"
									dos.writeInt(chaveDeleite);
									break;
						case "X": 
									scanIn.close(); 
									menu.close(); 
									return;
						default: 
									System.out.println("opcao inválida"); break;
						
					}
				}catch (InputMismatchException e){
					System.out.println("Formato de chave inválido");
					System.out.println("Operação não realizada");
				} // tratamento de chave inválida
				  // continua dentro do loop
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
  }
	
	public static void main (String args[]) {
			Thread t1 = new Thread(new ClienteMenu(), "threadMenu");
			t1.start(); // inicia thread do menu
			Thread t2 = new Thread(new ClienteListener(t1), "threadListener");
			t2.start(); // inicia thread do listener
	}
}