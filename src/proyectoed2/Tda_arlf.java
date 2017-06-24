package proyectoed2;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

public class Tda_arlf {

    Registro registro;
    private File file;
    private RandomAccessFile buffer;
    //private final int sizeOfRecord = Integer.BYTES + Character.BYTES + 40 + Character.BYTES + 10 + Double.BYTES;
    private final int sizeOfHeader = Integer.BYTES;
    private LinkedList availList = new LinkedList();
    ArrayList<Registro> lista = new ArrayList();
    private int header;

    public Tda_arlf(File file) throws FileNotFoundException, IOException {
       this.file = file;
       buffer = new RandomAccessFile(file, "rw");//abierto para lectura
       buffer.seek(0);
       try{
           if(file.length() > 0){
               header = buffer.readInt();
               if(header != -1){
                   availList.add(header);
                   registro = new Registro();
                   buffer.seek(0 + sizeOfHeader);
                   while(true){
                       buffer.seek((header-1) * registro.sizeOfRecord() + sizeOfHeader);
                       buffer.readChar();
                       header = buffer.readInt();
                       if(header != - 1){
                           availList.add(0, header);
                       }
                   }
               }else{
                   buffer.writeInt(-1);
               }
           }
       }catch(Exception e){
           
       }
    }

    public File getFile() {
        return file;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public ArrayList<Registro> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Registro> lista) {
        this.lista = lista;
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

    public void closeBuffer() {
        try {
            buffer.close();
        } catch (Exception e) {

        }
    }

    boolean insertRegistro(Registro registro) throws IOException{
        boolean bool = false;
        try {
            buffer.seek(0 + sizeOfHeader);
            while (true) {
                if (availList.isEmpty()){//revisa si el availList esta vacio 
                    bool = true;
                    buffer.seek(file.length());
                    buffer.writeChar(registro.getAsterisco());
                    buffer.writeInt(0);
                    buffer.writeInt(registro.getId());//escribe los 4 bytes en el file
                    buffer.writeUTF(registro.getBirthdate());
                    buffer.writeUTF(registro.getName());
                    buffer.writeDouble(registro.getSalary());
                    break;
                } else {
                    buffer.seek(0);
                    bool = true;
                    int posicion = (int) availList.remove(0); //inserta en la primera posicion
                    int reference = 0;
                    buffer.seek(registro.sizeOfRecord() * (posicion - 1) + sizeOfHeader);
                    buffer.readChar();
                    reference = buffer.readInt();
                    buffer.seek(0);
                    buffer.writeInt(reference);
                    buffer.seek(registro.sizeOfRecord() * (posicion - 1) + sizeOfHeader);
                    buffer.writeChar(registro.getAsterisco());
                    buffer.writeInt(0);
                    buffer.writeInt(registro.getId());
                    buffer.writeUTF(registro.getBirthdate());
                    buffer.writeUTF(registro.getName());
                    buffer.writeDouble(registro.getSalary());
                    break;
                }
            }
        } catch (EOFException e) {

        }
        return bool;
    }

    void listarRegistro(){
        openBuffer();
        lista = new ArrayList();
        try {
            while (true) {
                registro.setName(buffer.readUTF());
                registro.setBirthdate(buffer.readUTF());
                registro.setId(buffer.readInt());
                registro.setSalary(buffer.readFloat());
                lista.add(registro);
                System.out.println(lista.get(lista.size() - 1).toString());
            }
        } catch (Exception e) {
        }
        closeBuffer();
    }

    public void openBuffer() {
        try {
            buffer = new RandomAccessFile(file, "rw");
        } catch (Exception e) {
            System.out.println("File not existant");
        }
    }

    public DefaultTableModel listarRegistros(DefaultTableModel model){
        try{
            while(model.getRowCount() > 0){
                model.removeRow(0);
            }
            buffer.seek(0 + sizeOfHeader);
            while(true){
                registro.setAsterisco(buffer.readChar());
                registro.setReferencia(buffer.readInt());
                registro.setName(buffer.readUTF());
                registro.setBirthdate(buffer.readUTF());
                registro.setId(buffer.readInt());
                registro.setSalary(buffer.readFloat());
                if(registro.getAsterisco() != '*'){
                    Object[]row = {registro.getName(), registro.getBirthdate(), registro.getId(), registro.getSalary()};
                    model.addRow(row);
                }
            }
        }catch(Exception e){
            
        }
        return model;
    }
}
