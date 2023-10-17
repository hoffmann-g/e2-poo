package org.example.model.CSV.implementation;

import org.example.model.CSV.AccessInterface;
import org.example.model.entities.Locomotiva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccessLocomotiveCSV implements AccessInterface<Locomotiva> {

    private final String PATH;

    public AccessLocomotiveCSV(String PATH) {
        this.PATH = PATH;
    }

    @Override
    public void create(Locomotiva locomotive) {
        FileWriter fw = null;
        try {
            List<String> data = new ArrayList<>();
            data.add(String.valueOf(locomotive.getId()));
            data.add(String.valueOf(locomotive.getLimiteVagoes()));

            fw = new FileWriter(PATH, true);

            for(int x =0 ; x < data.size(); x++){
                fw.append(data.get(x));
                if(x != data.size() - 1){
                    fw.append(",");
                }
            }
            fw.append("\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeFW(fw);
            System.out.println("bw closed");
        }
    }

    @Override
    public Locomotiva read(int id) {
        BufferedReader bw = null;
        try {
            bw = new BufferedReader(new FileReader(PATH));

            String line = bw.readLine();
            for (int x = 0; x <= id; x++) {
                if (x == id && line != null){
                    return instantiateLocomotiva(line);
                } else {
                    line = bw.readLine();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBW(bw);
        }
        return null;
    }

    @Override
    public void update(Locomotiva locomotive) {

    }

    @Override
    public void delete(Locomotiva locomotive) {
        List<Locomotiva> data = readAll();
        clear();
        for(Locomotiva l : data){
            if (l.getId() != locomotive.getId()){
                create(l);
            }
        }
    }

    @Override
    public List<Locomotiva> readAll() {
        BufferedReader bw = null;
        try {
            bw = new BufferedReader(new FileReader(PATH));
            List<Locomotiva> data = new ArrayList<>();
            String line = bw.readLine();
            while (line != null) {
                data.add(instantiateLocomotiva(line));
                line = bw.readLine();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBW(bw);
        }
        return null;
    }

    private void closeFW(FileWriter fileWriter){
        try {
            if (fileWriter != null){
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeBW(BufferedReader bufferedReader){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Locomotiva instantiateLocomotiva(String string){
        String[] data = string.split(",");
        int id = Integer.parseInt(data[0]);
        int cap = Integer.parseInt(data[1]);
        return new Locomotiva(id, cap);
    }

    private void clear() {
        try {
            FileWriter writer = new FileWriter(PATH);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
