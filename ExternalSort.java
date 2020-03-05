import java.io.*;
import java.util.*;

public class ExternalSort{

	public static void main(String[] args){
		ExternalSort external = new ExternalSort();
		System.out.println(args[0] + "\n" + args[1]);
		external.externalSort(args[0], args[1], 172, 2);
	}

    public void externalSort(String inputFile, String outputFile, int n, int k){
    	int chunky_chunks = (int)Math.ceil(n/k);
    	int i = 0;
    	String line = null; //maybe change this to null?
    	double[] chunks;

    	try{
    		System.out.println("a"); //this is a test
    		FileReader fileReader = new FileReader(inputFile);
    		BufferedReader bufferReader = new BufferedReader(fileReader); //appends to buffer after input is read //CHECK THIS
    		for(i = 0; i < chunky_chunks; i++){
    			chunks = new double[k]; 

    			int j = 0;

    			while(j < k){
    				line = bufferReader.readLine(); //appends what was read to the line variable //CHECK THIS

    				if(line == null){
    					break;
    				}

    				else{
    					try{
    						double num = Double.parseDouble(line);
    						chunks[j] = num;
    						j++; 
    					}
    					catch (NumberFormatException e){
    						System.out.println("Error. Cannot convert the string into a double.");
    					}
    				}
    			}
    			//System.out.println("b");  //this is a test
    			sort(chunks);

    			try{
    				BufferedWriter tempFile = new BufferedWriter(new FileWriter("tempCHUNKO" + i + ".txt"));
    				for(int l = 0; l < k; l++){
    					tempFile.write(Double.toString(chunks[l]));

    					if(l == (k - 1)){
    						break;
    					}

    					tempFile.newLine();

    				}

    				tempFile.close();
    			}

    			catch (IOException e){
    				System.out.println("Error. Cannot write into the file.");
    			}
    		}

    		if((n % k) != 0){
    			chunks = new double[n%k];
    			for(int m = 0; m < (n%k); m++){
    				line = bufferReader.readLine();
    				try{
    					double num = Double.parseDouble(line);
    					chunks[m] = num;
    				}
    				catch (NumberFormatException e){
    					System.out.println("Error. Cannot write into the file.");
    				}
    				catch (NullPointerException e){
    					System.out.println("Error.");
    				}
    			}
    			sort(chunks);
    			BufferedWriter tempFile = new BufferedWriter(new FileWriter("tempCHUNKO" + i + ".txt"));

    			for(int o = 0; o < (n % k); o++){
    					tempFile.write(Double.toString(chunks[o]));
    					if(o == (n % k) - 1){
    						break;
    					}
    					tempFile.newLine();
    			}
    			tempFile.close();
    		}
   		bufferReader.close();
	   	}
	   	catch (FileNotFoundException e){
	   		//System.out.println("Error. Unable to open file.");
	   	}
	   	catch (IOException e){
	   		System.out.println("Error. Unable to read file.");
	   	}

	   	double nd = n*1.0;
	   	double kd = k*1.0;
	   	int anotherChunk= (int)Math.ceil(nd/kd);

	   	ArrayList<String> fileNames = new ArrayList<String>();
	   	for(int p = 0; p < anotherChunk; p++){
	   		fileNames.add("tempCHUNKO" + p + ".txt");
	   	}

	   	output(outputFile, n, k, fileNames);


   }

   private void output(String outputFile, int n, int k, ArrayList<String> fileNames) {
   		double nd = n*1.0;
   		double kd = k*1.0;
   		int chunks = (int)Math.ceil(nd/kd);
   		double[] merged = new double[2];
   		int left = fileNames.size();
   		int numFiles = fileNames.size();
   		String line1;
   		String line2;
   		int i = 0;

   		while(left > 1){ //the files that are left
   			try{
   				BufferedReader[] files = new BufferedReader[2]; //only reading 2 files at a time
   				for(int j = 0; j < numFiles; j += 2){
   					if(j+1 >= fileNames.size()){ //if there are an odd number of files
   						break;
   					}
   					BufferedWriter temp = new BufferedWriter(new FileWriter("tempCHUNKNO" + (chunks + i) + ".txt"));
   					BufferedReader temp2 = new BufferedReader(new FileReader(fileNames.get(0)));
   					BufferedReader temp3 = new BufferedReader(new FileReader(fileNames.get(1)));

   					for(int l = j; l < (j + 2); l++){
   						files[0] = temp2;
   						files[1] = temp3;

   						line1 = files[0].readLine();
   						line2 = files[1].readLine();

   						while(!(line1 == null && line2 == null)){
   							if(line1 == null){
   								while(line2 != null){
   									temp.write(Double.toString(Double.parseDouble(line2)) + "\n");
   									line2 = files[1].readLine();
   								}
   								break;
   							}

   							if(line2 == null){
   								while(line1 != null){
   									temp.write(Double.toString(Double.parseDouble(line1)) + "\n");
   									line1 = files[0].readLine();
   								}
   								break;
   							}
   							try{
   								merged[0] = Double.parseDouble(line1);
   								merged[1] = Double.parseDouble(line2);

   							}
   							catch (NullPointerException f){
   								continue;
   							}
   							if(merged[0] < merged[1]){
   								temp.write(Double.toString(merged[0])+"\n");
   								merged[0] = Double.parseDouble(line1);
   								line1 = files[0].readLine();
   							}
   							else{
   								temp.write(Double.toString(merged[1])+"\n");
   								merged[1] = Double.parseDouble(line2);
   								line2 = files[1].readLine();
   							}

   						}
   						
   					}
   					temp.close();
   					temp2.close();
   					temp3.close();
   					fileNames.add("tempCHUNKNO" + (chunks + i) + ".txt");

   					File delete = new File(fileNames.get(0));
   					File delete2 = new File(fileNames.get(1));

   					fileNames.remove(0);
   					fileNames.remove(0);

   					i++;
   				}

   				left = fileNames.size();
   			}

   			catch (FileNotFoundException f){
   				//System.out.println("Error. Unable to open file.");
   			}

  			catch (IOException f){
  				System.out.println("Error. Unable to read file.");
  			}
   		}
   		try{
   			BufferedReader temp2 = new BufferedReader(new FileReader(fileNames.get(0)));
   			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));

   			String temporary = temp2.readLine();

   			while(temporary != null){
   				out.write(temporary + "\n");
   				temporary = temp2.readLine();
   			}
   			temp2.close();
   			out.close();
   			File delete = new File(fileNames.get(0));
   		}
   		catch (FileNotFoundException f){
   			//System.out.println("Error. Unable to open file.");
   		}
   		catch (IOException f){
   			System.out.println("Error. Unable to read file.");
   		}

   }

   public static void sort(double[] a){
   		double[] left = leftArr(a);
   		double[] right = rightArr(a);

   		if(left.length > 1){
   			sort(left);
   		}

   		if(right.length > 1){
   			sort(right);
   		}

   		merge(left, right, a);

   }

   public static void merge(double[] left, double[] right, double[] target){
   		int l = 0;
   		int r = 0;
   		int t = 0;

   		while(l < left.length && r < right.length){
   			if(left[l] <= right[r]){
   				target[t++] = left[l++];
   			}
   			else{
   				target[t++] = right[r++];
   			}
   		}

   		while(l < left.length){
   			target[t++] = left[l++];
   		}

   		while(r < right.length){
   			target[t++] = right[r++];
   		}

   }

   public static double[] leftArr(double[] a){
   		double[] left = Arrays.copyOfRange(a, 0, (a.length/2));
   		return left;
   }

   public static double[] rightArr(double[] a){
   		double[] right = Arrays.copyOfRange(a, (a.length/2), a.length);
   		return right;
   }


}