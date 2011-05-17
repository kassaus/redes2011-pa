package serverUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MetodosServidor {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	public static final String HOUR_FORMAT_NOW = "HH:mm:ss";
	public static final String DAY_FORMAT_NOW = "yyyy-MM-dd";

	static ArrayList<String> f = new ArrayList<String>(); //objecto arraylist

	/* metodo que carrega ficheiro de texto para um arraylist */
	public static boolean fileToArrayList(String fileName) {
		try{
			f.clear();
			FileReader file = new FileReader(fileName); //objecto ficheiro
			BufferedReader input = new BufferedReader(file);//objecto input
			String str; //string de linhas do ficheiro
			/* ciclo leitura ficheiro */ 	
			while ((str = input.readLine()) != null) {			
				f.add(str);//adicionar linha ao arraylist 
			}			
			input.close();//terminar o input
		}catch (Exception e){
			return false;
		}
		return true;
	}

	/* metodo que devolve uma frase aleatoria */
	public static String getFrase()                             
	{
		int i = (int)(Math.random() * f.size()) + 0;//gera aleatorio 'i'
		return f.get(i);//retorna frase na posicao 'i' 
	}

	/* metodo que devolve frase numa determinada posicao */
	public static String getFrasePosition(int i)                             
	{
		return f.get(i);//retorna frase na posicao 'i'
	}   

	/* metodo que retorna total frases */
	public static int getFraseTotal()                             
	{
		return f.size();//retorna tamanho do arraylist
	}   

	/* metodo que devolve data do sistema*/
	public static String datenow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	/* metodo que devolve hora do sistema*/
	public static String hournow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	/* metodo que devolve dia do sistema*/
	public static String daynow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

}



