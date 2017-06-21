
package proyectoed2;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class Tda_arlf {
    Registro registro;
    private File file;
    private RandomAccessFile buffer;
    private final int sizeOfRecord = Integer.BYTES + Character.BYTES+40 + Character.BYTES+10 + Double.BYTES;
    private final int sizeOfHeader = Integer.BYTES;
    private LinkedList availList = new LinkedList();
    
    public Tda_arlf(File file){
        this.file = file;
        try{
            buffer = new RandomAccessFile(file,"rw");//abrir el archivo para lectura
        }catch(Exception e){
            System.out.println("El archivo no existe!");
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RandomAccessFile getBuffer() {
        return buffer;
    }

    public void setBuffer(RandomAccessFile buffer) {
        this.buffer = buffer;
    }

    public LinkedList getAvailList() {
        return availList;
    }

    public void setAvailList(LinkedList availList) {
        this.availList = availList;
    }
    
    public void closeBuffer(){
        try{
            buffer.close();
        }catch (Exception e){
            
        }
    }

boolean insertRegistro(Registro registro)throws IOException{
    try{
        while(true){
            if(availList.isEmpty()){//revisa si el availList esta vacio 
                buffer.seek(file.length());
                buffer.writeInt(registro.getId());//escribe los 4 bytes en el file
                buffer.writeUTF(registro.getBirthdate());
                buffer.writeUTF(registro.getName());
                buffer.writeDouble(registro.getSalary());
                return true;
            }else{
                int posicion = (int)availList.remove(0); //inserta en la primera posicion
                buffer.seek(sizeOfRecord*(posicion-1));
                buffer.writeInt(registro.getId());
                buffer.writeUTF(registro.getBirthdate());
                buffer.writeUTF(registro.getName());
                buffer.writeDouble(registro.getSalary());
                return true;
            }   
        }
    }catch(EOFException e){
        
    }
    return false;
}

    void listarRegistro(){
        try{
            while(true){
                registro.setBirthdate(buffer.readUTF());
                registro.setName(buffer.readUTF());
                registro.setId(buffer.readInt());
                registro.setSalary(buffer.readDouble());
            }
        }catch(Exception e){
        }
    }
}
