package org.example.model.CSV.implementation;

import org.example.model.CSV.AccessInterface;
import org.example.model.entities.Vagao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccessWagonCSV implements AccessInterface<Vagao> {

    private final String PATH;

    public AccessWagonCSV(String PATH) {
        this.PATH = PATH;
    }

    @Override
    public void create(Vagao vagao) {
        FileWriter fw = null;
        try {
            List<String> data = new ArrayList<>();
            data.add(String.valueOf(vagao.getId()));
            data.add(String.valueOf(vagao.getCapacidadeCarga()));

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
    public Vagao read(int id) {
        BufferedReader bw = null;
        try {
            bw = new BufferedReader(new FileReader(PATH));

            String line = bw.readLine();
            for (int x = 0; x <= id; x++) {
                if (x == id && line != null){
                    return instantiateVagao(line);
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
    public void update(Vagao vagao) {

    }

    @Override
    public void delete(Vagao vagao) {
        List<Vagao> data = readAll();
        clear();
        for(Vagao v : data){
            if (v.getId() != v.getId()){
                create(v);
            }
        }
    }

    @Override
    public List<Vagao> readAll() {
        BufferedReader bw = null;
        try {
            bw = new BufferedReader(new FileReader(PATH));
            List<Vagao> data = new ArrayList<>();

            String line = bw.readLine();
            while (line != null) {
                data.add(instantiateVagao(line));
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

    private Vagao instantiateVagao(String string){
        String[] data = string.split(",");
        int id = Integer.parseInt(data[0]);
        double cap = Double.parseDouble(data[1]);
        return new Vagao(id, cap);
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
