package org.example.model.entities;

import org.example.model.CSV.AccessInterface;
import org.example.model.CSV.implementation.AccessLocomotiveCSV;
import org.example.model.CSV.implementation.AccessWagonCSV;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Garagem {

    private List<Locomotiva> garagemLocomotivas = new ArrayList<>();
    private List<Vagao> garagemVagoes = new ArrayList<>();
    private List<Trem> garagemTrens = new ArrayList<>();

    private AccessInterface<Vagao> aiv;
    private AccessInterface<Locomotiva> ail;

    public Garagem(String PATH){
        aiv = new AccessWagonCSV(PATH + "WagonGarage.csv");
        ail = new AccessLocomotiveCSV(PATH + "LocomotiveGarage.csv");

        registerCars();
    }

    /*
     * Os seguidos 3 metodos sao responsaveis por adicionar os objetos informados
     * nas respectivas 'garagens' utilizando 'add' do List
     */
    public void cadastrarLocomotiva(int id, double pesoMaximo) {
        garagemLocomotivas.add(new Locomotiva(id, pesoMaximo));
    }

    public void cadastrarVagao(int id, double capacidadeCarga) {
        garagemVagoes.add(new Vagao(id, capacidadeCarga));
    }

    public void cadastrarTrem(int id) throws InvalidParameterException {
        for (Trem t : garagemTrens) {
            if (t.getId() == id) {
                throw new InvalidParameterException("O ID inserido pertence a outro trem já cadastrado");
            }
        }
        garagemTrens.add(new Trem(id));
    }

    /*
     * Checa se o trem entrado como parametro possui algum vagao. Se possuir, lanca
     * uma Exception,
     * pois nao se deve alocar uma locomotiva atras de um vagao
     * Se nao :
     * - aloca o trem na variavel de 'tremAlocado' da locomotiva
     * - em seguida, adiciona a locomotiva na lista de locomotivas do trem
     * - por fim, remove a locomotiva passada como parametro da garagem de
     * locomotivas nao usadas
     */
    public void alocarLocomotiva(Locomotiva locomotiva, Trem trem) throws InvalidParameterException {
        if (garagemLocomotivas.isEmpty()){
            throw new InvalidParameterException("A garagem de locomotivas está vazia");
        } else if (!garagemLocomotivas.get(garagemLocomotivas.size()-1).equals(locomotiva)){
            throw new InvalidParameterException("A locomotiva informada não está acessível. Apenas a última unidade da garagem pode ser retirada");
        } else {
            if (!trem.getListaVagoes().isEmpty()) {
                throw new InvalidParameterException(
                        "Nao é possível alocar locomotivas a unidades com vagões já acoplados.");
            }
            locomotiva.setTremAlocado(trem);
            trem.addLocomotiva(locomotiva);
            ail.delete(locomotiva);
            garagemLocomotivas.remove(locomotiva);
        }
    }

    /*
     * Checa se o trem entrado como parametro possui pelo menos uma locomotiva. Se
     * possuir, lanca uma Exception,
     * pois nao se deve alocar um vagao a um trem sem locomotiva:
     * Se nao :
     * - aloca o trem na variavel de 'tremAlocado' do vagao
     * - em seguida, adiciona o vagao na lista de vagoes do trem
     * - por fim, remove o vagao passada como parametro da garagem de vagoes nao
     * usadas
     */
    public void alocarVagao(Vagao vagao, Trem trem) throws InvalidParameterException {
        if (garagemVagoes.isEmpty()){
            throw new InvalidParameterException("A garagem de vagões está vazia");
        } else if (!garagemVagoes.get(garagemVagoes.size()-1).equals(vagao)){
            throw new InvalidParameterException("O vagão informado não está acessível. Apenas a última unidade da garagem pode ser retirada");
        } else {
            if (trem.getListaLocomotivas().isEmpty()) {
                throw new InvalidParameterException("Não é possível alocar vagões a unidades sem locomotivas acopladas.");
            }
            vagao.setTremAlocado(trem);
            trem.addVagao(vagao);
            aiv.delete(vagao);
            garagemVagoes.remove(vagao);
        }
    }

    /*
     * Checa se a lista de a lista de vagoes possui algum elemento. Se possuir,
     * lanca uma Exception, pois
     * nao se deve desacoplar locomotivas sem antes desacoplar todos os vagoes
     * Se nao:
     * - adiciona na 'garagemLocomotivas' o ultimo elemento da lisa de locomotivas
     * do trem
     * - em seguida, remove o ultimo elemento da lista de locomotivas do trem;
     */
    public void desacoplarLocomotiva(Trem trem) throws InvalidParameterException {
        if (!trem.getListaVagoes().isEmpty()) {
            throw new InvalidParameterException(
                    "Não é possível desacoplar locomotivas sem antes desacoplar todos os vagões.");
        }
        garagemLocomotivas.add(trem.getListaLocomotivas().get(trem.getListaLocomotivas().size() - 1));
        ail.create(trem.getListaLocomotivas().get(trem.getListaLocomotivas().size() - 1));
        trem.getListaLocomotivas().remove(trem.getListaLocomotivas().size() - 1);
    }

    /*
     * - adiciona na 'garagemVagoes' o ultimo elemento da lisa de vagoes do trem
     * - em seguida, remove o ultimo elemento da lista de vagoes do trem;
     */
    public void desacoplarVagao(Trem trem) {
        garagemVagoes.add(trem.getListaVagoes().get(trem.getListaVagoes().size() - 1));
        aiv.create(trem.getListaVagoes().get(trem.getListaVagoes().size() - 1));
        trem.getListaVagoes().remove(trem.getListaVagoes().size() - 1);
    }

    /*
     * Percorre a lista de locomotivas e retorna o objeto 'Locomotiva' com o mesmo
     * ID,
     * se nao encontrar lanca uma Exception
     */
    public Locomotiva getLocomotiva(int id) throws InvalidParameterException {
        for (Locomotiva l : garagemLocomotivas) {
            if (l.getId() == id)
                return l;
        }
        throw new InvalidParameterException("Locomotiva não encontrada.");
    }

    /*
     * Percorre a lista de vagoes e retorna o objeto 'Vagao' com o mesmo ID,
     * se nao encontrar lanca uma Exception
     */
    public Vagao getVagao(int id) throws InvalidParameterException {
        for (Vagao v : garagemVagoes) {
            if (v.getId() == id)
                return v;
        }
        throw new InvalidParameterException("Vagão não encontrado.");
    }

    /*
     * Percorre a lista de trens e retorna o objeto 'Trem' com o mesmo ID,
     * se nao encontrar lanca uma Exception
     */
    public Trem getTrem(int id) throws InvalidParameterException {
        for (Trem t : garagemTrens) {
            if (t.getId() == id)
                return t;
        }
        throw new InvalidParameterException("Trem não encontrado.");
    }

    /*
     * Percorre a lista de trens e compara com o trem informado no parametro,
     * armazena em uma lista de String as informacoes (em formato de String) do trem
     * e retorna essa lista de informacoes;
     */
    @Deprecated
    public List<String> inspecionarTrem(Trem trem) {
        List<String> info = new ArrayList<>();
        for (Trem t : garagemTrens) {
            if (t.equals(trem)) {
                info.add(String.valueOf(t.getId()));
                info.add(String.valueOf(t.getListaVagoes()));
                info.add(String.valueOf(t.getListaLocomotivas()));
            }
        }
        return info;
    }

    /*
     * Percorre a lista de Trens, para cada trem encontrado, percorre a lista de
     * locomotivas tentando
     * encontrar a locomotiva com o ID informado no parametro, entao retorna a ID do
     * trem que essa
     * locomotiva esta acoplada.
     * Se nao encontrar, procura na lista de locomotivas. Caso encontre, lança uma
     * Exception informando
     * que a unidade nao esta alocada a um trem, mas sim esta na garagem de
     * locomotivas nao utilizadas.
     * Se nao encontrar na garagem de locomotivas, lanca uma Exception informando
     * que a unidade informada
     * nao pode ser encontrada.
     * Nota: implementar um algoritimo mais eficiente e reduzir o uso de 'for';
     */
    public int inspecionarLocomotiva(int id) throws InvalidParameterException {
        for (Trem t : garagemTrens) {
            for (Locomotiva l : t.getListaLocomotivas()) {
                if (l.getId() == id) {
                    return l.getTremAlocado().getId();
                }
            }
        }
        for (Locomotiva l : garagemLocomotivas) {
            if (l.getId() == id) {
                throw new InvalidParameterException("Unidade não alocada a nenhum trem");
            }
        }
        throw new InvalidParameterException("Unidade não encontrada");
    }

    /*
     * Percorre a lista de Trens, para cada trem encontrado, percorre a lista de
     * vagoes tentando
     * encontrar o vagao com o ID informado no parametro, entao retorna a ID do trem
     * que esse vagao
     * esta acoplado.
     * Se nao encontrar, procura na lista de vagoes. Caso encontre, lança uma
     * Exception informando
     * que a unidade nao esta alocada a um trem, mas sim esta na garagem de vagoes
     * nao utilizadas.
     * Se nao encontrar na garagem de vagoes, lanca uma Exception informando que a
     * unidade informada
     * nao pode ser encontrada.
     * Nota: implementar um algoritimo mais eficiente e reduzir o uso de 'for';
     */
    public int inspecionarVagao(int id) throws InvalidParameterException {
        for (Trem t : garagemTrens) {
            for (Vagao v : t.getListaVagoes()) {
                if (v.getId() == id) {
                    return v.getTremAlocado().getId();
                }
            }
        }
        for (Vagao v : garagemVagoes) {
            if (v.getId() == id) {
                throw new InvalidParameterException("Unidade não alocada a nenhum trem");
            }
        }
        throw new InvalidParameterException("Unidade não encontrada");
    }

    /*
     * - adiciona na garagem de vagoes todos os vagoes do trem informado;
     * - adiciona na garagem de locomotivas todas as locomotivas do trem informado;
     * - limpa a lista de vagoes e a lista de locomotivas do trem;
     * - remove o trem da garagem de trens;
     */
    public void desfazerTrem(Trem trem) throws InvalidParameterException {
        garagemVagoes.addAll(trem.getListaVagoes());
        garagemLocomotivas.addAll(trem.getListaLocomotivas());
        trem.getListaVagoes().clear();
        trem.getListaLocomotivas().clear();
        garagemTrens.remove(trem);
    }

    @Override
    public String toString() {
        String text = "    GARAGENS: "       +
                "\n Garagem de Locomotivas: "+
                "\n " + garagemLocomotivas   +
                "\n "                        +
                "\n Garagem de Vagoes: "     +
                "\n " + garagemVagoes        +
                "\n "                        +
                "\n Garagem de Trens: ";

        if (garagemTrens.isEmpty()) {
            return text + "<vazia>";
        } else {
            String tremList = "\n";
            for (Trem t : garagemTrens){
                tremList = tremList.concat(t.toString() + "\n");
            }
            return text + tremList;
        }
    }

    private void registerCars(){
        for (Vagao v : aiv.readAll()) {
            cadastrarVagao(v.getId(), v.getCapacidadeCarga());
        }

        for (Locomotiva l : ail.readAll()) {
            cadastrarLocomotiva(l.getId(), l.getLimiteVagoes());
        }
    }
}
